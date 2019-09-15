package com.ajs.demo.banking.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class AccountTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="account_id")
    private Long accountId;

    @Column(name="updated_by")
    private Long updatedBy;

    @Column(name="updated_on")
    private Long updatedOn;

    @Column(name="message")
    private String message;

    @Column(name="balance")
    private Integer balance;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountTransactions{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", updatedBy=" + updatedBy +
                ", updatedOn=" + updatedOn +
                ", message='" + message + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
