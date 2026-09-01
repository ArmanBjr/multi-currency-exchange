package com.example.demo1.Server;

import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static List<String> users = new ArrayList<>();
    public static void add(List<String> list) {
        for (String str: list) {
            users.add(str);
        }
    }
}
