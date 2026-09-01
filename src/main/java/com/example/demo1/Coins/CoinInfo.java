package com.example.demo1.Coins;

public class CoinInfo {
    private String market;
    private double price;
    private double highestPrice;
    private double lowestPrice;
    private double changePercentage;

    public CoinInfo(String market, double price, double highestPrice, double lowestPrice, double changePercentage) {
        this.market = market;
        this.price = price;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.changePercentage = changePercentage;
    }

    // Getters and setters
    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }
}