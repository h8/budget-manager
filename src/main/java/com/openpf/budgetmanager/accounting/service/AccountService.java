package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        // TODO: sorting Sort.by("title")
        return StreamSupport
                .stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList());
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

        var currency = currencyService.all()
                .stream()
                .filter(c -> c.id.equals(currencyId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("No currency available with id '%d'.", currencyId)
                ));

        var account = new Account();
        account.title = title;
        account.currency = currency;
        account.description = description;

        return repo.save(account);
    }
}
