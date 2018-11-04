package com.openpf.budgetmanager.accounting.repository;

import com.openpf.budgetmanager.accounting.model.Transaction;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {
}
