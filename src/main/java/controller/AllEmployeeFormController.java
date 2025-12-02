package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class AllEmployeeFormController {

    @FXML
    private ComboBox<?> comboSortByGender;

    @FXML
    private FlowPane flowCardContainer;

    @FXML
    private ListView<?> lstEmployeeSuggestions;

    @FXML
    private TextField txtSearch;

    @FXML
    void comboSortByGenderOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

}
