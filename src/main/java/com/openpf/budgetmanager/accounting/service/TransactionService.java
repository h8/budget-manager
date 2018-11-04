package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.repository.TransactionRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        // TODO: sorting Sort.by("date").descending()
        return StreamSupport
                .stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Transaction create(
            double amount, long accountId,
            Long categoryId, String description, String date
    ) {
        var t = new Transaction();
        t.amount = amount;
        t.account = accountService.get(accountId).orElseThrow(() ->
                new IllegalArgumentException(String.format("Account with id '%d' doesn't exist.", accountId))
        );
        t.category = categoryService.get(categoryId).orElse(null);
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
