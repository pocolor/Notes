package com.pocolor.notes.notefile;

import com.pocolor.notes.notefile.header.DocumentVersion;
import com.pocolor.notes.notefile.header.Header;
import com.pocolor.notes.notefile.objects.*;
import com.pocolor.notes.notefile.parsers.other.*;
import com.pocolor.notes.notefile.trailer.Dependencies;
import com.pocolor.notes.notefile.trailer.Trailer;
import com.pocolor.notes.notefile.writer.NoteFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Represents a complete note file with its header, content objects, and metadata.
 *
 * <p><b>File Structure:</b></p>
 * <ul>
 *   <li><b>Note Mark:</b> 4-byte signature "NOTE" identifying the file type</li>
 *   <li><b>Header:</b> Document version and offset information</li>
 *   <li><b>Master XRef Header:</b> Metadata for the cross-reference table</li>
 *   <li><b>Objects:</b> Variable-length content objects (Text, etc.)</li>
 *   <li><b>Master XRef:</b> Cross-reference table for object locations</li>
 *   <li><b>Trailer:</b> Dependency information and metadata</li>
 * </ul>
 *
 * <p><b>Supported Object Types:</b></p>
 * <ul>
 *   <li>{@link Text} - Text content objects</li>
 *   <li>{@link XRef} - Cross-reference tables</li>
 * </ul>
 */
public class NoteFile {
    private static final HashMap<String, Class<? extends NFObject>> OBJECTS = new HashMap<>();

    static {
        OBJECTS.put(XRef.class.getSimpleName(), XRef.class);
        OBJECTS.put(Text.class.getSimpleName(), Text.class);
    }

    private Header header;
    private NFOHeader masterXRefHeader;
    private XRef masterXRef;
    private Trailer trailer;

    private long masterXRefOffset;
    private long trailerOffset;

    private final ArrayList<Class<? extends NFObject>> objectIDs;
    private final LinkedList<XRef> cachedXRefs;

    private final NoteFileWriter writer;

    public NoteFile(java.io.File file) throws UnmarkedNoteFileException, MalformedNoteFileException {
        this.writer = new NoteFileWriter(file);

        throwIfNotMarked();

        this.objectIDs = new ArrayList<>();
        this.cachedXRefs = new LinkedList<>();

        try {
            loadHeader();
            this.masterXRefOffset = this.header.getMasterXRefOffset();
            this.trailerOffset = this.header.getTrailerOffset();

            loadMasterXRefHeader();
            loadMasterXRef();
            loadTrailer();

            loadObjectIDs();
            cacheAllXRefs();
        } catch (Exception e) {
            throw new MalformedNoteFileException(e);
        }
    }

    private NoteFile(
            Header header,
            NFOHeader masterXRefHeader,
            XRef masterXRef,
            Trailer trailer,
            long masterXRefOffset,
            long trailerOffset,
            NoteFileWriter writer
    ) {
        this.writer = writer;
        this.header = header;
        this.masterXRefHeader = masterXRefHeader;
        this.masterXRef = masterXRef;
        this.trailer = trailer;
        this.masterXRefOffset = masterXRefOffset;
        this.trailerOffset = trailerOffset;

        this.objectIDs = new ArrayList<>();
        this.cachedXRefs = new LinkedList<>();
    }

    public void addObject(NFObject object) {
        long offset = findOffset(object.getByteSize());

        if (!this.objectIDs.contains(object.getClass())) {
            this.objectIDs.add(object.getClass());
            this.objectIDs.trimToSize();
            this.trailer.dependencies().addObject(object.getClass().getSimpleName());
        }

        NFOHeader nfoHeader = new NFOHeader(
                (byte) this.objectIDs.indexOf(object.getClass()),
                object.getByteSize(),
                object.getBitsUsedInLastByte()
        );

        XRef.XRefEntry[] entries = new XRef.XRefEntry[this.masterXRef.getEntries().size() + 1];
        System.arraycopy(this.masterXRef.getEntries().toArray(), 0, entries, 0, this.masterXRef.getEntries().size());
        entries[entries.length - 1] = new XRef.XRefEntry(offset, nfoHeader);

        XRef xRef = new XRef(entries);

        this.cachedXRefs.remove(this.masterXRef);
        this.cachedXRefs.add(xRef);
        this.masterXRef = xRef;
        this.masterXRefHeader = new NFOHeader(
                this.masterXRefHeader.objectID(),
                xRef.getByteSize(),
                xRef.getBitsUsedInLastByte()
        );

        // append it at the end of file
        this.writer.writeObject(offset, object);
    }

    public NFObject getObject(XRef.XRefEntry xRefEntry) {
        Class<? extends NFObject> type = this.objectIDs.get(xRefEntry.header().objectID());
        byte[] data = this.writer.readBytesAndFlush(xRefEntry.offset(), xRefEntry.header().bytesOccupied());

        return NoteFileObjectParser.parseObject(type, data);
    }

    @SuppressWarnings("unchecked")
    public <T extends NFObject> T[] getAllObjectsOfType(Class<T> type) {
        ArrayList<byte[]> objectsData = new ArrayList<>();

        for (XRef.XRefEntry entry : this.masterXRef) {
            if (this.objectIDs.get(entry.header().objectID()) == type) {
                objectsData.add(this.writer.readBytes(entry.offset(), entry.header().bytesOccupied()));
            }
        }
        this.writer.flush();

        return objectsData.stream()
                .map(e -> (T) NoteFileObjectParser.parseObject(type, e))
                .toArray(size -> (T[]) java.lang.reflect.Array.newInstance(type, size));
    }

    public void save() {
        this.header = new Header(
                this.header.getDocumentVersion(),
                this.masterXRefOffset,
                this.trailerOffset
        );

//        this.writer.writeBytes(0L, NoteMark.NOTE);
        this.writer.writeBytes(NoteMark.FIXED_BYTE_SIZE, this.header.serialize());
        this.writer.writeBytes(NoteMark.FIXED_BYTE_SIZE + Header.FIXED_BYTE_SIZE, this.masterXRefHeader.serialize());
        this.writer.writeObject(this.masterXRefOffset, this.masterXRef);
        this.writer.writeBytes(this.trailerOffset, this.trailer.serialize());
        this.writer.flush();
    }

    private long findOffset(int objectSize) {
        long offset = this.masterXRefOffset;
        this.masterXRefOffset += objectSize;
        this.trailerOffset += objectSize + XRef.XRefEntry.FIXED_BYTE_SIZE;
        return offset;
    }

    public File getFile() {
        return this.writer.getFile();
    }

    // ----- init stuff -----

    private void throwIfNotMarked() throws UnmarkedNoteFileException {
        byte[] bytes = this.writer.readBytesAndFlush(0L, NoteMark.FIXED_BYTE_SIZE);
        if (!Arrays.equals(NoteMark.NOTE, bytes)) throw new UnmarkedNoteFileException();
    }

    private void loadHeader() {
        byte[] bytes = this.writer.readBytesAndFlush(NoteMark.FIXED_BYTE_SIZE, Header.FIXED_BYTE_SIZE);
        this.header = HeaderParser.parse(bytes);
    }

    private void loadMasterXRefHeader() {
        // master xrefs header is right after file header
        byte[] bytes = this.writer.readBytesAndFlush(Header.FIXED_BYTE_SIZE + NoteMark.FIXED_BYTE_SIZE, NFOHeader.FIXED_BYTE_SIZE);
        this.masterXRefHeader = NFOHeaderParser.parse(bytes);
    }

    private void loadMasterXRef() {
        byte[] bytes = this.writer.readBytesAndFlush(
                this.masterXRefOffset,
                this.masterXRefHeader.bytesOccupied()
        );
        this.masterXRef = (XRef) NoteFileObjectParser.parseObject(XRef.class, bytes);
    }

    private void loadTrailer() {
        byte[] bytes = this.writer.readBytesAndFlush(
                this.trailerOffset,
                (int) (this.writer.getFileSize() - this.trailerOffset)
        );
        this.trailer = TrailerParser.parse(bytes);
    }

    private void loadObjectIDs() {
        for (String s : this.trailer.dependencies().getObjects()) {
            this.objectIDs.add(OBJECTS.get(s));
        }
        this.objectIDs.trimToSize();
    }

    private void cacheAllXRefs() {
        this.cachedXRefs.add(this.masterXRef);
        cacheChildXRefs(this.masterXRef);
    }

    private void cacheChildXRefs(XRef parent) {
        for (XRef.XRefEntry xRefEntry : parent) {
            if (this.objectIDs.get(xRefEntry.header().objectID()) == XRef.class) {
                XRef child = (XRef) getObject(xRefEntry);
                this.cachedXRefs.add(child);
                cacheChildXRefs(child);
            }
        }
    }

    public static NoteFile newBlankFile(java.io.File file) {
        assert file.isFile();

        Header header = new Header(
                DocumentVersion.NEWEST,
                NoteMark.FIXED_BYTE_SIZE + Header.FIXED_BYTE_SIZE + NFOHeader.FIXED_BYTE_SIZE,
                NoteMark.FIXED_BYTE_SIZE + Header.FIXED_BYTE_SIZE + NFOHeader.FIXED_BYTE_SIZE
        );

        NFOHeader masterXRefsHeader = new NFOHeader(
                (byte) 0,
                0,
                (byte) Byte.SIZE
        );

        XRef masterXRef = new XRef();

        Trailer trailer = new Trailer(new Dependencies(XRef.class.getSimpleName()));

        NoteFileWriter noteFileWriter = new NoteFileWriter(file);
        noteFileWriter.writeBytes(0L, NoteMark.NOTE);

        NoteFile noteFile = new NoteFile(
                header,
                masterXRefsHeader,
                masterXRef,
                trailer,
                header.getMasterXRefOffset(),
                header.getTrailerOffset(),
                noteFileWriter
        );

        noteFile.objectIDs.add(XRef.class);
        noteFile.cachedXRefs.add(masterXRef);

        noteFile.save();
        return noteFile;
    }
}
