package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

public class ExpensePredictionRequestDTO {
    private Double income;
    private Integer month;
    private Double savings;

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }


    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }
}
