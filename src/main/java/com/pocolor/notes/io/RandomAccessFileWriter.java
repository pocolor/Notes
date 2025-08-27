package com.pocolor.notes.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWriter extends FileWriter {
    private final RandomAccessFile raf;

    public RandomAccessFileWriter(File file) throws IOException {
        super(file);
        this.raf = new RandomAccessFile(file, "rw");
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        ensureOpen();
        this.raf.write(buffer);
    }

    @Override
    public void writeByte(byte b) throws IOException {
        ensureOpen();
        this.raf.write(b);
    }

    @Override
    public void read(byte[] buffer) throws IOException {
        ensureOpen();
        this.raf.read(buffer);
    }

    @Override
    public byte readByte() throws IOException {
        ensureOpen();
        return this.raf.readByte();
    }

    @Override
    public void seek(long position) throws IOException {
        ensureOpen();
        this.raf.seek(position);
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
        this.raf.close();
    }
}
