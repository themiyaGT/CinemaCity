package com.trollema.controllers;
import java.util.ArrayList;

/**
 * Created by themiya on 2/23/2016.
 */
public class MovieDataController {
    /**
     * Returns an arraylist of movies
     * @param movieTitle given movie title
     * @author Themiya D Chandraratna
     * @return arraylist
     */
    public static ArrayList<String> getMovieTitles(String movieTitle, RequestHandler handler){
        ArrayList<String>movies = new ArrayList<String>();
        String movieData = handler.searchMovies(movieTitle);
        if (!movieData.equals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}")) {
            movieData = movieData.replace("{\"Search\":[", "");
            movieData = movieData.replace("},", "~");
            String[] movieDataArr = movieData.split("~");
            for (String givenTitle : movieDataArr) {
                givenTitle = givenTitle.replace(",\"Year\":", "~");
                String[] movieRecord = givenTitle.split("~");
                String myMovieTitle = movieRecord[0].replace("{\"Title\":", "");
                myMovieTitle = myMovieTitle.replace("\"", "");
                movies.add(myMovieTitle);
            }
        }
        return movies;
    }
}
