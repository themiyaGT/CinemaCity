package com.trollema.controllers.appControllers.HTTPRequestClassifiers;
import com.trollema.appdata.constants.BaseURL;
import com.trollema.controllers.appControllers.GETHTTPRequest;

/**
 * RecommendationsRequest: the HTTP Request that retrieves movie recommendations
 * Created by themiya on 4/27/2016.
 */
public class RecommendationsRequest implements GETHTTPRequest {
    private final String requestName = "getRecommendations";
    private String queryResult;
    private String major;

    /**
     * Constructor
     * @param major --user major
     */
    public RecommendationsRequest(String major) {
        this.major = major;
    }

    @Override
    public String getQuery() {
        String key = "key="+ BaseURL.cinemacityKEY;
        String action = "action="+requestName;
        major = major.replaceAll(" ","%20");
        String param1 = "major="+major;
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

