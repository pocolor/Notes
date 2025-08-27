package com.pocolor.notes.notefile.objects;

public abstract class NFObject {
    public abstract int getByteSize();
    public abstract byte getBitsUsedInLastByte();
    public abstract byte[] serialize();
}
