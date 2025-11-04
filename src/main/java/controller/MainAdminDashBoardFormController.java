package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainAdminDashBoardFormController implements Initializable {

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnEmployees;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnPos;

    @FXML
    private JFXButton btnReports;

    @FXML
    private JFXButton btnSettings;

    @FXML
    private JFXButton btnSupplyers;

    @FXML
    private ImageView imgProfilePic;

    @FXML
    private Label lblData;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserName;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    void btnDashboardOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmployeesOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) {

    }

    @FXML
    void btnPosOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportsOnAction(ActionEvent event) {

    }

    @FXML
    void btnSettingsOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupplyersOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Circle clip = new Circle(28, 28,25);
        imgProfilePic.setClip(clip);
    }
}
