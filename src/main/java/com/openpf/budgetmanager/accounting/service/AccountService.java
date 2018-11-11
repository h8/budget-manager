package com.openpf.budgetmanager.accounting.service;

import com.google.common.collect.Lists;
import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {

    private AccountRepo repo;

    private CurrencyService currencyService;

    @Autowired
    public AccountService(AccountRepo repo, CurrencyService currencyService) {
        this.repo = repo;
        this.currencyService = currencyService;
    }

    public List<Account> all() {
        return repo.findAllByOrderByTitleAsc();
    }

    public Optional<Account> get(Long id) {
        return (id == null)
                ? Optional.empty()
                : repo.findById(id);
    }

    public Account create(String title, Long currencyId, String description) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Account title can't be blank.");
        }

        if (!currencyService.exists(currencyId)) {
            throw new IllegalArgumentException(String.format("No currency available with id '%d'.", currencyId));
        }

        var account = new Account();
        account.title = title;
        account.currencyId = currencyId;
        account.description = description;

        return repo.save(account);
    }

    public boolean exists(Long id) {
        return (id != null) && repo.existsById(id);
    }

    public List<Account> getMany(Set<Long> keys) {
        return Lists.newLinkedList(repo.findAllById(keys));
    }
}
