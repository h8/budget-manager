package com.openpf.budgetmanager.accounting.model;

import org.springframework.data.annotation.Id;

public class Account {

    @Id
    public Long id;

    public String title;

    public String description;

    public Long currencyId;
}
