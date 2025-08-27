package com.pocolor.notes.gui.editor;

import javax.swing.*;
import java.awt.*;

public class Editor extends JPanel {
    private final EditorViewModel viewModel;
    private final JTextArea textArea;
    private final Timer timer;

    public Editor(EditorViewModel viewModel) {
        this.viewModel = viewModel;
        this.textArea =  new JTextArea();
        JScrollPane scrollPane = new JScrollPane(this.textArea);

        this.timer = new Timer(10_000, e -> this.notifyTextChanged());
        this.timer.start();

        this.setLayout(new BorderLayout());

        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        this.textArea.setText(viewModel.getText());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void notifyTextChanged() {
        if (!this.viewModel.getText().equals(this.textArea.getText())) {
            this.viewModel.textChanged(this.textArea.getText());
        }
    }

    public void onClose() {
        this.timer.stop();
        notifyTextChanged();
    }
}
