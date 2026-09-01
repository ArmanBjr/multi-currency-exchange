package com.example.demo1;

import com.example.demo1.Coins.CoinInfo;
import com.example.demo1.CurrencyManagement.CurrencyData;
import com.example.demo1.CurrencyManagement.Order;
import com.example.demo1.CurrencyManagement.Wallet;
import com.example.demo1.Server.Helper;
import com.example.demo1.Server.Server;
import com.example.demo1.User.GetUser;
import com.example.demo1.User.User;
import com.example.demo1.User.UserDAO;
import com.example.demo1.Utilities.FixedSizeList;
import com.example.demo1.Utilities.Values;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class HomePageController implements Initializable {

    @FXML
    private Label ExchangePageLabel;

    @FXML
    private Label ExchangePageLabel1;
    @FXML
    private Button swap;

    @FXML
    private MenuItem SwapPageEuro;

    @FXML
    private MenuItem SwapPageEuro1;

    @FXML
    private MenuItem SwapPageGbp;

    @FXML
    private MenuItem SwapPageGbp1;

    @FXML
    private MenuButton SwapPageMenuButton;

    @FXML
    private MenuButton SwapPageMenuButton1;

    @FXML
    private TextField SwapPageOriginAmount;

    @FXML
    private Text SwapPagePurAmount;

    @FXML
    private MenuItem SwapPageToman;

    @FXML
    private MenuItem SwapPageToman1;

    @FXML
    private MenuItem SwapPageUsd;

    @FXML
    private MenuItem SwapPageUsd1;

    @FXML
    private MenuItem SwapPageYen;

    @FXML
    private MenuItem SwapPageYen1;

    @FXML
    private AnchorPane SwapPage;

    public User user;

    @FXML
    private TableView<Order> HistoryPageTable;

    @FXML
    private TableColumn<Order, Integer> HistoryPageTableID;

    @FXML
    private TableColumn<Order, String> HistoryPageTableFromCurrency;

    @FXML
    private TableColumn<Order, Double> HistoryPageTableAmount;

    @FXML
    private TableColumn<Order, String> HistoryPageTableDate;

    @FXML
    private TableColumn<Order, String> HistoryPageTableTime;

    @FXML
    private TableColumn<Order, String> HistoryPageTableSituation;
    @FXML
    private TableColumn<Order, String> HistoryPageTableToCurrency;

    @FXML
    private TableColumn<Order, String> HistoryPageTableDestUser;


    @FXML
    private Button WalletPageExit;
    @FXML
    private Button WalletPageExit1;

    @FXML
    private Label CurrentProperty;
    @FXML
    private TableView<CurrencyData> FirstCurrecnyTable;

    @FXML
    private TableColumn<CurrencyData, String> FirstCurrecnyTableCurrency;

    @FXML
    private TableColumn<CurrencyData, Double> FirstCurrecnyTablePrice;

    @FXML
    private TableColumn<CurrencyData, Double> FirstCurrecnyTableUserProperty;
    @FXML
    private AnchorPane WalletAnchor;

    @FXML
    private Tab HomePageProfile;
    @FXML
    private Label timeLabel;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @FXML
    private Label HomePageUserName;
    @FXML
    private Button Exchange;

    @FXML
    private Button HomePage;

    @FXML
    private Button Profile;

    @FXML
    private MenuItem Euro;
    @FXML
    private AnchorPane HistoryPage;
    @FXML
    private Button SignOut;

    @FXML
    private Button SwapPageSwap;

    @FXML
    private Button Token;

    @FXML
    private Button Transfer;

    @FXML
    private TableView<CoinInfo> CoinInfoTable;
    @FXML
    private Label TransferPageUsername;

    @FXML
    private TableColumn<CoinInfo, String> TableChangeColumn;


    @FXML
    private TableColumn<CoinInfo, String> TableHighestPriceColumn;

    @FXML
    private TableColumn<CoinInfo, String> TableLowestColumn;

    @FXML
    private TableColumn<CoinInfo, String> TableMarketColumn;

    @FXML
    private TableColumn<CoinInfo, String> TablePriceColumn;
    @FXML
    private ImageView ProfileImage;

    @FXML
    private MenuItem Toman;
    @FXML
    private MenuItem Usd;

    @FXML
    private Label ProfileUsername;

    @FXML
    private Label ProfileUserUsername;

    @FXML
    private Label ProfileFirstName;

    @FXML
    private Label ProfileUserFirstName;

    @FXML
    private Label ProfileLastName;

    @FXML
    private Label ProfileUserLastName;

    @FXML
    private ImageView ProfileUserImage;

    @FXML
    private Label ProfilePhoneNumber;

    @FXML
    private Label ProfileEmail;

    @FXML
    private Label ProfileUserPhoneNumber;

    @FXML
    private Label ProfileUserEmail;

    @FXML
    private Label ProfilePassword;

    @FXML
    private Label ProfileUserPassword;

    @FXML
    private Button History;

    @FXML
    private Button Wallet;

    @FXML
    private Button Edit;
    @FXML
    private AnchorPane ProfilePage;
    @FXML
    private AnchorPane HomePageAnchor;
    @FXML
    private AnchorPane ProfileAnchor;
    @FXML
    private Button EditImportPhoto;
    @FXML
    private AnchorPane HomePageProfileEdit;
    @FXML
    private ImageView EditImage;
    @FXML
    private Button Export;


    @FXML
    private TextField TransferWalletId;
    @FXML
    private Label AmountOfPurposeCurrency;
    @FXML
    private TextField TransferAmount;

    @FXML
    private Circle TransferCircle;

    @FXML
    private MenuItem TransferGbp;

    @FXML
    private AnchorPane TransferPage;

    @FXML
    private MenuButton TransferPageMenuButton;
    @FXML
    private MenuItem TransferToman;

    @FXML
    private Button TransferTransferButton;

    @FXML
    private MenuItem TransferUsd;

    @FXML
    private MenuItem TransferYen;

    @FXML
    private MenuItem TransferEuro;

    @FXML
    private MenuItem ExchangeBuyChoose;

    @FXML
    private AnchorPane ExchangeAnchor;

    @FXML
    private TextField ExchangePageAmount;

    @FXML
    private MenuButton ExchangePageCurrency;

    @FXML
    private MenuItem ExchangePageEuroSelected;

    @FXML
    private MenuItem ExchangePageGbpSelected;

    @FXML
    private TextField ExchangePagePrice;

    @FXML
    private Button ExchangePageRecord;

    @FXML
    private MenuItem ExchangePageTomanSelected;

    @FXML
    private MenuItem ExchangePageUsdSelected;

    @FXML
    private MenuItem ExchangePageYenSelected;

    @FXML
    private MenuItem ExchangeSellChoose;

    @FXML
    private AnchorPane ExchangeTableBuyOrSell;
    @FXML
    private TableView<Order> ExchangeTable;

    @FXML
    private TableColumn<Order, Double> ExchangeTableAmount;

    @FXML
    private TableColumn<Order, String> ExchangeTableButOrSell;

    @FXML
    private TableColumn<Order, String> ExchangeTableDate;

    @FXML
    private TableColumn<Order, String> ExchangeTableDestUser;

    @FXML
    private TableColumn<Order, Integer> ExchangeTableId;

    @FXML
    private TableColumn<Order, String> ExchangeTableOrgUser;

    @FXML
    private TableColumn<Order, String> ExchangeTablePrgCurrency;

    @FXML
    private TableColumn<Order, String> ExchangeTableSituation;

    @FXML
    private TableColumn<Order, String> ExchangeTableTime;
    @FXML
    private TableColumn<Order, String> ExchangeTableDestCurrency;
    @FXML
    private MenuButton BuyOrSellBuuton;

    @FXML
    private Label MarketSituLabel;

    public String usernameGet = GetUser.username;

    public void setMarketSituLabel() {
        if (Values.isMarketOpenGetter()) {
            MarketSituLabel.setText("Market Is Open_You Can Set An Exchange Order");
            MarketSituLabel.setTextFill(Color.GREEN);
        } else {
            MarketSituLabel.setText("Due to admin's Order, the market is not available");
            MarketSituLabel.setTextFill(Color.RED);
        }
    }
    public static boolean checkConnection(String username) {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("CHECK_CONNECTION");
            out.println(username);
            String response = in.readLine();
            return "CONNECTED".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getSelectedCurrency() {
        return ExchangePageCurrency.getText();
    }
    public void displayExchangeTable() {
        String selectedCurrency = getSelectedCurrency();
        if (selectedCurrency == null) {
            return;
        }
        List<Order> orders = Order.getOrdersByCurrency(selectedCurrency);
        ExchangeTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ExchangeTableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ExchangeTableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        ExchangeTableTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        ExchangeTableSituation.setCellValueFactory(new PropertyValueFactory<>("situation"));
        ExchangeTableOrgUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        ExchangeTableDestUser.setCellValueFactory(new PropertyValueFactory<>("DestUsername"));
        ExchangeTablePrgCurrency.setCellValueFactory(new PropertyValueFactory<>("OrgCurrency"));
        ExchangeTableDestCurrency.setCellValueFactory(new PropertyValueFactory<>("DstCurrency"));

        ExchangeTable.setItems(FXCollections.observableArrayList(orders));
    }

    private boolean isValid;

    private void checkAndUpdateAmount() {
        String currency = SwapPageMenuButton.getText();
        String currency1 = SwapPageMenuButton1.getText();
        String amountText = SwapPageOriginAmount.getText();
        isValid = true;
        if (currency.equals(currency1)) {
            isValid = false;
        }
        double amount = 0;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            isValid = false;
        }
        if (isValid && (currency.equals("Euro") || currency.equals("Usd") || currency.equals("GBP") ||
                currency.equals("Toman") || currency.equals("Yen")) && (currency1.equals("Euro") || currency1.equals("Usd") || currency1.equals("GBP") ||
                currency1.equals("Toman") || currency1.equals("Yen"))) {
            double userBalance = getUserBalance(GetUser.username, currency);
            if (amount > userBalance) {
                isValid = false;
            }
        } else {
            isValid = false;
        }
        if (isValid) {
            SwapPageOriginAmount.setStyle("-fx-text-inner-color: green;");
            double result = (amount / Values.Value(currency)) * Values.Value(currency1);
            SwapPagePurAmount.setText(String.format("%.3f", result));
        } else {
            SwapPageOriginAmount.setStyle("-fx-text-inner-color: red;");
            SwapPagePurAmount.setText("invalidInput");
        }
    }
    public int compareValueWithCurrency(double value, String currency) {
        double currentPrice = Values.Value(currency);
        if (value > currentPrice) {
            return 1;
        } else if (value == currentPrice) {
            return 0;
        } else {
            return -1;
        }
    }

    public void onExchangeButtonClicked(ActionEvent actionEvent) {
        if (!Values.isMarketOpenGetter()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setContentText("The Market is closed");
            alert.showAndWait();
            return;
        }
        if (!isOrderCapapble1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error message");
            alert.setContentText("Invalid Input!");
            alert.showAndWait();
        } else {
            double amount = Double.parseDouble(ExchangePageAmount.getText());
            double price = Double.parseDouble(ExchangePagePrice.getText());
            amount = amount / price;
            Order order = new Order(GetUser.username, BuyOrSellBuuton.getText(),
                    null, Values.getCurrentDate(), Values.getCurrentTime(), "pending",
                    ExchangePageCurrency.getText(), ExchangePageCurrency.getText(), amount);
            order.setDestUsername("admin");
            Order.addOrder(order);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("info");
            alert.setContentText("Order Set");
            alert.showAndWait();
        }
    }
    public void onMenuButtonExchangePageBuyOrSellClicked(ActionEvent actionEvent) {
        if (actionEvent.getSource() == ExchangeSellChoose) {
            BuyOrSellBuuton.setText("Sell");
        } else if (actionEvent.getSource() == ExchangeBuyChoose) {
            BuyOrSellBuuton.setText("Buy");
        }
    }
    public void onMenubuttonExchangePageClicked(ActionEvent e) {
        if (e.getSource() == ExchangePageEuroSelected) {
            ExchangePageCurrency.setText("Euro");
        } else if (e.getSource() == ExchangePageTomanSelected) {
            ExchangePageCurrency.setText("Toman");
        } else if (e.getSource() == ExchangePageYenSelected) {
            ExchangePageCurrency.setText("Yen");
        } else if (e.getSource() == ExchangePageGbpSelected) {
            ExchangePageCurrency.setText("GBP");
        } else if (e.getSource() == ExchangePageUsdSelected) {
            ExchangePageCurrency.setText("Usd");
        }
    }
    private double getUserBalance(String username, String currency) {
        double balance = 0.0;
        String query = "SELECT " + currency + "_currency FROM wallet WHERE username = ?";

        try (Connection conn = DataBase.connectDb();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public void onSwapClicked(ActionEvent actionEvent) {
        if (isValid) {
            String username = GetUser.user.getUsername();
            String originCurrency = SwapPageMenuButton.getText();
            if (!originCurrency.equals("GBP")) originCurrency = originCurrency.toLowerCase();
            double originCurrencyValue = Values.Value(originCurrency);
            String targetCurrency = SwapPageMenuButton1.getText();
            double orgCurrency = Values.Value(originCurrency);
            String newTargetCurrency = null;
            if (!targetCurrency.equals("GBP"))  newTargetCurrency = targetCurrency.toLowerCase();
            double tarCurrency = Values.Value(targetCurrency);
            switch (originCurrency) {
                case "euro":
                    originCurrency = "euro_currency";
                    break;
                case "usd":
                    originCurrency = "usd_currency";
                    break;
                case "yen":
                    originCurrency = "yen_currency";
                    break;
                case "toman":
                    originCurrency = "toman_currency";
                    break;
                case "GBP":
                    originCurrency = "GBP_currency";
                    break;
            }
            switch (targetCurrency) {
                case "Euro":
                    targetCurrency = "euro_currency";
                    break;
                case "Usd":
                    targetCurrency = "usd_currency";
                    break;
                case "Yen":
                    targetCurrency = "yen_currency";
                    break;
                case "Toman":
                    targetCurrency = "toman_currency";
                    break;
                case "GBP":
                    targetCurrency = "GBP_currency";
                    break;
            }
            if (!targetCurrency.equals("GBP")) {
                targetCurrency.toLowerCase();
            }
            double orgAmount;
            double purAmount;
            try {
                orgAmount = Double.parseDouble(SwapPageOriginAmount.getText()) / orgCurrency;
                purAmount = Double.parseDouble(SwapPagePurAmount.getText()) / tarCurrency;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setContentText("input values are not correct!");
                alert.showAndWait();
                return;
            }
            try (Connection conn = DataBase.connectDb()) {
                conn.setAutoCommit(false);
                String updateOriginCurrencyQuery = "UPDATE wallet SET " + originCurrency + " = " + originCurrency + " - ? WHERE username = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateOriginCurrencyQuery)) {
                    pstmt.setDouble(1, orgAmount);
                    pstmt.setString(2, username);
                    pstmt.executeUpdate();
                }
                String updateTargetCurrencyQuery = "UPDATE wallet SET " + targetCurrency + " = " + targetCurrency + " + ? WHERE username = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateTargetCurrencyQuery)) {
                    pstmt.setDouble(1, purAmount);
                    pstmt.setString(2, username);
                    pstmt.executeUpdate();
                }
                conn.commit();
                Order order = new Order(GetUser.username, GetUser.username, Values.getCurrentDate(), Values.getCurrentTime(),
                        "accepted", SwapPageMenuButton.getText(), SwapPageMenuButton1.getText(), orgAmount / originCurrencyValue);
                Order.addOrder(order);
                displayHistoryTable();
                displayCurrentProperty();
                displayWalletValues();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Swap was successful");
                alert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
                try (Connection conn = DataBase.connectDb()) {
                    //conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText("Swap is not Valid!");
            alert.showAndWait();
        }
    }
    private BooleanProperty TransferPageisVerified = new SimpleBooleanProperty(false);
    public boolean CheckVerificationOfCircle() {
        try {
            int id = Integer.parseInt(TransferWalletId.getText());
            if (WalletIdChecker(id)) {
                TransferCircle.setFill(Color.GREEN);
                return true;
            } else {
                TransferCircle.setFill(Color.RED);
                return false;
            }
        } catch (NumberFormatException e) {
            TransferCircle.setFill(Color.RED);
            return false;
        }
    }

    public  boolean WalletIdChecker(int id) {
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
    public void displayHistoryTable() {
        List<Order> orders = Order.getOrdersByUsername(GetUser.username);
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        if (orders != null) {
            ordersList.addAll(orders);
        }

        HistoryPageTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        HistoryPageTableFromCurrency.setCellValueFactory(new PropertyValueFactory<>("OrgCurrency"));
        HistoryPageTableToCurrency.setCellValueFactory(new PropertyValueFactory<>("DstCurrency"));
        HistoryPageTableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        HistoryPageTableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        HistoryPageTableTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        HistoryPageTableSituation.setCellValueFactory(new PropertyValueFactory<>("situation"));
        HistoryPageTableDestUser.setCellValueFactory(new PropertyValueFactory<>("DestUsername"));

        HistoryPageTable.setItems(ordersList);
    }

    private double getUserProperty(String currency) {
        switch (currency.toLowerCase()) {
            case "euro":
                return GetUser.user.getWallet().CurrentEuro;
            case "usd":
                return GetUser.user.getWallet().CurrentUsd;
            case "gbp":
                return GetUser.user.getWallet().CurentGbp;
            case "toman":
                return GetUser.user.getWallet().CurrentToman;
            case "yen":
                return GetUser.user.getWallet().CurrentYen;
            default:
                throw new IllegalArgumentException("Unknown currency: " + currency);
        }
    }
    public void displayWalletValues() {
        UserDAO.loadUsersFromDatabase();
        List<CurrencyData> currencyDataList = new ArrayList<>();
        String[] currencies = {"euro", "usd", "GBP", "toman", "yen"};
        for (String currency : currencies) {
            double price = Values.Value(currency);
            User user = UserDAO.userFinder1(GetUser.username);
            GetUser.user = user;
            double userProperty = getUserProperty(currency);
            currencyDataList.add(new CurrencyData(currency, price, userProperty));
        }
        FirstCurrecnyTable.getItems().setAll(currencyDataList);
    }
    public void OnMenuButtonEuroClicked(ActionEvent e) throws IOException {
        Thread euroChartThread = new Thread(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("EuroChart.fxml"));
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        euroChartThread.start();
    }
    public void OnMenuButtonTomanClicked(ActionEvent e) throws IOException {
        Thread TomanChartThread = new Thread(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("TomanChart.fxml"));
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        TomanChartThread.start();
    }
    public void OnMenuButtonUsdClicked(ActionEvent e) throws IOException {
        Thread UsdChartThread = new Thread(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("UsdChart.fxml"));
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        UsdChartThread.start();
    }
    public void OnMenuButtonYenClicked(ActionEvent e) throws IOException {
        Thread YenChartThread = new Thread(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("YenChart.fxml"));
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        YenChartThread.start();
    }
    public void OnMenuButtonGbpClicked(ActionEvent e) throws IOException {
        Thread GbpChartThread = new Thread(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("GbpChart.fxml"));
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        GbpChartThread.start();
    }
    public void displayCurrentProperty() {
        double newValue = GetUser.user.wallet.setCurrentProperty();
        GetUser.user.wallet.currentProperty = newValue;
        String formattedValue = String.format("%.4f", newValue);
        Platform.runLater(() -> CurrentProperty.setText(formattedValue));
    }
    public void setValuesOnProfilePage() {
        ProfileUserUsername.setText(GetUser.username);
        ProfileUserFirstName.setText(GetUser.user.getFirstName());
        ProfileUserLastName.setText(GetUser.user.getLastName());
        ProfileUserPhoneNumber.setText(GetUser.user.getPhoneNumber());
        ProfileUserEmail.setText(GetUser.user.getEmail());
        ProfileUserPassword.setText(GetUser.user.getPassword());
    }
    public void ExportClickedBuuton() {
        Order.exportOrdersToExcel();
    }
    public void onMenuButtonTransferClicked(ActionEvent e) {
        if (e.getSource() == TransferEuro) {
            TransferPageMenuButton.setText("Euro");
        } else if (e.getSource() == TransferToman) {
            TransferPageMenuButton.setText("Toman");
        } else if (e.getSource() == TransferYen) {
            TransferPageMenuButton.setText("Yen");
        } else if (e.getSource() == TransferGbp) {
            TransferPageMenuButton.setText("GBP");
        } else if (e.getSource() == TransferUsd) {
            TransferPageMenuButton.setText("Usd");
        }
    }
    public void OnTransferButtonClicked(ActionEvent event) {
        if (!check) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("wrong format of input!");
            alert.showAndWait();
        }
        else {
            int id = Integer.parseInt(TransferWalletId.getText());
            double amount = Double.parseDouble(AmountOfPurposeCurrency.getText());
            Order order = new Order(GetUser.username, TransferPageUsername.getText(), Values.getCurrentDate(), Values.getCurrentTime(),
                    "accepted", "property", TransferPageMenuButton.getText(), amount);
            Order.addOrder(order);
            double euro = 0, toman = 0, usd = 0, GBP = 0, yen = 0;
            switch (TransferPageMenuButton.getText()) {
                case "Euro":
                    euro = amount;
                    break;
                case "Usd":
                    usd = amount;
                    break;
                case "Toman":
                    toman = amount;
                    break;
                case "GBP":
                    GBP = amount;
                    break;
                case "Yen":
                    yen = amount;
            }
            com.example.demo1.CurrencyManagement.Wallet.addCash(TransferPageUsername.getText(), euro, usd, GBP, toman, yen);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            displayCurrentProperty();
            displayWalletValues();
            displayHistoryTable();
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("transferred successfully");
            alert.showAndWait();
        }
        displayCurrentProperty();
        displayWalletValues();
        displayHistoryTable();
    }
    private void updatePurposeCurrency() {
        String purposeCurrency = TransferPageMenuButton.getText();
        if (TransferPageMenuButton.getText().equals("MenuButton")) {
            AmountOfPurposeCurrency.setText("invalidInput");
            AmountOfPurposeCurrency.setStyle("-fx-text-fill: red;");
            this.check = false;
        }
        else {
            try {
                double amount = Double.parseDouble(TransferAmount.getText());
                double result = amount * Values.Value(purposeCurrency);
                AmountOfPurposeCurrency.setText(String.format("%.2f", result));
                AmountOfPurposeCurrency.setStyle("-fx-text-fill: green;");
                check = true;
            } catch (NumberFormatException e) {
                AmountOfPurposeCurrency.setText("invalidInput");
                AmountOfPurposeCurrency.setStyle("-fx-text-fill: red;");
                check = false;
            } catch (Exception e) {
                AmountOfPurposeCurrency.setText("invalidInput");
                AmountOfPurposeCurrency.setStyle("-fx-text-fill: red;");
                check = false;
            }
        }
    }
    public void switchForm(ActionEvent e) {
        if (e.getSource() == Profile) {
            HistoryPage.setVisible(false);
            ProfilePage.setVisible(true);
            ProfileAnchor.setVisible(true);
            HomePageAnchor.setVisible(false);
            WalletAnchor.setVisible(false);
            TransferPage.setVisible(false);
            SwapPage.setVisible(false);
            ExchangeAnchor.setVisible(false);
        } else if (e.getSource() == HomePage) {
            HistoryPage.setVisible(true);
            ProfilePage.setVisible(false);
            ProfileAnchor.setVisible(false);
            WalletAnchor.setVisible(false);
            HomePageAnchor.setVisible(true);
            TransferPage.setVisible(false);
            SwapPage.setVisible(false);
            ExchangeAnchor.setVisible(false);
        } else if(e.getSource() == Wallet) {
            HistoryPage.setVisible(false);
            WalletAnchor.setVisible(true);
            ProfilePage.setVisible(false);
            ProfileAnchor.setVisible(true);
            HomePageAnchor.setVisible(false);
            TransferPage.setVisible(false);
            SwapPage.setVisible(false);
            ExchangeAnchor.setVisible(false);
        } else if (e.getSource() == History) {
            HistoryPage.setVisible(true);
            WalletAnchor.setVisible(false);
            ProfilePage.setVisible(false);
            ProfileAnchor.setVisible(true);
            HomePageAnchor.setVisible(false);
            TransferPage.setVisible(false);
            SwapPage.setVisible(false);
            ExchangeAnchor.setVisible(false);
        } else if (e.getSource() == WalletPageExit) {
            HistoryPage.setVisible(false);
            WalletAnchor.setVisible(false);
            ProfilePage.setVisible(true);
            ProfileAnchor.setVisible(true);
            HomePageAnchor.setVisible(false);
            TransferPage.setVisible(false);
            SwapPage.setVisible(false);
            ExchangeAnchor.setVisible(false);
        } else if (e.getSource() == WalletPageExit1) {
            HistoryPage.setVisible(false);
            WalletAnchor.setVisible(false);
            ProfilePage.setVisible(true);
            ProfileAnchor.setVisible(true);
            HomePageAnchor.setVisible(false);
            TransferPage.setVisible(false);
            SwapPage.setVisible(false);
            ExchangeAnchor.setVisible(false);
        } else if(e.getSource() == Transfer) {
            TransferPage.setVisible(true);
            HistoryPage.setVisible(false);
            WalletAnchor.setVisible(false);
            ProfilePage.setVisible(false);
            ProfileAnchor.setVisible(true);
            HomePageAnchor.setVisible(false);
            SwapPage.setVisible(false);
            ExchangeAnchor.setVisible(false);
        } else if (e.getSource() == swap) {
            TransferPage.setVisible(false);
            HistoryPage.setVisible(false);
            WalletAnchor.setVisible(false);
            ProfilePage.setVisible(false);
            ProfileAnchor.setVisible(true);
            HomePageAnchor.setVisible(false);
            SwapPage.setVisible(true);
            ExchangeAnchor.setVisible(false);
        } else if (e.getSource() == Exchange) {
            ExchangeAnchor.setVisible(true);
            TransferPage.setVisible(false);
            HistoryPage.setVisible(false);
            WalletAnchor.setVisible(false);
            ProfilePage.setVisible(false);
            ProfileAnchor.setVisible(true);
            HomePageAnchor.setVisible(false);
            SwapPage.setVisible(false);
        }
    }

    public void onMenuButtonSwapClicked(ActionEvent e) {
        if (e.getSource() == SwapPageEuro) {
            SwapPageMenuButton.setText("Euro");
        } else if (e.getSource() == SwapPageToman) {
            SwapPageMenuButton.setText("Toman");
        } else if (e.getSource() == SwapPageYen) {
            SwapPageMenuButton.setText("Yen");
        } else if (e.getSource() == SwapPageGbp) {
            SwapPageMenuButton.setText("GBP");
        } else if (e.getSource() == SwapPageUsd) {
            SwapPageMenuButton.setText("Usd");
        }
    }
    public void onMenuBuutonSwapClicked1(ActionEvent e) {
        if (e.getSource() == SwapPageEuro1) {
            SwapPageMenuButton1.setText("Euro");
        } else if (e.getSource() == SwapPageToman1) {
            SwapPageMenuButton1.setText("Toman");
        } else if (e.getSource() == SwapPageYen1) {
            SwapPageMenuButton1.setText("Yen");
        } else if (e.getSource() == SwapPageGbp1) {
            SwapPageMenuButton1.setText("GBP");
        } else if (e.getSource() == SwapPageUsd1) {
            SwapPageMenuButton1.setText("Usd");
        }
    }
    @FXML
    void OnSignOutButtonClicked(ActionEvent event) throws IOException {
        SignOut.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("SignView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("/com/example/demo1/Beauty.css")));
        stage.setScene(scene);
        stage.show();
    }
    private static final double ROW_HEIGHT = 40.0; // یا هر مقداری که برای ارتفاع هر ردیف مناسب است
    private static final int NUM_ROWS = 6; // تعداد ردیف‌های مورد نظر

    @FXML
    private Label Username;

    public void displayUsername() {
        try {
            Username.setText(GetUser.username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void displayProfile() {
        try {
            if(GetUser.imageUrl != null) {
                Image image = new Image(GetUser.user.getImageUrl(), 150, 200, false, true);
                ProfileImage.setImage(image);
                ProfileUserImage.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeLabel.setText(currentTime.format(timeFormatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    private String jdbcURL = "jdbc:mysql://localhost:3306/exchange";
    private String username = "root";
    private String password = "";
    private ObservableList<CoinInfo> coinData = FXCollections.observableArrayList();
    private boolean check = false;
    private boolean isOrderCapapble1 = false, isOrderCapapble2 = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMarketSituLabel();
        ExchangePageAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (BuyOrSellBuuton.getText().equals("Buy")) {
                try {
                    Double.parseDouble(newValue);
                    ExchangePageLabel.setText("Valid input");
                    ExchangePageLabel.setTextFill(Color.GREEN);
                    isOrderCapapble1 = true;
                } catch (NumberFormatException e) {
                    ExchangePageLabel.setText("Invalid input");
                    ExchangePageLabel.setTextFill(Color.RED);
                    isOrderCapapble1 = false;
                }
            } else {
                try {
                    double amount = Double.parseDouble(newValue);
                    boolean result = com.example.demo1.CurrencyManagement.Wallet.checkCurrencyAmount(amount, GetUser.username, ExchangePageCurrency.getText());
                    if (result) {
                        ExchangePageLabel.setText("Capable Price");
                        ExchangePageLabel.setTextFill(Color.GREEN);
                        isOrderCapapble1 = true;
                    } else {
                        ExchangePageLabel.setText("Not enough amount of money");
                        ExchangePageLabel.setTextFill(Color.RED);
                        isOrderCapapble1 = false;
                    }
                } catch (NumberFormatException e) {
                    ExchangePageLabel.setText("Invalid input");
                    ExchangePageLabel.setTextFill(Color.RED);
                    isOrderCapapble1 = false;
                }
            }
        });
        ExchangePagePrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double price = Double.parseDouble(newValue);
                int result = compareValueWithCurrency(price, ExchangePageCurrency.getText());
                if (BuyOrSellBuuton.getText().equals("Buy")) {
                    if (result == 1) {
                        ExchangePageLabel1.setText("More than the real value");
                        ExchangePageLabel1.setTextFill(Color.RED);
                    } else if (result == 0) {
                        ExchangePageLabel1.setText("The real price");
                        ExchangePageLabel1.setTextFill(Color.BLACK);
                    } else if (result == -1) {
                        ExchangePageLabel1.setText("Less than the real price");
                        ExchangePageLabel1.setTextFill(Color.GREEN);
                    }
                } else {
                    if (result == 1) {
                        ExchangePageLabel1.setText("More than the real value");
                        ExchangePageLabel1.setTextFill(Color.GREEN);
                    } else if (result == 0) {
                        ExchangePageLabel1.setText("The real price");
                        ExchangePageLabel1.setTextFill(Color.BLACK);
                    } else if (result == -1) {
                        ExchangePageLabel1.setText("Less than the real price");
                        ExchangePageLabel1.setTextFill(Color.RED);
                    }
                }
            } catch (NumberFormatException e) {
                ExchangePageLabel1.setText("Invalid input");
                ExchangePageLabel1.setTextFill(Color.RED);
            }
        });
        TransferAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updatePurposeCurrency();
            }
        });
        TransferPageMenuButton.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updatePurposeCurrency();
            }
        });
        TransferWalletId.textProperty().addListener((observable, oldValue, newValue) -> {
            check = CheckVerificationOfCircle();
            if (check) {
                int id = Integer.parseInt(TransferWalletId.getText());
                TransferPageUsername.setText(com.example.demo1.CurrencyManagement.Wallet.findUsernameById(id));
                TransferAmount.setDisable(false);
                TransferPageMenuButton.setDisable(false);
            } else {
                TransferPageUsername.setText("");
                TransferAmount.setDisable(true);
                TransferPageMenuButton.setDisable(true);
            }
        });
        SwapPageOriginAmount.textProperty().addListener((observable, oldValue, newValue) -> checkAndUpdateAmount());
        SwapPageMenuButton.textProperty().addListener((observable, oldValue, newValue) -> checkAndUpdateAmount());
        SwapPageMenuButton1.textProperty().addListener((observable, oldValue, newValue) -> checkAndUpdateAmount());
        startClock();
        displayUsername();
        displayProfile();
        setValuesOnProfilePage();
        displayCurrentProperty();
        TableMarketColumn.setCellValueFactory(new PropertyValueFactory<>("market"));
        TablePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableHighestPriceColumn.setCellValueFactory(new PropertyValueFactory<>("highestPrice"));
        TableLowestColumn.setCellValueFactory(new PropertyValueFactory<>("lowestPrice"));
        TableChangeColumn.setCellValueFactory(new PropertyValueFactory<>("changePercentage"));
        CoinInfoTable.setItems(coinData);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new UpdateTask(), 0, 60000);
        FirstCurrecnyTableCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        FirstCurrecnyTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        FirstCurrecnyTableUserProperty.setCellValueFactory(new PropertyValueFactory<>("userProperty"));

        displayWalletValues();
        displayHistoryTable();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> displayWalletValues());
            }
        }, 0, 60000);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> isDisplayOkSaidByReza());
            }
        }, 0, 5000);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> setMarketSituLabel());
            }
        }, 0, 5000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> displayHistoryTable());
            }
        }, 0, 60000);
        displayExchangeTable();
        ExchangePageCurrency.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                displayExchangeTable();
            }
        });
    }
    public static boolean isUserOnline(String username) {
        String querySQL = "SELECT COUNT(*) FROM online_users WHERE username = ?";
        boolean isOnline = false;

        try (Connection conn = DataBase.connectOnline();
             PreparedStatement stmt = conn.prepareStatement(querySQL)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    isOnline = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isOnline;
    }
    public void isDisplayOkSaidByReza() {
        if (!isUserOnline(GetUser.username)) {
            ExchangeAnchor.getScene().getWindow().hide();
            //System.out.println("online");
        }
    }
    private double getChangePercentage(Connection connection, String column, String date, String time) throws SQLException {
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
    private double getLowestPrice(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT MIN(" + column + ") AS lowest FROM currency_rates WHERE CONCAT(date, ' ', time) <= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("lowest");
        }
        return 0.0;
    }
    private double getHighestPrice(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT MAX(" + column + ") AS highest FROM currency_rates WHERE CONCAT(date, ' ', time) <= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("highest");
        }
        return 0.0;
    }
    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(() -> displayTable());
            displayCurrentProperty();
        }
    }
    public void displayTable() {
        List<CoinInfo> coins = null;
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            coinData.clear();
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String query = "SELECT euro, usd, GBP, toman, yen FROM currency_rates WHERE CONCAT(date, ' ', time) <= ? ORDER BY CONCAT(date, ' ', time) DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, formattedDate + " " + formattedTime);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double euro = resultSet.getDouble("euro");
                double usd = resultSet.getDouble("usd");
                double gpb = resultSet.getDouble("GBP");
                double toman = resultSet.getDouble("toman");
                double yen = resultSet.getDouble("yen");
                // Add rows to the table
                coins = List.of(
                        new CoinInfo("Euro", euro, getHighestPrice(connection, "euro", formattedDate, formattedTime), getLowestPrice(connection, "euro", formattedDate, formattedTime), getChangePercentage(connection, "euro", formattedDate, formattedTime)),
                        new CoinInfo("USD", usd, getHighestPrice(connection, "usd", formattedDate, formattedTime), getLowestPrice(connection, "usd", formattedDate, formattedTime), getChangePercentage(connection, "usd", formattedDate, formattedTime)),
                        new CoinInfo("GPB", gpb, getHighestPrice(connection, "GBP", formattedDate, formattedTime), getLowestPrice(connection, "GBP", formattedDate, formattedTime), getChangePercentage(connection, "GBP", formattedDate, formattedTime)),
                        new CoinInfo("Toman", toman, getHighestPrice(connection, "toman", formattedDate, formattedTime), getLowestPrice(connection, "toman", formattedDate, formattedTime), getChangePercentage(connection, "toman", formattedDate, formattedTime)),
                        new CoinInfo("Yen", yen, getHighestPrice(connection, "yen", formattedDate, formattedTime), getLowestPrice(connection, "yen", formattedDate, formattedTime), getChangePercentage(connection, "yen", formattedDate, formattedTime))
                );
                coinData.add(new CoinInfo("Euro", euro, getHighestPrice(connection, "euro", formattedDate, formattedTime), getLowestPrice(connection, "euro", formattedDate, formattedTime), getChangePercentage(connection, "euro", formattedDate, formattedTime)));
                coinData.add(new CoinInfo("USD", usd, getHighestPrice(connection, "usd", formattedDate, formattedTime), getLowestPrice(connection, "usd", formattedDate, formattedTime), getChangePercentage(connection, "usd", formattedDate, formattedTime)));
                coinData.add(new CoinInfo("GPB", gpb, getHighestPrice(connection, "GBP", formattedDate, formattedTime), getLowestPrice(connection, "GBP", formattedDate, formattedTime), getChangePercentage(connection, "GBP", formattedDate, formattedTime)));
                coinData.add(new CoinInfo("Toman", toman, getHighestPrice(connection, "toman", formattedDate, formattedTime), getLowestPrice(connection, "toman", formattedDate, formattedTime), getChangePercentage(connection, "toman", formattedDate, formattedTime)));
                coinData.add(new CoinInfo("Yen", yen, getHighestPrice(connection, "yen", formattedDate, formattedTime), getLowestPrice(connection, "yen", formattedDate, formattedTime), getChangePercentage(connection, "yen", formattedDate, formattedTime)));
            }
            coinData.setAll(new FixedSizeList<>(coins));
            CoinInfoTable.setFixedCellSize(ROW_HEIGHT);
            CoinInfoTable.prefHeightProperty().bind(Bindings.size(CoinInfoTable.getItems()).multiply(ROW_HEIGHT).add(30).add(ROW_HEIGHT * (NUM_ROWS - coinData.size())));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void onEditClicked(ActionEvent e) throws IOException {
        Thread EditPage = new Thread(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("ProfileEdit.fxml"));
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        EditPage.start();
    }
    public void HomePageClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("are you sure you want to quite?");
        alert.showAndWait();
        Server.disconnectUser(GetUser.username);
        System.exit(0);
    }
    public void HomePageMinimize() {
        Stage stage = (Stage) ExchangeAnchor.getScene().getWindow();
        stage.setIconified(true);
    }
}
