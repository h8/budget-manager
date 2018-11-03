package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.openpf.budgetmanager.testutil.CategoryHelper.createCategory;
import static com.openpf.budgetmanager.testutil.CategoryHelper.createOptionalCategory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutationCategoryResolverTests {

    @Mock
    private CategoryService service;

    @InjectMocks
    private MutationCategoryResolver resolver;

    @Test
    @DisplayName("Add new category")
    void addCategory() {
        when(service.create("C1")).thenReturn(createCategory(1L, "C1"));

        var c = resolver.addCategory("C1");
        assertEquals("C1", c.title);
        verify(service).create("C1");
    }

    @Test
    @DisplayName("Try to add category with empty title")
    void addCategoryWithEmptyTitle() {
        when(service.create("")).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> resolver.addCategory(""));
        verify(service).create("");
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Delete category")
    void deleteCategory() {
        when(service.delete(1L)).thenReturn(createOptionalCategory(1L, "C1"));

        var opt = resolver.deleteCategory(1L);
        assertTrue(opt.isPresent());
        assertEquals(1L, (long) opt.get().id);
        verify(service).delete(1L);
    }

    @Test
    @DisplayName("Rename category")
    void renameCategory() {
        when(service.rename(1L, "C2")).thenReturn(createOptionalCategory(1L, "C2"));

        var opt = resolver.renameCategory(1L, "C2");
        assertTrue(opt.isPresent());
        assertEquals(1L, (long) opt.get().id);
        verify(service).rename(1L, "C2");
    }
}