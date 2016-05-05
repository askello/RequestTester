package com.askello.requesttester.controller;

import com.askello.requesttester.model.Param;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DataModalController {

    @FXML
    private TextField keyField;
    @FXML
    private TextField valueField;
    @FXML
    public Button saveButton;

    private Param param;
    private Stage dialogStage;

    @FXML
    public void initialize() {

    }

    @FXML
    private void saveButtonHandler() {
        if(keyField.getText().length()>0 && valueField.getText().length()>0) {
            param = new Param(keyField.getText(), valueField.getText());
        }
        dialogStage.close();
    }

    public void setParam(Param param) {
        if(param != null) {
            keyField.setText(param.getKey());
            valueField.setText(param.getValue());
        }
    }

    public Param getParam() {
        return param;
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

}
