package com.openpf.budgetmanager.accounting.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Date;

public class Transaction {
    @Id
    public Long id;

    public Double amount;

    @Column("account_id")
    public Long accountId;

    @Column("category_id")
    public Long categoryId;

    public String description;

    public Date date;

    @Column("created_at")
    public Date createdAt;
}
