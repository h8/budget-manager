package com.openpf.budgetmanager.accounting.service;

import com.openpf.budgetmanager.accounting.model.Currency;
import com.openpf.budgetmanager.accounting.repository.CurrencyRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.openpf.budgetmanager.testutil.CurrencyHelper.createCurrency;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private CurrencyRepo repo;

    @InjectMocks
    private CurrencyService service;

    @Test
    @DisplayName("Return all currencies")
    void all() {
        when(repo.findAllByOrderByCodeDesc()).thenReturn(
                Arrays.asList(createCurrency("PLN"), createCurrency("USD"))
        );

        var list = service.all();
        assertEquals(2, list.size());
        assertEquals("PLN", list.get(0).code);
        verify(repo).findAllByOrderByCodeDesc();
    }

    @Test
    @DisplayName("Add currency and check code is in uppercase")
    void add() {
        when(repo.save(any())).thenAnswer(invocation -> {
            var c = (Currency) invocation.getArgument(0);
            c.id = 1L;
            return c;
        });

        var c = service.add("rub");
        assertEquals("RUB", c.code);
        assertEquals(1L, (long) c.id);
        verify(repo).save(argThat(arg -> arg.code.equals("RUB")));
    }

    @Test
    @DisplayName("Try to add currency with empty code")
    void addWithEmptyCode() {
        assertThrows(IllegalArgumentException.class, () -> service.add(""));
    }

    @Test
    @DisplayName("Try to add currency with null code")
    void addWithNullCode() {
        assertThrows(IllegalArgumentException.class, () -> service.add(null));
    }

    @Test
    @DisplayName("Try to add currency with code of invalid length")
    void addWithCodeOfWrongLength() {
        assertThrows(IllegalArgumentException.class, () -> service.add("US"));
    }
}