package com.openpf.budgetmanager.accounting.model;

//import javax.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.List;

//@Entity
//@Table(name = "currency")
public class Currency {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    //@Column(columnDefinition = "VARCHAR(3)", unique = true)
    public String code;

    //@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="currency")
    //public List<Account> accounts;
}
