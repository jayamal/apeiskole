package com.jk.schoo.management.spring.common.domain;

import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.transaction.domain.Fee;
import com.jk.schoo.management.spring.transaction.domain.Transaction;

import java.util.Set;

public class AmountBreakdown {

    private Double discount;
    private Double penalty;
    private Double amount;
    private Double outstanding;
    private Double paid;
    final private Fee fee;
    final private Set<Transaction> transactions;

    public AmountBreakdown(Student student, Fee fee) {
        this.transactions = student.getTransactions();
        this.fee = fee;
        Double discount = 0.0;
        Double penalty = 0.0;
        Double amount = fee != null ? fee.getAmount() : 0.0;
        Double outstanding = 0.0;
        Double paid = 0.0;
        if(transactions != null) {
            for (Transaction transaction : transactions) {
                if(fee.getId().equals(transaction.getFee().getId())) {
                    paid += transaction.getAmount();
                }
            }
        }
        outstanding = amount - paid - discount + penalty;
        this.discount = discount;
        this.penalty = penalty;
        this.amount = amount;
        this.outstanding = outstanding;
        this.paid = paid;
    }

    public Fee getFee() {
        return fee;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Double outstanding) {
        this.outstanding = outstanding;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }
}
