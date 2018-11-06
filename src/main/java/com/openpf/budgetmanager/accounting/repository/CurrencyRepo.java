package com.openpf.budgetmanager.accounting.repository;

import com.openpf.budgetmanager.accounting.model.Currency;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrencyRepo extends CrudRepository<Currency, Long> {

    @Query("select * from currency order by code asc")
    List<Currency> findAllByOrderByCodeDesc();
}
