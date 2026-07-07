package com.FinanceAdvisor.PersonalFinanceAdvisor.Controller;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.TransactionRequestDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.Transactions;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transServ;

    @PostMapping("/add")
    public String addTransaction(@RequestBody TransactionRequestDTO trans){
        return transServ.addTransaction(trans);
    }

    @GetMapping("/viewTransactions/{userId}")
    public List<TransactionRequestDTO> getUserTransactions(@PathVariable Integer userId){
        return transServ.getUserTransactions(userId);
    }

    @DeleteMapping("/delete/{transactionId}")
    public String deleteTransaction(@PathVariable Integer transactionId){
        return transServ.deleteTransaction(transactionId);
    }

    @PutMapping("/update/{transactionId}")
    public String updateTransaction(@PathVariable Integer transactionId , @RequestBody TransactionRequestDTO trans){
        return transServ.updateTransaction(transactionId,trans);
    }
}
