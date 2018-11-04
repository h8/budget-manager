package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private TransactionRepo transactionRepo;

    @Autowired
    public Query(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public List<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }
}