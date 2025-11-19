package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.impl.SignInRepositoryImpl;
import service.SignInService;
import service.impl.SignInServiceImpl;

import java.io.IOException;

public class SignInFormController {

    private Stage stage;

    private SignInService signInService = new SignInServiceImpl();

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void btnSignInonAction(ActionEvent event) {
        if (checkInputFields()){
            String [] adminData = signInService.IsExist(txtEmail.getText(), txtPassword.getText());
            if(adminData != null){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_admin_dashboard_form.fxml"));
                    Parent root = loader.load();
                    MainAdminDashBoardFormController controller = loader.getController();
                    controller.setAdminData(adminData);

                    stage = (Stage) txtEmail.getScene().getWindow();
                    stage.setTitle("Admin Panel");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                setError("Invalid Password or Email Address");
            }
        }
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

    private void setError(String error){
        if (!lblError.isVisible()){
            lblError.setVisible(true);
        }

        lblError.setText(error);
    }

    private boolean checkInputFields(){
        if(txtEmail.getText() == null || txtEmail.getText().isEmpty()){
            setError("Email Address Required");
            return false;
        } else if (txtPassword.getText() == null || txtPassword.getText().isEmpty()) {
            setError("Password Required");
            return false;
        }
        return true;
    }

}
