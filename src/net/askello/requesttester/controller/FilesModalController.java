package net.askello.requesttester.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.askello.requesttester.model.Param;

import java.io.File;

public class FilesModalController {

    @FXML
    private TextField keyField;
    @FXML
    private File choosedFile;
    @FXML
    private Label fileNameLabel;
    @FXML
    public Button saveButton;

    private Param param;
    private Stage dialogStage;

    @FXML
    public void initialize() {

    }

    @FXML
    public void chooseButtonHandler() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        choosedFile = fileChooser.showOpenDialog(dialogStage);
        fileNameLabel.setText(choosedFile.getName());
    }

    @FXML
    private void saveButtonHandler() {
        if(keyField.getText().length()>0 && choosedFile!=null) {
            param = new Param(keyField.getText(), choosedFile);
        }
        dialogStage.close();
    }

    public void setParam(Param param) {
        if(param != null) {
            keyField.setText(param.getKey());
            choosedFile = param.getFile();
        }
    }

    public Param getParam() {
        return param;
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

}
