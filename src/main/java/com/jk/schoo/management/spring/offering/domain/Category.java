package com.jk.schoo.management.spring.offering.domain;

import com.jk.schoo.management.spring.common.domain.AbstractEntity;

import javax.persistence.Entity;

/**
 * Created by jayamalk on 9/4/2018.
 */
@Entity
public class Category  extends AbstractEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
