package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.model.Transaction;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Service
public class TransactionResolver implements GraphQLResolver<Transaction> {

    private DataLoaderRegistry loaders;

    @Autowired
    public TransactionResolver(DataLoaderRegistry loaders) {
        this.loaders = loaders;
    }

    public CompletableFuture<Account> getAccount(Transaction transaction) {
        DataLoader<Long, Account> loader = loaders.getDataLoader("account");

        return loader.load(transaction.accountId);
    }

    public CompletableFuture<Category> getCategory(Transaction transaction) {
        if (transaction.categoryId == null) {
            return completedFuture(null);
        }

        DataLoader<Long, Category> loader = loaders.getDataLoader("category");

        return loader.load(transaction.categoryId);
    }
}
