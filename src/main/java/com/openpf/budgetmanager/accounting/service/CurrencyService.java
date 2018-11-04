package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Currency;
import com.openpf.budgetmanager.accounting.repository.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class CurrencyService {

    private CurrencyRepo repo;

    @Autowired
    public CurrencyService(CurrencyRepo repo) {
        this.repo = repo;
    }

    public List<Currency> all() {
        // TODO: sorting Sort.by("code")
        return StreamSupport
                .stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Currency add(String code) {
        if (code == null || code.isBlank() || code.length() != 3) {
            throw new IllegalArgumentException(
                    String.format("Currency code is invalid: '%s'.", code)
            );
        }

        var c = new Currency();
        c.code = code.toUpperCase();

        return repo.save(c);
    }
}
