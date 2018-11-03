package com.openpf.budgetmanager.testutil;

import com.openpf.budgetmanager.accounting.model.Category;

import java.util.Optional;

public class CategoryHelper {

    public static Category createCategory(Long id, String title) {
        var category = new Category();
        category.id = id;
        category.title = title;

        return category;
    }

    public static Optional<Category> createOptionalCategory(Long id, String title) {
        return Optional.of(createCategory(id, title));
    }
}
