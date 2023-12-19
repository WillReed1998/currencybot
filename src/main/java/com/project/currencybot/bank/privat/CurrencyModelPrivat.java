package com.project.currencybot.bank.privat;

import lombok.Data;

@Data
public class CurrencyModelPrivat {
    private String ccy;
    private String base_ccy;
    private float buy;
    private float sale;
}
