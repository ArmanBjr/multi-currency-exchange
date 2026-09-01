module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.ooxml;
    requires com.opencsv;
    requires commons.math3;
    requires java.mail;
    requires javafx.graphics;

    opens com.example.demo1.User to javafx.base;
    opens com.example.demo1 to javafx.fxml;
    opens com.example.demo1.Coins to javafx.base;
    opens com.example.demo1.CurrencyManagement to javafx.base;
    exports com.example.demo1;
    exports com.example.demo1.Coins;
    exports com.example.demo1.CoinPages;
    exports com.example.demo1.CurrencyManagement;
    opens com.example.demo1.CoinPages to javafx.fxml;
    exports com.example.demo1.Server to javafx.graphics;
    opens com.example.demo1.Server to javafx.fxml;
}