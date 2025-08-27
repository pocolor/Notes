package com.pocolor.notes.notefile;

public class UnmarkedNoteFileException extends Exception {
    public UnmarkedNoteFileException() {
        super("file doesnt start with NOTE mark");
    }
}
