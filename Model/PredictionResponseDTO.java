package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

public class PredictionResponseDTO {
    private Double predictedExpense;
    private Double predictedSavings;

    public Double getPredictedExpense() {
        return predictedExpense;
    }

    public void setPredictedExpense(Double predictedExpense) {
        this.predictedExpense = predictedExpense;
    }

    public Double getPredictedSavings() {
        return predictedSavings;
    }

    public void setPredictedSavings(Double predictedSavings) {
        this.predictedSavings = predictedSavings;
    }
}
