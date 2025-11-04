package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainLoginFormController {

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXButton btnEmoloyee;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private BorderPane mainBoderPane;

    @FXML
    void btnAdminOnAction(ActionEvent event) {
        setContent("/view/admin_login_form.fxml");
    }

    @FXML
    void btnEmoloyeeOnAction(ActionEvent event) {
        setContent("/view/employee_login_form.fxml");
    }

    private void setContent(String path){
        try {
            mainBoderPane.setCenter(FXMLLoader.load(getClass().getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
