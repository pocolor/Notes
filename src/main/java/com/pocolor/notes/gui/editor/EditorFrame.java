package com.pocolor.notes.gui.editor;

import com.pocolor.notes.di.DI;
import com.pocolor.notes.gui.startup.StartupFrame;
import com.pocolor.notes.notefile.NoteFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditorFrame extends JFrame {
    public EditorFrame(NoteFile noteFile) {
        EditorViewModel viewModel = DI.viewModels.editorViewModel();

        this.setTitle(DI.values.appTitle());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(300, 300));

        viewModel.setNoteFile(noteFile);
        Editor editor = new Editor(viewModel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);

                editor.onClose();
                viewModel.saveNoteFile();
                new StartupFrame();
            }
        });

        this.add(editor);

        this.setVisible(true);
    }
}
