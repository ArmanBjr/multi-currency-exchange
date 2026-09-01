package com.example.demo1.Server;

import com.example.demo1.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Application {
    private static final int PORT = 12345;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);
    private static List<ClientHandler> clientHandlers = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo1/AdminPage.fxml"));
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args){
        Thread javafxThread = new Thread(() -> Application.launch(Server.class, args));
        javafxThread.setDaemon(true);
        javafxThread.start();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client is connected!");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                pool.execute(clientHandler);
                getConnectedUsers();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<String> ConnectedUsers = new ArrayList<>();
    public synchronized static void getConnectedUsers() {
        String deleteSQL = "DELETE FROM online_users";
        String insertSQL = "INSERT INTO online_users (username) VALUES (?)";

        try (Connection conn = DataBase.connectOnline();
             Statement deleteStmt = conn.createStatement();
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
            deleteStmt.executeUpdate(deleteSQL);
            for (ClientHandler clientHandler : clientHandlers) {
                insertStmt.setString(1, clientHandler.getUsername());
                insertStmt.executeUpdate();
            }
            System.out.println("Table 'online_users' updated with new users.");
            ConnectedUsers = getAllOnlineUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean disconnectUser(String username) {
        for (ClientHandler handler : clientHandlers) {
            if (handler.getUsername().equals(username)) {
                handler.disconnect();
                clientHandlers.remove(handler);
                getConnectedUsers();
                return true;
            }
        }
        return false;
    }
    public synchronized static boolean isUserConnected(String username) {
        for (String handler : ConnectedUsers) {
            if (handler.equals(username)) {
                return true;
            }
        }
        return false;
    }
    public static List<String> getAllOnlineUsers() {
        String querySQL = "SELECT username FROM online_users";
        List<String> users = new ArrayList<>();

        try (Connection conn = DataBase.connectOnline();
             PreparedStatement stmt = conn.prepareStatement(querySQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
