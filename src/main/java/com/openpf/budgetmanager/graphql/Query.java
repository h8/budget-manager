package com.openpf.budgetmanager.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.openpf.budgetmanager.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Query implements GraphQLQueryResolver {

    private CategoryService categoryService;

    private CurrencyRepo currencyRepo;

    private TransactionRepo transactionRepo;

    @Autowired
    public Query(CategoryService categoryService, CurrencyRepo currencyRepo, TransactionRepo transactionRepo) {
        this.categoryService = categoryService;
        this.currencyRepo = currencyRepo;
        this.transactionRepo = transactionRepo;
    }

    public List<Category> getCategories() {
        return categoryService.all();
    }

    public Optional<Category> getCategory(Long id) {
        return categoryService.get(id);
    }

    public List<Currency> getCurrencies() {
        return currencyRepo.findAll(Sort.by("code"));
    }

    public List<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }
}