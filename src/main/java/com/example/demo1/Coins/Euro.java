package com.example.demo1.Coins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class Euro extends CoinInfo {
    public Euro(String market, double price, double highestPrice, double lowestPrice, double changePercentage) {
        super(market, price, highestPrice, lowestPrice, changePercentage);
    }
}