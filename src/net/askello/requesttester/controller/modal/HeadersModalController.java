package net.askello.requesttester.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.askello.requesttester.library.common.Param;

public class HeadersModalController {

    @FXML
    private ComboBox keyComboBox;
    @FXML
    private TextField valueField;
    @FXML
    public Button saveButton;

    private Param param;
    private Stage dialogStage;

    @FXML
    public void initialize() {
        initKeys();
    }

    @FXML
    private void save() {
        if(keyComboBox.getValue().toString().length()>0 && valueField.getText().length()>0) {
            param = new Param(keyComboBox.getValue().toString(), valueField.getText());
        }
        dialogStage.close();
    }

    public void setParam(Param param) {
        if(param != null) {
            keyComboBox.setValue(param.getKey());
            valueField.setText(param.getValue());
        }
        valueField.requestFocus();
    }

    public Param getParam() {
        return param;
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    private void initKeys() {
        keyComboBox.setValue("Choose header...");

        keyComboBox.getItems().addAll(
            "Accept",
            "Accept-Charset",
            "Accept-Encoding",
            "Accept-Language",
            "Accept-Ranges",
            "Age",
            "Allow",
            "Alternates",
            "Authorization",
            "Cache-Control",
            "Connection",
            "Content-Base",
            "Content-Disposition",
            "Content-Encoding",
            "Content-Language",
            "Content-Length",
            "Content-Location",
            "Content-MD5",
            "Content-Range",
            "Content-Type",
            "Content-Version",
            "Date",
            "Derived-From",
            "ETag",
            "Expect",
            "Expires",
            "From",
            "Host",
            "If-Match",
            "If-Modified-Since",
            "If-None-Match",
            "If-Range",
            "If-Unmodified-Since",
            "Last-Modified",
            "Link",
            "Location",
            "Max-Forwards",
            "MIME-Version",
            "Pragma",
            "Proxy-Authenticate",
            "Proxy-Authorization",
            "Public",
            "Range",
            "Referer",
            "Retry-After",
            "Server",
            "Title",
            "TE",
            "Trailer",
            "Transfer-Encoding",
            "Upgrade",
            "URI",
            "User-Agent",
            "Vary",
            "WWW-Authenticate"
        );
    }

}
