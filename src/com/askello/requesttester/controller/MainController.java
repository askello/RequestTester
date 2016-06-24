package com.askello.requesttester.controller;

import com.askello.requesttester.MainApp;
import com.askello.requesttester.library.FileLoader;
import com.askello.requesttester.library.Server;
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
        /*
        HashMap<String, String> outputData = new HashMap<String, String>();

        for(Param param : dataParams)
            outputData.put(param.getKey(), param.getValue());

        String serverResponse = Server.doRequestAndGetResponse(url.getText(), outputData);

        //outputArea.setText(serverResponse);

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(serverResponse);
            outputArea.setText(gson.toJson(je));
        } catch (Exception e) {
            outputArea.setText(serverResponse);
        }
        */


        final File uploadFile = new File("logo.png");
        try {
            final FileLoader http = new FileLoader (new URL(url.getText()));
            http.addFormField("action", "test/test");
            http.addFormField("language", "ru");
            http.addFilePart("image", uploadFile);
            final byte[] bytes = http.finish();
            OutputStream os = new FileOutputStream("someoutput.txt");
            os.write(bytes);
            os.close();

            outputArea.setText(new BufferedReader(new FileReader("someoutput.txt")).readLine());
        } catch (IOException e) {
            e.printStackTrace();
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

        dataParams.add(new Param("action", "crowdfunding/donaters"));
        dataParams.add(new Param("language", "ru"));
        dataParams.add(new Param("mtoken", "8s0e3u563mostjvtdrs107pll0"));
        dataParams.add(new Param("crowdfunding", "53"));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
