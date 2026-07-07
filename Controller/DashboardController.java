package com.FinanceAdvisor.PersonalFinanceAdvisor.Controller;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.*;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class DashboardController {

     @Autowired
     private DashboardService dashServ;


     @GetMapping("/totalExpense/{userId}")
     public double totalExpense(@PathVariable Integer userId){
         return dashServ.totalExpense(userId);
     }


     @GetMapping("/currMonthCategoryWiseExpense/{userId}")
     public Map<String,Double> currMonthCategoryWiseExpense(@PathVariable Integer userId){
          return dashServ.currentMonthCategoryWiseExpense(userId);
     }

     @GetMapping("/categoryWiseExpense/{userId}")
     public Map<String,Double> categoryWiseExpense(@PathVariable Integer userId){
          return dashServ.categoryWiseExpense(userId);
     }

     @GetMapping("/currentMonthExpense/{userId}")
     public Double currentMonthExpense(@PathVariable Integer userId){
          return dashServ.currentMonthExpense(userId);
     }

     @GetMapping("/recentTransactions/{userId}")
     public List<TransactionRequestDTO> getRecentTransaction(@PathVariable Integer userId){
          return dashServ.getRecentTransactions(userId);
     }

     @GetMapping("/topExpenseCategory/{userId}")
     public TopExpenseDTO topExpenseCategory(@PathVariable Integer userId){
          return dashServ.getTopExpenseCategory(userId);
     }

     @GetMapping("/budgetAlertCount/{userId}")
     public Integer budgetAlertCount(@PathVariable Integer userId){
          return dashServ.getBudgetAlertCount(userId);
     }

     @GetMapping("/budgetUtilization/{userId}")
     public List<BudgetUtilizationDTO> budgetUtilization(@PathVariable Integer userId){
          return dashServ.getBudgetUtilization(userId);
     }

     @GetMapping("/totalSavings/{userId}")
     public Double totalSavings(@PathVariable Integer userId){
          return dashServ.totalSavings(userId);
     }

     @GetMapping("/financialScore/{userId}")
     public Integer getFinancialScore(@PathVariable Integer userId){
          return dashServ.getFinancialScore(userId);
     }

     @GetMapping("/currentMonthSavings/{userId}")
     public CurrentMonthSavingsDetail currentMonthSavings(@PathVariable  Integer userId){
          return dashServ.currentMonthSavings(userId);
     }
}
