package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MutationTransactionResolver implements GraphQLMutationResolver {

    private TransactionService service;

    @Autowired
    public MutationTransactionResolver(TransactionService service) {
        this.service = service;
    }

    public Transaction addTransaction(
            double amount, long accountId,
            Long categoryId, String description, String date) {
        return service.create(amount, accountId, categoryId, description, date);
    }
}
