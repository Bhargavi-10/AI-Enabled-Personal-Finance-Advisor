package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

import java.time.LocalDate;

public class DailySpendingDTO {

    private LocalDate date;
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
