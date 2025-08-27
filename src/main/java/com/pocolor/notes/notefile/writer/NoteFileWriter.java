package com.pocolor.notes.notefile.writer;

import com.pocolor.notes.io.FileWriter;
import com.pocolor.notes.io.RandomAccessFileWriter;
import com.pocolor.notes.notefile.objects.NFObject;
import com.pocolor.notes.notefile.objects.XRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class NoteFileWriter {
    public static final int QUEUE_LIMIT = 10;
    private static final Logger log = LoggerFactory.getLogger(NoteFileWriter.class);

    private Action.SeekAction lastSeek;
    private final File file;
    private final Deque<Action> queue;

    public NoteFileWriter(File file) {
        this.file = file;
        this.queue = new LinkedList<>();
        this.lastSeek = Action.seek(0);
    }

    public File getFile() {
        return this.file;
    }

    public long getFileSize() {
        return this.file.length();
    }

    private void offer(Action action) {
        this.queue.offer(action);
        if (this.queue.size() > QUEUE_LIMIT) flush();
    }

    public void flush() {
        if (this.queue.isEmpty()) return;

        int size = this.queue.size();

        try (FileWriter fw = new RandomAccessFileWriter(this.file)) {
            fw.seek(this.lastSeek.getPosition());

            for (int i = 0; i < size; i++) {
                Action action = this.queue.poll();
                if (action instanceof Action.WriteAction writeAction) fw.write(writeAction.getData());
                if (action instanceof Action.ReadAction readAction) fw.read(readAction.getBuffer());
                if (action instanceof Action.SeekAction seekAction) {
                    fw.seek(seekAction.getPosition());
                    this.lastSeek = seekAction;
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    //---

    public byte[] readBytes(int size) {
        byte[] bytes = new byte[size];
        offer(Action.read(bytes));
        return bytes;
    }

    public void writeBytes(byte[] bytes) {
        offer(Action.write(bytes));
    }

    public void seek(long position) {
        offer(Action.seek(position));
    }

    //---

    public byte[] readBytes(long offset, int size) {
        seek(offset);
        return readBytes(size);
    }

    public void writeBytes(long offset, byte[] bytes) {
        seek(offset);
        writeBytes(bytes);
    }

    //---

    public byte[] readXRefEntryCurrentOffset(XRef.XRefEntry xRefEntry) {
        return readBytes(xRefEntry.header().bytesOccupied());
    }

    public byte[] readXRefEntry(XRef.XRefEntry xRefEntry) {
        seek(xRefEntry.offset());
        return readXRefEntryCurrentOffset(xRefEntry);
    }

    //---

    public void writeObject(NFObject nfObject) {
        writeBytes(nfObject.serialize());
    }

    public void writeObject(long offset, NFObject nfObject) {
        seek(offset);
        writeObject(nfObject);
    }

    //----------------------------------------------------------------------

    public byte[] readBytesAndFlush(int size) {
        byte[] bytes = readBytes(size);
        flush();
        return bytes;
    }

    public void writeBytesAndFlush(byte[] bytes) {
        writeBytes(bytes);
        flush();
    }

    public void seekAndFlush(long position) {
        seek(position);
        flush();
    }

    //---

    public byte[] readBytesAndFlush(long offset, int size) {
        seek(offset);
        return readBytesAndFlush(size);
    }

    public void writeBytesAndFlush(long offset, byte[] bytes) {
        seek(offset);
        writeBytesAndFlush(bytes);
    }

    //---

    public byte[] readXRefEntryCurrentOffsetAndFlush(XRef.XRefEntry xRefEntry) {
        return readBytesAndFlush(xRefEntry.header().bytesOccupied());
    }

    public byte[] readXRefEntryAndFlush(XRef.XRefEntry xRefEntry) {
        seek(xRefEntry.offset());
        return readBytesAndFlush(xRefEntry.header().bytesOccupied());
    }

    //---

    public void writeObjectAndFlush(NFObject nfObject) {
        writeBytesAndFlush(nfObject.serialize());
    }

    public void writeObjectAndFlush(long offset, NFObject nfObject) {
        seek(offset);
        writeObjectAndFlush(nfObject);
    }
}
