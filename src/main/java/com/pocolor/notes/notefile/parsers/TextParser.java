package com.pocolor.notes.notefile.parsers;

import com.pocolor.notes.notefile.objects.NFObject;
import com.pocolor.notes.notefile.objects.Text;

import java.nio.charset.StandardCharsets;

public class TextParser extends NFOParser {
    @Override
    public NFObject parse(byte[] objectData) {
        return new Text(new String(objectData, StandardCharsets.UTF_8));
    }
}
