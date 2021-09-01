package com.jiahe.api;

public class PriceData {
    public int id;
    public boolean whitelisted;
    public Listing buys;
    public Listing sells;

    @Override
    public String toString() {
        return "PriceData{" +
                "id=" + id +
                ", whitelisted=" + whitelisted +
                ", buys=" + buys +
                ", sells=" + sells +
                '}';
    }
}
