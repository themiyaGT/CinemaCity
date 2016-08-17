package com.trollema.appdata.models;

/**
 * User : stores app data
 * Loads current user data
 * Created by themiya on 2/15/2016.
 */
public class User {
    //User Data
    public String email;
    public String username;
    public String major;
    public String year;
    public String bio;

    /*
    Creates Default User
    Constructor
     */
    public User() {
        email = "default";
        username = "default";
        major = "default";
        year = "0";
        bio = "null";
    }

    public User(String email, String username, String major, String year, String bio) {
        this.email = email;
        this.username = username;
        this.major = major;
        this.year = year;
        this.bio = bio;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;

        return this.email.equals(otherUser.email)
            && this.username.equals(username)
            && this.major.equals(otherUser.major)
            && this.year.equals(otherUser.year)
            && this.bio.equals(otherUser.bio);
    }
}
