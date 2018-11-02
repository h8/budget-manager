package com.openpf.budgetmanager.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(columnDefinition = "VARCHAR(100)", unique = true)
    public String title;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="category")
    public List<Transaction> transactions;
}
