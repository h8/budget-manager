package com.openpf.budgetmanager.accounting.repository;

import com.openpf.budgetmanager.accounting.model.Category;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends CrudRepository<Category, Long> {

    @Query("select * from category order by title asc")
    List<Category> findAllByOrderByTitleAsc();
}
