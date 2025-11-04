package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowFormController implements Initializable {

    @FXML
    private JFXButton btnGetStarted;

    @FXML
    void btnGetStartedOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnGetStarted.setTranslateY(70.0);

        // buton slide up animation
        TranslateTransition slideUp = new TranslateTransition(Duration.millis(2000), btnGetStarted);
        slideUp.setFromY(80);
        slideUp.setToY(0);
        slideUp.setCycleCount(1);
        slideUp.setAutoReverse(false);

        // start animation
        slideUp.play();
    }
}
