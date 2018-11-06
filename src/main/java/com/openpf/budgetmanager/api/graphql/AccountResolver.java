package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.model.Currency;
import com.openpf.budgetmanager.accounting.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountResolver implements GraphQLResolver<Account> {

    private CurrencyService currencyService;

    @Autowired
    public AccountResolver(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public Currency getCurrency(Account account) {
        return currencyService.get(account.currencyId).orElseThrow();
    }
}
