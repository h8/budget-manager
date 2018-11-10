package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.service.AccountService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Service
public class TransactionResolver implements GraphQLResolver<Transaction> {

    private AccountService accountService;

    private DataLoaderRegistry loaders;

    @Autowired
    public TransactionResolver(AccountService accountService, DataLoaderRegistry loaders) {
        this.accountService = accountService;
        this.loaders = loaders;
    }

    public Account getAccount(Transaction transaction) {
        return accountService.get(transaction.accountId).orElseThrow();
    }

    public CompletableFuture<Category> getCategory(Transaction transaction) {
        if (transaction.categoryId == null) {
            return completedFuture(null);
        }

        DataLoader<Long, Category> loader = loaders.getDataLoader("category");

        return loader.load(transaction.categoryId);
    }
}
