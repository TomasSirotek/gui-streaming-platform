package com.javagui.bll;

import com.javagui.be.Movie;
import com.javagui.be.TopMovie;
import com.javagui.be.User;
import com.javagui.be.UserSimilarity;

import java.util.Collection;
import java.util.List;

public interface ILogicManager {
    Collection<User> getAllUsers();
    List<Movie> getTopAverageRatedMovies(User u);
    List<Movie> getTopAverageRatedMoviesUserDidNotSee(User u);
    List<UserSimilarity> getTopSimilarUsers(User user);
    List<TopMovie> getTopMoviesFromSimilarPeople(User u);
    User getUser(String userName);
}
