package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class DashboardContentFormController implements Initializable {

    @FXML
    private Label lblGreeting;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGreetingMessage();
    }
}
