package com.pocolor.notes.app.launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class AppLauncher extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        _showStartupView();
    }

    public static void showStartupView() throws IOException {
        // properly close editor view

        _showStartupView();
    }

    private static void _showStartupView() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
        primaryStage.show();
    }

    public static void showEditorView() throws IOException {
        // properly close startup view? or idk
    }

    private static void _showEditorView() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
    }
}
