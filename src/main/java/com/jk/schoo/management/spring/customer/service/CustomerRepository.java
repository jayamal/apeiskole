package com.jk.schoo.management.spring.customer.service;

import com.jk.schoo.management.spring.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jayamalk on 9/6/2018.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
