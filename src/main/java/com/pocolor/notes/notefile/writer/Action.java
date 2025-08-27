package com.pocolor.notes.notefile.writer;

public abstract class Action {
    public static WriteAction write(byte[] data) {
        return new WriteAction(data);
    }
    public static class WriteAction extends Action {
        private final byte[] data;
        public WriteAction(byte[] data) {
            this.data = data;
        }

        public byte[] getData() {
            return this.data;
        }
    }

    public static ReadAction read(byte[] buffer) {
        return new ReadAction(buffer);
    }
    public static class ReadAction extends Action {
        private final byte[] buffer;
        public ReadAction(byte[] buffer) {
            this.buffer = buffer;
        }
        public byte[] getBuffer() {
            return this.buffer;
        }
    }

    public static SeekAction seek(long position) {
        return new SeekAction(position);
    }
    public static class SeekAction extends Action {
        private final long position;
        public SeekAction(long position) {
            this.position = position;
        }
        public long getPosition() {
            return this.position;
        }
    }
}
