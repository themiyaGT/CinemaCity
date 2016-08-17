package com.trollema.controllers.appControllers.HTTPRequestClassifiers;
import com.trollema.appdata.constants.BaseURL;
import com.trollema.controllers.appControllers.GETHTTPRequest;

/**
 * CinemaCityRatingDataRequest: the HTTP Request that retrieves user rating data
 * Created by themiya on 4/27/2016.
 */
public class CinemaCityRatingDataRequest implements GETHTTPRequest {
    private final String requestName = "getRatingData";
    private String queryResult;
    private String email;

    /**
     * Constructor
     * @param email --user email
     */
    public CinemaCityRatingDataRequest(String email) {
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

    @Override
    public String getResult() {
        return queryResult;
    }

    @Override
    public void setResult(String givenResult) {
        queryResult = givenResult;
    }
}
