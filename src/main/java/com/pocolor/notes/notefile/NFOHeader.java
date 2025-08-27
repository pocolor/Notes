package com.pocolor.notes.notefile;

import com.pocolor.notes.notefile.util.FixedByteSize;
import static com.pocolor.notes.notefile.util.FixedByteSizeGetter.getFixedByteSizeOf;

import java.nio.ByteBuffer;

@FixedByteSize
public record NFOHeader(
        byte objectID,
        int bytesOccupied,
        byte bitsUsedInLastByte
) {
    public static final int FIXED_BYTE_SIZE = getFixedByteSizeOf(NFOHeader.class);

    public byte[] serialize() {
        return ByteBuffer.wrap(new byte[FIXED_BYTE_SIZE])
                .put(this.objectID)
                .putInt(this.bytesOccupied)
                .put(this.bitsUsedInLastByte)
                .array();
    }
}
