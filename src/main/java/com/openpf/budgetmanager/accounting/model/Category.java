package com.openpf.budgetmanager.accounting.model;

import org.springframework.data.annotation.Id;


public class Category {

    @Id
    public Long id;

    public String title;
}
