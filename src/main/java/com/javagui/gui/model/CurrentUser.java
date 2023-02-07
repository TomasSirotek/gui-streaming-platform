package com.javagui.gui.model;


import com.javagui.be.User;
import com.javagui.bll.ILogicManager;
import com.javagui.bll.LogicManager;

import java.util.Optional;

public class CurrentUser {
    private static CurrentUser instance;
    // Private variables to hold the user's name and password
    private String userName;

    private String password;
    private final static String PASSWORD = "1234";

    private boolean isLoggedIn;

    private final ILogicManager logicManager;

    // Private constructor to prevent direct instantiation
    private CurrentUser(){
        this.logicManager = new LogicManager();
    }
    // Public static method to get the singleton instance
    public static CurrentUser getInstance() {
        if (instance == null) {
            synchronized (CurrentUser.class) {
                if (instance == null) {
                    instance = new CurrentUser();
                }
            }
        }
        return instance;
    }

    // Public getter and setter methods for the name and password
    public String getUserName() {
        return userName;
    }

    public void login(String userName,String password){
        this.userName = userName;
        this.password = password;
        this.isLoggedIn = true;
    }

    public void logout(){
        this.userName = null;
        this.password = null;
        this.isLoggedIn = false;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    // Method to check whether the user is authorized
    public boolean isAuthorized() {
        User user = logicManager.getUser(this.userName);
        return Optional.ofNullable(user)
                .filter(u -> u.getName().equals(this.userName))
                .filter(u -> this.password.equals(PASSWORD))
                .isPresent();
    }
}
