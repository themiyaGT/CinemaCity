package com.trollema.controllers.appControllers;

/**
 * Interface for Generic HTTP Requests
 * Provides 2 methods
 * getQuery: a method that creates HTTP Request Query from the constructor parameters
 * getRequestName: a method that provides the HTTP method name
 * Created by themiya on 4/27/2016.
 */
public interface HTTPRequest {
    /**
     * Creates HTTP Request
     * @return String: the HTTP Request
     */
    public String getQuery();

    /**
     * Identifies the HTTP Request by Name
     * @return String: the name of the request
     */
    public String getRequestName();
}
