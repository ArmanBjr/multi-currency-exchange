package com.example.demo1;

import com.example.demo1.User.GetUser;
import com.example.demo1.User.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileEditController implements Initializable {

    @FXML
    private Label EditPageUsername;
    @FXML
    private TextField EditPageEmail;

    @FXML
    private TextField EditPageFirstName;

    @FXML
    private ImageView EditPageImage;

    @FXML
    private Button EditPageImport;

    @FXML
    private TextField EditPageLastName;

    @FXML
    private PasswordField EditPagePassword;

    @FXML
    private TextField EditPagePhoneNumber;

    public void importImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Import Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));
        Stage stage = (Stage) EditPageImport.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString(), 46, 45, false, true);
            EditPageImage.setImage(image);
        }
    }
    public void onEditClicked(ActionEvent e) {
        String email = EditPageEmail.getText().isEmpty() ? null : EditPageEmail.getText();
        String firstName = EditPageFirstName.getText().isEmpty() ? null : EditPageFirstName.getText();
        String lastName = EditPageLastName.getText().isEmpty() ? null : EditPageLastName.getText();
        String password = EditPagePassword.getText().isEmpty() ? null : EditPagePassword.getText();
        String phoneNumber = EditPagePhoneNumber.getText().isEmpty() ? null : EditPagePhoneNumber.getText();
        String imageUrl = EditPageImage.getImage().getUrl();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("your input does not have the correct format!");
        if (email != null) {
            if (!Validator.isValidEmail(email)) {
                alert.showAndWait();
                return;
            }
        }
        if (firstName != null) {
            if (!Validator.isValidName(firstName)) {
                alert.showAndWait();
                return;
            }
        }
        if (lastName != null) {
            if (!Validator.isValidName(lastName)) {
                alert.showAndWait();
                return;
            }
        }
        if (password != null) {
            if (!Validator.isValidPassword(password)) {
                alert.showAndWait();
                return;
            }
        }
        if (phoneNumber != null) {
            if (!Validator.isValidPhoneNumber(phoneNumber)) {
                alert.showAndWait();
                return;
            }
        }
        System.out.println("before editUser");
        EditUser(GetUser.username, email, firstName, lastName, password, imageUrl, phoneNumber);
        System.out.println("after editUser");
    }
    public void EditUser(String username, String email, String firstName, String lastName, String password, String imageUrl, String phoneNumber) {
            StringBuilder sql = new StringBuilder("UPDATE info SET ");
            boolean firstField = true;
            if (email != null) {
                sql.append("email = ?");
                firstField = false;
            }
            if (firstName != null) {
                if (!firstField) {
                    sql.append(", ");
                }
                sql.append("firstName = ?");
                firstField = false;
            }
            if (lastName != null) {
                if (!firstField) {
                    sql.append(", ");
                }
                sql.append("lastName = ?");
                firstField = false;
            }
            if (password != null) {
                if (!firstField) {
                    sql.append(", ");
                }
                sql.append("password = ?");
            } if (phoneNumber != null) {
                if (!firstField) {
                    sql.append(", ");
                }
                sql.append("phoneNumber = ?");
            } if (imageUrl != null) {
            if (!firstField) {
                sql.append(", ");
            }
            sql.append("Image = ?");
        }

            sql.append(" WHERE username = ?");
            try (Connection conn = DataBase.connectDb();
                 PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

                int paramIndex = 1;

                if (email != null) {
                    pstmt.setString(paramIndex++, email);
                }
                if (firstName != null) {
                    pstmt.setString(paramIndex++, firstName);
                }
                if (lastName != null) {
                    pstmt.setString(paramIndex++, lastName);
                }
                if (password != null) {
                    pstmt.setString(paramIndex++, password);
                }
                if (phoneNumber != null) {
                    pstmt.setString(paramIndex++, phoneNumber);
                } if (imageUrl != null) {
                    pstmt.setString(paramIndex++, imageUrl);
                }
                pstmt.setString(paramIndex, username);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    UserDAO.getAllUsers();
                    alert.setContentText("User updated successfully!");
                    alert.showAndWait();
                    EditPagePassword.getScene().getWindow().hide();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EditPageUsername.setText(GetUser.username);
    }
}
