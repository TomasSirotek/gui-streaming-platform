package com.javagui.be;

import java.util.ArrayList;
import java.util.List;

public class TopMovie {
    private Movie movie;
    private List<Double> rawRatings;

    public TopMovie(Movie movie) {
        this.movie = movie;
        this.rawRatings = new ArrayList<>();
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Double> getRawRatings() {
        return rawRatings;
    }

    public void setRawRatings(List<Double> rawRatings) {
        this.rawRatings = rawRatings;
    }

    public String getTitle(){
        return movie.getTitle();
    }

    public int getYear(){
        return movie.getYear();
    }

    public double getAverageRating(){
        double sum=0;
        for (double rating : rawRatings){
            sum+=rating;
        }
        return sum/rawRatings.size();
    }

    @Override
    public String toString() {
        return movie +
                ", rating=" + rawRatings;
    }
}
