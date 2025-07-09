package poco.notes;

import poco.notes.homescreen.HomeScreen;

import javax.swing.*;

class HomeScreenFrame extends JFrame {
    public HomeScreenFrame() {
        this.setTitle("Notes");
        this.setSize(800, 600);
        JFrameCommonLogic.applyTo(this);

        this.add(new HomeScreen());
    }
}
