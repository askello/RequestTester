package net.askello.requesttester;

import net.askello.requesttester.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public Stage getMainStage() {
        return mainStage;
    }

}
