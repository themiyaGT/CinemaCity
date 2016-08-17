package com.trollema.controllers;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import android.util.Log;
import java.util.regex.Pattern;

/**
 * HTTP Controller: Manages HTTP Request to RESTful APIs
 * Created by themiya on 2/15/2016.
 */
public class HTTPHandler implements RequestHandler {

    //API Host and Key
    private static String baseUrl = "http://cinemacity.us/api.php/?";
    private static String apiKey = "0a9f1322d388b7d10c68dc9df32fb4b47d310d84b9e25d9cb2e8ff0db8c615d4";

    /**
     * Requests API for entire User List
     * @author Themiya D Chandraratna
     * Provides a ArrayList String of Users
     * @return ArrayList containing User list
     */
    @Override
    public ArrayList<String> getUsers() {
        String key = "key="+apiKey;
        String action = "action=getUsers";
        String myQuery = baseUrl+key+"&"+action;
        ArrayList<String> resultData = new ArrayList<String>();
        try {
            URL myApi = new URL(myQuery);
            HttpURLConnection conn = (HttpURLConnection) myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            String JSONString = inputLine.replace("[", "");
            JSONString = JSONString.replace("]","");
            String[] myData = JSONString.split(Pattern.quote("},"));
            for (String givenRecord : myData) {
                if (!givenRecord.contains("}")) {
                    givenRecord = givenRecord + "}";
                    resultData.add(givenRecord);
                }
                else {
                    resultData.add(givenRecord);
                }
            }
            conn.disconnect();
            in.close();
            return resultData;
        } catch (Exception e) {
            Log.d("Error",e.getMessage());
            Log.d("Server_Connection_Error", "Failed to getUsers");
            return resultData;
        }
    }

    /**
     * Requests API for User Profile
     * @author Themiya D Chandraratna
     * Provides a String containing User Data
     * @param username : the email of the desired User Profile
     * @return String containing User Profile
     */
    @Override
    public String getProfile(String username) {
        String key = "key="+apiKey;
        String action = "action=getProfile";
        String email = "email="+username;
        String myQuery = baseUrl+key+"&"+action+"&"+email;
        String resultData = "";
        try {
            URL myApi = new URL(myQuery);
            HttpURLConnection conn = (HttpURLConnection) myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            resultData = inputLine;
            in.close();
            conn.disconnect();
            return resultData;
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to getProfile");
            return resultData;
        }
    }

    /**
     * Requests API for User Profile
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User Profile
     * @return boolean true if login successful// false if login failed
     */
    @Override
    public boolean login(String username) {
        String key = "key="+apiKey;
        String action = "action=login";
        String email = "email="+username;
        String myQuery = baseUrl+key+"&"+action+"&"+email;
        boolean resultData = false;
        try {
            URL myApi = new URL(myQuery);
            URLConnection conn = myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            if ("true".equals(inputLine)) {
                resultData = true;
            }
            in.close();
            return resultData;
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to login");
            return false;
        }
    }

    /**
     * Requests API for User Logout
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User Profile
     * @return boolean true if logout successful// false if logout failed
     */
    @Override
    public boolean logout(String username) {
        String key = "key="+apiKey;
        String action = "action=logout";
        String email = "email="+username;
        String myQuery = baseUrl+key+"&"+action+"&"+email;
        boolean resultData = false;
        try {
            URL myApi = new URL(myQuery);
            URLConnection conn = myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            if ("true".equals(inputLine)) {
                resultData = true;
            }
            in.close();
            return resultData;
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to logout");
            return false;
        }
    }

    /**
     * Requests API to Create User
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User
     * @param password : the password of the desired User
     * @return boolean true if creation successful// false if creation failed
     */
    @Override
    public boolean createUser(String username, String password) {
        String key = "key="+apiKey;
        String action = "action=createUser";
        String email = "email="+username;
        String usrPassword = "password="+password;
        String myQuery = baseUrl+key+"&"+action+"&"+email+"&"+usrPassword;
        boolean resultData = false;
        try {
            URL myApi = new URL(myQuery);
            URLConnection conn = (HttpURLConnection) myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            if ("true".equals(inputLine)) {
                resultData = true;
            }
            in.close();
            return resultData;
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to createUser");
            return false;
        }
    }

    /**
     * Requests API to Create User Profile
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User
     * @return boolean true if creation successful// false if creation failed
     */
    @Override
    public boolean createProfile(String username) {
        String key = "key="+apiKey;
        String action = "action=createProfile";
        String email = "email="+username;
        String myQuery = baseUrl+key+"&"+action+"&"+email;
        boolean resultData = false;
        try {
            URL myApi = new URL(myQuery);
            URLConnection conn = myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            if ("true".equals(inputLine)) {
                resultData = true;
            }
            in.close();
            return resultData;
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to createProfile");
            return false;
        }
    }

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
    @Override
    public boolean updateProfile(String email, String name, String major, String year, String bio, String img) {
        String key = "key="+apiKey;
        String action = "action=updateProfile";
        String myemail = "email="+email;
        String myname = "name="+name;
        String mymajor = "major="+major;
        String myyear = "year="+year;
        bio = bio.replaceAll(" ","_");
        String mybio = "bio="+bio;
        String myimg = "img="+img;
        String myQuery = baseUrl+key+"&"+action+"&"+myemail+"&"+myname+"&"+mymajor+"&"+myyear+"&"+mybio+"&"+myimg;
        boolean resultData = false;
        try {
            URL myApi = new URL(myQuery);
            URLConnection conn = myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            if ("true".equals(inputLine)) {
                resultData = true;
            }
            in.close();
            return resultData;
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to updateProfile");
            return false;
        }
    }

    /**
     * Requests API for User Ban
     * @author Themiya D Chandraratna
     * @param username : the email of the desired User Profile
     */
    @Override
    public void banUser(String username) {
        String key = "key="+apiKey;
        String action = "action=ban";
        String email = "email="+username;
        String myQuery = baseUrl+key+"&"+action+"&"+email;
        try {
            URL myApi = new URL(myQuery);
            URLConnection conn = myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            in.readLine();
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to Ban");
        }
    }

    /**
     * Requests API for User undo Ban
     * @author Themiya D Chandraratna
     * @param username : the email of the desired User Profile
     */
    @Override
    public void undoBan(String username) {
        String key = "key="+apiKey;
        String action = "action=unban";
        String email = "email="+username;
        String myQuery = baseUrl+key+"&"+action+"&"+email;
        try {
            URL myApi = new URL(myQuery);
            URLConnection conn = myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            in.readLine();
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to Undo-Ban");
        }
    }

    /**
     * Requests API to determine Admin Account
     * @author Themiya D Chandraratna
     * Provides a boolean status
     * @param username : the email of the desired User Profile
     * @return boolean true if Admin// false otherwise
     */
    @Override
    public boolean isAdmin(String username) {
        String key = "key="+apiKey;
        String action = "action=isAdmin";
        String email = "email="+username;
        String myQuery = baseUrl+key+"&"+action+"&"+email;
        boolean resultData = false;
        try {
            URL myApi = new URL(myQuery);
            URLConnection conn = myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            if ("true".equals(inputLine)) {
                resultData = true;
            }
            in.close();
            return resultData;
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to logout");
            return false;
        }
    }

    // < -------------------------------------------------------------------------------------------------------------------------->

    //OMDb Api
    private static String plugin = "http://www.omdbapi.com/?";

    /**
     * Searches omdb for movie title
     * @author Themiya D Chandraratna
     * @param givenTitle given title
     * @return string output
     */
    @Override
    public String searchMovies(String givenTitle) {
        givenTitle = givenTitle.replace(" ","%20");
        String myQuery = plugin+"s="+givenTitle;
        try {
            URL myApi = new URL(myQuery);
            URLConnection conn = myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            in.close();
            return inputLine;
        } catch (Exception e) {
            Log.d("Server_Connection_Error", "Failed to getMovieTitles");
            return null;
        }
    }
}

// < -------------------------------------------------------------------------------------------------------------------------->

//the moviedb api
//api key -- cb06a6efd06fd2c42b09682197137b08
//url --
