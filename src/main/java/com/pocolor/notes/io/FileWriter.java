package com.pocolor.notes.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public abstract class FileWriter implements Closeable {
    protected final File file;
    protected boolean closed;

    public FileWriter(File file) throws IOException {
        this.file = file;
    }

    public abstract void seek(long position) throws IOException;
    public abstract void write(byte[] buffer) throws IOException;
    public abstract void writeByte(byte b) throws IOException;
    public abstract void read(byte[] buffer) throws IOException;
    public abstract byte readByte() throws IOException;

    public void ensureOpen() throws IOException {
        if (this.closed) throw new IOException("File is closed");
    }
}
