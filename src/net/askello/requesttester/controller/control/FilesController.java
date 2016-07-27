package net.askello.requesttester.controller.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.askello.requesttester.MainApp;
import net.askello.requesttester.controller.modal.FilesModalController;
import net.askello.requesttester.library.common.Param;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by askello on 15.07.2016.
 */
public class FilesController {

    @FXML
    private TableView filesTable;
    @FXML
    private TableColumn<Param, String> keyFileColumn;
    @FXML
    private TableColumn<Param, File> pathFileColumn;

    private ObservableList<Param> files = FXCollections.observableArrayList();
    private Param selectedFile;

    @FXML
    public Button addFileButton;
    @FXML
    public Button editFileButton;
    @FXML
    public Button removeFileButton;

    private MainApp mainApp;

    @FXML
    public void initialize() {
        initFilesTable();
    }

    @FXML
    private void addFileButtonHandler() {
        Param newFile = showFilesModal(null);
        if(newFile != null)
            files.add(newFile);
    }

    @FXML
    private void editFileButtonHandler() {
        Param newFile = showFilesModal(selectedFile);
        if(newFile != null) {
            int index = files.indexOf(selectedFile);
            files.set(index, newFile);
        }
    }

    @FXML
    private void removeFileButtonHandler() {
        files.remove(selectedFile);
    }

    private void initFilesTable() {
        keyFileColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("key"));
        pathFileColumn.setCellValueFactory(new PropertyValueFactory<Param, File>("file"));
        filesTable.setItems(files);
        filesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedFile = (Param)newValue);
    }

    public HashMap<String, File> getFiles() {
        HashMap<String, File> files = new HashMap<String, File>();
        for(Param param : this.files)
            files.put(param.getKey(), param.getFile());

        return files;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public Param showFilesModal(Param param) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/modal/filesModal.fxml"));
            Parent page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit File");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getMainStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            FilesModalController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setParam(param);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getParam();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
