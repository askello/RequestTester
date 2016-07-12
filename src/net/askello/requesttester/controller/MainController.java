package net.askello.requesttester.controller;

import net.askello.requesttester.MainApp;
import net.askello.requesttester.library.request.Request;
import net.askello.requesttester.model.Param;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.*;
import java.util.HashMap;

public class MainController {

    @FXML
    private TextField url;
    @FXML
    private Button runButton;
    @FXML
    private TextArea outputArea;

    /* Params */
    @FXML
    private TableView paramsTable;
    @FXML
    private TableColumn<Param, String> keyParamColumn;
    @FXML
    private TableColumn<Param, String> valueParamColumn;

    private ObservableList<Param> params = FXCollections.observableArrayList();
    private Param selectedParam;

    @FXML
    public Button addParamButton;
    @FXML
    public Button editParamButton;
    @FXML
    public Button removeParamButton;
    /* End Params */

    /* Files */
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
    /* End Files */

    @FXML
    private ComboBox<String> requestChooser;

    private MainApp mainApp;

    @FXML
    public void initialize() {
        initRequestChooser();
        initParamsTable();
        initFilesTable();
    }

    @FXML
    public void runHandler() throws IOException {
        // prepare params
        HashMap<String, String> params = new HashMap<String, String>();
        for(Param param : this.params)
            params.put(param.getKey(), param.getValue());

        // prepare files
        HashMap<String, File> files = new HashMap<String, File>();
        for(Param param : this.files)
            files.put(param.getKey(), param.getFile());

        // execute request
        String response = Request.MULTIPART(url.getText(), params, files);

        // print result
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(response);
            outputArea.setText(gson.toJson(je));
        } catch (Exception e) {
            outputArea.setText(response);
        }
    }

    @FXML
    private void addParamButtonHandler() {
        Param newParam = mainApp.showParamsModal(null);
        if(newParam != null)
            params.add(newParam);
    }

    @FXML
    private void editParamButtonHandler() {
        Param newParam = mainApp.showParamsModal(selectedParam);
        if(newParam != null) {
            int index = params.indexOf(selectedParam);
            params.set(index, newParam);
        }
    }

    @FXML
    private void removeParamButtonHandler() {
        params.remove(selectedParam);
    }

    @FXML
    private void addFileButtonHandler() {
        Param newFile = mainApp.showFilesModal(null);
        if(newFile != null)
            files.add(newFile);
    }

    @FXML
    private void editFileButtonHandler() {
        Param newFile = mainApp.showParamsModal(selectedFile);
        if(newFile != null) {
            int index = files.indexOf(selectedFile);
            files.set(index, newFile);
        }
    }

    @FXML
    private void removeFileButtonHandler() {
        files.remove(selectedFile);
    }

    private void initRequestChooser() {
        requestChooser.getItems().add("post");
        requestChooser.getItems().add("get");
        requestChooser.setValue("post");
    }

    private void initParamsTable() {
        keyParamColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("key"));
        valueParamColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("value"));
        paramsTable.setItems(params);
        paramsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedParam = (Param)newValue);

        params.add(new Param("action", "test/test"));
        params.add(new Param("language", "ru"));
        //dataParams.add(new Param("mtoken", "8s0e3u563mostjvtdrs107pll0"));
    }

    private void initFilesTable() {
        keyFileColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("key"));
        pathFileColumn.setCellValueFactory(new PropertyValueFactory<Param, File>("file"));
        filesTable.setItems(files);
        filesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedFile = (Param)newValue);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
