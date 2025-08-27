package com.pocolor.notes.notefile.header;

import com.pocolor.notes.notefile.util.FixedByteSize;
import static com.pocolor.notes.notefile.util.FixedByteSizeGetter.getFixedByteSizeOf;

import java.nio.ByteBuffer;

@FixedByteSize
public class Header {
    public static final int FIXED_BYTE_SIZE = getFixedByteSizeOf(Header.class);
    private DocumentVersion documentVersion;
    private long masterXRefOffset;
    private long trailerOffset;

    public Header(DocumentVersion documentVersion, long masterXRefOffset, long trailerOffset) {
        this.documentVersion = documentVersion;
        this.masterXRefOffset = masterXRefOffset;
        this.trailerOffset = trailerOffset;
    }

    public DocumentVersion getDocumentVersion() {
        return this.documentVersion;
    }

    public void setDocumentVersion(DocumentVersion documentVersion) {
        this.documentVersion = documentVersion;
    }

    public long getMasterXRefOffset() {
        return this.masterXRefOffset;
    }

    public void setMasterXRefOffset(long masterXRefOffset) {
        this.masterXRefOffset = masterXRefOffset;
    }

    public long getTrailerOffset() {
        return this.trailerOffset;
    }

    public void setTrailerOffset(long trailerOffset) {
        this.trailerOffset = trailerOffset;
    }

    public byte[] serialize() {
        return ByteBuffer.wrap(new byte[FIXED_BYTE_SIZE])
                .put(this.documentVersion.serialize())
                .putLong(this.masterXRefOffset)
                .putLong(this.trailerOffset)
                .array();
    }
}
