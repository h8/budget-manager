package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.AccountService;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.openpf.budgetmanager.testutil.AccountHelper.createOptionalAccount;
import static com.openpf.budgetmanager.testutil.CategoryHelper.createOptionalCategory;
import static com.openpf.budgetmanager.testutil.TransactionHelper.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionResolverTests {

    @Mock
    private AccountService accountService;

    @Mock
    private CategoryService categoryService;

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
    void getCategory() {
        var t = createTransaction(1L);

        when(categoryService.get(t.categoryId)).thenReturn(createOptionalCategory(t.categoryId, "C1"));
        assertEquals(t.categoryId, resolver.getCategory(t).id);
    }

    @Test
    @DisplayName("Get category by ID and category not present")
    void getCategoryAndThrow() {
        var t = createTransaction(1L);

        when(categoryService.get(t.categoryId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> resolver.getCategory(t));
    }
}