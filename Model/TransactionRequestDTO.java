package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

import java.time.LocalDate;

public class TransactionRequestDTO {
    private LocalDate transactionDate;
    private Double amount;
    private String category;
    private String description;
    private Integer userId;
    private Integer transactionId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transaction_date) {
        this.transactionDate = transaction_date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
