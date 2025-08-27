package com.pocolor.notes.notefile;

import com.pocolor.notes.notefile.objects.*;
import com.pocolor.notes.notefile.parsers.*;

import java.util.HashMap;

public final class NoteFileObjectParser {
    private NoteFileObjectParser() throws Exception { throw new Exception("no instances of this class"); }

    private static final HashMap<Class<? extends NFObject>, NFOParser> parsers = new HashMap<>();

    static {
        parsers.put(XRef.class, new XRefParser());
        parsers.put(Text.class, new TextParser());
    }

    public static NFObject parseObject(Class<? extends NFObject> clazz, byte[] objectData) {
        return parsers.get(clazz).parse(objectData);
    }
}
