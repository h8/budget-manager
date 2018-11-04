package com.openpf.budgetmanager.accounting.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@Tag("slow")
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CurrencyServiceIntegrationTests {

    @Autowired
    private CurrencyService service;

    @Test
    @DisplayName("Should be sorted by code")
    void checkOrder() {
        var list = service.all();

        assertEquals("EUR", list.get(0).code);
        assertEquals("PLN", list.get(1).code);
        assertEquals("USD", list.get(2).code);
    }

    @Test
    @Transactional
    @DisplayName("New currency should be saved")
    void addNew() {
        var c = service.add("AAA");

        assertTrue(c.id > 0);
    }

    @Test
    @Transactional
    @DisplayName("Try to add currency with existing code")
    void addExisting() {
        assertThrows(DbActionExecutionException.class, () -> service.add("PLN"));
    }
}