package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.model.Account;
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

import java.util.concurrent.ExecutionException;

import static com.openpf.budgetmanager.testutil.AccountHelper.createAccount;
import static com.openpf.budgetmanager.testutil.CategoryHelper.createCategory;
import static com.openpf.budgetmanager.testutil.TransactionHelper.createTransaction;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionResolverTests {

    @Mock
    private AccountService accountService;

    @Mock
    private DataLoaderRegistry loaders;

    @Mock
    private DataLoader<Long, Category> categoryLoader;

    @Mock
    private DataLoader<Long, Account> accountLoader;

    @InjectMocks
    private TransactionResolver resolver;

    @Test
    @DisplayName("Get account by ID")
    void getAccount() throws ExecutionException, InterruptedException {
        var t = createTransaction(1L);

        when(accountLoader.load(t.accountId)).thenReturn(completedFuture(createAccount(t.accountId, "A1", 10L)));
        when(loaders.getDataLoader("account")).then(invocation -> accountLoader);

        assertEquals(t.accountId, resolver.getAccount(t).get().id);
        verify(accountLoader).load(t.accountId);
    }

    @Test
    @DisplayName("Get account by ID and account not present")
    void getAccountAndGetNull() throws ExecutionException, InterruptedException {
        var t = createTransaction(1L);

        when(accountLoader.load(t.accountId)).thenReturn(completedFuture(null));
        when(loaders.getDataLoader("account")).then(invocation -> accountLoader);

        assertNull(resolver.getAccount(t).get());
        verify(accountLoader).load(t.accountId);
    }

    @Test
    @DisplayName("Get category by ID")
    void getCategory() throws ExecutionException, InterruptedException {
        var t = createTransaction(1L);

        when(categoryLoader.load(t.categoryId)).thenReturn(completedFuture(createCategory(t.categoryId, "C1")));
        when(loaders.getDataLoader("category")).then(invocation -> categoryLoader);

        assertEquals(t.categoryId, resolver.getCategory(t).get().id);
        verify(categoryLoader).load(t.categoryId);
    }

    @Test
    @DisplayName("Get category by ID and category not present")
    void getCategoryAndReturnNull() throws ExecutionException, InterruptedException {
        var t = createTransaction(1L);

        when(categoryLoader.load(t.categoryId)).thenReturn(completedFuture(null));
        when(loaders.getDataLoader("category")).then(invocation -> categoryLoader);

        assertNull(resolver.getCategory(t).get());
        verify(categoryLoader).load(t.categoryId);
    }

    @Test
    @DisplayName("Get category by ID and category is null")
    void getCategoryByNullId() throws ExecutionException, InterruptedException {
        var t = createTransaction(1L);
        t.categoryId = null;

        assertNull(resolver.getCategory(t).get());
        verifyZeroInteractions(categoryLoader);
        verifyZeroInteractions(loaders);
    }
}