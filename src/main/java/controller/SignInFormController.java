package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInFormController {

    private Stage stage;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void btnSignInonAction(ActionEvent event) {

    }

    @FXML
    void btnSignUponAction(ActionEvent event) {
        try {
            stage = (Stage) btnSignIn.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sign_up_form.fxml"))));
            stage.setResizable(false);
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
