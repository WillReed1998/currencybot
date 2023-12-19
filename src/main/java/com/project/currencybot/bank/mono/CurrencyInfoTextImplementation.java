package com.project.currencybot.bank.mono;

import java.io.IOException;
import java.text.ParseException;

public class CurrencyInfoTextImplementation {

    public static void main(String[] args) {
        CurrencyModelMono modelMono = new CurrencyModelMono();

        try {
            String currencyInfo = CurrencyServiceMono.getCurrencyRateMono(modelMono);
            System.out.println("Currency Information:");
            System.out.println(currencyInfo);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}