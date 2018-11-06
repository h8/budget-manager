package com.openpf.budgetmanager.accounting.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Account {

    @Id
    public Long id;

    public String title;

    public String description;

    @Column("currency_id")
    public Long currencyId;
}
