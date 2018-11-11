package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static com.openpf.budgetmanager.testutil.CategoryHelper.createCategory;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryCategoryResolverTests {

    @Mock
    private CategoryService service;

    @Mock
    private DataLoaderRegistry loaders;

    @Mock
    private DataLoader<Long, Category> loader;

    @InjectMocks
    private QueryCategoryResolver resolver;

    @Test
    @DisplayName("Get all available categories")
    void getCategories() {
        when(service.all()).thenReturn(Collections.singletonList(createCategory(1L, "C1")));

        var result = resolver.getCategories();
        verify(service).all();
        assertEquals(1, result.size());
        assertEquals(1L, (long) result.get(0).id);
        assertEquals("C1", result.get(0).title);
    }

    @Test
    @DisplayName("Get single existing category")
    void getExistingCategory() throws ExecutionException, InterruptedException {
        when(loader.load(10L)).thenReturn(completedFuture(createCategory(10L, "C10")));
        when(loaders.getDataLoader("category")).then(invocation -> loader);

        var category = resolver.getCategory(10L).get();
        verify(loader).load(10L);
        assertNotNull(category);
        assertEquals("C10", category.title);
    }

    @Test
    @DisplayName("Get non existing category")
    void getNonExistingCategory() throws ExecutionException, InterruptedException {
        when(loader.load(10L)).thenReturn(completedFuture(null));
        when(loaders.getDataLoader("category")).then(invocation -> loader);

        assertNull(resolver.getCategory(10L).get());
        verify(loader).load(10L);
    }
}