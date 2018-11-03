package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.openpf.budgetmanager.accounting.model.Currency;
import com.openpf.budgetmanager.accounting.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MutationCurrencyResolver implements GraphQLMutationResolver {

    private CurrencyService service;

    @Autowired
    public MutationCurrencyResolver(CurrencyService service) {
        this.service = service;
    }

    public Currency addCurrency(String code) {
        return service.add(code);
    }
}
