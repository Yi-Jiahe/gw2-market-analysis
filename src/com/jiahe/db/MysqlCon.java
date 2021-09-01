package com.jiahe.db;

import java.sql.*;
import java.util.Arrays;

import com.jiahe.api.PriceData;
import com.jiahe.api.Listing;

public class MysqlCon{
    public static void main(String args[]){
        try{
//           createObserationTable();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void createObserationTable() throws Exception {
        Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/"+System.getenv("DB_NAME"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));

        Statement stmt=con.createStatement();
        int result = stmt.executeUpdate("""
            CREATE TABLE observations_table (
                itemid INT NOT NULL,
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
            INSERT INTO observations_table (itemid, buy_price, sell_price, buy_orders, sell_orders)
            VALUES (?, ?, ?, ?, ?)""");
        for (PriceData d : data) {
            pstmt.setInt(1, d.id);
            pstmt.setInt(2, d.buys.unit_price);
            pstmt.setInt(3, d.sells.unit_price);
            pstmt.setInt(4, d.buys.quantity);
            pstmt.setInt(5, d.sells.quantity);
            pstmt.addBatch();
        }

        int[] result = pstmt.executeBatch();

        System.out.println(Arrays.toString(result));

        con.close();
    }
}