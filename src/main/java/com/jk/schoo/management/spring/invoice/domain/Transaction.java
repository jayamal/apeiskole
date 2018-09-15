package com.jk.schoo.management.spring.invoice.domain;

import com.jk.schoo.management.spring.common.domain.AbstractEntity;
import com.jk.schoo.management.spring.customer.domain.Customer;
import com.jk.schoo.management.spring.offering.domain.Offering;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by jayamalk on 9/4/2018.
 */
@Entity
public class Transaction extends AbstractEntity {

    public static final String CUSTOMER = "customer";
    public static final String OFFERING = "offering";
    public static final String PAYMENT_TERM = "paymentTerm";
    public static final String TRANSACTIONS = "transactions";
    public static final String TRANSACTION = "transaction";
    public static final String TRANSACTION_DATE = "transactionDate";
    public static final String DUE_DATE = "dueDate";
    public static final String AMOUNT = "amount";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String TRANSACTION_STATUS = "transactionStatus";
    public static final String PAYMENT_TYPE = "paymentType";
    public static final String PAYMENT_TYPE_REFERENCE = "paymentTypeReference";
    public static final String REMARK = "remark";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offering_id")
    private Offering offering;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentTerm_id")
    private PaymentTerm paymentTerm;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "transaction", orphanRemoval = true)
    private Set<Transaction> transactions = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @NotNull
    private Double amount;
    @NotNull
    private TransactionType transactionType;
    @NotNull
    private TransactionStatus transactionStatus;
    private PaymentType paymentType;
    private String paymentTypeReference;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeReference() {
        return paymentTypeReference;
    }

    public void setPaymentTypeReference(String paymentTypeReference) {
        this.paymentTypeReference = paymentTypeReference;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Offering getOffering() {
        return offering;
    }

    public void setOffering(Offering offering) {
        this.offering = offering;
    }

    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public List<Transaction> getTransactionsIncludingSelf() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.addAll(getTransactions());
        transactions.add(this);
        return transactions;
    }
}
