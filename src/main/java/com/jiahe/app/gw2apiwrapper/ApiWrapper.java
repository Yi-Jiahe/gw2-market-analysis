package com.jiahe.app.gw2apiwrapper;

import java.io.*;
import java.net.*;

import com.google.gson.*;

public class ApiWrapper {
    public static Integer[] getItemIdList() throws Exception {
        URL url = new URL("https://api.guildwars2.com/v2/commerce/prices");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        System.out.println("Calling /v2/commerce/price endpoint to fetch ids");
        int status = con.getResponseCode();
        System.out.println("Status: " + status);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream())
        );
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        con.disconnect();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Integer[] itemIds = gson.fromJson(content.toString(), Integer[].class);

//        System.out.println(Arrays.toString(itemIds));
        return itemIds;
    }

    public static PriceData[] getPrices(Integer[] itemIds) throws Exception {
        // Maximum batch size permitted by the /v2/commerce/prices endpoint is 200
        int maxBatchSize = 200;

        PriceData[] priceData = new PriceData[itemIds.length];

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        for (int i = 0; i < itemIds.length; i += maxBatchSize) {
            System.out.println(i + "/" + itemIds.length + "prices fetched.");

            int numIds = (i + 200 < itemIds.length) ? 200 : itemIds.length - i;

            String params = "?ids=";

            for (int j = i, k = 0; j < i + numIds; j++, k++) {
                if (k != 0) {
                    params += ",";
                }
                params += itemIds[j].toString();
            }

            URL url = new URL("https://api.guildwars2.com/v2/commerce/prices"+params);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            System.out.println("Calling /v2/commerce/prices to fetch " + numIds + " prices");
            int status = con.getResponseCode();
            System.out.println("Status: " + status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();

            PriceData[] data = gson.fromJson(content.toString(), PriceData[].class);

            for (int j = i, k = 0; j < i + numIds; j++, k++) {
                priceData[j] = data[k];
            }
        }

        return priceData;
    }
}