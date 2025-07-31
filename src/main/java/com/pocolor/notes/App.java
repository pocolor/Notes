package com.pocolor.notes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.pocolor.notes.startup.controller.StartupController.STARTUP_FXML_RESOURCE_PATH;

public class App extends Application {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage stage) throws Exception {
        log.info("Starting Application");

        FXMLLoader loader = new FXMLLoader(getClass().getResource(STARTUP_FXML_RESOURCE_PATH));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Notes");
        stage.setMaximized(true);
        stage.show();
    }
}
