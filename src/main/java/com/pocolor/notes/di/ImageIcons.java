package com.pocolor.notes.di;

import javax.swing.ImageIcon;

public final class ImageIcons {
    private final ImageIcon addFile;
    private final ImageIcon book;
    private final ImageIcon openFile;

    ImageIcons() {
        this.addFile = loadImageIcon("add-file.png");
        this.book = loadImageIcon("book.png");
        this.openFile = loadImageIcon("open-file.png");
    }

    public ImageIcon addFile() { return this.addFile; }
    public ImageIcon book() { return this.book; }
    public ImageIcon openFile() { return this.openFile; }

    private static ImageIcon loadImageIcon(String name) {
        //noinspection DataFlowIssue
        return new ImageIcon(ImageIcons.class.getResource("/icons/" + name));
    }
}
