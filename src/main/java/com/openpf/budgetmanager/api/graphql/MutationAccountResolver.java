package com.openpf.budgetmanager.api.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.openpf.budgetmanager.accounting.model.Account;
import com.openpf.budgetmanager.accounting.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MutationAccountResolver implements GraphQLMutationResolver {

    private AccountService service;

    @Autowired
    public MutationAccountResolver(AccountService service) {
        this.service = service;
    }

    public Account addAccount(String title, Long currencyId, String description) {
        return service.create(title, currencyId, description);
    }
}
