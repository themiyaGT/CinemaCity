package com.trollema.controllers.appControllers.HTTPRequestClassifiers;
import com.trollema.appdata.constants.BaseURL;
import com.trollema.controllers.appControllers.GETHTTPRequest;

/**
 * SearchMovieRequest: the HTTP Request that searches for movie information
 * Created by themiya on 4/27/2016.
 */
public class SearchMovieRequest implements GETHTTPRequest {
    private final String requestName = "s";
    private String queryResult;
    private String title;

    /**
     * Constructor
     * @param title --movie title
     */
    public SearchMovieRequest(String title) {
        this.title = title;
    }

    @Override
    public String getQuery() {
        String action = requestName+"=";
        title = title.replaceAll(" ","%20");
        String param1 = title;
        String myQuery = BaseURL.omdbURL+action+param1;
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

