package com.ajs.demo.banking.model;

public class TransactionInfo {


    private Long toAccountId;
    private Long fromAccountId;
    private Integer amount;

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    @Override
    public String toString() {
        return "TransactionInfo{" +
                "toAccountId=" + toAccountId +
                ", fromAccountId=" + fromAccountId +
                ", amount=" + amount +
                '}';
    }
}
