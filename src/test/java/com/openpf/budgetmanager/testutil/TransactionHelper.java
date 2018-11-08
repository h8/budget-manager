package com.openpf.budgetmanager.testutil;

import com.openpf.budgetmanager.accounting.model.Transaction;

import java.util.Date;

public class TransactionHelper {

    public static Transaction createTransaction(Long id) {
        var t = new Transaction();
        t.id = id;
        t.amount = -10D;
        t.categoryId = 1L;
        t.accountId = 1L;
        t.description = "Description";
        t.date = new Date();
        t.createdAt = new Date();

        return t;
    }
}
