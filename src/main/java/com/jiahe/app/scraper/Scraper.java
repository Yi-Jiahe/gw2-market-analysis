package com.jiahe.app.scraper;

import com.jiahe.app.gw2apiwrapper.ApiWrapper;
import com.jiahe.app.gw2apiwrapper.PriceData;
import com.jiahe.app.db.MysqlCon;

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
