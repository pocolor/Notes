module com.pocolor.notes.app.launcher {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.pocolor.notes.app.ui;
    requires com.pocolor.notes.app.viewmodel;
    requires com.pocolor.notes.core;
    requires com.pocolor.notes.shared;

    requires ch.qos.logback.classic;
    requires org.slf4j;

    exports com.pocolor.notes.app.launcher;
}