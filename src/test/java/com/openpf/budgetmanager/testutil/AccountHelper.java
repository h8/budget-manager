package com.openpf.budgetmanager.testutil;

import com.openpf.budgetmanager.accounting.model.Account;

import java.util.Optional;

public class AccountHelper {
    public static Account createAccount(Long id, String titile, Long currencyId) {
        var a = new Account();
        a.title = titile;
        a.id = id;
        a.currencyId = currencyId;

        return a;
    }

    public static Optional<Account> createOptionalAccount(Long id, String titile, Long currencyId) {
        return Optional.of(createAccount(id, titile, currencyId));
    }
}
