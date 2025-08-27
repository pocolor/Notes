package com.pocolor.notes.di;

public final class Values {
    private final String appDevName;
    private final String appTitle;
    private final String createNew;

    Values() {
        this.appDevName = "pocolorNotes";
        this.appTitle = "Notes";
        this.createNew = "Create New";
    }

    public String appDevName() { return this.appDevName; }
    public String appTitle() { return this.appTitle; }
    public String createNew() { return this.createNew; }
}
