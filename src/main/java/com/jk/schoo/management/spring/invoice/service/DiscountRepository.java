package com.jk.schoo.management.spring.invoice.service;

import com.jk.schoo.management.spring.invoice.domain.Discount;
import com.jk.schoo.management.spring.invoice.domain.PaymentTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by jayamalk on 9/6/2018.
 */
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    public Set<Discount> findByPaymentTerm(PaymentTerm paymentTerm);

}
