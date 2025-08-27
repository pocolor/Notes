package com.pocolor.notes.notefile.parsers.other;

import com.pocolor.notes.notefile.header.DocumentVersion;
import com.pocolor.notes.notefile.header.Header;

import java.nio.ByteBuffer;

public final class HeaderParser {
    private HeaderParser() throws Exception { throw new Exception("no instances of this class"); }

    public static Header parse(byte[] bytes) {
        assert bytes.length == Header.FIXED_BYTE_SIZE;

        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        DocumentVersion documentVersion = new DocumentVersion(buffer.get(), buffer.get(), buffer.getShort());
        return new Header(documentVersion, buffer.getLong(), buffer.getLong());
    }
}
