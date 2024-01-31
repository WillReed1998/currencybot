package com.project.currencybot.bank.mono;

import lombok.Data;

@Data
public class CurrencyModelMono {
    private int currencyCodeA;
    private int currencyCodeB;
    private int date;
    private float rateBuy;
    private float rateSell;

    public String getCurrencyName(int code) {
        return switch (code) {
            case 980 -> "UAH";
            case 840 -> "USD";
            case 978 -> "EUR";
            default -> "Unknown";
        };

    }
}