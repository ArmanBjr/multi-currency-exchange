package com.example.demo1.User;

import com.example.demo1.CurrencyManagement.Wallet;
import com.example.demo1.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static List<User> users = new ArrayList<>();
    public static int whichUser;
    public static void getAllUsers() throws SQLException {
        if (users != null) users.clear();
        try(Connection connection = DataBase.connectDb(); Statement statement = connection.createStatement();) {
            String query = "SELECT username, firstName, lastName, password, email, phoneNumber, image FROM info";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                String username = rs.getString("username");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String passwordDb = rs.getString("password");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String image = rs.getString("image");
                User user = new User(username, email, passwordDb, firstName, lastName, phoneNumber, image);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<User> getAllUsersAsAList() throws SQLException {
        List<User> users = new ArrayList<>();
        try(Connection connection = DataBase.connectDb(); Statement statement = connection.createStatement();) {
            String query = "SELECT username, firstName, lastName, password, email, phoneNumber, image FROM info";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                String username = rs.getString("username");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String passwordDb = rs.getString("password");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String image = rs.getString("image");
                User user = new User(username, email, passwordDb, firstName, lastName, phoneNumber, image);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    public static void loadUsersFromDatabase() {
        if (users.size() != 0) {
            users.clear();
        }
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DataBase.connectDb();
            stmt = conn.createStatement();
            String sql = "SELECT username, firstName, lastName, password, email, phoneNumber, image FROM info";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString("username");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String passwordDb = rs.getString("password");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String image = rs.getString("image");
                User userObj = new User(username, email, passwordDb, firstName, lastName, phoneNumber, image);
                userObj.wallet = Wallet.findUser(username);
                users.add(userObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static User userFinder(String username, String password) {
        try {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getUsername().equals(username) && user.getEmail().equals(password)) {
                    whichUser = i;
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }
    public static User userFinder1(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User userFinder2(String username, String password) {
        if (users != null) {
            for (User user : users) {
                if (user.getUsername() != null && user.getUsername().equals(username) &&
                        user.getPassword() != null && user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }
    public static void printAllUsers() {
        if (users != null) {
            for (User user : users) {
                System.out.println(user.toString());
            }
        }
    }
    public static boolean isUsernameAvailable(String username) {

        String sql = "SELECT COUNT(*) FROM info WHERE username = ?";

        try (Connection conn = DataBase.connectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // در صورت بروز خطا یا مشکل در اتصال به پایگاه داده
    }
}
