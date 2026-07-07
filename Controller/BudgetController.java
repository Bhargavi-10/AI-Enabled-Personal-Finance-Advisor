package com.FinanceAdvisor.PersonalFinanceAdvisor.Controller;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.BudgetRequestDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budget")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetService budgetServ;

    @PostMapping("/add")
    public String addBudget(@RequestBody BudgetRequestDTO budget){
        return budgetServ.addBudget(budget);
    }

    @GetMapping("/viewBudget/{userId}")
    public List<BudgetRequestDTO>  viewBudget(@PathVariable Integer userId){
        return budgetServ.retrieveBudgets(userId);
    }

    @PutMapping("/update/{budgetId}")
    public String updateBudget(@PathVariable Integer budgetId , @RequestBody BudgetRequestDTO budget){
        return budgetServ.updateBudget(budgetId,budget);
    }

    @DeleteMapping("/delete/{budgetId}")
    public String deleteBudget(@PathVariable Integer budgetId){
        return budgetServ.deleteBudget(budgetId);
    }
}
