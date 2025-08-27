package com.pocolor.notes.notefile.parsers;

import com.pocolor.notes.notefile.NFOHeader;
import com.pocolor.notes.notefile.objects.NFObject;
import com.pocolor.notes.notefile.objects.XRef;
import com.pocolor.notes.notefile.parsers.other.NFOHeaderParser;

import java.nio.ByteBuffer;

public class XRefParser extends NFOParser {
    @Override
    public NFObject parse(byte[] objectData) {
        assert objectData.length % XRef.XRefEntry.FIXED_BYTE_SIZE == 0;

        ByteBuffer buffer = ByteBuffer.wrap(objectData);

        int numberOfEntries = objectData.length / XRef.XRefEntry.FIXED_BYTE_SIZE;
        XRef.XRefEntry[] entries = new XRef.XRefEntry[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            long offset = buffer.getLong();
            NFOHeader header = NFOHeaderParser.parse(buffer);
            entries[i] = new XRef.XRefEntry(offset, header);
        }

        return new XRef(entries);
    }
}
