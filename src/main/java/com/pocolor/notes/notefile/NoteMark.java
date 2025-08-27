package com.pocolor.notes.notefile;

public final class NoteMark {
    private NoteMark() throws Exception { throw new Exception("no instances of this class"); }

    public static final byte[] NOTE = new byte[]{78, 79, 84, 69};  // "NOTE" in UTF-8
    public static final int FIXED_BYTE_SIZE = NOTE.length;
}
