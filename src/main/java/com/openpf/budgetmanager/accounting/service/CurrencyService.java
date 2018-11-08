package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Currency;
import com.openpf.budgetmanager.accounting.repository.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    private CurrencyRepo repo;

    @Autowired
    public CurrencyService(CurrencyRepo repo) {
        this.repo = repo;
    }

    public Optional<Currency> get(Long id) {
        return (id != null)
                ? repo.findById(id)
                : Optional.empty();
    }

    public List<Currency> all() {
        return repo.findAllByOrderByCodeAsc();
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

    public boolean exists(Long id) {
        return (id != null) && repo.existsById(id);
    }
}
