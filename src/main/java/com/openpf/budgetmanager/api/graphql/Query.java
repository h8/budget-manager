package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.repository.AccountRepo;
import com.openpf.budgetmanager.accounting.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Query implements GraphQLQueryResolver {

    private TransactionRepo transactionRepo;

    private AccountRepo accountRepo;

    @Autowired
    public Query(TransactionRepo transactionRepo, AccountRepo accountRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    public List<Account> getAccounts() {
        return accountRepo.findAll(Sort.by("title"));
    }

    public Optional<Account> getAccount(Long id) {
        return accountRepo.findById(id);
    }

    public List<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }
}