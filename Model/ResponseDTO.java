package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

public class ResponseDTO {
    private String message;
    private Integer userId;

    public ResponseDTO(String message, Integer userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
