package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.repository.CategoryRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTests {

    @Mock
    private CategoryRepo repo;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Get by null Id")
    void getWithNullId() {
        assertTrue(categoryService.get(null).isEmpty());
        verifyZeroInteractions(repo);
    }

    @Test
    @DisplayName("Get by Id returns empty Optional")
    void getReturnsEmpty() {
        when(repo.findById(any())).thenReturn(Optional.empty());

        assertTrue(categoryService.get(1L).isEmpty());
        verify(repo).findById(any());
    }

    @Test
    @DisplayName("Get by Id returns data")
    void getByIdReturnsEntity() {
        Category c = createCategory(10L, "Category 1");

        when(repo.findById(c.id)).thenReturn(Optional.of(c));

        var opt = categoryService.get(c.id);
        assertTrue(opt.isPresent());
        verify(repo).findById(any());

        Category category = opt.get();
        assertEquals(c.id, category.id);
        assertEquals(c.title, category.title);
    }

    @Test
    @DisplayName("Create new category")
    void createNew() {
        Category c = createCategory(10L, "Category 1");

        when(repo.save(any())).thenReturn(c);

        Category category = categoryService.create(c.title);
        assertEquals(c.id, category.id);
        verify(repo).save(any());
    }

    @Test
    @DisplayName("Create new with existing name")
    void createNewWithExistingName() {
        when(repo.save(any())).thenThrow(new DataIntegrityViolationException("Already exists"));

        assertThrows(DataIntegrityViolationException.class, () -> categoryService.create("Existing category"));
        verify(repo).save(any());
    }

    @Test
    @DisplayName("Create new category with empty name")
    void createNewWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.create(""));
        verifyZeroInteractions(repo);
    }

    @Test
    @DisplayName("Create new category with null name")
    void createNewWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.create(null));
        verifyZeroInteractions(repo);
    }

    @Test
    @DisplayName("Get all categories")
    void all() {
        when(repo.findAll((Sort) any())).thenReturn(Collections.emptyList());

        assertTrue(categoryService.all().isEmpty());
        verify(repo).findAll((Sort) any());
    }

    @Test
    @DisplayName("Delete existing category")
    void deleteExisting() {
        Category category = createCategory(1L, "Category");

        when(repo.findById(category.id)).thenReturn(Optional.of(category));

        var opt = categoryService.delete(category.id);
        verify(repo).findById(any());
        verify(repo).delete(any());
        assertTrue(opt.isPresent());
        assertEquals(category, opt.get());
    }

    @Test
    @DisplayName("Delete non existing category")
    void deleteNonExisting() {
        when(repo.findById(any())).thenReturn(Optional.empty());
        assertTrue(categoryService.delete(15L).isEmpty());
        verify(repo).findById(any());
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("Rename existing category")
    void renameExisting() {
        when(repo.findById(any())).thenReturn(Optional.of(createCategory(5L, "C5")));

        var opt = categoryService.rename(5L, "New title");
        verify(repo).findById(any());
        verify(repo).save(any());
        assertTrue(opt.isPresent());
        var category = opt.get();
        assertEquals(5L, (long) category.id);
        assertEquals("New title", category.title);
    }

    @Test
    @DisplayName("Rename non existing category")
    void renameNonExisting() {
        when(repo.findById(any())).thenReturn(Optional.empty());

        var opt = categoryService.rename(155L, "New title");
        verify(repo).findById(any());
        verifyNoMoreInteractions(repo);
        assertTrue(opt.isEmpty());
    }

    private Category createCategory(Long id, String title) {
        var category = new Category();
        category.id = id;
        category.title = title;

        return category;
    }
}