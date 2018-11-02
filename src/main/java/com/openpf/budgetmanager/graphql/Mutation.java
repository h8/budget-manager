package com.openpf.budgetmanager.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.openpf.budgetmanager.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {

    private CategoryService categoryService;

    private CurrencyRepo currencyRepo;

    private TransactionRepo transactionRepo;

    private AccountRepo accountRepo;

    @Autowired
    public Mutation(
            CategoryService categoryService, CurrencyRepo currencyRepo,
            TransactionRepo transactionRepo, AccountRepo accountRepo
    ) {
        this.categoryService = categoryService;
        this.currencyRepo = currencyRepo;
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    public Category addCategory(String title) {
        return categoryService.create(title);
    }

    public Optional<Category> deleteCategory(Long id) {
        return categoryService.delete(id);
    }

    public Optional<Category> renameCategory(Long id, String title) {
        return categoryService.rename(id, title);
    }

    public Currency addCurrency(String code) {
        var currency = new Currency();
        currency.code = code;

        return currencyRepo.save(currency);
    }

    public Account addAccount(String title, Long currencyId, String description) {
        var account = new Account();
        account.title = title;
        account.currency = currencyRepo.findById(currencyId).orElseThrow();
        account.description = description;

        return accountRepo.save(account);
    }

    public Transaction addTransaction(double amount, long accountId, Long categoryId, String description) {
        var tr = new Transaction();
        tr.amount = amount;
        tr.account = accountRepo.findById(accountId).orElseThrow();
        tr.category = categoryService.get(categoryId).orElse(null);
        tr.description = description;

        return transactionRepo.save(tr);
    }
}
