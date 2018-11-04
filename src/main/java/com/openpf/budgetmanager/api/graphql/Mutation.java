package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.repository.AccountRepo;
import com.openpf.budgetmanager.accounting.repository.TransactionRepo;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class Mutation implements GraphQLMutationResolver {

    private CategoryService categoryService;

    private TransactionRepo transactionRepo;

    private AccountRepo accountRepo;

    @Autowired
    public Mutation(
            CategoryService categoryService,
            TransactionRepo transactionRepo, AccountRepo accountRepo
    ) {
        this.categoryService = categoryService;
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    public Transaction addTransaction(
            double amount, long accountId,
            Long categoryId, String description, String date) {
        var tr = new Transaction();
        tr.amount = amount;
        tr.account = accountRepo.findById(accountId).orElseThrow();
        tr.category = categoryService.get(categoryId).orElse(null);
        tr.description = description;
        tr.date = (date != null)
                ? Date.from(LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toInstant())
                : new Date();

        return transactionRepo.save(tr);
    }
}
