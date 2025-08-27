package com.pocolor.notes.notefile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

public class NoteFileTest {
    private static NoteFile createTempNoteFile() {
        File testFile = new File("testFile" + Instant.now().getNano() + ".note");
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return NoteFile.newBlankFile(testFile);
    }

    @Test
    void testUnmarkedNoteFile() {
        NoteFile noteFile = createTempNoteFile();
        File file = noteFile.getFile();

        try {
            Files.write(
                    file.toPath(),
                    new byte[]{0, 0, 0, 0},
                    StandardOpenOption.WRITE
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertThrows(UnmarkedNoteFileException.class, () -> new NoteFile(file));

        file.delete();
    }
}
