package com.jiahe.app.db;

import java.sql.*;
import java.util.Arrays;
import java.time.Instant;

import com.jiahe.app.gw2apiwrapper.PriceData;

public class MysqlCon{
    public static void createObserationTable() throws Exception {
        Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/"+System.getenv("DB_NAME"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));

        Statement stmt=con.createStatement();
        int result = stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS observations_table (
                item_id INT NOT NULL,
                buy_price INT NOT NULL,
                sell_price INT NOT NULL,
                buy_orders INT NOT NULL,
                sell_orders INT NOT NULL,
                timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
            )""");

        System.out.println(result);

        con.close();
    }

    public static void addObservation(PriceData[] data) throws Exception {
        Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/"+System.getenv("DB_NAME"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));

        PreparedStatement pstmt = con.prepareStatement("""
            INSERT INTO observations_table (item_id, buy_price, sell_price, buy_orders, sell_orders)
            VALUES (?, ?, ?, ?, ?)""");
        for (PriceData d : data) {
            pstmt.setInt(1, d.id);
            pstmt.setInt(2, d.buys.unit_price);
            pstmt.setInt(3, d.sells.unit_price);
            pstmt.setInt(4, d.buys.quantity);
            pstmt.setInt(5, d.sells.quantity);
            pstmt.addBatch();
        }

        System.out.println("Inserting " + data.length + " observations");

        int[] result = pstmt.executeBatch();

        System.out.println(Arrays.toString(result));

        con.close();
    }

    // Returns the 2 latest observations for a given offset in days
    // Observations are taken from different windows to try and spread them out
    static PriceData[] getLatest2ObservationsByOffset(int itemId, int offset) throws Exception {
        Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/"+System.getenv("DB_NAME"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));

        long unixTime = Instant.now().getEpochSecond();

        long secondsPerDay = 86400;

        PriceData[] data = new PriceData[2];
        int i = 0;
        for (PriceData d: data) {
            PreparedStatement pstmt = con.prepareStatement("""
            SELECT buy_price, sell_price, buy_orders, sell_orders 
            FROM observations_table 
            WHERE item_id=? 
            AND timestamp BETWEEN ? AND ?)
            ORDER BY timestamp DESC 
            LIMIT 1""");

            pstmt.setInt(1, itemId);
            pstmt.setLong(2, (long)(unixTime-secondsPerDay*((i+1)/2)));
            pstmt.setLong(3, (long)(unixTime-secondsPerDay*((i)/2)));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                d.id = itemId;
                d.buys.unit_price = rs.getInt("buy_price");
                d.sells.unit_price = rs.getInt("sell_price");
                d.buys.quantity = rs.getInt("buy_orders");
                d.sells.quantity = rs.getInt("sell_orders");
            }
            i++;
        }

        System.out.println(Arrays.toString(data));

        con.close();

        return data;
    }

    static void updateRunningAverage() {
        int[] days = {7, 30};
        String[][] columns = {
                {},
                {}
        };
    }
}