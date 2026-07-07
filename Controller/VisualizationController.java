package com.FinanceAdvisor.PersonalFinanceAdvisor.Controller;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.*;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Service.DashboardService;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Service.VisualizationService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visualizations")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class VisualizationController {

    @Autowired
    private VisualizationService visuServ;

    @Autowired
    private DashboardService dashServ;

    @GetMapping("/monthlySpending/{userId}")
    public List<MonthlyTrendDTO> monthlySpending(@PathVariable Integer userId){
        return visuServ.getMonthlySpendingTrend(userId);
    }

    @GetMapping("/dailySpending/{userId}")
    public List<DailySpendingDTO> dailySpending(@PathVariable Integer userId){
        return visuServ.getDailySpendingTrend(userId);
    }

    @GetMapping("/categorySpending/{userId}")
    public List<CategoryTrendDTO> categorySpending(@PathVariable Integer userId){
        return visuServ.getCategorySpendingTrend(userId);
    }

    @GetMapping("/monthlySavings/{userId}")
    public List<MonthlyTrendDTO> monthlySavings(@PathVariable Integer userId){
        return visuServ.getMonthlySavingsTrend(userId);
    }

    @GetMapping("/IncomeVsExpense/{userId}")
    public List<IncomeExpenseDTO> incomeVsExpense(@PathVariable Integer userId){
        return visuServ.getIncomeVsExpense(userId);
    }

    @GetMapping("/budgetVsActualSpending/{userId}")
    public List<BudgetComparisonDTO> budgetVsActualSpending(@PathVariable Integer userId){
        return dashServ.budgetVsActualSpending(userId);
    }
}
