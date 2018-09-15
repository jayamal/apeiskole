package com.jk.schoo.management.spring.offering.domain;

import com.jk.schoo.management.spring.common.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by jayamalk on 9/4/2018.
 */
@Entity
public class Offering extends AbstractEntity{

    public static final String OFFERING_TYPE = "offeringType";
    public static final String CATEGORY = "category";
    public static final String IS_SALE = "sale";
    public static final String SALES_DESCRIPTION = "salesDescription";
    public static final String SALES_PRICERATE = "salesPriceRate";

    private OfferingType offeringType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;
    private Boolean isSale;
    private String salesDescription;
    private Double salesPriceRate;

    public OfferingType getOfferingType() {
        return offeringType;
    }

    public void setOfferingType(OfferingType offeringType) {
        this.offeringType = offeringType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getSale() {
        return isSale;
    }

    public void setSale(Boolean sale) {
        isSale = sale;
    }

    public String getSalesDescription() {
        return salesDescription;
    }

    public void setSalesDescription(String salesDescription) {
        this.salesDescription = salesDescription;
    }

    public Double getSalesPriceRate() {
        return salesPriceRate;
    }

    public void setSalesPriceRate(Double salesPriceRate) {
        this.salesPriceRate = salesPriceRate;
    }

    @Override
    public String toString() {
        return salesDescription + " (" + salesPriceRate + ")";
    }
}
