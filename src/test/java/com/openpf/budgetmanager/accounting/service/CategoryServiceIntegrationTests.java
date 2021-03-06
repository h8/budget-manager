package com.openpf.budgetmanager.accounting.service;

import com.google.common.collect.Sets;
import com.openpf.budgetmanager.accounting.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@Tag("slow")
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CategoryServiceIntegrationTests {

    @Autowired
    private CategoryService service;

    @Test
    @DisplayName("Get by Id returns empty Optional")
    void getByIdReturnsEmpty() {
        assertTrue(service.get(10L).isEmpty());
    }

    @Test
    @DisplayName("Get by Id should return value")
    @Sql({"/datasets/categories-01.sql"})
    @Transactional
    void getByIdReturnsEntity() {
        Optional<Category> optional = service.get(1L);
        assertTrue(optional.isPresent());

        Category category = optional.get();
        assertEquals("Category 1", category.title);
    }

    @Test
    @DisplayName("Create new should save the value")
    @Transactional
    void createNew() {
        Category category = service.create("Category 3");

        assertNotNull(category);
        assertTrue(category.id > 0);
        assertEquals("Category 3", category.title);
    }

    @Test
    @DisplayName("Create new with existing name")
    @Transactional
    void createNewWithExistingName() {
        Category category = service.create("Category 3");
        assertNotNull(category);
        assertThrows(DbActionExecutionException.class, () -> service.create(category.title));
    }

    @Test
    @DisplayName("Delete category")
    @Transactional
    void delete() {
        Category category = service.create("Category 3");

        service.delete(category.id);

        assertTrue(service.get(category.id).isEmpty());
    }

    @Test
    @DisplayName("Rename")
    @Transactional
    void rename() {
        var category = service.create("Category 3");

        service.rename(category.id, "New name");

        var optionalCategory = service.get(category.id);
        assertTrue(optionalCategory.isPresent());
        assertEquals("New name", optionalCategory.get().title);
    }

    @Test
    @DisplayName("Should be sorted by title")
    @Sql({"/datasets/categories-02.sql"})
    @Transactional
    void getAllSorted() {
        var categories = service.all();

        assertEquals("A", categories.get(0).title);
        assertEquals("B", categories.get(1).title);
        assertEquals("F", categories.get(2).title);
    }

    @Test
    @DisplayName("Exists should return actual values")
    @Sql({"/datasets/categories-02.sql"})
    @Transactional
    void exists() {
        assertTrue(service.exists(1L));
        assertFalse(service.exists(5L));
    }

    @Test
    @DisplayName("Get many should return actual values")
    @Sql({"/datasets/categories-02.sql"})
    @Transactional
    void getMany() {
        var categories = service.getMany(Sets.newHashSet(1L, 2L, 15L)).stream()
                .sorted(Comparator.comparing(c -> c.id))
                .collect(Collectors.toList());

        assertEquals(2, categories.size());
        assertEquals(1L, (long) categories.get(0).id);
        assertEquals(2L, (long) categories.get(1).id);
    }
}