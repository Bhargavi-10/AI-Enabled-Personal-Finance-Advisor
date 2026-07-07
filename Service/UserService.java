package com.FinanceAdvisor.PersonalFinanceAdvisor.Service;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.ResponseDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.User;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.UserRequestDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;


    //USER REGISTRATION
    public ResponseDTO registerUser(UserRequestDTO user){
        Optional<User>  existingUser = userRepo.findByEmail(user.getEmail());

        if(existingUser.isPresent()){
            return new ResponseDTO(
                    "user already exists",
                    null
            );
        }


        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User savedUser = userRepo.save(newUser);

        return new ResponseDTO(
                "Registration Successful",
                savedUser.getUserId()
        );
    }


    //USER LOGIN
    public ResponseDTO loginUser(UserRequestDTO user){
         Optional<User> existingUser = userRepo.findByEmail(user.getEmail());

         if(existingUser.isEmpty()){
             return new ResponseDTO("User does not exist" , null);
         }
         if(!existingUser.get().getPassword().equals(user.getPassword())){
             return new ResponseDTO("Invalid password"  ,null);
         }
         return new ResponseDTO("Login Successful" , existingUser.get().getUserId());
    }



}
