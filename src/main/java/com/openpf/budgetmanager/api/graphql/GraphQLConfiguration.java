package com.openpf.budgetmanager.api.graphql;

import com.openpf.budgetmanager.accounting.service.AccountService;
import com.openpf.budgetmanager.accounting.service.CategoryService;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentation;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class GraphQLConfiguration {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public DataLoaderRegistry dataLoaderRegistry(
            CategoryService categoryService,
            AccountService accountService
    ) {
        DataLoaderRegistry registry = new DataLoaderRegistry();

        registry.register("category", DataLoader.newMappedDataLoader(new CategoryLoader(categoryService)));

        registry.register("account", DataLoader.newMappedDataLoader(new AccountLoader(accountService)));

        return registry;
    }

    @Bean
    public Instrumentation instrumentation(DataLoaderRegistry dataLoaderRegistry) {
        return new DataLoaderDispatcherInstrumentation(dataLoaderRegistry);
    }
}
