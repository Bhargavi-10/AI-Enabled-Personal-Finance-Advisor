package com.FinanceAdvisor.PersonalFinanceAdvisor.Controller;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.IncomeRequestDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("income")
@CrossOrigin(origins = "*")
public class IncomeController {

      @Autowired
      private IncomeService incomeServ;

      @PostMapping("/add/{userId}")
      public String addIncome(@PathVariable Integer userId, @RequestBody IncomeRequestDTO income){
           return incomeServ.addIncome(income,userId);
      }

      @GetMapping("/viewIncome/{userId}")
      public List<IncomeRequestDTO> viewIncome(@PathVariable Integer userId){
          return incomeServ.viewIncome(userId);
      }

      @PutMapping("/update/{incomeId}")
      public String updateIncome(@PathVariable Integer incomeId , @RequestBody IncomeRequestDTO income){
          return incomeServ.updateIncome(income,incomeId);
      }

      @DeleteMapping("/delete/{incomeId}")
      public String deleteIncome(@PathVariable Integer incomeId){
          return incomeServ.deleteIncome(incomeId);
      }

}
