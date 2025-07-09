package poco.notes;

import poco.notes.editorscreen.EditorScreen;

import javax.swing.*;

public class EditorScreenFrame extends JFrame {
    public EditorScreenFrame() {
        this.setTitle("Note Editor");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JFrameCommonLogic.applyTo(this);

        this.add(new EditorScreen());
    }
}
