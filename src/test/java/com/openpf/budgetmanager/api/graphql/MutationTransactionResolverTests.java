package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.openpf.budgetmanager.testutil.TransactionHelper.createTransaction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MutationTransactionResolverTests {

    @Mock
    private TransactionService service;

    @InjectMocks
    private MutationTransactionResolver resolver;

    @Test
    @DisplayName("Add new transaction")
    void addTransaction() {
        when(service.create(1D, 1L, 1L, null, null)).thenReturn(createTransaction(1L));

        resolver.addTransaction(1D, 1L, 1L, null, null);
        verify(service).create(1D, 1L, 1L, null, null);
    }
}