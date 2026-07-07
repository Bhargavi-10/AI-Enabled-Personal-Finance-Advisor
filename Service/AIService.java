package com.FinanceAdvisor.PersonalFinanceAdvisor.Service;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.PredictionDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.TopExpenseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    @Autowired
    private DashboardService dashboardServ;

    @Autowired
    private GeminiService geminiServ;

    @Autowired
    private MLPredictionService mlServ;

    //GEMINI CHATBOT
    public String chat(Integer userId , String question){
         Double totalExpense = dashboardServ.totalExpense(userId);
         TopExpenseDTO topCategory = dashboardServ.getTopExpenseCategory(userId);
        String prompt = """
                You are a Personal Finance Advisor.
                
                            Your role is ONLY to answer questions related to:
                            - Personal Finance
                            - Budgeting
                            - Expenses
                            - Savings
                            - Income
                            - Financial Planning
                            - Spending Habits
                            - Investments (general guidance only)
                            - Financial insights based on the user's data
                
                            If the user's question is NOT related to finance or personal money management,
                            DO NOT answer the question, and give a message that "ask about finance, budgets.."
                
                Return the answer in this exact format:
                
                Title: <one short title>
                
                Summary:
                - 1 short bullet
                
                Insights:
                - 2 to 3 bullets
                
                Action Plan:
                1. Step one
                2. Step two
                3. Step three
                
                Rules:
                - Use simple language.
                - Keep it under 120 words.
                - Do not write a paragraph.
                - Do not use markdown bold.
                - Use bullet points and numbered points only.
                
                User Financial Data:
                - Total Expense: Rs.%s
                - Top Expense Category: %s (Rs.%s)
                
                User Question:
                %s
                """.formatted(
                                totalExpense,
                                topCategory.getCategory(),
                                topCategory.getAmount(),
                                question
                        );

         return geminiServ.askGemini(prompt);

    }

    //PREDICTIONS

    public PredictionDTO getPredictions(Integer userId){
        PredictionDTO dto = new PredictionDTO();
        dto.setPredictedSpending(mlServ.predictExpense(userId));
        dto.setPredictedSavings(mlServ.predictSavings(userId));

        return dto;
    }
}
