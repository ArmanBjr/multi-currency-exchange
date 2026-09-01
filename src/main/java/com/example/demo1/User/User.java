package com.example.demo1.User;

import com.example.demo1.CurrencyManagement.Wallet;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String email;
    private String imageUrl;
    public Wallet wallet;
    public User(String username, String email, String password, String firstName, String lastName, String phoneNumber, String imageUrl) {
        setEmail(email);
        setName(firstName, lastName);
        setPassword(password);
        setUsername(username);
        setPhoneNumber(phoneNumber);
        setImageUrl(imageUrl);
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public String getUsername() {
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getPassword() {
        return this.password;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    @Override
    public String toString() {
        return this.username + " " + this.password;
    }
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
    public Wallet getWallet() {
        return this.wallet;
    }
}
