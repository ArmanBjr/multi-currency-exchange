package com.example.demo1.Server;

import com.example.demo1.DataBase;
import com.example.demo1.User.GetUser;
import com.example.demo1.User.User;
import com.example.demo1.User.UserDAO;
import com.example.demo1.Utilities.Values;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.*;
public class AdminController {

    @FXML
    private Button AdminDisconnectUser;

    @FXML
    private Label AdminUsernameLabel;
    @FXML
    private TableColumn<User, String> AdminEmail;

    @FXML
    private TableColumn<User, String> AdminFirstName;

    @FXML
    private TableColumn<User, String> AdminLastName;

    @FXML
    private TableColumn<User, String> AdminPassword;

    @FXML
    private TableColumn<User, String> AdminPhoneNumber;

    @FXML
    private TableView<User> AdminTable;

    @FXML
    private TableColumn<User, String> AdminUsername;

    @FXML
    private Button MarketSituation;

    @FXML
    public void initialize() {
        AdminUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        AdminFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        AdminLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        AdminEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        AdminPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        AdminPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        AdminUsername.setCellFactory(new Callback<TableColumn<User, String>, TableCell<User, String>>() {
            @Override
            public TableCell<User, String> call(TableColumn<User, String> param) {
                return new TableCell<User, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            if (Server.isUserConnected(item)) {
                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.RED);
                            }
                        }
                    }
                };
            }
        });

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        displayTable();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, 6000);
    }

    public void selectUser() {
        User user = AdminTable.getSelectionModel().getSelectedItem();
        int num = AdminTable.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) return;
        AdminUsernameLabel.setText(user.getUsername());
    }
    public void displayTable() throws SQLException {
        List<User> users = UserDAO.getAllUsersAsAList();
        AdminTable.getItems().setAll(users);
    }

    public void onAdminDisconnectUserClicked(javafx.event.ActionEvent event) {
        boolean isOk = Server.disconnectUser(AdminUsernameLabel.getText());
        if (isOk) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("user disconnected successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("User is not connected!");
            alert.showAndWait();
        }
    }
    public void updateMarketSituation(javafx.event.ActionEvent event) {
        if (MarketSituation.getText().equals("Open")) {
            MarketSituation.setText("Closed");
            String updateQuery = "UPDATE id SET situation = 0";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/id_getter", "root", "");
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            MarketSituation.setText("Open");
            String updateQuery = "UPDATE id SET situation = 1";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/id_getter", "root", "");
                 PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void updateWallets(javafx.event.ActionEvent event) {
        double totalValue = 0.0;
        try (Connection conn = DataBase.connectDb()) {
            String query = "SELECT username, euro_currency, usd_currency, yen_currency, toman_currency, GBP_currency, property FROM wallet";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    double euro = rs.getDouble("euro_currency");
                    double usd = rs.getDouble("usd_currency");
                    double yen = rs.getDouble("yen_currency");
                    double toman = rs.getDouble("toman_currency");
                    double gbp = rs.getDouble("GBP_currency");
                    double property = rs.getDouble("property");
                    double euroValue = euro / Values.Value("euro");
                    double usdValue = usd / Values.Value("usd");
                    double yenValue = yen / Values.Value("yen");
                    double tomanValue = toman / Values.Value("toman");
                    double gbpValue = gbp / Values.Value("GBP");
                    totalValue += euroValue + usdValue + yenValue + tomanValue + gbpValue + property;
                    String updateQuery = "UPDATE wallet SET euro_currency = 0, usd_currency = 0, yen_currency = 0, toman_currency = 0, GBP_currency = 0, property = 0 WHERE username = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, username);
                        updateStmt.executeUpdate();
                    }
                }

                String updateQuery = "UPDATE id SET AdminProperty = ?";

                try (Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/id_getter", "root", "");
                     PreparedStatement pstmt = connect.prepareStatement(updateQuery)) {
                    pstmt.setDouble(1, totalValue);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("you have ekhtelased!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/id_getter", "root", "");
            String selectSQL = "SELECT AdminProperty FROM id";
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double currencValue = resultSet.getDouble("AdminProperty");
                double newValue = currencValue + totalValue;
                String updateSQL = "UPDATE id SET AdminProperty = ?";
                preparedStatement = connection.prepareStatement(updateSQL);
                preparedStatement.setDouble(1, newValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}