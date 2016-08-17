package com.trollema.controllers.appControllers.HTTPRequestClassifiers;
import com.trollema.appdata.constants.BaseURL;
import com.trollema.controllers.appControllers.HTTPRequest;

/**
 * AccountUpdateRequest: the HTTP Request that handles account update
 * Created by themiya on 4/27/2016.
 */
public class AccountUpdateRequest implements HTTPRequest {
    private final String requestName = "updateProfile";
    private String email;
    private String name;
    private String major;
    private String year;
    private String bio;
    private String img;

    /**
     * Constructor
     * @param email --user email
     * @param name --user name
     * @param major --user major
     * @param year --user year
     * @param bio --user bio
     * @param img --user img
     */
    public AccountUpdateRequest(String email, String name, String major, String year, String bio, String img) {
        this.email = email;
        this.name = name;
        this.major = major;
        this.year = year;
        this.bio = bio;
        this.img = img;
    }

    @Override
    public String getQuery() {
        String key = "key="+ BaseURL.cinemacityKEY;
        String action = "action="+requestName;
        String param1 = "email="+email;
        name = name.replaceAll(" ","%20");
        String param2 = "name="+name;
        major = major.replaceAll(" ","%20");
        String param3 = "major="+major;
        String param4 = "year="+year;
        bio = bio.replaceAll(" ","%20");
        String param5 = "bio="+bio;
        String param6 = "img="+img;
        String myQuery = BaseURL.cinemacityURL+key+"&"+action+"&"+param1+"&"+param2+"&"+param3+"&"+param4+"&"+param5+"&"+param6;
        return myQuery;
    }

    @Override
    public String getRequestName() {
        return requestName;
    }
}
