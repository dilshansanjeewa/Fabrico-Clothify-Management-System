package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import model.dto.Employee;
import service.EmployeeService;
import service.impl.EmployeeServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AllEmployeeFormController implements Initializable {

    EmployeeService employeeService = new EmployeeServiceImpl();

    ObservableList<Employee> allEmployees;
    List<Employee> filteredEmployeeList;

    @FXML
    private ComboBox<String> comboSortByGender;

    @FXML
    private FlowPane flowCardContainer;

    @FXML
    private ListView<?> lstEmployeeSuggestions;

    @FXML
    private TextField txtSearch;

    private void setupSearch() {
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterEmployees(newValue);
        });

        comboSortByGender.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            filterEmployees(null);
        });
    }

    private void filterEmployees(String keyWord) {
        if (keyWord == null || keyWord.trim().isEmpty()) {
            filteredEmployeeList = new ArrayList<>(allEmployees);

        } else {
            filteredEmployeeList = allEmployees.stream()
                    .filter(emp ->
                            emp.getVisibleId().toLowerCase().contains(keyWord.toLowerCase()) ||
                            emp.getFirstName().toLowerCase().contains(keyWord.toLowerCase()) ||
                            emp.getLastName().toLowerCase().contains(keyWord.toLowerCase())
            ).toList();
        }

        String gender = comboSortByGender.getValue();

        if(gender != null && !gender.equals("All")){
            filteredEmployeeList = allEmployees.stream()
                    .filter(emp -> emp.getGender().equalsIgnoreCase(gender)).toList();
        }

        renderEmployeeCards(filteredEmployeeList);
    }

    private void renderEmployeeCards(List<Employee> list) {
        flowCardContainer.getChildren().clear();

        for (Employee employee : filteredEmployeeList) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employee_detail_card_form.fxml"));
                Parent empCard = loader.load();

                EmployeeDetailCardFormController controller  = loader.getController();
                controller.setCardData(employee);
                flowCardContainer.getChildren().add(empCard);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadEmpCards(){
        allEmployees = employeeService.getAllEmployees();
        filteredEmployeeList = new ArrayList<>(allEmployees);

        flowCardContainer.getChildren().clear();
        ObservableList<Employee> allEmployees = employeeService.getAllEmployees();

        for (Employee employee : allEmployees) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employee_detail_card_form.fxml"));
                Parent empCard = loader.load();

                EmployeeDetailCardFormController controller  = loader.getController();
                controller.setCardData(employee);
                flowCardContainer.getChildren().add(empCard);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sortTitleList = FXCollections.observableArrayList();
        sortTitleList.addAll("All", "Male", "Female");
        comboSortByGender.setItems(sortTitleList);
        comboSortByGender.setValue("All");

        loadEmpCards();
        setupSearch();
    }
}
