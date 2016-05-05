package com.askello.requesttester.controller;

import com.askello.requesttester.MainApp;
import com.askello.requesttester.library.Server;
import com.askello.requesttester.model.Param;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class MainController {

    @FXML
    private TextField url;
    @FXML
    private Button runButton;
    @FXML
    private TextArea outputArea;

    @FXML
    private TableView dataTable;
    @FXML
    private TableColumn<Param, String> keyColumn;
    @FXML
    private TableColumn<Param, String> valueColumn;

    private ObservableList<Param> dataParams = FXCollections.observableArrayList();
    private Param selectedData;

    @FXML
    public Button addDataButton;
    @FXML
    public Button editDataButton;
    @FXML
    public Button removeDataButton;

    @FXML
    private ComboBox<String> requestChooser;

    private MainApp mainApp;

    @FXML
    public void initialize() {
        initRequestChooser();
        initDataTable();
    }

    @FXML
    public void runHandler() throws IOException, ParseException {
        HashMap<String, String> outputData = new HashMap<String, String>();

        for(Param param : dataParams)
            outputData.put(param.getKey(), param.getValue());

        String serverResponse = Server.doRequestAndGetResponse(url.getText(), outputData);

        outputArea.setText(serverResponse);

        //JSONParser parser = new JSONParser();
        //JSONObject jsonObject = (JSONObject) parser.parse(serverResponse);
        //JSONArray jsonArray = (JSONArray) parser.parse(serverResponse);
        //outputArea.setText(jsonObject.get("message").toString());
    }

    @FXML
    private void addDataButtonHandler() {
        Param newParam = mainApp.showDataModal(null);
        if(newParam != null)
            dataParams.add(newParam);
    }

    @FXML
    private void editDataButtonHandler() {
        Param newParam = mainApp.showDataModal(selectedData);
        if(newParam != null) {
            int index = dataParams.indexOf(selectedData);
            dataParams.set(index, newParam);
        }
    }
    @FXML
    private void removeDataButtonHandler() {
        dataParams.remove(selectedData);
    }

    private void initRequestChooser() {
        requestChooser.getItems().add("post");
        requestChooser.getItems().add("get");
        requestChooser.setValue("post");
    }

    private void initDataTable() {
        keyColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("value"));
        dataTable.setItems(dataParams);
        dataTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedData = (Param)newValue);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
