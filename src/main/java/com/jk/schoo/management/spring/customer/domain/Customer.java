package com.jk.schoo.management.spring.customer.domain;

import com.jk.schoo.management.spring.common.domain.AbstractEntity;
import com.jk.schoo.management.spring.invoice.domain.Discount;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jayamalk on 9/4/2018.
 */
@Entity
public class Customer  extends AbstractEntity {

    public static final String NAME = "name";
    public static final String DISCOUNTS = "discounts";

    private String name;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "customers")
    private Set<Discount> discounts = new HashSet<>();

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<Discount> discounts) {
        this.discounts = discounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName() + " (" + getId() + ")";
    }
}
