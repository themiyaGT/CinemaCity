package com.trollema.controllers.appControllers.HTTPRequestClassifiers;
import com.trollema.appdata.constants.BaseURL;
import com.trollema.controllers.appControllers.GETHTTPRequest;

/**
 * AccountCreateRequest: the HTTP Request that handles account creation
 * Created by themiya on 4/27/2016.
 */
public class AccountCreateRequest implements GETHTTPRequest {
    private final String requestName = "createUserAccount";
    private String queryResult;
    private String email;
    private String password;

    /**
     * Constructor
     * @param email --user email
     * @param password --user password
     */
    public AccountCreateRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getQuery() {
        String key = "key="+ BaseURL.cinemacityKEY;
        String action = "action="+requestName;
        String param1 = "email="+email;
        String param2 = "password="+password;
        String myQuery = BaseURL.cinemacityURL+key+"&"+action+"&"+param1+"&"+param2;
        return myQuery;
    }

    @Override
    public String getRequestName() {
        return requestName;
    }

    @Override
    public String getResult() {
        return queryResult;
    }

    @Override
    public void setResult(String givenResult) {
        queryResult = givenResult;
    }
}

