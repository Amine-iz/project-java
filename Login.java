package com.example.project_java.BD;

public class Login {
    private int id;
    private String username;
    private String password;

    public Login(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}