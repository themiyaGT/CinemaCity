package com.trollema.controllers.appControllers;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * BaseHTTPHandler: handles the basic HTTP connection with Restful Services
 * Supports methods get and post
 * Created by themiya on 4/26/2016.
 */
public class BaseHTTPHandler {
    /**
     * Get Request: executes a URL connection that returns a value
     * @param givenRequest : the HTTP Request to be executed
     * @return GETHTTPRequest : the HTTP Request object containing the result
     */
    public static GETHTTPRequest get(GETHTTPRequest givenRequest) {
        try {
            URL myApi = new URL(givenRequest.getQuery());
            HttpURLConnection conn = (HttpURLConnection) myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            givenRequest.setResult(inputLine);
            conn.disconnect();
            in.close();
            return givenRequest;
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Log.d("Server_Connection_Error", "Failed to "+givenRequest.getRequestName());
            givenRequest.setResult("404");
            return givenRequest;
        }
    }

    /**
     * POST Request: executes a URL connection that does not return a value
     * @param givenRequest : the HTTP Request to be executed
     */
    public static void post(HTTPRequest givenRequest) throws Exception{
        try {
            URL myApi = new URL(givenRequest.getQuery());
            HttpURLConnection conn = (HttpURLConnection) myApi.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            conn.disconnect();
            in.close();
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Log.d("Server_Connection_Error", "Failed to "+givenRequest.getRequestName());
            throw new Exception ("Failed to "+givenRequest.getRequestName());
        }
    }
}
