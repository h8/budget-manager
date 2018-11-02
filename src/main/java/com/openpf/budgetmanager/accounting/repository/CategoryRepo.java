package com.openpf.budgetmanager.accounting.repository;

import com.openpf.budgetmanager.accounting.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
}
