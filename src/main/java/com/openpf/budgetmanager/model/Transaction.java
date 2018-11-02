package com.openpf.budgetmanager.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public Double amount;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="account_id")
    public Account account;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="category_id")
    public Category category;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(columnDefinition = "DATE")
    public Date date;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    public Date createdAt;
}
