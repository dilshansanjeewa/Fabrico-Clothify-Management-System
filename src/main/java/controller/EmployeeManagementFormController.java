package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class EmployeeManagementFormController implements Initializable {

    @FXML
    private JFXButton btnAll;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    void btnAllOnAction(ActionEvent event) {
        loadContent("/view/all_employee_form.fxml");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        loadContent("/view/delete_employee_form.fxml");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        loadContent("/view/save_employee_form.fxml");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        loadContent("/view/update_employee_form.fxml");
    }

    private void loadContent(String url){
        try {
            mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource(url)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadContent("/view/all_employee_form.fxml");
    }
}
