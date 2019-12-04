package ch.makery.address.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import ch.makery.address.MainApp;
import ch.makery.address.util.AlertManager;
import ch.makery.address.util.FileManager;

public class RootLayoutController {

    private MainApp mainApp;
    private FileManager fileManager;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.fileManager = new FileManager(mainApp.getPrimaryStage(), mainApp.getPersonData());
    }
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        fileManager.setPersonFilePath(null);
    }
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            fileManager.loadPersonDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File personFile = fileManager.getPersonFilePath();
        if (personFile != null) {
            fileManager.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            fileManager.savePersonDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        AlertManager.alert(Alert.AlertType.INFORMATION, "AddressApp",
                    "Sobre", "Autor: Marco Jakob\nWebsite: http://code.makery.ch\nModificações por: Samuel Ribeiro Simonetti\n");
    }
    
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleShowBirthdayStatistics() {
      mainApp.showBirthdayStatistics();
    }
}