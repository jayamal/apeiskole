package com.jk.schoo.management.spring.invoice.service;

import com.jk.schoo.management.spring.invoice.domain.PaymentTerm;
import com.jk.schoo.management.spring.invoice.domain.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by jayamalk on 9/6/2018.
 */
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    public Set<Penalty> findByPaymentTerm(PaymentTerm paymentTerm);

}
