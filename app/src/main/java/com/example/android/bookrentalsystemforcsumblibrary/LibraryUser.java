package com.example.android.bookrentalsystemforcsumblibrary;

/**
 * Created by atomi on 12/8/2016.
 */

public class LibraryUser {
    private String userName;
    private String userPassword;
    private boolean isAdmin;

    public LibraryUser(String userName, String userPassword, int isAdmin){
        this.userName = userName;
        this.userPassword = userPassword;
        this.isAdmin = isAdmin == 1;
    }

    public String getUserName(){return userName;}
    public String getUserPassword(){return userPassword;}
    public int getIsAdmin(){return isAdmin ? 1:0;}
}
