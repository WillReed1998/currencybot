package com.project.currencybot.privat;

import lombok.Data;

@Data
public class CurrencyModel {
    String ccy;
    String base_ccy;
    private float buy;
    private float sale;
}
