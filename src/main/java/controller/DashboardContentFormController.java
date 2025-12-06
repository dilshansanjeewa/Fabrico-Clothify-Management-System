package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
import service.DashboardService;
import service.impl.DashboardServiceImpl;

public class DashboardContentFormController implements Initializable {

    DashboardService dashboardService = new DashboardServiceImpl();

    @FXML
    private ImageView imgStockAlert;

    @FXML
    private Label lblGreeting;

    @FXML
    private Label lblItemCount;

    @FXML
    private Label lblMonthlyRevenue;

    @FXML
    private Label lblOrderCount;

    @FXML
    private Label lblStockAlert;

    @FXML
    private Label lblTodayRevenue;

    private void checkStock() {
        int itmCount = dashboardService.checkLowStockItems();

        if (itmCount == 0){
            lblStockAlert.setText("No low-stock items");
            setAlertImage("images/icons/tick icon.png");
            lblItemCount.setText("0");
        } else {
            lblStockAlert.setText("Stock Low");
            setAlertImage("images/icons/warning icon.png");
            lblItemCount.setText(String.valueOf(itmCount));
        }    }

    private void setGreetingMessage() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();

        String greeting = null;

        if(hour >= 5 && hour < 12) {
            greeting = "Goog Morning Admin";
        } else if (hour >= 12 && hour < 15) {
            greeting = "Good Afternoon Admin";
        } else if (hour >= 15 && hour < 21) {
            greeting = "Good Evening Admin";
        }else {
            greeting = "Good Night Admin";
        }

        lblGreeting.setText(greeting);
    }

    private void setAlertImage(String path){
        imgStockAlert.setImage(new Image(path));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGreetingMessage();
        checkStock();

        lblOrderCount.setText(dashboardService.getTodayOrderCount());
        lblTodayRevenue.setText(dashboardService.getTodayRevenue());
        lblMonthlyRevenue.setText(dashboardService.getThisMonthRevenue());
    }
}
