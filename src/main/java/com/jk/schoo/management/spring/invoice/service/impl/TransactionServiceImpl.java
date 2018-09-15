package com.jk.schoo.management.spring.invoice.service.impl;

import com.jk.schoo.management.spring.customer.domain.Customer;
import com.jk.schoo.management.spring.invoice.domain.*;
import com.jk.schoo.management.spring.invoice.service.TransactionRepository;
import com.jk.schoo.management.spring.invoice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by jayamalk on 9/13/2018.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Customer customer = transaction.getCustomer();
        PaymentTerm paymentTerm = transaction.getPaymentTerm();
        Double amount = transaction.getOffering().getSalesPriceRate();
        transaction.setAmount(-amount);
        Transaction savedTransaction = transactionRepository.save(transaction);
        //Eligible discounts
        List<Discount> commonDiscounts = new ArrayList<Discount>(customer.getDiscounts());
        commonDiscounts.retainAll(paymentTerm.getDiscounts());
        Double discountTotal = 0.0;
        if (!commonDiscounts.isEmpty()) {
            for (Discount discount : commonDiscounts) {
                //Discount
                Transaction discountTransaction = new Transaction();
                discountTransaction.setTransaction(savedTransaction);
                discountTransaction.setTransactionType(TransactionType.CREDIT_MEMO);
                discountTransaction.setTransactionStatus(TransactionStatus.OPEN);
                Date date = new Date();
                discountTransaction.setDueDate(date);
                discountTransaction.setTransactionDate(date);
                double disc = Math.round((amount * (discount.getDiscountPercentage() / 100.0)) * 100.0) / 100.0;
                discountTransaction.setAmount(disc);
                discountTransaction.setRemark("Discount " + discount.toString());
                transactions.add(discountTransaction);
                discountTotal += discountTransaction.getAmount();
            }
        }
        //Tax
        Transaction nbtTransaction = new Transaction();
        nbtTransaction.setTransaction(savedTransaction);
        nbtTransaction.setTransactionType(TransactionType.CHARGE);
        nbtTransaction.setTransactionStatus(TransactionStatus.OPEN);
        Date date = new Date();
        nbtTransaction.setDueDate(date);
        nbtTransaction.setTransactionDate(date);
        double nbt = Math.round(((amount - discountTotal) * 0.02) * 100.0) / 100.0;
        nbtTransaction.setAmount(-nbt);
        nbtTransaction.setRemark("NBT (2%)");
        transactions.add(nbtTransaction);
        transactionRepository.saveAll(transactions);
        return savedTransaction;
    }

    @Override
    public List<Transaction> generateInMemoryPenaltyTransaction(Transaction transaction) {
        List<Transaction> inMemoryTransactions = new ArrayList<>();
        Date currentDate = new Date();
        Date dueDate = transaction.getDueDate();
        PaymentTerm terms = transaction.getPaymentTerm();
        if (transaction.getTransactionStatus().equals(TransactionStatus.OPEN) && terms != null && dueDate != null) {
            Set<Penalty> penaltyList = terms.getPenalties();
            if (!penaltyList.isEmpty()) {
                Long diffInMillies = dueDate.getTime() - currentDate.getTime();
                if (diffInMillies < 0) {
                    TransactionSummary transactionSummary = getTransactionSummary(transaction, transaction.getTransactionsIncludingSelf());
                    if (transactionSummary.getTotal() / 2.0 > transactionSummary.getPaidBeforeDue()) {
                        //overdue
                        Penalty applicablePenalty = null;
                        Long distance = 0L;
                        Long duration = TimeUnit.DAYS.convert(Math.abs(diffInMillies), TimeUnit.MILLISECONDS);
                        for (Penalty penalty : penaltyList) {
                            if (penalty.getDueDays() < duration) {
                                Long tempDistance = duration - penalty.getDueDays();
                                if (distance.equals(0L)) {
                                    distance = tempDistance;
                                    applicablePenalty = penalty;
                                } else if (tempDistance < distance) {
                                    distance = tempDistance;
                                    applicablePenalty = penalty;
                                }
                            }
                        }
                        //Penalty Transaction
                        Transaction penaltyTransaction = null;
                        penaltyTransaction = new Transaction();
                        penaltyTransaction.setTransaction(transaction);
                        penaltyTransaction.setTransactionType(TransactionType.CHARGE);
                        penaltyTransaction.setTransactionStatus(TransactionStatus.OPEN);
                        Date date = new Date();
                        penaltyTransaction.setDueDate(date);
                        penaltyTransaction.setTransactionDate(date);
                        double penalty = Math.round((transactionSummary.getTotal() * (applicablePenalty.getPenaltyPercentage() / 100.0)) * 100.0) / 100.0;
                        penaltyTransaction.setAmount(-penalty);
                        penaltyTransaction.setRemark("Penalty " + applicablePenalty);
                        //Tax
                        Transaction nbtTransaction = new Transaction();
                        nbtTransaction.setTransaction(transaction);
                        nbtTransaction.setTransactionType(TransactionType.CHARGE);
                        nbtTransaction.setTransactionStatus(TransactionStatus.OPEN);
                        nbtTransaction.setDueDate(date);
                        nbtTransaction.setTransactionDate(date);
                        double nbt = Math.round(((Math.abs(penaltyTransaction.getAmount())) * 0.02) * 100.0) / 100.0;
                        nbtTransaction.setAmount(-nbt);
                        nbtTransaction.setRemark("NBT (2%) for " + "Penalty + " + applicablePenalty);
                        //
                        inMemoryTransactions.add(nbtTransaction);
                        inMemoryTransactions.add(penaltyTransaction);
                    }
                }
            }
        }
        return inMemoryTransactions;
    }

    @Override
    public TransactionSummary getTransactionSummary(Transaction transaction, List<Transaction> subTransactions) {
        Double outstanding = 0.0;
        Double total = 0.0;
        Double paidBeforeDue = 0.0;
        Double totalPaid = 0.0;
        for (Transaction transactionTemp : subTransactions) {
            outstanding += transactionTemp.getAmount();
            total += !transactionTemp.getTransactionType().equals(TransactionType.PAYMENT) ? transactionTemp.getAmount() : 0.0;
            if (transaction.getDueDate().compareTo(transaction.getTransactionDate()) <= 0 && transactionTemp.getTransactionType().equals(TransactionType.PAYMENT)) {
                paidBeforeDue += transaction.getAmount();
            }
            if (transactionTemp.getTransactionType().equals(TransactionType.PAYMENT)) {
                totalPaid += transactionTemp.getAmount();
            }
        }
        outstanding = Math.abs(outstanding);
        total = Math.abs(total);
        paidBeforeDue = Math.abs(paidBeforeDue);
        totalPaid = Math.abs(totalPaid);
        return new TransactionSummary(outstanding, total, paidBeforeDue, totalPaid);
    }

    @Override
    public Transaction createPaymentTransaction(Transaction transaction) throws Exception {
        Transaction parent = transactionRepository.getOne(transaction.getTransaction().getId());
        Transaction saved = null;
        if (parent.getTransactionStatus().equals(TransactionStatus.OPEN)) {
            List<Transaction> penalty = generateInMemoryPenaltyTransaction(parent);
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            transactions.addAll(penalty);
            transactions.addAll(parent.getTransactionsIncludingSelf());
            TransactionSummary transactionSummary = getTransactionSummary(parent, transactions);
            System.out.println(transactionSummary.toString());
            if((transactionSummary.getOutstanding() + transactionSummary.getTotalPaid()) > transactionSummary.getTotal()){
                throw new Exception("Payment amount " + transaction.getAmount() + " is larger than outstanding amount");
            }
            if (transactionSummary.getOutstanding().equals(0.0)) {
                List<Transaction> saveList = penalty;
                Set<Transaction> allSubTransactions = parent.getTransactions();
                for (Transaction transactionSub : allSubTransactions) {
                    saveList.add(transactionSub);
                }
                for (Transaction transactionSub : saveList) {
                    transactionSub.setTransactionStatus(TransactionStatus.CLOSED);
                }
                parent.setTransactionStatus(TransactionStatus.PAID);
                saveList.add(parent);
                transactionRepository.saveAll(saveList);
            }
            saved = transactionRepository.save(transaction);
        } else {
            throw new Exception("Transaction is not in " + TransactionStatus.OPEN + " status");
        }
        return saved;
    }

}
