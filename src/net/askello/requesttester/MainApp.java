package net.askello.requesttester;

import net.askello.requesttester.controller.FilesModalController;
import net.askello.requesttester.controller.ParamsModalController;
import net.askello.requesttester.controller.MainController;
import net.askello.requesttester.model.Param;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public Param showParamsModal(Param param) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/modal/paramsModal.fxml"));
            Parent page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Param");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ParamsModalController controller = loader.getController();
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
            dialogStage.initOwner(mainStage);
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
