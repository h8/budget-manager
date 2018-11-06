package com.openpf.budgetmanager.accounting.repository;

import com.openpf.budgetmanager.accounting.model.Transaction;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {

    @Query("select * from transaction order by date desc")
    List<Transaction> findAllByOrderByDateDesc();
}
