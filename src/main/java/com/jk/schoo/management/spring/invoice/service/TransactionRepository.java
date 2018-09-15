package com.jk.schoo.management.spring.invoice.service;

import com.jk.schoo.management.spring.invoice.domain.Transaction;
import com.jk.schoo.management.spring.invoice.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by jayamalk on 9/6/2018.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public Collection<Transaction> findByTransactionType(TransactionType invoice);
}
