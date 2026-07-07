package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

public class CurrentMonthSavingsDetail {
    private Double savings;
    private CurrentMonthSavingsReason reason;

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public CurrentMonthSavingsReason getReason() {
        return reason;
    }

    public void setReason(CurrentMonthSavingsReason reason) {
        this.reason = reason;
    }
}
