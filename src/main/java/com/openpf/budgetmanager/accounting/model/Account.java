package com.openpf.budgetmanager.accounting.model;

//import javax.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;

//@Entity
//@Table(name = "account")
public class Account {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

//    @Column(columnDefinition = "VARCHAR(100)", unique = true)
    public String title;

//    @Column(columnDefinition = "TEXT")
    public String description;

//    @ManyToOne(fetch=FetchType.EAGER)
//    @JoinColumn(name="currency_id")
    @Column("id")
    @Transient
    public Currency currency;

//    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="account")
//    public List<Transaction> transactions;
}
