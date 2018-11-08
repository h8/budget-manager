package com.openpf.budgetmanager.accounting.model;

import org.springframework.data.annotation.Id;

public class Currency {

    @Id
    public Long id;

    public String code;
}
