package com.pocolor.notes.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage stage) throws Exception {
        log.info("Starting Application");
    }
}
