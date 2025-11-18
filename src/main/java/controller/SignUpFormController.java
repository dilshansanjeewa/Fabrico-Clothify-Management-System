package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {

    Stage stage;
    FileChooser fileChooser;
    private final String adminImageDirectory = "admin_image/";
    File selectedImage;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnSendOtp;

    @FXML
    private JFXButton btnSelectImg;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private JFXButton btnVerify;

    @FXML
    private ImageView imgAdmin;

    @FXML
    private Label lblWerning;

    @FXML
    private Label lblEnterOtp;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOtp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void btnBackinAction(ActionEvent event) {
        try {
            stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sign_in_form.fxml"))));
            stage.setTitle("Sign In");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSendOtponAction(ActionEvent event) {
        System.out.println(getImagePath());
    }

    @FXML
    void btnSignUponAction(ActionEvent event) {

    }

    @FXML
    void btnVerifyonAction(ActionEvent event) {

    }

    @FXML
    void imgOnMouseClicked(MouseEvent event) {
        File adminImgFile = fileChooser.showOpenDialog(null);

        if(adminImgFile != null) {
            selectedImage = adminImgFile;
            imgAdmin.setImage(new Image(selectedImage.toURI().toString()));
        }

        System.out.println("image clicked");
    }

    private String getImagePath(){
        return saveImage();
    }

    private String saveImage(){
        try {
            File destinationFile = new File(adminImageDirectory, System.currentTimeMillis() +"_"+ selectedImage.getName());
            Files.copy(selectedImage.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return destinationFile.getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File directory = new File(adminImageDirectory);
        if(!directory.exists()){
            directory.mkdir();
        }

        Rectangle clip = new Rectangle(imgAdmin.getFitWidth(), imgAdmin.getFitHeight());
        clip.setArcWidth(200); // Adjust for desired roundness
        clip.setArcHeight(200);
        imgAdmin.setClip(clip);
        //------------------------------------------------------------------------------------

        fileChooser = new FileChooser();
        fileChooser.setTitle("Select Admin Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
    }
}
