package com.trollema.controllers;

import java.util.ArrayList;

/**
 * Created by maxmi_000 on 09-Apr-16.
 */
public interface RequestHandler {

    /**
     * Requests API for entire User List
     * @author Themiya D Chandraratna
     * Provides a ArrayList String of Users
     * @return ArrayList containing User list
     */
    ArrayList<String> getUsers();

    /**
     * Requests API for User Profile
     * @author Themiya D Chandraratna
     * Provides a String containing User Data
     * @param username : the email of the desired User Profile
     * @return String containing User Profile
     */
    String getProfile(String username);

    /**
     * Requests API for User Profile
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User Profile
     * @return boolean true if login successful// false if login failed
     */
    boolean login(String username);

    /**
     * Requests API for User Logout
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User Profile
     * @return boolean true if logout successful// false if logout failed
     */
    boolean logout(String username);

    /**
     * Requests API to Create User
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User
     * @param password : the password of the desired User
     * @return boolean true if creation successful// false if creation failed
     */
    boolean createUser(String username, String password);

    /**
     * Requests API to Create User Profile
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User
     * @return boolean true if creation successful// false if creation failed
     */
    boolean createProfile(String username);

    /**
     * Requests API to Update User Profile
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param email : the email of the desired User
     * @param name : the name of the desired User
     * @param major : the major of the desired User
     * @param year : the year of the desired User
     * @param bio : the description of the desired User
     * @param img : the image reference of the desired User
     * @return boolean true if update successful// false if update failed
     */
    boolean updateProfile(String email, String name, String major, String year, String bio, String img);

    /**
     * Requests API for User Ban
     * @author Themiya D Chandraratna
     * @param username : the email of the desired User Profile
     */
    void banUser(String username);

    /**
     * Requests API for User undo Ban
     * @author Themiya D Chandraratna
     * @param username : the email of the desired User Profile
     */
    void undoBan(String username);

    /**
     * Requests API to determine Admin Account
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User Profile
     * @return boolean true if Admin// false otherwise
     */
    boolean isAdmin(String username);

    /**
     * Searches omdb for movie title
     * @author Themiya D Chandraratna
     * @param givenTitle given title
     * @return string output
     */
    String searchMovies(String givenTitle);
}
