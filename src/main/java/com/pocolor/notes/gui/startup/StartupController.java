package com.pocolor.notes.gui.startup;

import com.pocolor.notes.gui.viewmodel.startup.StartupViewModel;
import com.pocolor.notes.gui.viewmodel.startup.StartupViewModelFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.pocolor.notes.gui.startup.template.TemplateController.TEMPLATE_FXML_RESOURCE_PATH;
import static com.pocolor.notes.gui.startup.recent.RecentController.RECENT_FXML_RESOURCE_PATH;

public class StartupController implements Initializable {
    public static final String STARTUP_FXML_RESOURCE_PATH = "/fxml/startup/Startup.fxml";

    private StartupViewModel viewModel;

    @FXML private VBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.viewModel = StartupViewModelFactory.getInstance().getStartupViewModel();

        try {
            loadTemplateFXML();
            loadRecentFXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTemplateFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(TEMPLATE_FXML_RESOURCE_PATH));
        this.vBox.getChildren().add(loader.load());
    }

    private void loadRecentFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(RECENT_FXML_RESOURCE_PATH));
        this.vBox.getChildren().add(loader.load());
    }
}
