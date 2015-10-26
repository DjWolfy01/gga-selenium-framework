package com.epam.career.page_objects.entities;

/**
 * Created by Roman_Iovlev on 5/21/2015.
 */
public class User {
    public static User DefaultUser = new User("UserTest", "Test Password");
    public static User CurrentUser;

    public String name;
    public String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }


}

