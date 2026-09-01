package com.example.demo1.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecordsAfterDate {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/exchange";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String deleteQuery = "DELETE FROM currency_rates WHERE date > '2023-06-06'";

            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            int rowsDeleted = deleteStatement.executeUpdate();

            System.out.println("Number of rows deleted: " + rowsDeleted);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
