package com.jiahe.scraper;

import com.jiahe.api.ApiWrapper;
import com.jiahe.api.PriceData;
import com.jiahe.db.MysqlCon;

import java.util.Arrays;

public class Scraper {
    public static void main(String[] args) {
        try {
            Integer[] itemids = ApiWrapper.getItemIdList();
            PriceData[] data = ApiWrapper.getPrices(itemids);
//            PriceData[] data = ApiWrapper.getPrices(Arrays.copyOfRange(itemids, 0, 512));

            MysqlCon.addObservation(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
