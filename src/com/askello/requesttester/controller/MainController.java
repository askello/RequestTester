package com.askello.requesttester.controller;

import com.askello.requesttester.MainApp;
import com.askello.requesttester.library.FileLoader;
import com.askello.requesttester.library.request.Request;
import com.askello.requesttester.model.Param;
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
import java.net.URL;
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
    public void runHandler() throws IOException {

        HashMap<String, String> params = new HashMap<String, String>();
        for(Param param : dataParams)
            params.put(param.getKey(), param.getValue());

        HashMap<String, File> files = new HashMap<String, File>();
        files.put("image", new File("logo.png"));

        //String response = Server.doRequestAndGetResponse(url.getText(), outputData);



        /*
        final File uploadFile = new File("logo.jpg");

        final FileLoader http = new FileLoader (new URL(url.getText()));
        http.addFormField("action", "test/test");
        http.addFormField("language", "ru");
        http.addFilePart("image", uploadFile);
        final byte[] bytes = http.finish();

        String response = new String(bytes);
        */

        /*
        HashMap<String, String> outputData = new HashMap<String, String>();
        for(Param param : dataParams)
            outputData.put(param.getKey(), param.getValue());

        Request request = new Request();
        request.setUrl(url.getText());
        request.addParams(outputData);
        request.addFile("image", "logo.jpg");
        request.execute();
        String response = request.getResponse();

        System.out.println(response);
        */

        String response = Request.MULTIPART(url.getText(), params, files);

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

        dataParams.add(new Param("action", "test/test"));
        dataParams.add(new Param("language", "ru"));
        //dataParams.add(new Param("mtoken", "8s0e3u563mostjvtdrs107pll0"));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
