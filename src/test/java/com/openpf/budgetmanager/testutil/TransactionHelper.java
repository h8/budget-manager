package com.openpf.budgetmanager.testutil;

import com.openpf.budgetmanager.accounting.model.Transaction;

import java.util.Date;

import static com.openpf.budgetmanager.testutil.AccountHelper.createAccount;
import static com.openpf.budgetmanager.testutil.CategoryHelper.createCategory;
import static com.openpf.budgetmanager.testutil.CurrencyHelper.createCurrency;

public class TransactionHelper {

    public static Transaction createTransaction(Long id) {
        var t = new Transaction();
        t.id = id;
        t.amount = -10D;
        t.category = createCategory(1L, "Category1");
        t.account = createAccount(1L, "Account", createCurrency("USD"));
        t.description = "Description";
        t.date = new Date();
        t.createdAt = new Date();

        return t;
    }
}
