package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryTransactionResolver implements GraphQLQueryResolver {

    private TransactionService service;

    @Autowired
    public QueryTransactionResolver(TransactionService transactionRepo) {
        this.service = transactionRepo;
    }

    public List<Transaction> getTransactions() {
        return service.all();
    }
}