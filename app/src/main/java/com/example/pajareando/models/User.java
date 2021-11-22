package com.example.pajareando.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    public static final String TABLE_NAME = "users";

    String username, email, password;
    Context context;

    public User(
            String username,
            String email,
            String password,
            Context context
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.context = context;
    }

    public void save() {
        Map<String, String> userInfo = new HashMap<String, String>();

        userInfo.put("username", this.username);
        userInfo.put("email", this.email);
        userInfo.put("password", this.password);

        ModelDb.save(userInfo, User.TABLE_NAME, this.context);
    }

    public static ArrayList<User> getAll(Context context) {
        ArrayList<Map<String, String>> usersInfo = ModelDb.getAll(User.TABLE_NAME, context);

        ArrayList<User> users = new ArrayList<User>();
        try {

        for (Map userInfo : usersInfo) {
            users.add(new User(
                userInfo.get("username") != null ? userInfo.get("username").toString() : "",
                userInfo.get("email") != null ? userInfo.get("email").toString() : "",
                userInfo.get("password") != null ? userInfo.get("password").toString() : "",
                context
            ));
        }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
