package com.FinanceAdvisor.PersonalFinanceAdvisor.Repository;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.Budget;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.BudgetRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Integer> {
    List<Budget>  findByUserUserId(Integer userId);

    List<Budget> findByUserUserIdAndMonthAndYear(Integer userId , Integer month , Integer year);

    //MONTHLY SAVINGS
    @Query("""
    SELECT b.month,
    SUM(b.monthlyAmount)
    FROM Budget b
    WHERE b.user.userId = :userId
    GROUP BY b.month
    ORDER BY b.month
""")
    List<Object[]> getMonthlyBudgets(Integer userId);
}
