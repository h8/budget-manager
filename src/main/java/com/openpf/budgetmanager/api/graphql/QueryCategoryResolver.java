package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class QueryCategoryResolver implements GraphQLQueryResolver {

    private CategoryService categoryService;

    private DataLoaderRegistry loaders;

    @Autowired
    public QueryCategoryResolver(CategoryService categoryService, DataLoaderRegistry loaders) {
        this.categoryService = categoryService;
        this.loaders = loaders;
    }

    public List<Category> getCategories() {
        return categoryService.all();
    }

    public CompletableFuture<Category> getCategory(Long id) {
        DataLoader<Long, Category> loader = loaders.getDataLoader("category");

        return loader.load(id);
    }
}