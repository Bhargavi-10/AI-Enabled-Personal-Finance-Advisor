package com.FinanceAdvisor.PersonalFinanceAdvisor.Controller;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.ResponseDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.User;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.UserRequestDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userServ;

    @PostMapping("/register")
    public ResponseDTO registerUser(@RequestBody UserRequestDTO user){
        return userServ.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseDTO loginUser(@RequestBody UserRequestDTO user){
        return userServ.loginUser(user);
    }


}
