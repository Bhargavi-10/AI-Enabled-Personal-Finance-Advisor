package com.FinanceAdvisor.PersonalFinanceAdvisor.Repository;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,Integer> {
      List<Transactions>  findByUserUserId(Integer userId);

      List<Transactions> findByUserUserIdOrderByTransactionDateDesc(Integer userId);

      //MONTHLY SPENDING TREND
      @Query("""
       SELECT 
       EXTRACT (MONTH FROM t.transactionDate),
       SUM(t.amount)
       FROM Transactions t
       WHERE t.user.userId = :userId
       GROUP BY EXTRACT(MONTH FROM t.transactionDate)
       ORDER BY EXTRACT(MONTH FROM t.transactionDate)
""")
       List<Object[]> getMonthlySpending(Integer userId);


      //DAILY SPENDING TREND
      @Query("""
      SELECT
      t.transactionDate,
      SUM(t.amount)
      FROM Transactions t
      WHERE t.user.userId = :userId
      GROUP BY t.transactionDate
      ORDER BY t.transactionDate
""")
      List<Object[]> getDailySpending(Integer userId);


      //CATEGORY SPENDING TREND
      @Query("""
      SELECT 
      EXTRACT(MONTH FROM t.transactionDate),
      t.category,
      SUM(t.amount)
      FROM Transactions t
      WHERE t.user.userId = :userId
      GROUP BY EXTRACT(MONTH FROM t.transactionDate),
      t.category
      ORDER BY EXTRACT(MONTH FROM t.transactionDate)
""")
      List<Object[]> getCategorySpendingTrend(Integer userId);
}
