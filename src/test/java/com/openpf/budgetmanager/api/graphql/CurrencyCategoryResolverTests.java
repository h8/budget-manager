package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.CurrencyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.openpf.budgetmanager.testutil.CurrencyHelper.createCurrency;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyCategoryResolverTests {

    @Mock
    private CurrencyService service;

    @InjectMocks
    private QueryCurrencyResolver resolver;

    @Test
    @DisplayName("Get all available categories")
    void getAll() {
        when(service.all()).thenReturn(Collections.singletonList(createCurrency("USD")));

        var list = resolver.getCurrencies();
        verify(service).all();
        assertEquals(1, list.size());
        assertEquals("USD", list.get(0).code);
    }
}