package com.jk.schoo.management.spring.report.domain;

/**
 * Created by jayamalk on 7/9/2018.
 */
public interface Reportable<T> {

    String getTitle();

    String generate(T metaData);
}
