package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.CurrencyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.openpf.budgetmanager.testutil.CurrencyHelper.createCurrency;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MutationCurrencyResolverTests {

    @Mock
    private CurrencyService service;

    @InjectMocks
    private MutationCurrencyResolver resolver;

    @Test
    @DisplayName("Add new currency")
    void addCategory() {
        when(service.add("USD")).thenReturn(createCurrency("USD"));

        var c = resolver.addCurrency("USD");
        assertEquals("USD", c.code);
        verify(service).add("USD");
    }
}