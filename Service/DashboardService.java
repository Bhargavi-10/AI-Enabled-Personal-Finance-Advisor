package com.FinanceAdvisor.PersonalFinanceAdvisor.Service;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.*;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.BudgetRepository;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.IncomeRepository;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.nio.DoubleBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    private TransactionRepository transRepo;

    @Autowired
    private BudgetRepository budgetRepo;

    @Autowired
    private IncomeRepository incomeRepo;


    //TOTAL EXPENSE
    public Double totalExpense(Integer userId){
        Double totAmt = 0.0;

        List<Transactions> trans = transRepo.findByUserUserId(userId);

        for(Transactions transaction : trans){
            totAmt += transaction.getAmount();
        }

        return totAmt;
    }


    //CATEGORY WISE TOTAL EXPENSE
    public Map<String,Double> categoryWiseExpense(Integer userId){

        List<Transactions> transactions = transRepo.findByUserUserId(userId);
        Map<String,Double> expenses = new HashMap<>();

        for(Transactions trans : transactions){
            String category = trans.getCategory();
            double amt = trans.getAmount();

            expenses.put(category , expenses.getOrDefault(category , 0.0)+amt);
        }

        return expenses;
    }


    //CURRENT MONTH EXPENSES
    public Double currentMonthExpense(Integer userId){

        List<Transactions> transactions = transRepo.findByUserUserId(userId);

        Double total = 0.0;

        LocalDate currDate = LocalDate.now();

        for(Transactions trans : transactions){
            LocalDate transDate = trans.getTransactionDate();

            if((transDate.getMonth() == currDate.getMonth()) && (transDate.getYear() == currDate.getYear())){
                total += trans.getAmount();
            }
        }
        System.out.println("current month expense : "+total);
        return total;

    }


    //CURRENT MONTH CATEGORY WISE EXPENSE
    public Map<String,Double> currentMonthCategoryWiseExpense(Integer userId){

        List<Transactions>transactions = transRepo.findByUserUserId(userId);
        Map<String,Double> currMonthExp = new HashMap<>();

        LocalDate currDate = LocalDate.now();

        for(Transactions trans : transactions){
            String category = trans.getCategory();
            Double amount = trans.getAmount();

            LocalDate transDate = trans.getTransactionDate();

            if((transDate.getMonth() == currDate.getMonth()) && (transDate.getYear() == currDate.getYear())){
               currMonthExp.put(category, currMonthExp.getOrDefault(category , 0.0)+amount);
            }
        }

        return currMonthExp;
    }


    //BUDGET VS ACTUAL SPENDING
    public List<BudgetComparisonDTO> budgetVsActualSpending(Integer userId){
        List<Budget> budgets = budgetRepo.findByUserUserId(userId);
        List<Transactions>transactions = transRepo.findByUserUserId(userId);

        List<BudgetComparisonDTO> result = new ArrayList<>();

        for(Budget budget : budgets){
            double actualExpense = 0;

            for(Transactions trans : transactions){
                if(trans.getCategory().equalsIgnoreCase(budget.getCategory())){
                    actualExpense += trans.getAmount();

                }
            }



            BudgetComparisonDTO dto = new BudgetComparisonDTO();

            if(actualExpense > budget.getMonthlyAmount()){
                dto.setStatus("OVER BUDGET");
            }
            else{
                dto.setStatus("WITHIN BUDGET");
            }

            dto.setCategory(budget.getCategory());
            dto.setActual(actualExpense);
            dto.setBudget(budget.getMonthlyAmount());

            result.add(dto);
        }
        return result;
    }


    //RECENT TRANSACTIONS
    public List<TransactionRequestDTO> getRecentTransactions(Integer userId){
        List<Transactions>transactions= transRepo.findByUserUserIdOrderByTransactionDateDesc(userId);

        return transactions.stream().limit(5)
                .map(transaction -> {
                    TransactionRequestDTO dto = new TransactionRequestDTO();

                    dto.setDescription(transaction.getDescription());
                    dto.setTransactionDate(transaction.getTransactionDate());
                    dto.setAmount(transaction.getAmount());
                    dto.setCategory(transaction.getCategory());

                    return dto;
                }).toList();
    }


    //TOP EXPENSE CATEGORY
    public TopExpenseDTO getTopExpenseCategory(Integer userId){
         Map<String,Double>categoryExpenses = currentMonthCategoryWiseExpense(userId);

         if(categoryExpenses.isEmpty()){
             TopExpenseDTO dto = new TopExpenseDTO();
             dto.setCategory("No data");
             dto.setAmount(0.0);
             return dto;
         }

         String topCategory = "";
         Double maxExpense = 0.0;

         for(Map.Entry<String,Double>entry : categoryExpenses.entrySet()){
             if(entry.getValue() > maxExpense){
                 maxExpense = entry.getValue();
                 topCategory = entry.getKey();
             }
         }

         TopExpenseDTO dto = new TopExpenseDTO();
         dto.setCategory(topCategory);
         dto.setAmount(maxExpense);

         return dto;
    }


    //BUDGETS ALERT
    public Integer getBudgetAlertCount(Integer userId){
        List<BudgetComparisonDTO> data = budgetVsActualSpending(userId);

        Integer count = 0;

        for(BudgetComparisonDTO dto : data){
            if(dto.getActual() > dto.getBudget()){
                count++;
            }
        }
        return count;
    }



    //BUDGET UTILIZATION
    public List<BudgetUtilizationDTO> getBudgetUtilization(Integer userId){
        LocalDate today = LocalDate.now();

        List<Budget> budgets = budgetRepo.findByUserUserIdAndMonthAndYear(userId , today.getMonthValue() , today.getYear());

        Map<String,Double> expenses = currentMonthCategoryWiseExpense(userId);

        List<BudgetUtilizationDTO> result = new ArrayList<>();


        for(Budget budget : budgets){

            Double actual = expenses.getOrDefault(budget.getCategory(), 0.0);
            Double utilization = (actual / budget.getMonthlyAmount())*100;

            BudgetUtilizationDTO dto = new BudgetUtilizationDTO();

            if(utilization>100){
                utilization = 100.00;
            }

            dto.setCategory(budget.getCategory());
            dto.setUtilization(utilization);

            result.add(dto);
        }

        return result;
    }


    //TOTAL SAVINGS
    public Double totalSavings(Integer userId){
        Double totalIncome = incomeRepo.getTotalIncome(userId);

        Double totalExpense  = totalExpense(userId);

        if(totalIncome==null){
            totalIncome = 0.0;
        }
        if(totalExpense==null){
            totalExpense = 0.0;
        }

        return totalIncome - totalExpense;
    }

    //CURRENT MONTH INCOME
    public Double currentMonthIncome(Integer userId){
        List<Income> incomes = incomeRepo.findByUserUserId(userId);
        Double total = 0.0;

        LocalDate currDate = LocalDate.now();

        for(Income inc : incomes){
            LocalDate incomeDate =  inc.getDate();

            if((incomeDate.getMonth()==currDate.getMonth()) && (incomeDate.getYear() == currDate.getYear())) {
                total += inc.getAmount();
            }
        }
        System.out.println("current month income : "+total);

        return total;
    }


    //FINANCIAL SCORE
    public Integer getFinancialScore(Integer userId){
        Double income = incomeRepo.getTotalIncome(userId);
        Double expense = totalExpense(userId);
        if(income==0){
            return 0;
        }
        Double savingsRate = ((income-expense)/income)*100;

        int score = 50;

        if(savingsRate > 40){
            score+= 40;
        }

        else if(savingsRate > 20){
            score+= 25;
        }
        else{
            score+=10;
        }

        score -= getBudgetAlertCount(userId) * 5;

        return Math.max(score,0);
    }


    //CURRENT MONTH SAVINGS
    public CurrentMonthSavingsDetail currentMonthSavings(Integer userId){
        Double monthlyIncome = currentMonthIncome(userId);
        Double monthlyExpense  = currentMonthExpense(userId);


        System.out.println("expense : " + monthlyExpense);
        System.out.println("income : " +monthlyIncome);

        if(monthlyIncome == null){
            monthlyIncome = 0.0;
        }if(monthlyExpense == null){
            monthlyExpense = 0.0;
        }

        Double net  = monthlyIncome - monthlyExpense;
        Double savings = net <  0 ? 0.0 : net;

        CurrentMonthSavingsReason reason;
        if(monthlyIncome ==0.0){
            reason = CurrentMonthSavingsReason.NO_INCOME_ENTERED;
        }else if(net < 0){
            reason = CurrentMonthSavingsReason.EXPENSE_GREATER_THAN_INCOME;
        }else{
            reason = CurrentMonthSavingsReason.NORMAL;
        }

        CurrentMonthSavingsDetail detail = new CurrentMonthSavingsDetail();
        detail.setSavings(savings);
        detail.setReason(reason);

        return detail;
    }
}
