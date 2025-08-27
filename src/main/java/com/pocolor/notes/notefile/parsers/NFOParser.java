package com.pocolor.notes.notefile.parsers;

import com.pocolor.notes.notefile.objects.NFObject;

public abstract class NFOParser {
    public abstract NFObject parse(byte[] objectData);
}
