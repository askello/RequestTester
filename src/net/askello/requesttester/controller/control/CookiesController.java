package net.askello.requesttester.controller.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.askello.requesttester.MainApp;
import net.askello.requesttester.controller.modal.CookiesModalController;
import net.askello.requesttester.library.common.Cookie;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by askello on 15.07.2016.
 */
public class CookiesController {

    @FXML
    private CheckBox useReceivedCheckbox;
    @FXML
    private CheckBox useCookiesCheckbox;
    @FXML
    private TableView cookiesTable;
    @FXML
    private TableColumn<Cookie, String> keyColumn;
    @FXML
    private TableColumn<Cookie, String> valueColumn;
    @FXML
    private TableColumn<Cookie, String> maxAgeColumn;
    @FXML
    private TableColumn<Cookie, String> expiresColumn;
    @FXML
    private TableColumn<Cookie, String> pathColumn;
    @FXML
    private TableColumn<Cookie, String> domainColumn;
    @FXML
    private TableColumn<Cookie, String> httpOnlyColumn;
    @FXML
    private TableColumn<Cookie, String> secureColumn;

    private ObservableList<Cookie> cookies = FXCollections.observableArrayList();
    private Cookie selectedCookie;

    @FXML
    public Button addCookieButton;
    @FXML
    public Button editCookieButton;
    @FXML
    public Button removeCookieButton;

    private MainApp mainApp;
    private TextField url;

    @FXML
    public void initialize() {
        initCookiesTable();
    }

    @FXML
    private void addCookieButtonHandler() {
        Cookie newCookie = showCookiesModal(null);
        if(newCookie != null)
            cookies.add(newCookie);
    }

    @FXML
    private void editCookieButtonHandler() {
        Cookie newCookie = showCookiesModal(selectedCookie);
        if(newCookie != null) {
            int index = cookies.indexOf(selectedCookie);
            cookies.set(index, newCookie);
        }
    }

    @FXML
    private void removeCookieButtonHandler() {
        cookies.remove(selectedCookie);
    }

    private void initCookiesTable() {
        keyColumn.setCellValueFactory(new PropertyValueFactory<Cookie, String>("key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Cookie, String>("value"));
        maxAgeColumn.setCellValueFactory(new PropertyValueFactory<Cookie, String>("MaxAge"));
        expiresColumn.setCellValueFactory(new PropertyValueFactory<Cookie, String>("expires"));
        pathColumn.setCellValueFactory(new PropertyValueFactory<Cookie, String>("path"));
        domainColumn.setCellValueFactory(new PropertyValueFactory<Cookie, String>("domain"));
        httpOnlyColumn.setCellValueFactory(new PropertyValueFactory<Cookie, String>("HttpOnly"));
        secureColumn.setCellValueFactory(new PropertyValueFactory<Cookie, String>("secure"));
        cookiesTable.setItems(cookies);
        cookiesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedCookie = (Cookie)newValue);
    }

    public Cookie showCookiesModal(Cookie cookie) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/modal/cookiesModal.fxml"));
            Parent page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Cookie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getMainStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the cookie into the controller.
            CookiesModalController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUrl(url.getText());
            controller.setCookie(cookie);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getCookie();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Cookie> getCookies() {
        HashMap<String, Cookie> cookies = new HashMap<>();

        if(useCookiesCheckbox.isSelected())
            for (Cookie cookie : this.cookies)
                cookies.put(cookie.getKey(), cookie);

        return cookies;
    }

    public boolean useReceived() {
        return useReceivedCheckbox.isSelected();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setUrlField(TextField url) {
        this.url = url;
    }

    public void setCookies(ArrayList<Cookie> cookies) {
        for(Cookie cookie : cookies) {
            boolean sameExists = false;

            for(int i=0; i<this.cookies.size(); i++) {
                if(cookie.getKey().equals(this.cookies.get(i).getKey())) {
                    sameExists = true;
                    if(!cookie.equals(this.cookies.get(i))) {
                        this.cookies.remove(i);
                        this.cookies.add(cookie);
                    }
                }
            }

            if(!sameExists) this.cookies.add(cookie);
        }
    }

}
