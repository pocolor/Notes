module com.pocolor.notes.app.ui {
    requires javafx.fxml;
    requires javafx.controls;

    requires com.pocolor.notes.app.viewmodel;

    opens com.pocolor.notes.app.ui to javafx.fxml;
    exports com.pocolor.notes.app.ui;
}