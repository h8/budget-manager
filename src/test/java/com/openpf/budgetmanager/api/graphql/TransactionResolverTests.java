package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.service.AccountService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.openpf.budgetmanager.testutil.AccountHelper.createOptionalAccount;
import static com.openpf.budgetmanager.testutil.CategoryHelper.createCategory;
import static com.openpf.budgetmanager.testutil.TransactionHelper.createTransaction;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionResolverTests {

    @Mock
    private AccountService accountService;

    @Mock
    private DataLoaderRegistry loaders;

    @Mock
    private DataLoader<Long, Category> loader;

    @InjectMocks
    private TransactionResolver resolver;

    @Test
    @DisplayName("Get account by ID")
    void getAccount() {
        var t = createTransaction(1L);

        when(accountService.get(t.accountId)).thenReturn(createOptionalAccount(t.accountId, "A1", 10L));
        assertEquals(t.accountId, resolver.getAccount(t).id);
    }

    @Test
    @DisplayName("Get account by ID and account not present")
    void getAccountAndThrow() {
        var t = createTransaction(1L);

        when(accountService.get(t.accountId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> resolver.getAccount(t));
    }

    @Test
    @DisplayName("Get category by ID")
    void getCategory() throws ExecutionException, InterruptedException {
        var t = createTransaction(1L);

        when(loader.load(t.categoryId)).thenReturn(completedFuture(createCategory(t.categoryId, "C1")));
        when(loaders.getDataLoader("category")).then(invocation -> loader);

        assertEquals(t.categoryId, resolver.getCategory(t).get().id);
        verify(loader).load(t.categoryId);
    }

    @Test
    @DisplayName("Get category by ID and category not present")
    void getCategoryAndReturnNull() throws ExecutionException, InterruptedException {
        var t = createTransaction(1L);

        when(loader.load(t.categoryId)).thenReturn(completedFuture(null));
        when(loaders.getDataLoader("category")).then(invocation -> loader);

        assertNull(resolver.getCategory(t).get());
        verify(loader).load(t.categoryId);
    }

    @Test
    @DisplayName("Get category by ID and category is null")
    void getCategoryByNullId() throws ExecutionException, InterruptedException {
        var t = createTransaction(1L);
        t.categoryId = null;

        assertNull(resolver.getCategory(t).get());
        verifyZeroInteractions(loader);
        verifyZeroInteractions(loaders);
    }
}