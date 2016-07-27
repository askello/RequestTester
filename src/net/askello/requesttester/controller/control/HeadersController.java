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
import net.askello.requesttester.controller.modal.HeadersModalController;
import net.askello.requesttester.controller.modal.ParamsModalController;
import net.askello.requesttester.library.common.Param;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by askello on 15.07.2016.
 */
public class HeadersController {

    @FXML
    private TableView headersTable;
    @FXML
    private TableColumn<Param, String> keyHeaderColumn;
    @FXML
    private TableColumn<Param, String> valueHeaderColumn;

    private ObservableList<Param> headers = FXCollections.observableArrayList();
    private Param selectedHeader;

    @FXML
    public Button addHeaderButton;
    @FXML
    public Button editHeaderButton;
    @FXML
    public Button removeHeaderButton;

    private MainApp mainApp;

    @FXML
    public void initialize() {
        initHeadersTable();
    }

    @FXML
    private void addHeaderButtonHandler() {
        Param newParam = showParamsModal(null);
        if(newParam != null)
            headers.add(newParam);
    }

    @FXML
    private void editHeaderButtonHandler() {
        Param newParam = showParamsModal(selectedHeader);
        if(newParam != null) {
            int index = headers.indexOf(selectedHeader);
            headers.set(index, newParam);
        }
    }

    @FXML
    private void removeHeaderButtonHandler() {
        headers.remove(selectedHeader);
    }

    private void initHeadersTable() {
        keyHeaderColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("key"));
        valueHeaderColumn.setCellValueFactory(new PropertyValueFactory<Param, String>("value"));
        headersTable.setItems(headers);
        headersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedHeader = (Param)newValue);

        headers.add(new Param("Accept-Charset", "UTF-8"));
        headers.add(new Param("User-Agent", "Java/"+System.getProperty("java.version")));
    }

    public Param showParamsModal(Param param) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/modal/headersModal.fxml"));
            Parent page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Header");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getMainStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            HeadersModalController controller = loader.getController();
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

    public HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        for(Param header : this.headers)
            headers.put(header.getKey(), header.getValue());

        return headers;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
