package com.pocolor.notes.notefile.parsers.other;

import com.pocolor.notes.notefile.NFOHeader;

import java.nio.ByteBuffer;

public final class NFOHeaderParser {
    private NFOHeaderParser() throws Exception { throw new Exception("no instances of this class"); }

    public static NFOHeader parse(byte[] bytes) {
        assert bytes.length == NFOHeader.FIXED_BYTE_SIZE;

        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        return new NFOHeader(buffer.get(), buffer.getInt(), buffer.get());
    }

    public static NFOHeader parse(ByteBuffer buffer) {
        assert buffer.remaining() >= NFOHeader.FIXED_BYTE_SIZE;

        return new NFOHeader(buffer.get(), buffer.getInt(), buffer.get());
    }
}
