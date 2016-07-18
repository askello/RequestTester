package net.askello.requesttester.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.askello.requesttester.MainApp;
import net.askello.requesttester.controller.control.FilesController;
import net.askello.requesttester.controller.control.ParamsController;
import net.askello.requesttester.library.Cookie;
import net.askello.requesttester.library.Param;
import net.askello.requesttester.library.request.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.askello.requesttester.library.response.Response;


import java.io.*;
import java.util.HashMap;

public class MainController {

    @FXML
    private TextField url;
    @FXML
    private Button runButton;
    @FXML
    private TabPane tabPane;
    @FXML
    private TextArea jsonArea;
    @FXML
    private TextArea cookiesArea;
    @FXML
    private TextArea fullResponseArea;

    private ParamsController paramsController;
    private FilesController filesController;

    private ImageView preloader;

    private MainApp mainApp;

    @FXML
    public void initialize() {
        initParams();
        initFiles();
        initPreloader();
    }

    @FXML
    public void runHandler() throws IOException {
        startPreloader();

        new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception
            {


                // get params
                HashMap<String, String> params = paramsController.getParams();

                // get request method
                String method = paramsController.getMethod();

                // get files
                HashMap<String, File> files = filesController.getFiles();

                Response response = null;
                if(method.equals("GET")) {
                    response = Request.GET(url.getText(), params);
                }
                else if(method.equals("POST") && files.size()==0) {
                    //response = Request.POST(url.getText(), params);
                    response = Request.MULTIPART(url.getText(), params, new HashMap<>());
                }
                else if(method.equals("POST") && files.size()>0) {
                    response = Request.MULTIPART(url.getText(), params, files);
                }

                // print response json
                try {
                    Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                    JsonParser jp = new JsonParser();
                    JsonElement je = jp.parse(response.getContent());
                    jsonArea.setText(gson.toJson(je));
                } catch (Exception e) {
                    jsonArea.setText(response.getContent());
                }

                // print cookies
                cookiesArea.clear();
                for(Cookie cookie : response.getCookies()) {
                    cookiesArea.appendText(cookie.toString() + '\n');
                }

                // print full response
                fullResponseArea.clear();
                for(Param header : response.getHeaders()) {
                    if(header.getKey()!=null)
                        fullResponseArea.appendText(header.getKey() + ": " + header.getValue() + '\n');
                    else fullResponseArea.appendText(header.getValue() + '\n');
                }
                fullResponseArea.appendText('\n' + response.getContent());

                stopPreloader();

                return null;
            }
        }).start();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        paramsController.setMainApp(mainApp);
        filesController.setMainApp(mainApp);
    }

    private void initParams() {
        FXMLLoader paramsPane = new FXMLLoader(MainApp.class.getResource("view/control/params.fxml"));

        try {
            Parent view = paramsPane.load();
            paramsController = paramsPane.getController();
            tabPane.getTabs().get(0).setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFiles() {
        FXMLLoader paramsPane = new FXMLLoader(MainApp.class.getResource("view/control/files.fxml"));

        try {
            Parent view = paramsPane.load();
            filesController = paramsPane.getController();
            tabPane.getTabs().get(1).setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPreloader() {
        preloader = new ImageView(new Image("/files/preloader.gif"));
    }

    private void startPreloader() {
        runButton.setText("");
        runButton.setGraphic(preloader);
    }

    private void stopPreloader() {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                runButton.setText("Run");
                runButton.setGraphic(null);
            }
        });
    }

}
