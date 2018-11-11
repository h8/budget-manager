package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.service.AccountService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static com.openpf.budgetmanager.testutil.AccountHelper.createAccount;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryAccountResolverTests {

    @Mock
    private AccountService service;

    @Mock
    private DataLoaderRegistry loaders;

    @Mock
    private DataLoader<Long, Account> loader;


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
    void getExistingAccount() throws ExecutionException, InterruptedException {
        when(loader.load(10L)).thenReturn(completedFuture(createAccount(10L, "A10", 1L)));
        when(loaders.getDataLoader("account")).then(invocation -> loader);

        var a = resolver.getAccount(10L).get();
        verify(loader).load(10L);
        assertEquals("A10", a.title);
    }

    @Test
    @DisplayName("Get non existing account")
    void getNonExistingAccount() throws ExecutionException, InterruptedException {
        when(loader.load(10L)).thenReturn(completedFuture(null));
        when(loaders.getDataLoader("account")).then(invocation -> loader);

        assertNull(resolver.getAccount(10L).get());
        verify(loader).load(10L);
    }
}