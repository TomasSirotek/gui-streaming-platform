package com.javagui.be;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int id;
    private String title;
    private int year;
    private List<Rating> ratings;

    public Movie(int id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.ratings = new ArrayList<>();
    }

    public double getAverageRating() {
        double sum = 0;
        for (Rating r : ratings) {
            sum += r.getRating();
        }
        if (ratings.size() == 0)
            return 0;
        return sum / ratings.size();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public int getRatingsSize() {
        return ratings.size();
    }

    @Override
    public String toString() {
        return title + ", " + year +
                ", ratings=" + ratings.size() +
                ", avg=" + Math.round(getAverageRating() * 10) / 10.0;
    }
}
