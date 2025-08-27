package com.pocolor.notes.gui.editor;

import com.pocolor.notes.notefile.NoteFile;
import com.pocolor.notes.notefile.objects.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class EditorViewModel {
    private String text;
    private NoteFile noteFile;

    public void setNoteFile(NoteFile noteFile) {
        this.noteFile = noteFile;

        StringBuilder sb = new StringBuilder();
        var texts = this.noteFile.getAllObjectsOfType(Text.class);

        if (texts.length == 0) {
            this.text = "";
            return;
        }

        for (Text t : texts) {
            sb.append(t.getText()).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);

        this.text = sb.toString();
    }

    public String getText() {
        return this.text;
    }

    public void textChanged(String text) {
        this.text = text;
        saveNoteFile();
    }

    public void saveNoteFile() {
        try {
            Files.writeString(
                    Path.of(this.noteFile.getFile().toURI()),
                    "",
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.noteFile = NoteFile.newBlankFile(this.noteFile.getFile());

        for (String s : this.text.split("\n")) {
            this.noteFile.addObject(new Text(s));
        }
        this.noteFile.save();
    }
}
