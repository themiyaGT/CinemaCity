package com.trollema.controllers;

import android.util.Log;

import com.trollema.appdata.models.User;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * UserDataController : Handles the Requested Data to simplify validation of User Data
 * Created by themiya on 2/15/2016.
 */
public class UserDataController {
    private RequestHandler handler;
    private User user;

    /**
     * Constructor requires user, so that controller can sync data in application
     * @author Themiya D Chandraratna
     * @param user : User data
     */
    public UserDataController(User user, RequestHandler handler) {
        this.user = user;
        this.handler = handler;
    }

    /**
     * String formatter used to clean JSON Data
     * @author Themiya D Chandraratna
     */
    private String removeQuotes(String givenString){
        return givenString = givenString.replaceAll("\"", "");
    }

    /**
    * String formatter used to clean JSON Data
     * @author Themiya D Chandraratna
     */
    private String formatDescription(String givenString){
        return givenString = givenString.replaceAll("_", " ");
    }

    /**
     * Login Desired User
     * @author Themiya D Chandraratna
     * @param username : Desired User email
     * @param password : Desired User password
     * @return true if the user was logged in otherwise false
     * @throws Exception : Database Connection Error Occurred
     * @throws InputMismatchException : User is already found to be active
     */
    public boolean loginUser(String username, String password) throws Exception {
        ArrayList<String> resultData = handler.getUsers();
        if (resultData.isEmpty()) {
            throw new Exception("Database Connection Failed!");
        }
        for (String record: resultData) {
            if (record.contains(username)) { //user exists
                record = record.replace("{", "");
                record = record.replace("}", "");
                String[] myData = record.split(",");
                String email = myData[0].replace("\"email\":", "");
                email = removeQuotes(email); //user email
                String usrpassword = myData[1].replace("\"password\":", "");
                usrpassword = removeQuotes(usrpassword); //user password
                String isActive = myData[2].replace("\"isActive\":", "");
                isActive = removeQuotes(isActive); //user activity
                String isBanned = myData[3].replace("\"isBanned\":", "");
                isBanned = removeQuotes(isBanned); //user isBanned
                if (isActive.equals("1")) { //user is already active
                    throw new java.util.InputMismatchException();
                }
                if (password.equals(usrpassword)) { //login valid
                    boolean isLogged = handler.login(email); //activate user
                    if (isLogged) { //user activated
                        String[] myProfile = getProfile(email);
                        user.email = email;
                        user.username = myProfile[1];
                        user.major = myProfile[2];
                        user.year = myProfile[3];
                        user.bio = myProfile[4];
                        user.img_id = myProfile[5];
                        user.activeCode = "1";
                        user.bannedCode = isBanned;
                        return true;
                    }
                    else { //login failed due to connection issue
                        //Log.d("Error", "Failed to login");
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Obtain User Profile
     * @author Themiya D Chandraratna
     * @param username : Desired User email
     * @return String[] in form [email,name,major,year,bio,img_id]
     */
    public String[] getProfile(String username) {
        String[]myProfile = new String[6];
        String resultObj = handler.getProfile(username);
        resultObj = resultObj.replace("{", "");
        resultObj = resultObj.replace("}", "");
        String[]temp = resultObj.split(",");
        String myemail = temp[0].replace("\"email\":", "");
        myemail = removeQuotes(myemail);
        String myname = temp[1].replace("\"name\":", "");
        myname = removeQuotes(myname);
        String mymajor = temp[2].replace("\"major\":", "");
        mymajor = removeQuotes(mymajor);
        String myyear = temp[3].replace("\"year\":","");
        myyear = removeQuotes(myyear);
        String mybio = temp[4].replace("\"bio\":","");
        mybio = removeQuotes(mybio);
        String myimg = temp[5].replace("\"img_id\":","");
        myimg = removeQuotes(myimg);
        myProfile[0] = myemail;
        myProfile[1] = myname;
        myProfile[2] = mymajor;
        myProfile[3] = myyear;
        myProfile[4] = mybio;
        myProfile[5] = myimg;
        return myProfile;
    }

    /**
     * Creates User
     * @author Themiya D Chandraratna
     * @param username : desired username
     * @param password : desired password
     * @return boolean true if success otherwise false
     * @throws Exception : Database Connection Error
     */
    public boolean createUser(String username, String password) throws Exception {
        ArrayList<String> resultData = handler.getUsers();
        if (resultData.isEmpty()) {
            throw new Exception("Database Connection Failed!");
        }
        for (String record: resultData) {
            if (record.contains(username)) { //user exists already
                return false;
            }
        }
        boolean isCreated = handler.createUser(username, password);
        if (isCreated) {
            handler.createProfile(username);
            return true;
        }
        else {
            return false;
        }
    }
}
