package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.repository.AccountRepo;
import com.openpf.budgetmanager.accounting.repository.TransactionRepo;
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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@Tag("slow")
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class TransactionServiceIntegrationTests {

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    CategoryService categoryService;

    @Test
    @DisplayName("Get by Id should return value")
    @Sql({"/datasets/categories-01.sql", "/datasets/accounts-01.sql", "/datasets/transactions-01.sql"})
    @Transactional
    void getByIdReturnsEntity() {
        var transactionOptional = transactionRepo.findById(1L);

        assertTrue(transactionOptional.isPresent());
        var tr = transactionOptional.get();

        assertNotNull(tr.category);
        assertNotNull(tr.account);
    }

    @Test
    @DisplayName("Should save")
    @Sql({"/datasets/categories-01.sql", "/datasets/accounts-01.sql"})
    @Transactional
    void save() {
        var tr = new Transaction();
        tr.account = accountRepo.findById(1L).orElseThrow(AssertionError::new);
        tr.category = categoryService.get(1L).orElse(null);
        tr.amount = -15.0;
        tr.description = "Test";
        tr.date = new Date();

        var saved = transactionRepo.save(tr);

        assertEquals("PLN", saved.account.currency.code);
        assertEquals("Category 1", saved.category.title);
        assertNotNull(saved.createdAt);
    }
}