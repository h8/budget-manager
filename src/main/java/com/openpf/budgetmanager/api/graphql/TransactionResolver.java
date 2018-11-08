package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.service.AccountService;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionResolver implements GraphQLResolver<Transaction> {

    private AccountService accountService;

    private CategoryService categoryService;

    @Autowired
    public TransactionResolver(AccountService accountService, CategoryService categoryService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    public Account getAccount(Transaction transaction) {
        return accountService.get(transaction.accountId).orElseThrow();
    }

    public Category getCategory(Transaction transaction) {
        return categoryService.get(transaction.categoryId).orElseThrow();
    }
}
