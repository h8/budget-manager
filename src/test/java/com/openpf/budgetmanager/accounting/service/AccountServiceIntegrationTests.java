package com.openpf.budgetmanager.accounting.service;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@Tag("slow")
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AccountServiceIntegrationTests {

    @Autowired
    private AccountService service;

    @Test
    @DisplayName("Get by Id returns empty Optional")
    void getByIdReturnsEmpty() {
        assertTrue(service.get(10L).isEmpty());
    }

    @Test
    @DisplayName("Get by Id should return value")
    @Sql({"/datasets/accounts-01.sql"})
    @Transactional
    void getByIdReturnsEntity() {
        var optional = service.get(1L);
        assertTrue(optional.isPresent());

        var account = optional.get();
        assertEquals("Account 1", account.title);
    }

    @Test
    @DisplayName("Create new should save the value")
    @Transactional
    void createNew() {
        var account = service.create("Account new", 1L, "");

        assertNotNull(account);
        assertTrue(account.id > 0);
        assertEquals("Account new", account.title);
    }

    @Test
    @DisplayName("Create new with existing name")
    @Sql({"/datasets/accounts-01.sql"})
    @Transactional
    void createNewWithExistingName() {
        assertThrows(DbActionExecutionException.class, () -> service.create("Account 1", 1L, ""));
    }

    @Test
    @DisplayName("Should be sorted by title")
    @Sql({"/datasets/accounts-01.sql"})
    @Transactional
    void getAllSorted() {
        var categories = service.all();

        assertEquals("Account 1", categories.get(0).title);
        assertEquals("Account 2", categories.get(1).title);
        assertEquals("X Account", categories.get(2).title);
    }

    @Test
    @DisplayName("Exists should return actual values")
    @Sql({"/datasets/accounts-01.sql"})
    @Transactional
    void exists() {
        assertTrue(service.exists(1L));
        assertFalse(service.exists(5L));
    }
}