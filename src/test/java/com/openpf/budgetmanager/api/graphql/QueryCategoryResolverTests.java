package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.openpf.budgetmanager.testutil.CategoryHelper.createCategory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryCategoryResolverTests {

    @Mock
    private CategoryService service;

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
    void getExistingCategory() {
        when(service.get(10L)).thenReturn(Optional.of(createCategory(10L, "C10")));

        var opt = resolver.getCategory(10L);
        verify(service).get(10L);
        assertTrue(opt.isPresent());
        assertEquals("C10", opt.get().title);
    }

    @Test
    @DisplayName("Get non existing category")
    void getNonExistingCategory() {
        when(service.get(any())).thenReturn(Optional.empty());

        assertTrue(resolver.getCategory(10L).isEmpty());
        verify(service).get(any());
    }
}