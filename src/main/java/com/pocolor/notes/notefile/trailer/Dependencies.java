package com.pocolor.notes.notefile.trailer;

import java.nio.charset.StandardCharsets;

public class Dependencies {
    public static final String DECLARATION = "/Dependencies";
    private String[] objects;

    public Dependencies() {
        this.objects = new String[0];
    }

    public Dependencies(String... objects) {
        this.objects = objects;
    }

    public String[] getObjects() {
        return this.objects;
    }

    public void addObject(String object) {
        String[] newArray = new String[this.objects.length + 1];
        System.arraycopy(this.objects, 0, newArray, 0, this.objects.length);
        newArray[this.objects.length] = object;
        this.objects = newArray;
    }

    public byte[] serialize() {
        StringBuilder sb = new StringBuilder(DECLARATION);

        for (String object : this.objects) {
            sb.append(" ").append(object);
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
