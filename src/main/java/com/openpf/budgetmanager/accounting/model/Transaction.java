package com.openpf.budgetmanager.accounting.model;

//import org.hibernate.annotations.CreationTimestamp;

//import javax.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Date;

//@Entity
//@Table(name = "transaction")
public class Transaction {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

//    @Column
    public Double amount;

//    @ManyToOne(fetch=FetchType.EAGER)
//    @JoinColumn(name="account_id")
    @Transient
    @Column("id")
    public Account account;

//    @ManyToOne(fetch=FetchType.EAGER)
//    @JoinColumn(name="category_id")
    @Column("id")
    @Transient
    public Category category;

//    @Column(columnDefinition = "TEXT")
    public String description;

//    @Column(columnDefinition = "DATE")
    public Date date;

//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_at")
    public Date createdAt;

    @Column("account_id")
    private Long accountId;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Column("category_id")
    private Long categoryId;

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
