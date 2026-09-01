package com.example.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    public static Connection connectDb() {
        try {
            return DriverManager.getConnection(
                    AppConfig.dbUrl(),
                    AppConfig.dbUser(),
                    AppConfig.dbPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static Connection connectOnline() {
        try {
            return DriverManager.getConnection(
                    AppConfig.onlineDbUrl(),
                    AppConfig.dbUser(),
                    AppConfig.dbPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Online-users database connection failed", e);
        }
    }
}
