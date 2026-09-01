package com.example.demo1;

import com.example.demo1.User.GetUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class ForgetPassController {
    @FXML
    private TextField emailField;
    @FXML
    private Button forgetPassButton;

    @FXML
    private void initialize() {
        forgetPassButton.setOnAction(e -> OnForgetPassClicked());
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        while (password.length() < 8) { // Password length
            int index = (int) (rnd.nextFloat() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    public void OnForgetPassClicked() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Email field is empty");
            return;
        }
        try (Connection conn = DataBase.connectDb()) {
            String query = "SELECT * FROM info WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String newPassword = generateRandomPassword();
                updatePasswordInDatabase(email, newPassword);
                if (!AppConfig.isSmtpConfigured()) {
                    showAlert(AlertType.ERROR, "SMTP not configured",
                            "Set SMTP_USER and SMTP_APP_PASSWORD before using password reset.");
                    return;
                }
                sendEmail(email, newPassword);
                GetUser.ForgetEmail = email;
                showAlert(AlertType.INFORMATION, "Success", "A new password has been sent to your email.");
                forgetPassButton.getScene().getWindow().hide();
            } else {
                showAlert(AlertType.ERROR, "Error", "Email not found in database");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "An error occurred");
        }
    }

    private void updatePasswordInDatabase(String email, String newPassword) {
        try (Connection conn = DataBase.connectDb()) {
            String query = "UPDATE info SET password = ? WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String email, String newPassword) throws MessagingException {
        SendEmail.emailSender(
                email,
                "Your new password is: " + newPassword,
                "Password Reset"
        );
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
