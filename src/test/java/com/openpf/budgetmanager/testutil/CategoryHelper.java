package com.openpf.budgetmanager.testutil;

import com.openpf.budgetmanager.accounting.model.Category;

public class CategoryHelper {

    public static Category createCategory(Long id, String title) {
        var category = new Category();
        category.id = id;
        category.title = title;

        return category;
    }
}
