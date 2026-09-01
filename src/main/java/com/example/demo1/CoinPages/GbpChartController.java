package com.example.demo1.CoinPages;

import com.example.demo1.DataBase;
import com.example.demo1.Utilities.Values;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class GbpChartController {

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label ChangePercentage;

    @FXML
    private Label HighestPrice;

    @FXML
    private Label LowestPrice;

    @FXML
    private Label Price;

    private XYChart.Series<Number, Number> series;

    @FXML
    private Button DailyChange;
    @FXML
    private Button MonthluChange;
    @FXML
    private Button minuteChange;

    private int situ = 1;
    public void switchSitu(ActionEvent event) {
        if(event.getSource() == minuteChange) {
            xAxis.setLabel("minute");
            updateChartForLast30Minutes();
            situ = 1;
        } else if (event.getSource() == DailyChange) {
            xAxis.setLabel("Hour");
            situ = 2;
            updateChart();
        } else if (event.getSource() == MonthluChange) {
            xAxis.setLabel("day");
            updateChartForLast30Days();
            situ = 3;
        }
    }

    @FXML
    public void initialize() {
        //series.setName("Change");
        series = new XYChart.Series<>();
        lineChart.getData().add(series);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(24);
        xAxis.setTickUnit(1);

        yAxis.setAutoRanging(true);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    updateChart();
                    updateLabels();
                });
            }
        }, 0, 60000);
    }

    private void updateChart() {
        if (situ == 2) {

            xAxis.setLabel("Hour");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            series.getData().clear();

            for (int i = 23; i >= 0; i--) {
                LocalDateTime startOfHour = now.minusHours(i).withMinute(0).withSecond(0).withNano(0);
                LocalDateTime endOfHour = startOfHour.plusHours(1);

                String startOfHourDate = startOfHour.format(dateFormatter);
                String startOfHourTime = startOfHour.format(timeFormatter);
                String endOfHourDate = endOfHour.format(dateFormatter);
                String endOfHourTime = endOfHour.format(timeFormatter);

                String query = "SELECT AVG(GBP) as avg_GBP FROM currency_rates WHERE " +
                        "(date = '" + startOfHourDate + "' AND time >= '" + startOfHourTime + "') OR " +
                        "(date = '" + endOfHourDate + "' AND time < '" + endOfHourTime + "')";

                try (Connection conn = DataBase.connectDb();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(query)) {

                    if (rs.next()) {
                        double avgPrice = rs.getDouble("avg_GBP");
                        series.getData().add(new XYChart.Data<>(24 - i, avgPrice));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                xAxis.setLowerBound(0);
                xAxis.setUpperBound(24);
                xAxis.setTickUnit(1);
            }
        } else if (situ == 1) {
            xAxis.setLabel("minute");
            updateChartForLast30Minutes();
        } else {
            xAxis.setLabel("Day");
            updateChartForLast30Days();
        }
    }
    private void updateChartForLast30Days() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        series.getData().clear();

        for (int i = 29; i >= 0; i--) {
            LocalDate date = now.minusDays(i);
            String dateString = date.format(dateFormatter);

            String query = "SELECT AVG(GBP) as avg_GBP FROM currency_rates WHERE date = '" + dateString + "'";

            try (Connection conn = DataBase.connectDb();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                if (rs.next()) {
                    double avgPrice = rs.getDouble("avg_GBP");
                    series.getData().add(new XYChart.Data<>(30 - i, avgPrice));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(30);
        xAxis.setTickUnit(1);
    }

    private void updateChartForLast30Minutes() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        series.getData().clear();

        for (int i = 30; i >= 0; i--) {
            LocalDateTime startOfMinute = now.minusMinutes(i).withSecond(0).withNano(0);
            LocalDateTime endOfMinute = startOfMinute.plusMinutes(1);

            String startOfMinuteDate = startOfMinute.format(dateFormatter);
            String startOfMinuteTime = startOfMinute.format(timeFormatter);
            String endOfMinuteDate = endOfMinute.format(dateFormatter);
            String endOfMinuteTime = endOfMinute.format(timeFormatter);

            String query = "SELECT AVG(GBP) as avg_GBP FROM currency_rates WHERE " +
                    "(date = '" + startOfMinuteDate + "' AND time >= '" + startOfMinuteTime + "') OR " +
                    "(date = '" + endOfMinuteDate + "' AND time < '" + endOfMinuteTime + "')";

            try (Connection conn = DataBase.connectDb();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                if (rs.next()) {
                    double avgPrice = rs.getDouble("avg_GBP");
                    series.getData().add(new XYChart.Data<>(30 - i, avgPrice));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(30);
        xAxis.setTickUnit(1);
    }

    public void updateLabels() {
        try (Connection connection = DataBase.connectDb()) {
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Query to get the latest values up to current date and time
            String query = "SELECT euro, usd, GBP, toman, yen FROM currency_rates WHERE CONCAT(date, ' ', time) <= ? ORDER BY CONCAT(date, ' ', time) DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, formattedDate + " " + formattedTime);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double euro = resultSet.getDouble("euro");
                double usd = resultSet.getDouble("usd");
                double gpb = resultSet.getDouble("GBP");
                double toman = resultSet.getDouble("toman");
                double yen = resultSet.getDouble("yen");
                Price.setText(String.valueOf(gpb));
                LowestPrice.setText(String.valueOf(Values.getLowestPrice(connection, "GBP", formattedDate, formattedTime)));
                HighestPrice.setText(String.valueOf(Values.getHighestPrice(connection, "GBP", formattedDate, formattedTime)));
                ChangePercentage.setText(String.valueOf(Values.getChangePercentage(connection, "GBP", formattedDate, formattedTime)));
                if (Values.getChangePercentage(connection, "GBP", formattedDate, formattedTime) >= 0) {
                    ChangePercentage.setTextFill(Color.GREEN);
                } else {
                    ChangePercentage.setTextFill(Color.RED);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
