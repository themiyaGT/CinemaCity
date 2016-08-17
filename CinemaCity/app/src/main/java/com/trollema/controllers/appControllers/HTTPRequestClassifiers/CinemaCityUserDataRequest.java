package com.trollema.controllers.appControllers.HTTPRequestClassifiers;
import com.trollema.appdata.constants.BaseURL;
import com.trollema.controllers.appControllers.GETHTTPRequest;

/**
 * CinemaCityUserDataRequest: the HTTP Request that retrieves all the users' data
 * Created by themiya on 4/27/2016.
 */
public class CinemaCityUserDataRequest implements GETHTTPRequest {
    private final String requestName = "getUserData";
    private String queryResult;

    /**
     * Constructor
     */
    public CinemaCityUserDataRequest() {
    }

    @Override
    public String getQuery() {
        String key = "key="+ BaseURL.cinemacityKEY;
        String action = "action="+requestName;
        String myQuery = BaseURL.cinemacityURL+key+"&"+action;
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
