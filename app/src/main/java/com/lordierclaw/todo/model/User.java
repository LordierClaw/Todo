package com.lordierclaw.todo.model;

import com.google.gson.Gson;

public class User {
    private static User instance;

    public static User getInstance() {
        if (instance == null) instance = new User();
        return instance;
    }

    public static void setInstance(User user) {
        if (instance != null) instance = null;
        instance = user;
    }

    public static void setNewInstance() {
        if (instance != null) instance = null;
        instance = new User();
    }

    private User() {
        isLocal = true;
        name = "User";
        email = "";
    }

    private boolean isLocal;
    private String name, email;

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
