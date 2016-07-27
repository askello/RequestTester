package net.askello.requesttester.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.askello.requesttester.library.common.Cookie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CookiesModalController {

    @FXML
    private TextField keyField;
    @FXML
    private TextField valueField;
    @FXML
    private TextField maxAgeField;
    @FXML
    private TextField expiresField;
    @FXML
    private TextField pathField;
    @FXML
    private TextField domainField;
    @FXML
    private CheckBox httpOnlyCheckBox;
    @FXML
    private CheckBox secureCheckBox;

    private Cookie cookie;
    private Stage dialogStage;
    private String url;

    @FXML
    public void initialize() {

    }

    @FXML
    private void save() {
        if(keyField.getText().length()>0 && valueField.getText().length()>0) {
            cookie = new Cookie(keyField.getText(), valueField.getText());
            cookie.setMaxAge(maxAgeField.getText());
            cookie.setExpires(expiresField.getText());
            cookie.setPath(pathField.getText());
            cookie.setDomain(domainField.getText());
            cookie.setHttpOnly(httpOnlyCheckBox.isSelected());
            cookie.setSecure(secureCheckBox.isSelected());
        }
        dialogStage.close();
    }

    public void setCookie(Cookie cookie) {
        if(cookie == null) cookie = new Cookie();
        else valueField.requestFocus();

        keyField.setText(cookie.getKey());
        valueField.setText(cookie.getValue());
        maxAgeField.setText(cookie.getMaxAge());
        expiresField.setText(cookie.getExpires());
        pathField.setText(cookie.getPath());
        domainField.setText(cookie.getDomain()!=null ? cookie.getDomain() : getDomainFromUrl());
        httpOnlyCheckBox.setSelected(cookie.isHttpOnly());
        secureCheckBox.setSelected(cookie.isSecure());
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String getDomainFromUrl() {
        String domain = url;

        if(domain.substring(0, 7).equals("http://")) domain = domain.substring(7, domain.length()-1);
        if(domain.substring(0, 8).equals("https://")) domain = domain.substring(8, domain.length()-1);
        if(domain.substring(0, 4).equals("www.")) domain = domain.substring(4, domain.length()-1);
        if(domain.contains("/")) domain = domain.substring(0, domain.indexOf("/"));

        return domain;
    }

}
