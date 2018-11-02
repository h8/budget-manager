package com.openpf.budgetmanager.accounting.repository;

import com.openpf.budgetmanager.accounting.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository<Currency, Long> {
}
