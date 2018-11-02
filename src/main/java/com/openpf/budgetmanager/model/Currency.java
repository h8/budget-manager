package com.openpf.budgetmanager.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(columnDefinition = "VARCHAR(3)", unique = true)
    public String code;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="currency")
    public List<Transaction> transactions;
}
