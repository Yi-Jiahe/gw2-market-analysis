package com.jiahe.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name="observations_table")
public class Observation {
    @Column(name="item_id")
    private int itemId;
    @Column(name="buy_price")
    private int buyPrice;
    @Column(name="sell_price")
    private int sellPrice;
    @Column(name="buy_orders")
    private int buyOrders;
    @Column(name="sell_orders")
    private int sellOrders;
    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date timestamp;

    public Observation() {
    }

    public Observation(int itemId, int buyPrice, int sellPrice, int buyOrders, int sellOrders, Date timestamp) {
        this.itemId = itemId;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
        this.timestamp = timestamp;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getBuyOrders() {
        return buyOrders;
    }

    public void setBuyOrders(int buyOrders) {
        this.buyOrders = buyOrders;
    }

    public int getSellOrders() {
        return sellOrders;
    }

    public void setSellOrders(int sellOrders) {
        this.sellOrders = sellOrders;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}