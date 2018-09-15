package com.jk.schoo.management.spring.invoice.service;

import com.jk.schoo.management.spring.invoice.domain.PaymentTerm;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jayamalk on 9/6/2018.
 */
public interface PaymentTermRepository extends JpaRepository<PaymentTerm, Long> {

}
