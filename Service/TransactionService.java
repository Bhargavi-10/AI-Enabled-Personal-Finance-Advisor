package com.FinanceAdvisor.PersonalFinanceAdvisor.Service;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.*;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.TransactionRepository;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transRepo;

    @Autowired
    private UserRepository userRepo;

    //ADD TRANSACTION
    public String addTransaction(TransactionRequestDTO trans){

        User user = userRepo.findById(trans.getUserId()).orElseThrow(()->new RuntimeException("User not Found"));
        Transactions transaction = new Transactions();

        transaction.setTransactionDate(trans.getTransactionDate());
        transaction.setAmount(trans.getAmount());
        transaction.setDescription(trans.getDescription());
        transaction.setCategory(trans.getCategory());
        transaction.setUser(user);

        transRepo.save(transaction);
        return "Transaction Added Successfully";
    }


    //VIEW USER TRANSACTIONS
    public List<TransactionRequestDTO> getUserTransactions(Integer userId){
        List<Transactions>trans =  transRepo.findByUserUserId(userId);

        return trans.stream().map(transactions -> {
            TransactionRequestDTO dto = new TransactionRequestDTO();
            dto.setCategory(transactions.getCategory());
            dto.setAmount(transactions.getAmount());
            dto.setTransactionDate(transactions.getTransactionDate());
            dto.setUserId(transactions.getUser().getUserId());
            dto.setDescription(transactions.getDescription());
            dto.setTransactionId(transactions.getTransactionId());

            return dto;
        }).toList();
    }


    //DELETE TRANSACTION
    public String deleteTransaction(Integer transactionId){
        if(!transRepo.existsById(transactionId)){
            return "Transaction not found";
        }
        transRepo.deleteById(transactionId);
        return "Transaction deleted successfully";
    }

    //UPDATE TRANSACTION
    public String updateTransaction(Integer transactionId , TransactionRequestDTO trans){
        Transactions transaction = transRepo.findById(transactionId).orElseThrow(()-> new RuntimeException("Transaction not found"));

        transaction.setAmount(trans.getAmount());
        transaction.setCategory(trans.getCategory());
        transaction.setDescription(trans.getDescription());
        transaction.setTransactionDate(trans.getTransactionDate());

        transRepo.save(transaction);

        return "Transaction update successfully";
    }
}
