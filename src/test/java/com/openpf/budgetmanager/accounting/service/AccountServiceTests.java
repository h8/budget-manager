package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.repository.AccountRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static com.openpf.budgetmanager.testutil.AccountHelper.createAccount;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTests {

    @Mock
    private AccountRepo repo;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private AccountService service;

    @Test
    @DisplayName("Get by null Id")
    void getWithNullId() {
        assertTrue(service.get(null).isEmpty());
        verifyZeroInteractions(repo);
    }

    @Test
    @DisplayName("Get by Id returns empty Optional")
    void getReturnsEmpty() {
        when(repo.findById(any())).thenReturn(Optional.empty());

        assertTrue(service.get(1L).isEmpty());
        verify(repo).findById(any());
    }

    @Test
    @DisplayName("Get by Id returns data")
    void getByIdReturnsEntity() {
        Account a = createAccount(1L, "A1", 1L);

        when(repo.findById(a.id)).thenReturn(Optional.of(a));

        var opt = service.get(a.id);
        assertTrue(opt.isPresent());
        verify(repo).findById(any());
    }

    @Test
    @DisplayName("Create new account")
    void createNew() {
        long currencyId = 1L;
        when(currencyService.exists(currencyId)).thenReturn(true);

        when(repo.save(any())).thenAnswer(invocation -> {
            var a = (Account) invocation.getArgument(0);
            a.id = 1L;
            return a;
        });

        var account = service.create("Account 1", currencyId, "Description");
        assertNotNull(account.id);
        assertEquals("Account 1", account.title);
        assertEquals(currencyId, (long) account.currencyId);
        verify(currencyService).exists(currencyId);
        verify(repo).save(any());
    }

    @Test
    @DisplayName("Try to create new account with invalid currency")
    void createNewWithInvalidCurrency() {
        when(currencyService.exists(10L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.create("A1", 10L, null));
        verify(currencyService).exists(10L);
        verifyZeroInteractions(repo);
    }

    @Test
    @DisplayName("Create new account with empty name")
    void createNewWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> service.create("", 1L, null));
        verifyZeroInteractions(repo);
    }

    @Test
    @DisplayName("Create new account with null name")
    void createNewWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null, 1L, null));
        verifyZeroInteractions(repo);
    }

    @Test
    @DisplayName("Get all accounts")
    void all() {
        when(repo.findAllByOrderByTitleAsc()).thenReturn(Collections.emptyList());

        assertTrue(service.all().isEmpty());
        verify(repo).findAllByOrderByTitleAsc();
    }

    @Test
    @DisplayName("Exists should return false for nulls")
    void exists() {
        assertFalse(service.exists(null));
        verifyZeroInteractions(repo);
    }

    @Test
    @DisplayName("Get many by empty list")
    void getMany() {
        Set<Long> ids = emptySet();
        when(repo.findAllById(ids)).thenReturn(emptyList());

        assertTrue(service.getMany(ids).isEmpty());
        verify(repo).findAllById(ids);
    }
}