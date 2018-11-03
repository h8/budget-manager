package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MutationCategoryResolver implements GraphQLMutationResolver {

    private CategoryService categoryService;

    @Autowired
    public MutationCategoryResolver(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Category addCategory(String title) {
        return categoryService.create(title);
    }

    public Optional<Category> deleteCategory(Long id) {
        return categoryService.delete(id);
    }

    public Optional<Category> renameCategory(Long id, String title) {
        return categoryService.rename(id, title);
    }
}
