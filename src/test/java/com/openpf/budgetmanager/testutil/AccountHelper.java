package com.openpf.budgetmanager.testutil;

import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.model.Currency;

import java.util.Optional;

public class AccountHelper {
    public static Account createAccount(Long id, String titile, Currency currency) {
        var a = new Account();
        a.title = titile;
        a.id = id;
        a.currency = currency;

        return a;
    }

    public static Optional<Account> createOptionalAccount(Long id, String titile, Currency currency) {
        return Optional.of(createAccount(id, titile, currency));
    }
}
