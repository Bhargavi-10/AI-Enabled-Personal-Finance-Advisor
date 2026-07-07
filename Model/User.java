package com.FinanceAdvisor.PersonalFinanceAdvisor.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Integer userId;
       @Column(nullable = false)
       private String userName;

       @Column(nullable = false , unique = true)
       private String email;

       @Column(nullable = false)
       private String password;

       public
       User(){

       }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
