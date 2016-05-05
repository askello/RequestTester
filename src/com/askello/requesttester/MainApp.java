package com.askello.requesttester;

import com.askello.requesttester.controller.DataModalController;
import com.askello.requesttester.controller.MainController;
import com.askello.requesttester.model.Param;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/main.fxml"));
        Parent personOverview = loader.load();

        MainController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage.setTitle("Request Tester");
        primaryStage.setScene(new Scene(personOverview));
        primaryStage.show();
        mainStage = primaryStage;
    }

    public Param showDataModal(Param param) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dataModal.fxml"));
            Parent page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Param");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            DataModalController controller = loader.getController();
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
