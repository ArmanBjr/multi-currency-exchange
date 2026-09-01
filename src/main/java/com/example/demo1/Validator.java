package com.example.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9]{8,12}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
    public static boolean isValidPassword(String password) {
        String regex = "^[a-zA-Z0-9]{8,12}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public static boolean isValidName(String name) {
        return name.length() <= 18 && name.matches("[a-zA-Z]*");
    }
    public static boolean isValidID(String Id) {
        return ((Id.length() == 10 || Id.length() == 6 || Id.length() == 4) && Id.matches("^[0-9]+$"));
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 11 && phoneNumber.matches("^09.*$");
    }
}
