package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    @Column(nullable = false)
    private LocalDate transactionDate;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String category;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Transactions(){

    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transaction_id) {
        this.transactionId = transaction_id;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transaction_date) {
        this.transactionDate = transaction_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
