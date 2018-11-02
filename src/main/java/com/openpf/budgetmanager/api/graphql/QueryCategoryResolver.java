package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QueryCategoryResolver implements GraphQLQueryResolver {

    private CategoryService categoryService;

    @Autowired
    public QueryCategoryResolver(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Category> getCategories() {
        return categoryService.all();
    }

    public Optional<Category> getCategory(Long id) {
        return categoryService.get(id);
    }
}