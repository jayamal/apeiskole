package com.jk.schoo.management.spring.common.domain;

import javax.persistence.Id;

/**
 * Created by jayamalk on 7/28/2018.
 */
public class User {

    @Id
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
