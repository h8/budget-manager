package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.openpf.budgetmanager.accounting.model.Currency;
import com.openpf.budgetmanager.accounting.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryCurrencyResolver implements GraphQLQueryResolver {

    private CurrencyService service;

    @Autowired
    public QueryCurrencyResolver(CurrencyService service) {
        this.service = service;
    }

    public List<Currency> getCurrencies() {
        return service.all();
    }
}