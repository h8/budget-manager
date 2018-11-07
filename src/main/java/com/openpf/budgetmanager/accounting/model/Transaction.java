package com.openpf.budgetmanager.accounting.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Transaction {
    @Id
    public Long id;

    public Double amount;

    public Long accountId;

    public Long categoryId;

    public String description;

    public Date date;

    public Date createdAt;
}
