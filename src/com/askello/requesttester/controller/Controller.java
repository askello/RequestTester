package com.askello.requesttester.controller;

import com.askello.requesttester.library.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

public class Controller {

    @FXML
    private TextField url;
    @FXML
    private Button runButton;
    @FXML
    private TextArea outputArea;

    @FXML
    public void initialize() {

    }

    @FXML
    public void runHandler() throws IOException, ParseException {
        HashMap<String, String> outputData = new HashMap<String, String>();
        outputData.put("language", "ru");
        outputData.put("login", "askello@ukr.net");
        outputData.put("password", "12345");

        String serverResponse = Server.doRequestAndGetResponse(url.getText(), outputData);

        outputArea.setText(serverResponse);

        //JSONParser parser = new JSONParser();
        //JSONObject jsonObject = (JSONObject) parser.parse(serverResponse);
        //JSONArray jsonArray = (JSONArray) parser.parse(serverResponse);
        //outputArea.setText(jsonObject.get("message").toString());
    }

}
