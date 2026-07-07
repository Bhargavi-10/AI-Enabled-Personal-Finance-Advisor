package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

public class IncomeExpenseDTO {
    private Integer month;
    private Double income;
    private Double expense;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }
}
