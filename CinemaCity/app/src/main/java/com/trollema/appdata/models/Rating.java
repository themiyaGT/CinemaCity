package com.trollema.appdata.models;

/**
 * Created by themiya on 3/29/2016.
 */
public class Rating {
    public String major = "";
    public String title = "";
    public int total_stars = 0;
    public int total_reviews = 0;
    public double avg_rating = 0;

    public Rating(String major, String title, int total_stars, int total_reviews){
        this.major = major;
        this.title = title;
        this.total_stars = total_stars;
        this.total_reviews = total_reviews;
        this.avg_rating = (double) total_stars / total_reviews;
    }
}
