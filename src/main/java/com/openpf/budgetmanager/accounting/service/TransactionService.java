package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.repository.TransactionRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private TransactionRepo repo;

    private AccountService accountService;

    private CategoryService categoryService;

    @Autowired
    public TransactionService(
            TransactionRepo repo,
            AccountService accountService,
            CategoryService categoryService
    ) {
        this.repo = repo;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    public List<Transaction> all() {
        return repo.findAllByOrderByDateDesc();
    }

    public Transaction create(
            double amount, long accountId,
            Long categoryId, String description, String date
    ) {
        if (!accountService.exists(accountId)) {
            throw new IllegalArgumentException(String.format("Account with id '%d' doesn't exist.", accountId));
        }

        var t = new Transaction();
        t.amount = amount;
        t.accountId = accountId;
        t.categoryId = (categoryService.exists(categoryId))
                ? categoryId
                : null;
        t.description = description;
        t.date = (date != null)
                ? parseDate(date)
                : new Date();

        return repo.save(t);
    }

    @NotNull
    private Date parseDate(String date) {
        try {
            return Date.from(LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(String.format("Can't parse transaction date '%s'.", date), e);
        }
    }
}
