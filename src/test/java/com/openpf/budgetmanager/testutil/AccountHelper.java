package com.openpf.budgetmanager.testutil;

import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.model.Currency;

public class AccountHelper {
    public static Account createAccount(Long id, String titile, Currency currency) {
        var a = new Account();
        a.title = titile;
        a.id = id;
        a.currency = currency;

        return a;
    }
}
