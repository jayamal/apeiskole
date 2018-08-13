package com.jk.schoo.management.spring.transaction.domain;

/**
 * Created by jayamalk on 7/23/2018.
 */
public interface Criteria<T> {

    Boolean checkEligibility(T bean);

    Long getUniqueKey();

}
