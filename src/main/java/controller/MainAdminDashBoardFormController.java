package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainAdminDashBoardFormController implements Initializable {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");

    private Stage posStage;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnEmployees;


    @FXML
    private JFXButton btnItems;

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
        setContent("/view/dashboard_content_form.fxml");
    }

    @FXML
    void btnEmployeesOnAction(ActionEvent event) {
        setContent("/view/employee_management_form.fxml");
    }

    @FXML
    void btnItemsOnAction(ActionEvent event) {
        setContent("/view/item_management_form.fxml");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) {

    }

    @FXML
    void btnPosOnAction(ActionEvent event) {
        posStage = new Stage();
        try {
            posStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/POS.fxml"))));
            posStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnReportsOnAction(ActionEvent event) {

    }

    @FXML
    void btnSettingsOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupplyersOnAction(ActionEvent event) {
        setContent("/view/supplier_management_form.fxml");
    }

    public void setAdminData(String[] adminData){
        imgProfilePic.setImage(new Image("file:"+adminData[1]));
        lblUserName.setText(adminData[0]);
        lblRole.setText("Admin");
    }

    private void setContent(String path) {
        try {
            mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDateTime(){
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalDateTime now = LocalDateTime.now();
            lblTime.setText(now.format(timeFormatter));
            lblData.setText(now.format(dateTimeFormatter));
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Circle clip = new Circle(28, 28,25);
        imgProfilePic.setClip(clip);

        setDateTime();

        setContent("/view/dashboard_content_form.fxml");
    }
}
