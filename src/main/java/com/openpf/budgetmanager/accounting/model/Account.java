package com.openpf.budgetmanager.accounting.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(columnDefinition = "VARCHAR(100)", unique = true)
    public String title;

    @Column(columnDefinition = "TEXT")
    public String description;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="currency_id")
    public Currency currency;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="account")
    public List<Transaction> transactions;
}
