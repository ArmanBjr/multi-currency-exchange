package com.example.demo1.User;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrencyForecast {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/exchange";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {

            String lastDateQuery = "SELECT MAX(date) as lastDate FROM currency_rates";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(lastDateQuery);

            Date lastDate = null;
            if (resultSet.next()) {
                lastDate = resultSet.getDate("lastDate");
            }

            if (lastDate == null) {
                throw new RuntimeException("No data found in the database.");
            }

            String query = "SELECT date, time, usd, euro, toman, yen, GBP FROM currency_rates";
            resultSet = statement.executeQuery(query);

            SimpleRegression usdRegression = new SimpleRegression();
            SimpleRegression euroRegression = new SimpleRegression();
            SimpleRegression tomanRegression = new SimpleRegression();
            SimpleRegression yenRegression = new SimpleRegression();
            SimpleRegression gpbRegression = new SimpleRegression();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                Time time = resultSet.getTime("time");
                double usd = resultSet.getDouble("usd");
                double euro = resultSet.getDouble("euro");
                double toman = resultSet.getDouble("toman");
                double yen = resultSet.getDouble("yen");
                double gpb = resultSet.getDouble("GBP");

                String dateTimeString = date.toString() + " " + time.toString();
                Date dateTime = dateFormat.parse(dateTimeString);
                long timeInMillis = dateTime.getTime();

                usdRegression.addData(timeInMillis, usd);
                euroRegression.addData(timeInMillis, euro);
                tomanRegression.addData(timeInMillis, toman);
                yenRegression.addData(timeInMillis, yen);
                gpbRegression.addData(timeInMillis, gpb);
            }
            String insertQuery = "INSERT INTO currency_rates (date, time, usd, euro, toman, yen, GBP) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            Calendar forecastCalendar = Calendar.getInstance();
            forecastCalendar.setTime(lastDate);
            forecastCalendar.set(Calendar.HOUR_OF_DAY, 0);
            forecastCalendar.set(Calendar.MINUTE, 0);
            forecastCalendar.set(Calendar.SECOND, 0);
            forecastCalendar.set(Calendar.MILLISECOND, 0);

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(2024, Calendar.AUGUST, 1, 0, 0);

            while (forecastCalendar.before(endCalendar)) {
                long forecastTimeInMillis = forecastCalendar.getTimeInMillis();
                Date forecastDate = new Date(forecastTimeInMillis);
                Time forecastTime = new Time(forecastTimeInMillis);

                double forecastedUsd = usdRegression.predict(forecastTimeInMillis);
                double forecastedEuro = euroRegression.predict(forecastTimeInMillis);
                double forecastedToman = tomanRegression.predict(forecastTimeInMillis);
                double forecastedYen = yenRegression.predict(forecastTimeInMillis);
                double forecastedGpb = gpbRegression.predict(forecastTimeInMillis);

                forecastedUsd = Math.abs(forecastedUsd);
                forecastedEuro = Math.abs(forecastedEuro);
                forecastedToman = Math.abs(forecastedToman);
                forecastedYen = Math.abs(forecastedYen);
                forecastedGpb = Math.abs(forecastedGpb);

                forecastedUsd = Math.round(forecastedUsd * 100.0) / 100.0;
                forecastedEuro = Math.round(forecastedEuro * 100.0) / 100.0;
                forecastedToman = Math.round(forecastedToman * 100.0) / 100.0;
                forecastedYen = Math.round(forecastedYen * 100.0) / 100.0;
                forecastedGpb = Math.round(forecastedGpb * 100.0) / 100.0;

                preparedStatement.setDate(1, new java.sql.Date(forecastDate.getTime()));
                preparedStatement.setTime(2, forecastTime);
                preparedStatement.setDouble(3, forecastedUsd);
                preparedStatement.setDouble(4, forecastedEuro);
                preparedStatement.setDouble(5, forecastedToman);
                preparedStatement.setDouble(6, forecastedYen);
                preparedStatement.setDouble(7, forecastedGpb);

                preparedStatement.addBatch();

                forecastCalendar.add(Calendar.MINUTE, 1);
            }

            preparedStatement.executeBatch();

        } catch (SQLException | java.text.ParseException e) {
            e.printStackTrace();
        }
    }
}
