package com.example.demo1.Utilities;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Values {
    public static double getChangePercentage(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT " + column + " FROM currency_rates WHERE CONCAT(date, ' ', time) <= ? ORDER BY CONCAT(date, ' ', time) DESC LIMIT 2";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            double latestPrice = resultSet.getDouble(column);
            if (resultSet.next()) {
                double previousPrice = resultSet.getDouble(column);
                return ((latestPrice - previousPrice) / previousPrice) * 100;
            }
        }
        return 0.0;
    }
    public static double getLowestPrice(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT MIN(" + column + ") AS lowest FROM currency_rates WHERE CONCAT(date, ' ', time) <= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("lowest");
        }
        return 0.0;
    }
    public static double getHighestPrice(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT MAX(" + column + ") AS highest FROM currency_rates WHERE CONCAT(date, ' ', time) <= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("highest");
        }
        return 0.0;
    }
    public static int getID() {
        String query = "SELECT ID FROM id";
        int id = -1;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/id_getter", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                id = rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
    public static int getOrderID() {
        String query = "SELECT orderID FROM id";
        int id = -1;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/id_getter", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                id = rs.getInt("orderID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public static void incrementOrderID() {
        int currentID = getOrderID();
        int newID = currentID + 1;

        String updateQuery = "UPDATE id SET orderID = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/id_getter", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setInt(1, newID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void incrementID() {
        int currentID = getID();
        int newID = currentID + 1;

        String updateQuery = "UPDATE id SET ID = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/id_getter", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setInt(1, newID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static String jdbcURL = "jdbc:mysql://localhost:3306/exchange";
    private static String username = "root";
    private static String password = "";
    public static double Value(String name) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String query = "SELECT euro, usd, GBP, toman, yen FROM currency_rates WHERE CONCAT(date, ' ', time) <= ? ORDER BY CONCAT(date, ' ', time) DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, formattedDate + " " + formattedTime);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(name);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return now.format(timeFormatter);
    }
    public static String getCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return today.format(dateFormatter);
    }
    public static boolean isMarketOpenGetter() {
        String query = "SELECT situation FROM id";
        boolean res = false;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/id_getter", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int help = rs.getInt("situation");
                if (help == 1) {
                    res = true;
                } else {
                    res = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
