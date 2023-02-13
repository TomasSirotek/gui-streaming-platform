package com.javagui.gui.model;


import com.javagui.be.User;
import com.javagui.bll.ILogicManager;
import com.javagui.bll.LogicManager;

import java.util.Optional;

public class CurrentUser {
    private final static String PASSWORD = "1234";
    private static CurrentUser instance;
    private final ILogicManager logicManager;
    private User currentUser = null;
    private String userName;
    private String password;

    // Private constructor to prevent direct instantiation
    private CurrentUser() {
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
    public User getLoggedUser() {
        return this.currentUser;
    }

    public void login(String userName, String password) {
        this.currentUser = new User(0, userName);
        this.userName = userName;
        this.password = password;
    }

    public void logout() {
        this.currentUser = null;
        this.password = null;
    }

    // Method to check whether the user is authorized
    public boolean isAuthorized() {
        User user = logicManager.getUser(userName);

        if (Optional.ofNullable(user)
                .filter(u -> password.equals(PASSWORD))
                .isPresent()) {
            this.currentUser = user;
            return true;
        } else {
            this.currentUser = null;
            return false;
        }
    }
}
