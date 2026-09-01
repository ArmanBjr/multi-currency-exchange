package com.example.demo1.Utilities;

import javafx.beans.binding.ObjectExpression;

public class Sort {
    public static void sort(Object o[]) {
        for (int i = 0; i + 1 < o.length; i++) {
            if (o[i].equals(o[i + 1])) {
                Object temple = o[i];
                o[i] = o[i + 1];
                o[i + 1] = temple;
            }
        }
    }
}
