package com.jiahe.api;

public class Listing {
    public int quantity;
    public int unit_price;

    @Override
    public String toString() {
        return "Listing{" +
                "quantity=" + quantity +
                ", unit_price=" + unit_price +
                '}';
    }
}
