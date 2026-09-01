package com.example.demo1.User;

import java.sql.*;

public class UpdateNegativeValues {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/exchange";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String selectQuery = "SELECT date, time, usd, euro, toman, yen, GBP FROM currency_rates";
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(selectQuery);

            String updateQuery = "UPDATE currency_rates SET usd = ?, euro = ?, toman = ?, yen = ?, GBP = ? WHERE date = ? AND time = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                Time time = resultSet.getTime("time");
                double usd = resultSet.getDouble("usd");
                double euro = resultSet.getDouble("euro");
                double toman = resultSet.getDouble("toman");
                double yen = resultSet.getDouble("yen");
                double gpb = resultSet.getDouble("GBP");

                // Convert negative values to positive
                usd = Math.abs(usd);
                euro = Math.abs(euro);
                toman = Math.abs(toman);
                yen = Math.abs(yen);
                gpb = Math.abs(gpb);

                // Round to 2 decimal places
                usd = Math.round(usd * 100.0) / 100.0;
                euro = Math.round(euro * 100.0) / 100.0;
                toman = Math.round(toman * 100.0) / 100.0;
                yen = Math.round(yen * 100.0) / 100.0;
                gpb = Math.round(gpb * 100.0) / 100.0;

                // Update the record
                updateStatement.setDouble(1, usd);
                updateStatement.setDouble(2, euro);
                updateStatement.setDouble(3, toman);
                updateStatement.setDouble(4, yen);
                updateStatement.setDouble(5, gpb);
                updateStatement.setDate(6, date);
                updateStatement.setTime(7, time);

                updateStatement.addBatch();
            }

            // Execute the batch update
            updateStatement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
