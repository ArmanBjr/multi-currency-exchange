package com.example.demo1.User;

import com.example.demo1.DataBase;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExcelToDatabase {

    public static void main(String[] args) {
        try (InputStream csvStream = ExcelToDatabase.class.getResourceAsStream("/Data/price.csv");
             InputStreamReader reader = new InputStreamReader(csvStream, StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(reader);
             Connection connection = DataBase.connectDb()) {

            if (csvStream == null) {
                throw new IllegalStateException("Missing resource: src/main/resources/Data/price.csv");
            }

            String[] nextLine;
            String sql = "INSERT INTO currency_rates (date, time, usd, euro, toman, yen, GBP) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            csvReader.readNext(); // Skip header row

            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length >= 7) {
                    statement.setString(1, nextLine[0]);
                    statement.setString(2, nextLine[1]);
                    statement.setDouble(3, Double.parseDouble(nextLine[2]));
                    statement.setDouble(4, Double.parseDouble(nextLine[3]));
                    statement.setDouble(5, Double.parseDouble(nextLine[4]));
                    statement.setDouble(6, Double.parseDouble(nextLine[5]));
                    statement.setDouble(7, Double.parseDouble(nextLine[6]));
                    statement.addBatch();
                } else {
                    System.err.println("Skipping line: " + String.join(",", nextLine));
                }
            }

            statement.executeBatch();
            System.out.println("Imported currency rates from price.csv");

        } catch (IOException | CsvValidationException | SQLException e) {
            e.printStackTrace();
        }
    }
}
