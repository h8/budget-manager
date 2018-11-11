package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.service.AccountService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class QueryAccountResolver implements GraphQLQueryResolver {

    private AccountService service;

    private DataLoaderRegistry loaders;

    @Autowired
    public QueryAccountResolver(AccountService service, DataLoaderRegistry loaders) {
        this.service = service;
        this.loaders = loaders;
    }

    public List<Account> getAccounts() {
        return service.all();
    }

    public CompletableFuture<Account> getAccount(Long id) {
        DataLoader<Long, Account> loader = loaders.getDataLoader("account");

        return loader.load(id);
    }
}