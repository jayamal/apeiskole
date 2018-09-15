package com.jk.schoo.management.spring.invoice.service;

import com.jk.schoo.management.spring.invoice.domain.Transaction;
import com.jk.schoo.management.spring.invoice.domain.TransactionSummary;

import java.util.List;

/**
 * Created by jayamalk on 9/13/2018.
 */
public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    List<Transaction> generateInMemoryPenaltyTransaction(Transaction transaction);

    TransactionSummary getTransactionSummary(Transaction transaction, List<Transaction> subTransactions);

    Transaction createPaymentTransaction(Transaction transaction) throws Exception;
}
