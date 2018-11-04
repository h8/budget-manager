package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.openpf.budgetmanager.testutil.AccountHelper.createAccount;
import static com.openpf.budgetmanager.testutil.CurrencyHelper.createCurrency;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MutationAccountResolverTests {

    @Mock
    private AccountService service;

    @InjectMocks
    private MutationAccountResolver resolver;

    @Test
    @DisplayName("Add new account")
    void addAccount() {
        when(service.create("A1", 1L, "")).thenReturn(createAccount(1L, "A1", createCurrency("PLN")));

        var a = resolver.addAccount("A1", 1L, "");
        assertEquals("A1", a.title);
        verify(service).create("A1", 1L, "");
    }
}