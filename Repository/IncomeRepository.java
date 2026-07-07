package com.FinanceAdvisor.PersonalFinanceAdvisor.Repository;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.Income;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income , Integer> {

    List<Income> findByUserUserId(Integer userId);

    @Query("""
            SELECT COALESCE(SUM(i.amount),0)
            FROM Income i
            WHERE i.user.userId=:userId
            """)
    Double getTotalIncome(Integer userId);

    @Query("""
       SELECT MONTH(i.date) , SUM(i.amount)
       FROM Income i
       WHERE i.user.userId=:userId
       GROUP BY MONTH(i.date)
       ORDER BY MONTH(i.date)
     """)
    List<Object[]> getMonthlyIncome(Integer userId);
}
