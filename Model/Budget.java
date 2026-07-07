package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer budgetId;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Double monthlyAmount;
    @Column(nullable = false)
    private Integer month;
    @Column(nullable = false)
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Budget(){

    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budget_id) {
        this.budgetId = budget_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(Double monthly_amount) {
        this.monthlyAmount = monthly_amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
