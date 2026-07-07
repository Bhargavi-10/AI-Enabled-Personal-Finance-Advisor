package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

public class PredictionDTO {
    private Double predictedSpending;
    private Double predictedSavings;

    public Double getPredictedSpending() {
        return predictedSpending;
    }

    public void setPredictedSpending(Double predictedSpending) {
        this.predictedSpending = predictedSpending;
    }

    public Double getPredictedSavings() {
        return predictedSavings;
    }

    public void setPredictedSavings(Double predictedSaving) {
        this.predictedSavings = predictedSaving;
    }


}
