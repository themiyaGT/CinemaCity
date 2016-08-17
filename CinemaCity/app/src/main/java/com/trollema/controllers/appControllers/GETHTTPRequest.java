package com.trollema.controllers.appControllers;

import com.trollema.controllers.appControllers.HTTPRequest;

/**
 * Interface for GET HTTP Requests
 * Provides 2 additional methods
 * setResult : a method that sets the query result
 * getResult : a method that gets the query result
 * Created by themiya on 4/27/2016.
 */
public interface GETHTTPRequest extends HTTPRequest {
    /**
     * Sets the query result
     */
    public void setResult(String givenResult);

    /**
     * Gets the query result
     * @return String: the query result
     */
    public String getResult();
}
