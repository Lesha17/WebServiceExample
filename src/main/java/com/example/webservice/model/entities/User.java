package com.example.webservice.model.entities;

public class User {

    private String login;
    private String password;
    private double balance;

    public User(String login, String password) {
        this(login, password, 0);
    }

    public User(String login, String password, double balance) {
        this.login = login;
        this.password = password;
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }
}
