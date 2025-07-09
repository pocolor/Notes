package poco.notes;

import javax.swing.*;
import java.awt.*;

public final class JFrameCommonLogic {
    public static void applyTo(JFrame jFrame) {
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(true);
        jFrame.setMinimumSize(new Dimension(256, 128));
        jFrame.setVisible(true);
    }
}
