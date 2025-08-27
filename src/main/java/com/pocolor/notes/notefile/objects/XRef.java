package com.pocolor.notes.notefile.objects;

import com.pocolor.notes.notefile.NFOHeader;
import com.pocolor.notes.notefile.util.FixedByteSize;
import static com.pocolor.notes.notefile.util.FixedByteSizeGetter.getFixedByteSizeOf;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class XRef extends NFObject implements Iterable<XRef.XRefEntry> {
    @FixedByteSize
    public record XRefEntry(long offset, NFOHeader header) {
        public static final int FIXED_BYTE_SIZE = getFixedByteSizeOf(XRefEntry.class);
    }

    private final LinkedList<XRefEntry> entries;

    public XRef(XRefEntry... entries) {
        this.entries = new LinkedList<>(List.of(entries));
    }

    public LinkedList<XRefEntry> getEntries() {
        return this.entries;
    }

    @Override
    public int getByteSize() {
        return XRefEntry.FIXED_BYTE_SIZE * this.entries.size();
    }

    @Override
    public byte getBitsUsedInLastByte() {
        return Byte.SIZE;
    }

    @Override
    public byte[] serialize() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[getByteSize()]);

        for (XRefEntry entry : this) {
            buffer.putLong(entry.offset());
            buffer.put(entry.header().serialize());
        }

        return buffer.array();
    }

    @Override
    public Iterator<XRefEntry> iterator() {
        return this.entries.iterator();
    }

    @Override
    public void forEach(Consumer<? super XRefEntry> action) {
        this.entries.forEach(action);
    }

    @Override
    public Spliterator<XRefEntry> spliterator() {
        return this.entries.spliterator();
    }
}
