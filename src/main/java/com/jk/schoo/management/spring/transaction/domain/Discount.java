package com.jk.schoo.management.spring.transaction.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by jayamalk on 7/23/2018.
 */
@Entity
public class Discount {

    public final static String FIELD_ID = "id";
    public final static String FIELD_CODE = "code";
    public final static String FIELD_DESCRIPTION = "description";
    public final static String FIELD_PERCENTAGE = "percentage";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String code;
    @NotNull
    private String description;
    @NotNull
    @Min(0)
    @Max(100)
    private Double percentage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

}
