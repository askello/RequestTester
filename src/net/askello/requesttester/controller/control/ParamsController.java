package net.askello.requesttester.controller.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.askello.requesttester.MainApp;
import net.askello.requesttester.controller.modal.ParamsModalController;
import net.askello.requesttester.library.common.Param;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by askello on 15.07.2016.
 */
public class ParamsController {

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

    @FXML
    private ComboBox<String> requestChooser;

    private MainApp mainApp;

    @FXML
    public void initialize() {
        initRequestChooser();
        initParamsTable();
    }

    @FXML
    private void addParamButtonHandler() {
        Param newParam = showParamsModal(null);
        if(newParam != null)
            params.add(newParam);
    }

    @FXML
    private void editParamButtonHandler() {
        Param newParam = showParamsModal(selectedParam);
        if(newParam != null) {
            int index = params.indexOf(selectedParam);
            params.set(index, newParam);
        }
    }

    @FXML
    private void removeParamButtonHandler() {
        params.remove(selectedParam);
    }

    private void initRequestChooser() {
        requestChooser.getItems().add("OPTIONS");
        requestChooser.getItems().add("GET");
        requestChooser.getItems().add("HEAD");
        requestChooser.getItems().add("POST");
        requestChooser.getItems().add("PUT");
        requestChooser.getItems().add("PATCH");
        requestChooser.getItems().add("DELETE");
        requestChooser.getItems().add("TRACE");
        requestChooser.getItems().add("CONNECT");

        requestChooser.setValue("POST");
    }

    private void initParamsTable() {
        keyParamColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("key"));
        valueParamColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("value"));
        paramsTable.setItems(params);
        paramsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedParam = (Param)newValue);

        params.add(new Param("action", "test/test"));
        params.add(new Param("language", "ru"));
        params.add(new Param("mtoken", "7ceerc3npgq3nk5rq1v238up42"));
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
            dialogStage.initOwner(mainApp.getMainStage());
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

    public HashMap<String, String> getParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        for(Param param : this.params)
            params.put(param.getKey(), param.getValue());

        return params;
    }

    public String getMethod() {
        return requestChooser.getValue();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
