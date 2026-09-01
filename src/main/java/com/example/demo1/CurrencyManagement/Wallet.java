package com.example.demo1.CurrencyManagement;

import com.example.demo1.DataBase;
import com.example.demo1.User.GetUser;
import com.example.demo1.User.User;
import com.example.demo1.User.UserDAO;
import com.example.demo1.Utilities.Values;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Wallet {
    public static int ID = 1;
    public int thisUserId;
    public int getID() {
        return this.thisUserId;
    }
    public double currentProperty; // based on the base currency
    public double CurrentEuro;
    public double CurrentUsd;
    public double CurrentToman;
    public double CurentGbp;
    public double CurrentYen;
    public Wallet() {
        currentProperty = 0;
        CurentGbp = 0;
        CurrentEuro = 0;
        CurrentToman = 0;
        CurrentUsd = 0;
        CurrentYen = 0;
        thisUserId = Values.getID();
        Values.incrementID();
    }
    public Wallet(int id, double euro, double usd, double gbp, double toman, double yen) {
        this.thisUserId = id;
        this.CurrentEuro = euro;
        this.CurrentUsd = usd;
        this.CurentGbp = gbp;
        this.CurrentToman = toman;
        this.CurrentYen = yen;
    }
    public double setCurrentProperty() {
        double Property = 0;
        try (Connection connection = DataBase.connectDb()) {
            String query = "SELECT property FROM wallet WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, GetUser.username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Property = resultSet.getDouble("property");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        double result = 0;
        result += CurrentToman / CurrencyGetter("toman");
        result += CurrentYen / CurrencyGetter("yen");
        result += CurrentUsd / CurrencyGetter("usd");
        result += CurrentEuro / CurrencyGetter("euro");
        result += CurentGbp / CurrencyGetter("GBp");
        this.currentProperty = result;
        this.currentProperty += Property;
        return currentProperty;
    }
    public double CurrencyGetter(String name) {
        try (Connection connection = DataBase.connectDb()) {
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
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0.0;
    }
    public static void addUser(String username) {
        String countQuery = "SELECT COUNT(*) FROM wallet";
        String insertQuery = "INSERT INTO wallet (username, id, euro_currency, usd_currency, GBP_currency, toman_currency, yen_currency, property) VALUES (?, ?, 0, 0, 0, 0, 0, 0)";

        try (Connection conn = DataBase.connectDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countQuery)) {

            int rowCount = 0;
            if (rs.next()) {
                rowCount = rs.getInt(1);
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, username);
                pstmt.setInt(2, rowCount + 1);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCash(String username, Double euro, Double usd, Double gpb, Double toman, Double yen) {
        String selectQuery = "SELECT euro_currency, usd_currency, GBP_currency, toman_currency, yen_currency FROM wallet WHERE username = ?";
        String updateQuery = "UPDATE wallet SET euro_currency = ?, usd_currency = ?, GBP_currency = ?, toman_currency = ?, yen_currency = ? WHERE username = ?";

        try (Connection conn = DataBase.connectDb();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                double currentEuro = rs.getDouble("euro_currency");
                double currentUsd = rs.getDouble("usd_currency");
                double currentGbp = rs.getDouble("GBP_currency");
                double currentToman = rs.getDouble("toman_currency");
                double currentYen = rs.getDouble("yen_currency");

                double newEuro = currentEuro + (euro != null ? euro : 0);
                double newUsd = currentUsd + (usd != null ? usd : 0);
                double newGbp = currentGbp + (gpb != null ? gpb : 0);
                double newToman = currentToman + (toman != null ? toman : 0);
                double newYen = currentYen + (yen != null ? yen : 0);

                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setDouble(1, newEuro);
                    updateStmt.setDouble(2, newUsd);
                    updateStmt.setDouble(3, newGbp);
                    updateStmt.setDouble(4, newToman);
                    updateStmt.setDouble(5, newYen);
                    updateStmt.setString(6, username);
                    updateStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Wallet findUser(String username) {
        Wallet wallet = null;
        String query = "SELECT id, euro_currency, usd_currency, GBP_currency, toman_currency, yen_currency FROM wallet WHERE username = ?";

        try (Connection conn = DataBase.connectDb();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                double euro = rs.getDouble("euro_currency");
                double usd = rs.getDouble("usd_currency");
                double gbp = rs.getDouble("GBP_currency");
                double toman = rs.getDouble("toman_currency");
                double yen = rs.getDouble("yen_currency");
                wallet = new Wallet(id, euro, usd, gbp, toman, yen);
                return wallet;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallet;
    }

    @Override
    public String toString() {
        return String.valueOf(CurrentEuro) + " " + String.valueOf(CurrentUsd) + " " + String.valueOf(CurentGbp) +
                " " + String.valueOf(CurrentToman) +
                " " + String.valueOf(CurrentYen) +  " and id is " + String.valueOf(thisUserId);
    }
    public static boolean WalletIdChecker(int id) {
        String query = "SELECT COUNT(*) FROM wallet WHERE id = ?";

        try (Connection connection = DataBase.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String findUsernameById(int id) {
        String username = null;
        String FIND_USERNAME_BY_ID_QUERY = "SELECT username FROM wallet WHERE id = ?";
        try (Connection connection = DataBase.connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USERNAME_BY_ID_QUERY)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                username = resultSet.getString("username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }
    public static boolean checkCurrencyAmount(double amount, String username, String currency) {
        String columnName;
        switch (currency.toLowerCase()) {
            case "euro":
                columnName = "euro_currency";
                break;
            case "usd":
                columnName = "usd_currency";
                break;
            case "yen":
                columnName = "yen_currency";
                break;
            case "toman":
                columnName = "toman_currency";
                break;
            case "gbp":
                columnName = "GBP_currency";
                break;
            default:
                return false;
        }
        String query = "SELECT " + columnName + " FROM wallet WHERE username = ?";

        try (Connection conn = DataBase.connectDb();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double userCurrencyAmount = rs.getDouble(columnName);
                return amount < userCurrencyAmount;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public static void updateWallet(String username, String currency, double currencyAmountChange, double propertyChange) {
        String currencyColumn = getCurrencyColumn(currency);
        String updateQuery = "UPDATE wallet SET " + currencyColumn + " = " + currencyColumn + " + ?, property = property + ? WHERE username = ?";
        try (Connection conn = DataBase.connectDb();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, currencyAmountChange);
            pstmt.setDouble(2, propertyChange);
            pstmt.setString(3, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getCurrencyColumn(String currency) {
        switch (currency.toLowerCase()) {
            case "euro":
                return "euro_currency";
            case "usd":
                return "usd_currency";
            case "yen":
                return "yen_currency";
            case "toman":
                return "toman_currency";
            case "gbp":
                return "gbp_currency";
            default:
                throw new IllegalArgumentException("Invalid currency: " + currency);
        }
    }
}
