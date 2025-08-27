package com.pocolor.notes.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Optional;

public final class Dialogs {
    private Dialogs() throws Exception { throw new Exception("no instances of this class"); }

    public static Optional<File> createNoteFileChooser() {
        return noteFileChooser("Create a note file", "Create");
    }

    public static Optional<File> openNoteFileChooser() {
        return noteFileChooser("Open note file", "Open");
    }

    public static Optional<File> noteFileChooser(String title, String approveButtonText) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileNameExtensionFilter("Note file (*.note)", "note"));
        chooser.setDialogTitle(title);
        int returnVal = chooser.showDialog(null, approveButtonText);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return Optional.of(chooser.getSelectedFile());
        }
        return Optional.empty();
    }

    public static boolean overrideFileConfirmationDialog(File file) {
        return JOptionPane.showConfirmDialog(
                null,
                "File " + file.getName() + " already exists. Are you sure you want to override this file?",
                "Override file",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static void info(String title, String text) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout(10, 10));

        JLabel label = new JLabel(text, SwingConstants.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        dialog.add(label, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setVisible(true);
    }
}
