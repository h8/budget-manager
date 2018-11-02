package com.openpf.budgetmanager.accounting.repository;

import com.openpf.budgetmanager.accounting.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
}
