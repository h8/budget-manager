package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.dataloader.MappedBatchLoader;
import org.springframework.core.task.SyncTaskExecutor;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class CategoryLoader implements MappedBatchLoader<Long, Category> {

    private CategoryService categoryService;

    public CategoryLoader(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CompletionStage<Map<Long, Category>> load(Set<Long> keys) {
        return CompletableFuture.supplyAsync(() -> categoryService.getMany(keys)
                .stream()
                .collect(Collectors.toMap(c -> c.id, c -> c)),
                new SyncTaskExecutor());
    }
}
