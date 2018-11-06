package com.openpf.budgetmanager.accounting.repository;

import com.openpf.budgetmanager.accounting.model.Transaction;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;

import java.util.Date;

@Configuration
@EnableJdbcRepositories
public class RepositoryConfiguration extends JdbcConfiguration {

    @Bean
    public ApplicationListener<?> autoDateSetter() {

        return (ApplicationListener<BeforeSaveEvent>) event -> {
            if (event.getEntity() instanceof Transaction) {
                var t = (Transaction) event.getEntity();

                if (t.createdAt == null) {
                    t.createdAt = new Date();
                }
            }
        };
    }
}
