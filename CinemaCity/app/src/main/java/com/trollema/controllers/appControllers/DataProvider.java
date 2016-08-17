package com.trollema.controllers.appControllers;

/**
 * DataProvider: Singleton class that handles all the application dataflow control
 * Created by themiya on 4/27/2016.
 */
public class DataProvider {
    private static DataProvider ourInstance = new DataProvider();

    public static DataProvider getInstance() {
        return ourInstance;
    }

    private DataProvider() {
    }

    public String[] accountLogin(String username, String password) {
        return null;
    }

    public String[] accountCreate(String username, String password) {
        return null;
    }
}
