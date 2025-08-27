package com.pocolor.notes.notefile.objects;

import java.nio.charset.StandardCharsets;

public class Text extends NFObject {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Text t && this.text.equals(t.text);
    }

    @Override
    public int getByteSize() {
        return serialize().length;
    }

    @Override
    public byte getBitsUsedInLastByte() {
        return Byte.SIZE;
    }

    @Override
    public byte[] serialize() {
        return this.text.getBytes(StandardCharsets.UTF_8);
    }
}
