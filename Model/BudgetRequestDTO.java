package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

public class BudgetRequestDTO {
    private String category;
    private Double monthlyAmount;
    private Integer userId;
    private Integer budgetId;
    private Integer month;
    private Integer year;

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

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
