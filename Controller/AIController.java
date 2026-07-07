package com.FinanceAdvisor.PersonalFinanceAdvisor.Controller;

import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.PredictionDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Model.chatRequestDTO;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Service.AIService;
import com.FinanceAdvisor.PersonalFinanceAdvisor.Service.MLPredictionService;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "*")

public class AIController {
    @Autowired
    private AIService aiServ;

    @Autowired
    private MLPredictionService mlServ;

    @PostMapping("/chat/{userId}")
    public String chat(@PathVariable Integer userId , @RequestBody chatRequestDTO request){
        return aiServ.chat(userId ,request.getMessage());
    }

    @GetMapping("/predictions/{userId}")
    public PredictionDTO predictions(@PathVariable Integer userId){
        return aiServ.getPredictions(userId);
    }



}
