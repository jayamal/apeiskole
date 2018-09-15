package com.jk.schoo.management.spring.invoice.domain;

/**
 * Created by jayamalk on 9/14/2018.
 */
public class TransactionSummary {

    private Double outstanding = 0.0;
    private Double total = 0.0;
    private Double paidBeforeDue = 0.0;
    private Double totalPaid = 0.0;

    public TransactionSummary(Double outstanding, Double total, Double paidBeforeDue, Double totalPaid) {
        this.outstanding = outstanding;
        this.total = total;
        this.paidBeforeDue = paidBeforeDue;
        this.totalPaid = totalPaid;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Double outstanding) {
        this.outstanding = outstanding;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPaidBeforeDue() {
        return paidBeforeDue;
    }

    public void setPaidBeforeDue(Double paidBeforeDue) {
        this.paidBeforeDue = paidBeforeDue;
    }

    @Override
    public String toString() {
        return "TransactionSummary{" +
                "outstanding=" + outstanding +
                ", total=" + total +
                ", paidBeforeDue=" + paidBeforeDue +
                ", totalPaid=" + totalPaid +
                '}';
    }
}
