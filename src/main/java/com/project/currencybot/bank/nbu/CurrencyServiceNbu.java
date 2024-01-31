package com.project.currencybot.bank.nbu;

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
public class CurrencyServiceNbu {

    private static final String API_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json%22";

    public static String getCurrencyRateNbu(CurrencyModelNbu modelNbu) throws IOException, ParseException {

        JSONArray jsonArray = getObjects();
        List<CurrencyModelNbu> currencyList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int r030 = jsonObject.getInt("r030");

            if (r030 == 840 || r030 == 978) {
                CurrencyModelNbu currencyModelNbu = new CurrencyModelNbu();
                currencyModelNbu.setCc(jsonObject.getString("cc"));
                currencyModelNbu.setRate(jsonObject.getBigDecimal("rate").floatValue());
                currencyList.add(currencyModelNbu);
            }
        }

        StringBuilder currencyInfo = new StringBuilder();

        for (CurrencyModelNbu currency : currencyList) {
            currencyInfo.append("Курс покупки ").append(currency.getCc())
                    .append(" до курсу UAH ").append(currency.getRate()).append("\n\n");
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