package com.example.demo1.CurrencyManagement;

import com.example.demo1.DataBase;
import com.example.demo1.User.GetUser;
import com.example.demo1.Utilities.Values;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order {
    private String username;
    private String DestUsername;
    private String date;
    private String time;
    private String situation;
    private String OrgCurrency;
    private String DstCurrency;
    private double amount;
    private int OrderId;
    private String buyOrSell = "notExchange";
    public Order(String user, String destUsername, int id, String date, String time, String situation, String OrgCurrency,
                 String DstCurrency, double amount) {
        setUsername(user);
        setDestUsername(destUsername);
        setAmount(amount);
        setDate(date);
        setTime(time);
        setId(id);
        setSituation(situation);
        setDstCurrency(DstCurrency);
        setOrgCurrency(OrgCurrency);
        this.buyOrSell = "NotExchange";
    }
    public Order(String user, String destUsername, String date, String time, String situation, String OrgCurrency,
                 String DstCurrency, double amount) {
        setUsername(user);
        setDestUsername(destUsername);
        setAmount(amount);
        setDate(date);
        setTime(time);
        setSituation(situation);
        setDstCurrency(DstCurrency);
        setOrgCurrency(OrgCurrency);
        this.OrderId = Values.getOrderID();
        System.out.println(this.OrderId);
        Values.incrementOrderID();
        this.buyOrSell = "NotExchange";
    }
    public Order(String user, String buyOrSell, String destUsername, String date, String time, String situation, String OrgCurrency,
                 String DstCurrency, double amount) {
        this.buyOrSell = buyOrSell;
        setUsername(user);
        setDestUsername(destUsername);
        setAmount(amount);
        setDate(date);
        setTime(time);
        setSituation(situation);
        setDstCurrency(DstCurrency);
        setOrgCurrency(OrgCurrency);
        this.OrderId = Values.getOrderID();
        System.out.println(this.OrderId);
        Values.incrementOrderID();
    }
    public Order() {

    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return this.time;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return this.date;
    }
    public void setId(int id) {
        this.OrderId = id;
    }
    public int getId() {
        return this.OrderId;
    }
    public void setSituation(String situation) {
        this.situation = situation;
    }
    public String getSituation() {
        return this.situation;
    }
    public boolean getSituationV() {
        return this.situation.equals("accepted");
    }
    public void setOrgCurrency(String name) {
        this.OrgCurrency = name;
    }
    public String getOrgCurrency() {
        return this.OrgCurrency;
    }
    public void setDstCurrency(String name) {
        this.DstCurrency = name;
    }
    public String getDstCurrency() {
        return this.DstCurrency;
    }
    public void setDestUsername(String username) {
        this.DestUsername = username;
    }
    public String getDestUsername() {
        return this.DestUsername;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return this.amount;
    }
    public static List<Order> getOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE username = ?";

        try (Connection connection = DataBase.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String dstUser = resultSet.getString("DstUser");
                int id = resultSet.getInt("id");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String situation = resultSet.getString("situation");
                String OrgCurrency = resultSet.getString("OriginCurrency");
                String DstCurrency = resultSet.getString("PurposeCurrency");
                double amount = resultSet.getDouble("amount");

                Order order = new Order(user, dstUser, id, date, time, situation, OrgCurrency, DstCurrency, amount);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
    public static List<Order> getOrdersByUsernameAndStatus(String username, boolean isAccepted) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE username = ? AND situation = ?";

        try (Connection connection = DataBase.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, isAccepted ? "accepted" : "pending");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String dstUser = resultSet.getString("DstUser");
                int id = resultSet.getInt("id");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String situation = resultSet.getString("situation");
                String OrgCurrency = resultSet.getString("OriginCurrency");
                String DstCurrency = resultSet.getString("PurposeCurrency");
                double amount = resultSet.getDouble("amount");

                Order order = new Order(user, dstUser, id, date, time, situation, OrgCurrency, DstCurrency, amount);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static List<Order> getOrdersByCurrency(String currency) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE (OriginCurrency = ? OR PurposeCurrency = ?)";

        try (Connection connection = DataBase.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, currency);
            statement.setString(2, currency);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                order.setUsername(resultSet.getString("username"));
                order.setDestUsername(resultSet.getString("DstUser"));
                order.setId(resultSet.getInt("id"));
                order.setDate(resultSet.getString("date"));
                order.setTime(resultSet.getString("time"));
                order.setSituation(resultSet.getString("situation"));
                order.setOrgCurrency(resultSet.getString("OriginCurrency"));
                order.setDstCurrency(resultSet.getString("PurposeCurrency"));
                order.setAmount(resultSet.getDouble("amount"));

                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
    public double getTotalAmountByCurrency(String currency) {
        double totalAmount = 0;
        String query = "SELECT SUM(amount) AS total_amount FROM orders WHERE OrgCurrency = ? OR DstCurrency = ?";

        try (Connection connection = DataBase.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, currency);
            statement.setString(2, currency);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalAmount = resultSet.getDouble("total_amount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalAmount;
    }
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Connection connection = DataBase.connectDb();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = new Order();
                order.setUsername(resultSet.getString("username"));
                order.setDestUsername(resultSet.getString("DestUsername"));
                order.setId(resultSet.getInt("id"));
                order.setDate(resultSet.getString("date"));
                order.setTime(resultSet.getString("time"));
                order.setSituation(resultSet.getString("situation"));
                order.setOrgCurrency(resultSet.getString("OrgCurrency"));
                order.setDstCurrency(resultSet.getString("DstCurrency"));
                order.setAmount(resultSet.getDouble("amount"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    public static void exportOrdersToExcel() {
        List<Order> orders = Order.getOrdersByUsername(GetUser.username);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Orders");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Username", "DestUsername", "ID", "Date", "Time", "Situation", "OrgCurrency", "DstCurrency", "Amount"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        int rowNum = 1;
        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getUsername());
            row.createCell(1).setCellValue(order.getDestUsername());
            row.createCell(2).setCellValue(order.getId());
            row.createCell(3).setCellValue(order.getDate());
            row.createCell(4).setCellValue(order.getTime());
            row.createCell(5).setCellValue(order.getSituation());
            row.createCell(6).setCellValue(order.getOrgCurrency());
            row.createCell(7).setCellValue(order.getDstCurrency());
            row.createCell(8).setCellValue(order.getAmount());
        }
        String path = "D:\\university\\AP codes\\testing\\demo1\\exported files\\Orders_";
        path += GetUser.username + ".xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Successful");
            alert.setHeaderText(null);
            alert.setContentText("Orders have been exported to: " + path);
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addOrder(Order order) {
        String query = "INSERT INTO orders (username, DstUser, id, date, time, situation, OriginCurrency, PurposeCurrency, amount, sellOrBuy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBase.connectDb();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, order.getUsername());
            pstmt.setString(2, order.getDestUsername());
            pstmt.setInt(3, order.getId());
            pstmt.setString(4, order.getDate());
            pstmt.setString(5, order.getTime());
            pstmt.setString(6, order.getSituation());
            pstmt.setString(7, order.getOrgCurrency());
            pstmt.setString(8, order.getDstCurrency());
            pstmt.setDouble(9, order.getAmount());
            pstmt.setString(10, order.buyOrSell);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        processOrder(order);
    }
    public static boolean processOrder(Order inputOrder) {
        if (!inputOrder.getOrgCurrency().equals(inputOrder.getDstCurrency())) {
            return false;
        }

        String query = "SELECT * FROM orders WHERE situation = 'pending' AND OriginCurrency = ? AND PurposeCurrency = ? AND sellOrBuy = ? AND amount >= ?";
        try (Connection conn = DataBase.connectDb();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            String BOS;
            if (inputOrder.buyOrSell.equals("Sell")) BOS = "Buy";
            else BOS = "Sell";
            pstmt.setString(1, inputOrder.getOrgCurrency());
            pstmt.setString(2, inputOrder.getDstCurrency());
            pstmt.setString(3, BOS);
            pstmt.setDouble(4, inputOrder.getAmount());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String orderUsername = rs.getString("username");
                if (!orderUsername.equals(inputOrder.getUsername())) {
                    int orderId = rs.getInt("id");
                    double matchAmount = rs.getDouble("amount");
                    String matchedOrderCurrency = rs.getString("OriginCurrency");
                    String updateQuery = "UPDATE orders SET situation = 'accepted', DstUser = ? WHERE id = ?";
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                        updatePstmt.setString(1, GetUser.username);
                        updatePstmt.setInt(2, orderId);
                        updatePstmt.executeUpdate();
                    }
                    String updateQuery2 = "UPDATE orders SET situation = 'accepted', DstUser = ? WHERE id = ?";
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery2)) {
                        updatePstmt.setString(1, GetUser.username);
                        updatePstmt.setInt(2, inputOrder.OrderId);
                        updatePstmt.executeUpdate();
                    }
                    if (inputOrder.buyOrSell.equals("Sell")) {
                        Wallet.updateWallet(orderUsername, matchedOrderCurrency, -matchAmount * Values.Value(matchedOrderCurrency), matchAmount);
                        Wallet.updateWallet(GetUser.username, matchedOrderCurrency, matchAmount * Values.Value(matchedOrderCurrency), -matchAmount);
                    } else if (inputOrder.buyOrSell.equals("Buy")) {
                        Wallet.updateWallet(inputOrder.getUsername(), matchedOrderCurrency, matchAmount * Values.Value(matchedOrderCurrency), -matchAmount);
                        Wallet.updateWallet(GetUser.username, matchedOrderCurrency, -matchAmount * Values.Value(matchedOrderCurrency), matchAmount);
                    }
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
