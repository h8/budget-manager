package com.openpf.budgetmanager.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTests {

    @Mock
    private CategoryRepo repo;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Get by Id returns empty Optional")
    void getByIdReturnsEmpty() {
        when(repo.findById(any())).thenReturn(Optional.empty());

        assertTrue(categoryService.get(1L).isEmpty());
    }

    @Test
    @DisplayName("Get by Id returns data")
    void getByIdReturnsEntity() {
        Category c = new Category();
        c.id = 10L;
        c.title = "Category 1";

        when(repo.findById(c.id)).thenReturn(Optional.of(c));

        Optional<Category> optionalCategory = categoryService.get(c.id);
        assertTrue(optionalCategory.isPresent());

        Category category = optionalCategory.get();
        assertEquals(c.id, category.id);
        assertEquals(c.title, category.title);
    }

    @Test
    @DisplayName("Save new category")
    void createNew() {
        Category c = new Category();
        c.id = 10L;

        when(repo.save(any())).thenReturn(c);

        Category category = categoryService.create("Category 1");
        assertEquals(c.id, category.id);
    }
}