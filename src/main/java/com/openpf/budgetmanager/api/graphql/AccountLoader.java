package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.service.AccountService;
import org.dataloader.MappedBatchLoader;
import org.springframework.core.task.SyncTaskExecutor;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class AccountLoader implements MappedBatchLoader<Long, Account> {

    private AccountService accountService;

    public AccountLoader(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CompletionStage<Map<Long, Account>> load(Set<Long> keys) {
        return CompletableFuture.supplyAsync(() -> accountService.getMany(keys)
                .stream()
                .collect(Collectors.toMap(a -> a.id, a -> a)),
                new SyncTaskExecutor());
    }
}
