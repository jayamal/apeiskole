package com.jk.schoo.management.spring.transaction.service.dao;

import com.jk.schoo.management.spring.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jayamalk on 7/28/2018.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
