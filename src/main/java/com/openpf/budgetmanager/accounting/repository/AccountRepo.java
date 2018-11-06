package com.openpf.budgetmanager.accounting.repository;

import com.openpf.budgetmanager.accounting.model.Account;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepo extends CrudRepository<Account, Long> {

    @Query("select * from account order by title asc")
    List<Account> findAllByOrderByTitleAsc();
}
