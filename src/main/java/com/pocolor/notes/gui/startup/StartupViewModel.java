package com.pocolor.notes.gui.startup;

import com.pocolor.notes.gui.Dialogs;
import com.pocolor.notes.gui.editor.EditorFrame;
import com.pocolor.notes.notefile.MalformedNoteFileException;
import com.pocolor.notes.notefile.NoteFile;
import com.pocolor.notes.notefile.UnmarkedNoteFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class StartupViewModel {
    private Runnable startupFrameDisposeEvent;

    public void setStartupFrameDisposeEvent(Runnable startupFrameDisposeEvent) {
        this.startupFrameDisposeEvent = startupFrameDisposeEvent;
    }

    public void newButtonClicked() {
        File file;
        do {
            Optional<File> optionalFile = Dialogs.createNoteFileChooser();

            if (optionalFile.isEmpty()) return;

            file = optionalFile.get();

        } while (file.exists() && !Dialogs.overrideFileConfirmationDialog(file));

        this.startupFrameDisposeEvent.run();

        try {
            Files.writeString(
                    Path.of(file.toURI()),
                    "",
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new EditorFrame(NoteFile.newBlankFile(file));
    }

    public void openButtonClicked() {
        File file;

        Optional<File> optionalFile = Dialogs.openNoteFileChooser();

        if (optionalFile.isEmpty()) return;

        file = optionalFile.get();

        NoteFile noteFile;
        try {
            noteFile = new NoteFile(file);
        } catch (UnmarkedNoteFileException e) {
            Dialogs.info("Not a note file", "This file doesn't contain the NOTE mark. Couldn't open it.");
            return;
        } catch (MalformedNoteFileException e) {
            Dialogs.info("Corrupted file.", "Couldn't open this file.");
            return;
        }

        this.startupFrameDisposeEvent.run();

        new EditorFrame(noteFile);
    }
}
