package com.example.demo1.CurrencyManagement;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class CurrencyData {
    private final SimpleStringProperty currency;
    private final SimpleDoubleProperty price;
    private final SimpleDoubleProperty userProperty;

    public CurrencyData(String currency, double price, double userProperty) {
        this.currency = new SimpleStringProperty(currency);
        this.price = new SimpleDoubleProperty(price);
        this.userProperty = new SimpleDoubleProperty(userProperty);
    }

    public String getCurrency() {
        return currency.get();
    }

    public SimpleStringProperty currencyProperty() {
        return currency;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public double getUserProperty() {
        return userProperty.get();
    }

    public SimpleDoubleProperty userPropertyProperty() {
        return userProperty;
    }
}
