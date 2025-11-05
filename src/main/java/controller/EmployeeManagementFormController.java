package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManagementFormController implements Initializable {

//    @FXML
//    private ListView<?> lstSuggestions;

    @FXML
    private TextField txtSearchBar;

    @FXML
    void txtSearchBarOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchBarOnKeyReleased(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        lstSuggestions.setVisible(false);
    }
}
