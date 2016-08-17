package com.trollema.controllers.appControllers.HTTPRequestClassifiers;
import com.trollema.appdata.constants.BaseURL;
import com.trollema.controllers.appControllers.HTTPRequest;

/**
 * RateMovieRequest: the HTTP Request that handles movie rating
 * Created by themiya on 4/27/2016.
 */
public class RateMovieRequest implements HTTPRequest {
    private final String requestName = "rateMovie";
    private String email;
    private String major;
    private String movie;
    private String year;
    private String rating;

    /**
     * Constructor
     * @param email
     * @param major
     * @param movie
     * @param year
     * @param rating
     */
    public RateMovieRequest(String email, String major, String movie, String year, String rating) {
        this.email = email;
        this.major = major;
        this.movie = movie;
        this.year = year;
        this.rating = rating;
    }

    @Override
    public String getQuery() {
        String key = "key="+ BaseURL.cinemacityKEY;
        String action = "action="+requestName;
        String param1 = "email="+email;
        major = major.replaceAll(" ","%20");
        String param2 = "major="+major;
        movie = movie.replaceAll(" ","%20");
        String param3 = "movie="+movie;
        String param4 = "year="+year;
        String param5 = "rating="+rating;
        String myQuery = BaseURL.cinemacityURL+key+"&"+action+"&"+param1+"&"+param2+"&"+param3+"&"+param4+"&"+param5;
        return myQuery;
    }

    @Override
    public String getRequestName() {
        return requestName;
    }
}
