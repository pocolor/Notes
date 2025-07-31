package com.pocolor.notes.startup.controller;

import com.pocolor.notes.startup.viewmodel.StartupViewModel;
import com.pocolor.notes.startup.viewmodel.ViewModelFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.pocolor.notes.startup.controller.template.TemplateController.TEMPLATE_FXML_RESOURCE_PATH;
import static com.pocolor.notes.startup.controller.recent.RecentController.RECENT_FXML_RESOURCE_PATH;

public class StartupController implements Initializable {
    public static final String STARTUP_FXML_RESOURCE_PATH = "/fxml/startup/Startup.fxml";
    private static final Logger log = LoggerFactory.getLogger(StartupController.class);

    private StartupViewModel viewModel;

    @FXML private VBox vBoxContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.viewModel = ViewModelFactory.getInstance().getStartupViewModel();

        try {
            loadTemplateFXML();
            loadRecentFXML();
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(-1);
        }
    }

    private void loadTemplateFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(TEMPLATE_FXML_RESOURCE_PATH));
        this.vBoxContainer.getChildren().add(loader.load());
    }

    private void loadRecentFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(RECENT_FXML_RESOURCE_PATH));
        this.vBoxContainer.getChildren().add(loader.load());
    }
}
