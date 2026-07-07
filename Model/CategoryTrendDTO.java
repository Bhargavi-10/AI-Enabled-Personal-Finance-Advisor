package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

public class CategoryTrendDTO {

    private Integer month;
    private String category;
    private Double amount;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
