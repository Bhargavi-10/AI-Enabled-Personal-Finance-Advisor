package com.FinanceAdvisor.PersonalFinanceAdvisor.Service;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.ExpensePredictionRequestDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.PredictionResponseDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.SavingsPredictionRequestDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.BudgetRepository;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLPredictionService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DashboardService dashServ;
    @Autowired
    private IncomeRepository incomeRepo;
    @Autowired
    private BudgetRepository budgetRepo;

    private final String FLASK_URL = "http://127.0.0.1:5000";

    public Double predictExpense(Integer userId){
        ExpensePredictionRequestDTO dto = createExpensePredictionRequest(userId);

        System.out.println("ML expense request: " + dto.getIncome() + ", " +
                dto.getSavings() + ", " + dto.getMonth());

        PredictionResponseDTO response = restTemplate.postForObject(FLASK_URL + "/predictExpense", dto , PredictionResponseDTO.class);
        Double val = response.getPredictedExpense();
        return val == null ? 0.0 : val;
    }

    public Double predictSavings(Integer userId){
        SavingsPredictionRequestDTO dto = createSavingsPredictionRequest(userId);

        System.out.println("ML expense request: " + dto.getIncome() + ", " +
                dto.getExpense() + ", " + dto.getMonth());

        PredictionResponseDTO response = restTemplate.postForObject(FLASK_URL+"/predictSavings",dto,PredictionResponseDTO.class);
        Double val  = response.getPredictedSavings();
        return val == null ? 0.0 : val;
    }

    public ExpensePredictionRequestDTO createExpensePredictionRequest(Integer userId){
        ExpensePredictionRequestDTO dto = new ExpensePredictionRequestDTO();

        Double income = incomeRepo.getTotalIncome(userId);
        Double expense  = dashServ.totalExpense(userId);
        Double savings = dashServ.totalSavings(userId);
        dto.setIncome(income);
        dto.setSavings(savings);

        dto.setMonth(java.time.LocalDate.now().getMonthValue());

        return dto;
    }

    public SavingsPredictionRequestDTO createSavingsPredictionRequest(Integer userId){

        SavingsPredictionRequestDTO dto = new SavingsPredictionRequestDTO();
        Double income = incomeRepo.getTotalIncome(userId);
        Double expense  = dashServ.totalExpense(userId);
        Double savings = dashServ.totalSavings(userId);
        dto.setIncome(income);
        dto.setExpense(expense);

        dto.setMonth(java.time.LocalDate.now().getMonthValue());

        return dto;
    }

}
