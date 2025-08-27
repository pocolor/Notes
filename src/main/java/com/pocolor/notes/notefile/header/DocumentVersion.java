package com.pocolor.notes.notefile.header;

import com.pocolor.notes.notefile.util.FixedByteSize;
import static com.pocolor.notes.notefile.util.FixedByteSizeGetter.getFixedByteSizeOf;

import java.nio.ByteBuffer;

@FixedByteSize
public record DocumentVersion(byte masterVersion, byte secondaryVersion, short ternaryVersion) {
    public static final int FIXED_BYTE_SIZE = getFixedByteSizeOf(DocumentVersion.class);
    public static final DocumentVersion NEWEST = new DocumentVersion((byte) 1, (byte) 0, (short) 0);

    public byte[] serialize() {
        return ByteBuffer.wrap(new byte[4]).put(this.masterVersion).put(this.secondaryVersion).putShort(this.ternaryVersion).array();
    }
}
