package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.openpf.budgetmanager.testutil.AccountHelper.createAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryAccountResolverTests {

    @Mock
    private AccountService service;

    @InjectMocks
    private QueryAccountResolver resolver;

    @Test
    @DisplayName("Get all available accounts")
    void getAccounts() {
        when(service.all()).thenReturn(Collections.singletonList(createAccount(1L, "A1", 1L)));

        var result = resolver.getAccounts();
        verify(service).all();
        assertEquals(1, result.size());
        assertEquals(1L, (long) result.get(0).id);
        assertEquals("A1", result.get(0).title);
    }

    @Test
    @DisplayName("Get single existing account")
    void getExistingAccount() {
        when(service.get(10L)).thenReturn(Optional.of(createAccount(10L, "A10", 1L)));

        var opt = resolver.getAccount(10L);
        verify(service).get(10L);
        assertTrue(opt.isPresent());
        assertEquals("A10", opt.get().title);
    }

    @Test
    @DisplayName("Get non existing account")
    void getNonExistingAccount() {
        when(service.get(any())).thenReturn(Optional.empty());

        assertTrue(resolver.getAccount(10L).isEmpty());
        verify(service).get(any());
    }
}