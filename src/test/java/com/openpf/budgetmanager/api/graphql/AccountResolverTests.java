package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.CurrencyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.openpf.budgetmanager.testutil.AccountHelper.createAccount;
import static com.openpf.budgetmanager.testutil.CurrencyHelper.createCurrency;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountResolverTests {

    @Mock
    private CurrencyService service;

    @InjectMocks
    private AccountResolver resolver;

    @Test
    @DisplayName("Get currency by ID")
    void getCurrency() {
        var a = createAccount(1L, "Account 1", 10L);

        when(service.get(a.currencyId)).thenReturn(Optional.of(createCurrency(a.currencyId, "USD")));
        assertEquals(a.currencyId, resolver.getCurrency(a).id);
    }

    @Test
    @DisplayName("Get currency by ID and currency not present")
    void getCurrencyAndThrow() {
        var a = createAccount(1L, "Account 1", 10L);

        when(service.get(a.currencyId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> resolver.getCurrency(a));
    }
}