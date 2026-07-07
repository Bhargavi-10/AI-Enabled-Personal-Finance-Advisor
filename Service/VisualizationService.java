package com.FinanceAdvisor.PersonalFinanceAdvisor.Service;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.CategoryTrendDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.DailySpendingDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.IncomeExpenseDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.MonthlyTrendDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.BudgetRepository;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.IncomeRepository;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.TransactionRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.sql.results.graph.entity.internal.AbstractNonJoinedEntityFetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class VisualizationService {

    @Autowired
    TransactionRepository transRepo;

    @Autowired
    BudgetRepository budgetRepo;

    @Autowired
    IncomeRepository incomeRepo;



    //MONTHLY SPENDING TREND
    public List<MonthlyTrendDTO> getMonthlySpendingTrend(Integer userId){
        List<Object[]> results = transRepo.getMonthlySpending(userId);

        List<MonthlyTrendDTO> response = new ArrayList<>();

        for(Object[] row : results){
            MonthlyTrendDTO dto = new MonthlyTrendDTO();

            dto.setMonth(((Number)row[0]).intValue());
            dto.setAmount(((Number)row[1]).doubleValue());

            response.add(dto);
        }
        return response;
    }


   //DAILY SPENDING TREND
    public List<DailySpendingDTO> getDailySpendingTrend(Integer userId){
        List<Object[]> results = transRepo.getDailySpending(userId);

        List<DailySpendingDTO> response = new ArrayList<>();

        for(Object[] row : results){
            DailySpendingDTO dto = new DailySpendingDTO();

            dto.setDate((LocalDate) row[0]);
            dto.setAmount(((Number)row[1]).doubleValue());

            response.add(dto);
        }

        return response;
    }

    //CATEGORY SPENDING TREND
    public List<CategoryTrendDTO> getCategorySpendingTrend(Integer userId){
        List<Object[]> results  =transRepo.getCategorySpendingTrend(userId);

        List<CategoryTrendDTO> response = new ArrayList<>();

        for(Object[] row  :results){
            CategoryTrendDTO dto = new CategoryTrendDTO();

            dto.setMonth(((Number)row[0]).intValue());
            dto.setCategory((String)row[1]);
            dto.setAmount(((Number)row[2]).doubleValue());

            response.add(dto);
        }
        return response;
    }


    //MONTHLY SAVINGS
    public List<MonthlyTrendDTO> getMonthlySavingsTrend(Integer userId){
        List<Object[]> incomeData = incomeRepo.getMonthlyIncome(userId);
        List<Object[]> expenseData = transRepo.getMonthlySpending(userId);

        Map<Integer,Double>incomeMap = new HashMap<>();
        Map<Integer,Double>expenseMap = new HashMap<>();

        for(Object[] row  : incomeData){
            incomeMap.put( ((Number)row[0]).intValue() , ((Number)row[1]).doubleValue() );
        }


        for(Object[] row : expenseData){
            expenseMap.put(((Number)row[0]).intValue() , ((Number)row[1]).doubleValue());
        }

        List<MonthlyTrendDTO> result = new ArrayList<>();
        Set<Integer>allMonths = new TreeSet<>();

        allMonths.addAll(incomeMap.keySet());
        allMonths.addAll(expenseMap.keySet());

        for(Integer month : allMonths){
            Double income = incomeMap.getOrDefault(month , 0.0);
            Double expense = expenseMap.getOrDefault(month , 0.0);

            MonthlyTrendDTO dto = new MonthlyTrendDTO();

            dto.setMonth(month);
            dto.setAmount(income-expense);

            result.add(dto);
        }
        return result;
    }

    //INCOME EXPENSE TREND

    public List<IncomeExpenseDTO> getIncomeVsExpense(Integer userId){
        List<Object[]> incomeData = incomeRepo.getMonthlyIncome(userId);
        List<Object[]> expenseData  = transRepo.getMonthlySpending(userId);


        Map<Integer,Double>incomeMap = new HashMap<>();
        Map<Integer,Double>expenseMap = new HashMap<>();

        for(Object[] row  : incomeData){
            incomeMap.put( ((Number)row[0]).intValue() , ((Number)row[1]).doubleValue() );
        }


        for(Object[] row : expenseData){
            expenseMap.put(((Number)row[0]).intValue() , ((Number)row[1]).doubleValue());
        }

        List<IncomeExpenseDTO> result = new ArrayList<>();
        Set<Integer>allMonths = new HashSet<>();

        allMonths.addAll(incomeMap.keySet());
        allMonths.addAll(expenseMap.keySet());

        for(Integer month : allMonths){
            Double income = incomeMap.getOrDefault(month , 0.0);
            Double expense = expenseMap.getOrDefault(month , 0.0);

            IncomeExpenseDTO dto = new IncomeExpenseDTO();

            dto.setMonth(month);
            dto.setIncome(income);
            dto.setExpense(expense);

            result.add(dto);
        }
        return result;
    }




}
