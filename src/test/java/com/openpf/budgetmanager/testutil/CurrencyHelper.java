package com.openpf.budgetmanager.testutil;

import com.openpf.budgetmanager.accounting.model.Currency;

public class CurrencyHelper {

    public static Currency createCurrency(String code) {
        var c = new Currency();
        c.code = code.toUpperCase();

        return c;
    }

    public static Currency createCurrency(Long id, String code) {
        var c = new Currency();
        c.id = id;
        c.code = code.toUpperCase();

        return c;
    }
}
