package com.openpf.budgetmanager.api.graphql;

import com.google.common.collect.Sets;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static com.openpf.budgetmanager.testutil.CategoryHelper.createCategory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryLoaderTests {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryLoader loader;

    @Test
    @DisplayName("Load values")
    void load() throws ExecutionException, InterruptedException {
        var keys = Sets.newHashSet(1L, 2L);
        when(categoryService.getMany(keys))
                .thenReturn(Arrays.asList(createCategory(1L, "C1"), createCategory(2L, "C2")));

        var categories = loader.load(keys).toCompletableFuture().get();
        assertEquals(2, categories.size());
        assertEquals(1L, (long) categories.get(1L).id);
        assertEquals(2L, (long) categories.get(2L).id);
    }
}