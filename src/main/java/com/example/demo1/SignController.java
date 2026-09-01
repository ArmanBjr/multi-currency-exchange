package com.example.demo1;

import com.example.demo1.CurrencyManagement.Wallet;
import com.example.demo1.User.GetUser;
import com.example.demo1.User.User;
import com.example.demo1.User.UserDAO;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.demo1.User.UserDAO;
import javafx.stage.StageStyle;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;

public class SignController {
    private boolean isCaptchaVerified;
    @FXML
    private RadioButton SignInCaptchaButton;

    @FXML
    private Button SignInForgotPasswordButton;

    @FXML
    private AnchorPane SignInPage;

    @FXML
    private PasswordField SignInPasswordText;

    @FXML
    private Button SignInSignUpButton;

    @FXML
    private TextField SignInUsernameText;

    @FXML
    private Button SignInsigningbutton;

    @FXML
    private TextField SignUpEmailText;
    private String imageUrl;

    @FXML
    private ImageView SignUpProfileImage;


    @FXML
    private TextField SignUpFirstNameText;

    @FXML
    private TextField SignUpLastNameText;

    @FXML
    private AnchorPane SignUpPage;

    @FXML
    private PasswordField SignUpPasswordText;

    @FXML
    private TextField SignUpPhoneNumberText;

    @FXML
    private PasswordField SignUpRepeatPasswordText;

    @FXML
    private TextField SignUpUsernameText;

    @FXML
    private Hyperlink SignUpalreadyhaveanaccountButton;

    @FXML
    private Button SignUpsignupbutton;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    public void switchForm(ActionEvent event) {
        if (event.getSource() == SignInSignUpButton) {
            SignInPage.setVisible(false);
            SignUpPage.setVisible(true);
        } else if (event.getSource() == SignUpalreadyhaveanaccountButton) {
            SignInPage.setVisible(true);
            SignUpPage.setVisible(false);
        }
    }
    public void onSignUpsignupbuttonClicked(ActionEvent event) {
        String sql = "INSERT INTO info (email, username, password, FirstName, LastName, phoneNumber, Image, WalletID) VALUEs (?,?,?,?,?,?,?,?)";
        connect = DataBase.connectDb();
        try{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            prepare = connect.prepareStatement(sql);
            if (!UserDAO.isUsernameAvailable(SignInUsernameText.getText())) {
                Alert newAlert = new Alert(Alert.AlertType.ERROR);
                newAlert.setContentText("repetitive username!");
                newAlert.showAndWait();
            };
            prepare.setString(1, SignUpEmailText.getText());
            prepare.setString(2, SignUpUsernameText.getText());
            prepare.setString(3, SignUpPasswordText.getText());
            prepare.setString(4, SignUpFirstNameText.getText());
            prepare.setString(5, SignUpLastNameText.getText());
            prepare.setString(6, SignUpPhoneNumberText.getText());
            //prepare.setString(7, this.imageUrl);
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message!");
            alert1.setHeaderText(null);
            if (!Validator.isValidEmail(SignUpEmailText.getText())) {
                alert1.setContentText("invalid email!");
                alert1.showAndWait();
            }
            else if (!(Validator.isValidName(SignUpFirstNameText.getText()) ||
                    Validator.isValidName(SignUpLastNameText.getText()))){
                alert1.setContentText("invalid first or last name!");
                alert1.showAndWait();
            }
            else if (!Validator.isValidPassword(SignUpPasswordText.getText())) {
                alert1.setContentText("invalid password!");
                alert1.showAndWait();
            }
            else if (!Validator.isValidPhoneNumber(SignUpPhoneNumberText.getText())) {
                alert1.setContentText("invalid phone number!");
                alert1.showAndWait();
            }
            else if (!SignUpPasswordText.getText().equals(SignUpRepeatPasswordText.getText())) {
                alert1.setContentText("password and its repeat are not the same!");
                alert1.showAndWait();
            }
            else {
                UserDAO.getAllUsers();
                User user = new User(SignUpUsernameText.getText(), SignUpEmailText.getText(), SignUpPasswordText.getText(),
                        SignUpFirstNameText.getText(), SignUpLastNameText.getText(), SignUpPhoneNumberText.getText(),
                        SignUpProfileImage.getImage().getUrl());
                user.wallet = new Wallet();
                Wallet.addUser(SignUpUsernameText.getText());
                prepare.setString(7, user.getImageUrl());
                prepare.setInt(8, user.wallet.getID() - 1);
                prepare.execute();
                UserDAO.users.add(user);
                String subject = "Account Created!";
                alert.setContentText("successfully signed up");
                alert.showAndWait();
                String email = SignUpEmailText.getText();
                SignUpEmailText.setText("");
                SignUpUsernameText.setText("");
                SignUpPasswordText.setText("");
                SignUpFirstNameText.setText("");
                SignUpLastNameText.setText("");
                SignUpPhoneNumberText.setText("");
                SignUpRepeatPasswordText.setText("");
                SignUpPage.setVisible(false);
                SignInPage.setVisible(true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onSignInButtonClicked(ActionEvent event) {
        System.out.println("salam");
        String sql = "SELECT * FROM info WHERE username = ? and password = ?";
        connect = DataBase.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, SignInUsernameText.getText());
            prepare.setString(2, SignInPasswordText.getText());
            result = prepare.executeQuery();
            Alert alert;
            if (SignInUsernameText.getText().isEmpty() || SignInPasswordText.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("wrong input! try again!");
                alert.showAndWait();
            }
            else {
                if (!isCaptchaVerified) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please verify the CAPTCHA");
                    alert.showAndWait();
                }
                else if (result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    try (Socket socket = new Socket("localhost", 12345);
                         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        out.println(SignInUsernameText.getText());
                        out.println(SignInPasswordText.getText());
                        String response = in.readLine();
                        if ("Login successful".equals(response)) {
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("successfully Login!");
                            alert.showAndWait();
                            UserDAO.loadUsersFromDatabase();
                            User user = UserDAO.userFinder1(SignInUsernameText.getText());
                            GetUser.username = SignInUsernameText.getText();
                            user.wallet = Wallet.findUser(GetUser.username);
                            GetUser.user = user;
                            GetUser.imageUrl = user.getImageUrl();
                            SignInsigningbutton.getScene().getWindow().hide();
                            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username/password");
                    alert.showAndWait();
                }
                }
            } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    public void importImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Import Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));
        Stage stage = (Stage) SignUpPasswordText.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString(), 46, 45, false, true);
            SignUpProfileImage.setImage(image);
        }
    }
    public void setSignInCaptchaButtonClicked(ActionEvent event) throws InterruptedException {
        SignInCaptchaButton.setSelected(false);
        Thread captchaThread = new Thread(() -> {
            CaptchaApp captchaApp = new CaptchaApp();
            captchaApp.showCaptcha();
            isCaptchaVerified = CaptchaApp.isVerified;
            Platform.runLater(() -> SignInCaptchaButton.setSelected(CaptchaApp.isVerified));
        });
        captchaThread.start();
    }
    public void OnForgetPassClicked(ActionEvent event) throws IOException {
        Thread euroChartThread = new Thread(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("ForgetPass.fxml"));
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        euroChartThread.start();
    }
}