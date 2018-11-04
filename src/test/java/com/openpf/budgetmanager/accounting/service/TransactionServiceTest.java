package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Transaction;
import com.openpf.budgetmanager.accounting.repository.TransactionRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;

import static com.openpf.budgetmanager.testutil.AccountHelper.createOptionalAccount;
import static com.openpf.budgetmanager.testutil.CategoryHelper.createOptionalCategory;
import static com.openpf.budgetmanager.testutil.CurrencyHelper.createCurrency;
import static com.openpf.budgetmanager.testutil.TransactionHelper.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepo repo;

    @Mock
    private AccountService accountService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private TransactionService service;

    @Test
    @DisplayName("Return all transactions")
    void all() {
        when(repo.findAll(Sort.by("date").descending())).thenReturn(
                Arrays.asList(createTransaction(1L), createTransaction(2L))
        );

        var list = service.all();
        assertEquals(2, list.size());
        assertEquals(1L, (long) list.get(0).id);
        verify(repo).findAll(Sort.by("date").descending());
    }

    @Test
    @DisplayName("Create transaction")
    void create() {
        when(accountService.get(1L)).thenReturn(createOptionalAccount(1L, "A1", createCurrency("USD")));
        when(categoryService.get(1L)).thenReturn(createOptionalCategory(1L, "C1"));
        when(repo.save(any())).thenAnswer(invocation -> {
            var t = (Transaction) invocation.getArgument(0);
            t.id = 1L;
            return t;
        });

        var transaction = service.create(-10D, 1L, 1L, "", null);
        assertEquals(1L, (long) transaction.id);
        verify(accountService).get(1L);
        verify(categoryService).get(1L);
        verify(repo).save(argThat(arg -> arg.date != null));
    }

    @Test
    @DisplayName("Create transaction with explicit date")
    void createWithDate() {
        when(accountService.get(1L)).thenReturn(createOptionalAccount(1L, "A1", createCurrency("USD")));
        when(categoryService.get(1L)).thenReturn(createOptionalCategory(1L, "C1"));
        when(repo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var transaction = service.create(-10D, 1L, 1L, "", "2000-01-01");
        assertEquals(2000, transaction.date.toInstant().atZone(ZoneId.systemDefault()).getYear());
        verify(accountService).get(1L);
        verify(categoryService).get(1L);
        verify(repo).save(argThat(arg -> arg.date != null));
    }

    @Test
    @DisplayName("Try to create transaction with explicit but wrong date")
    void createWithInvalidDate() {
        when(accountService.get(1L)).thenReturn(createOptionalAccount(1L, "A1", createCurrency("USD")));
        when(categoryService.get(1L)).thenReturn(createOptionalCategory(1L, "C1"));
        assertThrows(IllegalArgumentException.class, () -> service.create(-10D, 1L, 1L, "", ""));
    }

    @Test
    @DisplayName("Try create transaction for non existing account")
    void createWithoutAccount() {
        when(accountService.get(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.create(-10D, 1L, 1L, "", null));
    }
}