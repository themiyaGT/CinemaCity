package com.trollema.controllers.appControllers.HTTPRequestClassifiers;
import com.trollema.appdata.constants.BaseURL;
import com.trollema.controllers.appControllers.HTTPRequest;

/**
 * AccountBanRequest: the HTTP Request that handles account banning
 * Created by themiya on 4/27/2016.
 */
public class AccountBanRequest implements HTTPRequest {
    private final String requestName = "ban";
    private String email;

    /**
     * Constructor
     * @param email --user email
     */
    public AccountBanRequest(String email) {
        this.email = email;
    }

    @Override
    public String getQuery() {
        String key = "key="+ BaseURL.cinemacityKEY;
        String action = "action="+requestName;
        String param1 = "email="+email;
        String myQuery = BaseURL.cinemacityURL+key+"&"+action+"&"+param1;
        return myQuery;
    }

    @Override
    public String getRequestName() {
        return requestName;
    }
}
