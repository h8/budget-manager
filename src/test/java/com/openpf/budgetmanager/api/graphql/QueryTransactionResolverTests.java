package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.openpf.budgetmanager.testutil.TransactionHelper.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryTransactionResolverTests {

    @Mock
    private TransactionService service;

    @InjectMocks
    private QueryTransactionResolver resolver;

    @Test
    @DisplayName("Get all available transactions")
    void getAll() {
        when(service.all()).thenReturn(Arrays.asList(createTransaction(1L), createTransaction(2L)));

        var result = resolver.getTransactions();
        verify(service).all();
        assertEquals(2, result.size());
    }
}