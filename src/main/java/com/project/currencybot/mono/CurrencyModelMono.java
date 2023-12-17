package com.project.currencybot.mono;

import lombok.Data;

@Data
public class CurrencyModelMono {
    private int currencyCodeA;
    private int currencyCodeB;
    private int date;
    private float rateBuy;
    private float rateSell;

    public String getCurrencyName(int code) {
        switch (code) {
            case 980:
                return "UAH";
            case 840:
                return "USD";
            case 978:
                return "EUR";
            default:
                return "Unknown";
        }

    }
}