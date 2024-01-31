package com.project.currencybot.bank.privat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyServicePrivat {

    private static final String API_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";

    public static String getCurrencyRatePrivat(CurrencyModelPrivat modelPrivat) throws IOException, ParseException {

        JSONArray jsonArray = getObjects();
        List<CurrencyModelPrivat> currencyList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CurrencyModelPrivat currencyModelPrivat = new CurrencyModelPrivat();
            currencyModelPrivat.setCcy(jsonObject.getString("ccy"));
            currencyModelPrivat.setBase_ccy(jsonObject.getString("base_ccy"));
            currencyModelPrivat.setBuy(Float.parseFloat(jsonObject.getString("buy")));
            currencyModelPrivat.setSale(Float.parseFloat(jsonObject.getString("sale")));

            currencyList.add(currencyModelPrivat);
        }
        StringBuilder currencyInfo = new StringBuilder();

        for (CurrencyModelPrivat currency : currencyList) {
            currencyInfo.append("Курс ").append(currency.getCcy())
                    .append(" до курсу ").append(currency.getBase_ccy()).append("\n")
                    .append("Покупка: ").append(currency.getBuy()).append("\n")
                    .append("Продаж: ").append(currency.getSale()).append("\n\n");
        }

        return currencyInfo.toString();
    }

    private static JSONArray getObjects() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return new JSONArray(response.toString());
    }
}