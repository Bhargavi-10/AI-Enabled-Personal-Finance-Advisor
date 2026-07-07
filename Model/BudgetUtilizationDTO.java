package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

public class BudgetUtilizationDTO {

    private String category;
    private Double utilization;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getUtilization() {
        return utilization;
    }

    public void setUtilization(Double utilization) {
        this.utilization = utilization;
    }
}
