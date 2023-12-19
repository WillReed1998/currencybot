package com.project.currencybot.bank.mono;

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

public class CurrencyServiceMono {
    public static final String API_URL = "https://api.monobank.ua/bank/currency";

    public static String getCurrencyRateMono(CurrencyModelMono modelMono) throws IOException, ParseException {

        JSONArray jsonArray = getObjects();
        List<CurrencyModelMono> euroList = new ArrayList<>();
        List<CurrencyModelMono> otherCurrenciesList = new ArrayList<>();

        for (int i = 0; i < Math.min(2, jsonArray.length()); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CurrencyModelMono currencyModelMono = new CurrencyModelMono();
            currencyModelMono.setCurrencyCodeA(jsonObject.getInt("currencyCodeA"));
            currencyModelMono.setCurrencyCodeB(jsonObject.getInt("currencyCodeB"));
            currencyModelMono.setDate(jsonObject.getInt("date"));
            currencyModelMono.setRateBuy(jsonObject.getBigDecimal("rateBuy").floatValue());
            currencyModelMono.setRateSell(jsonObject.getBigDecimal("rateSell").floatValue());

            if (currencyModelMono.getCurrencyCodeA() == 978) { // 978 is the currency code for EUR
                euroList.add(currencyModelMono);
            } else {
                otherCurrenciesList.add(currencyModelMono);
            }
        }

        euroList.addAll(otherCurrenciesList);

        StringBuilder currencyInfo = new StringBuilder();

        for (CurrencyModelMono currency : euroList) {
            String currencyCodeA = currency.getCurrencyName(currency.getCurrencyCodeA());
            String currencyCodeB = currency.getCurrencyName(currency.getCurrencyCodeB());

            currencyInfo.append("Курс ").append(currencyCodeA)
                    .append(" до курсу ").append(currencyCodeB).append("\n")
                    .append("Покупка: ").append(currency.getRateBuy()).append("\n")
                    .append("Продаж: ").append(currency.getRateSell()).append("\n\n");
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

        JSONArray jsonArray = new JSONArray(response.toString());
        JSONArray TwoObjects = new JSONArray();

        for (int i = 0; i < Math.min(2, jsonArray.length()); i++) {
            TwoObjects.put(jsonArray.get(i));
        }

        return jsonArray;
    }

}
