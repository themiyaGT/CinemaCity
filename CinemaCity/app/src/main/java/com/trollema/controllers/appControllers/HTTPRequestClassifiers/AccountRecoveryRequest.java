package com.trollema.controllers.appControllers.HTTPRequestClassifiers;
import com.trollema.appdata.constants.BaseURL;
import com.trollema.controllers.appControllers.HTTPRequest;

/**
 * AccountRecoveryRequest: the HTTP Request that handles account recovery
 * Created by themiya on 4/27/2016.
 */
public class AccountRecoveryRequest implements HTTPRequest {
    private final String requestName = "emailRecovery";
    private String email;

    /**
     * Constructor
     * @param email --user email
     */
    public AccountRecoveryRequest(String email) {
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
