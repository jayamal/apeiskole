package com.jk.schoo.management.spring.transaction.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by jayamalk on 7/23/2018.
 */
//@Entity
public class Discount<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double percentage;
    private Criteria<T> criteria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Criteria<T> getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria<T> criteria) {
        this.criteria = criteria;
    }
}
