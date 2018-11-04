package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QueryAccountResolver implements GraphQLQueryResolver {

    private AccountService service;

    @Autowired
    public QueryAccountResolver(AccountService service) {
        this.service = service;
    }

    public List<Account> getAccounts() {
        return service.all();
    }

    public Optional<Account> getAccount(Long id) {
        return service.get(id);
    }
}