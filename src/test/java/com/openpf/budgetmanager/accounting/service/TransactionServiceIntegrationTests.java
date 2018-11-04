package com.openpf.budgetmanager.accounting.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@Tag("slow")
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class TransactionServiceIntegrationTests {

    @Autowired
    TransactionService transactionService;

    @Test
    @DisplayName("Get all in proper order")
    @Sql({"/datasets/categories-01.sql", "/datasets/accounts-01.sql", "/datasets/transactions-01.sql"})
    @Transactional
    void getAllInOrder() {
        var list = transactionService.all();

        assertEquals(3, list.size());
        assertEquals(2L, (long) list.get(0).id);
        assertEquals(1L, (long) list.get(1).id);
    }

    @Test
    @DisplayName("Create new")
    @Sql({"/datasets/categories-01.sql", "/datasets/accounts-01.sql"})
    @Transactional
    void create() {
        var t = transactionService.create(1.0, 1L, 1L, "", null);

        assertNotNull(t.id);
        assertNotNull(t.date);
        assertNotNull(t.createdAt);
    }
}