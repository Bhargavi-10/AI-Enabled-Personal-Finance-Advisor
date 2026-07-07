package com.FinanceAdvisor.PersonalFinanceAdvisor.Service;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.*;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.IncomeRepository;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepo;

    @Autowired
    private UserRepository userRepo;

    //ADD INCOME
    public String addIncome(IncomeRequestDTO income , Integer userId){

        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not Found"));

        Income inc = new Income();

        inc.setAmount(income.getAmount());
        inc.setUser(user);
        inc.setDate(income.getDate());
        inc.setSource(income.getSource());


        incomeRepo.save(inc);
        return "Income added Successfully";
    }


    //EDIT INCOME
    public String updateIncome(IncomeRequestDTO income , Integer incomeId){
        Income inc = incomeRepo.findById(incomeId).orElseThrow(()->new RuntimeException("Income not found"));

        inc.setSource(income.getSource());
        inc.setDate(income.getDate());
        inc.setAmount(income.getAmount());


        incomeRepo.save(inc);

        return "Income updated Successfully";
    }


    //DELETE INCOME
    public String deleteIncome(Integer incomeId){
        if(!incomeRepo.existsById(incomeId)){
            return "Transaction not found";
        }
        incomeRepo.deleteById(incomeId);

        return "Income deleted successfully";
    }


    //VIEW INCOME
    public List<IncomeRequestDTO> viewIncome(Integer userId){
        List<Income> income = incomeRepo.findByUserUserId(userId);

        return income.stream().map(inc -> {
            IncomeRequestDTO dto = new IncomeRequestDTO();

            dto.setAmount(inc.getAmount());
            dto.setDate(inc.getDate());
            dto.setSource(inc.getSource());
            dto.setIncomeId(inc.getIncomeId());
            dto.setUserId(inc.getUser().getUserId());


            return dto;
        }).toList();
    }
}
