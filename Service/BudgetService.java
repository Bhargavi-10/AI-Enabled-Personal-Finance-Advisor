package com.FinanceAdvisor.PersonalFinanceAdvisor.Service;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.Budget;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.BudgetRequestDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.User;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.BudgetRepository;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepo;
    @Autowired
    private UserRepository userRepo;

    //ADD BUDGET
    public String addBudget(BudgetRequestDTO budget){
        User user = userRepo.findById(budget.getUserId()).orElseThrow(()->new RuntimeException("User not Found"));
        Budget bgt = new Budget();

        bgt.setMonthlyAmount(budget.getMonthlyAmount());
        bgt.setCategory(budget.getCategory());
        bgt.setYear(budget.getYear());
        bgt.setMonth(budget.getMonth());
        bgt.setUser(user);
        budgetRepo.save(bgt);
        return  "Budget added successfully";
    }

    //RETRIEVE BUDGET TABLE
    public List<BudgetRequestDTO> retrieveBudgets(Integer userId){
        List<Budget> budgets =  budgetRepo.findByUserUserId(userId);

        return budgets.stream().map(budget -> {
            BudgetRequestDTO dto = new BudgetRequestDTO();
            dto.setCategory(budget.getCategory());
            dto.setMonthlyAmount(budget.getMonthlyAmount());
            dto.setUserId(budget.getUser().getUserId());
            dto.setMonth(budget.getMonth());
            dto.setYear(budget.getYear());
            dto.setBudgetId(budget.getBudgetId());
            return dto;
        }).toList();
    }

    //UPDATE BUDGET
    public String updateBudget(Integer budgetId , BudgetRequestDTO budget){
         Budget bgt  = budgetRepo.findById(budgetId).orElseThrow(()->new RuntimeException("Budget not found"));
         bgt.setCategory(budget.getCategory());
         bgt.setMonthlyAmount(budget.getMonthlyAmount());
         bgt.setMonth(budget.getMonth());
         bgt.setYear(budget.getYear());

         budgetRepo.save(bgt);
         return "Budget updated successfully";
    }

    //DELETE BUDGET
    public String deleteBudget(Integer budgetId){
        Budget bgt = budgetRepo.findById(budgetId).orElseThrow(()->new RuntimeException("Budget not found"));

        budgetRepo.deleteById(budgetId);
        return "Budget deleted successfully";
    }


}
