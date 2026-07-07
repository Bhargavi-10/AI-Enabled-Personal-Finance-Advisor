package com.FinanceAdvisor.PersonalFinanceAdvisor.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiService {


    private final Client client;


    public String askGemini(String prompt){
          GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash",prompt,null);

          String text = response.text();

          if(text==null){
              return " ";
          }
          text = text.replace("**","");
          return text.replace("\n", "<br/>");
    }
}
