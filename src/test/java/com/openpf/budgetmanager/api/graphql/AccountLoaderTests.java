package com.openpf.budgetmanager.api.graphql;

import com.google.common.collect.Sets;
import com.openpf.budgetmanager.accounting.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static com.openpf.budgetmanager.testutil.AccountHelper.createAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountLoaderTests {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountLoader loader;

    @Test
    @DisplayName("Load values")
    void load() throws ExecutionException, InterruptedException {
        var keys = Sets.newHashSet(1L, 2L);
        when(accountService.getMany(keys))
                .thenReturn(Arrays.asList(createAccount(1L, "A1", 1L), createAccount(2L, "A2", 10L)));

        var categories = loader.load(keys).toCompletableFuture().get();
        assertEquals(2, categories.size());
        assertEquals(1L, (long) categories.get(1L).id);
        assertEquals(2L, (long) categories.get(2L).id);
    }
}