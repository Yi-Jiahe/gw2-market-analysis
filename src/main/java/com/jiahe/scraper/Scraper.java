package com.jiahe.scraper;

import com.jiahe.api.ApiWrapper;
import com.jiahe.api.PriceData;
import com.jiahe.db.MysqlCon;

public class Scraper implements Runnable {
    @Override
    public void run() {
        try {
            Integer[] itemIds = ApiWrapper.getItemIdList();
            PriceData[] data = ApiWrapper.getPrices(itemIds);

            MysqlCon.createObserationTable();
            MysqlCon.addObservation(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
