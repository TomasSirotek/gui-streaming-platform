package com.javagui.bll;
import com.javagui.be.*;
import com.javagui.dal.DataAccessManager;

import java.util.*;

public class LogicManager implements ILogicManager{

    DataAccessManager dataMgr = new DataAccessManager();

    public void reloadAllDataFromStorage(){
        dataMgr.updateCacheFromDisk();
    }

    public Collection<User> getAllUsers() {
        return dataMgr.getAllUsers().values();
    }
    // Gets all rated movies for one user and returns them sorted by avg. best by all users.
    @Override
    public List<Movie> getTopAverageRatedMovies(User u) {
        List<Movie> top = new ArrayList<>();

        for(Rating rating: u.getRatings())
            top.add(rating.getMovie());

        top.sort(Comparator.comparing(Movie::getAverageRating).reversed());

        return top;
    }

    // Gets all rated movies for one user and returns them sorted by avg. best by all users.
    @Override
    public List<Movie> getTopAverageRatedMoviesUserDidNotSee(User u) {
        List<Movie> top = new ArrayList<>();

        for (Movie m : dataMgr.getAllMovies().values()){
            boolean isSeen = false;
            for(Rating r : u.getRatings())
                if(r.getMovie()==m) {
                    isSeen = true;
                    break;
                }
            if(!isSeen)
                top.add(m);
        }

        Collections.sort(top, Comparator.comparing(Movie::getAverageRating).reversed());

        return top;
    }

    private double calculateUserSimilarity(User u1, User u2){
        int count = 0;
        double rsim = 0;
        List<Rating> r1 = u1.getRatings();
        List<Rating> r2 = u2.getRatings();
        for (Rating ur1 : r1){
            for(Rating ur2 : r2){
                if(ur1.getMovie()==ur2.getMovie())
                {
                    double diff = ur1.getRating() - ur2.getRating();
                    rsim += Math.abs(diff)/10;
                    count++;
                }
            }
        }
        return 1-(rsim/count); // 1.0 = 100% identical; 0.0 no similarities
    }
    @Override
    public List<UserSimilarity> getTopSimilarUsers(User user){
        List<UserSimilarity> allUsersSimList = new ArrayList<>();
        for (User u: dataMgr.getAllUsers().values()){
            if(u!=user)
                allUsersSimList.add(new UserSimilarity(u, calculateUserSimilarity(user, u)));
        }
        Collections.sort(allUsersSimList, Comparator.comparing(UserSimilarity::getSimilarity).reversed());

        return allUsersSimList;
    }
    @Override
    public List<TopMovie> getTopMoviesFromSimilarPeople(User u){
        List<UserSimilarity> userSimList = getTopSimilarUsers(u);
        List<TopMovie> favorites = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int topAmount = 100;
            List<Rating> ratings = userSimList.get(i).getUser().getRatings();
            if(topAmount>=ratings.size())
                topAmount=ratings.size()-1;
            for (int j = 0; j < topAmount; j++) {
                Movie m = ratings.get(j).getMovie();
                double rating = ratings.get(j).getRating();

                boolean found = false;
                for (TopMovie topmovie : favorites){
                    if(topmovie.getMovie()==m) {
                        topmovie.getRawRatings().add(rating);
                        found=true;
                    }
                }
                if(!found){
                    TopMovie tm = new TopMovie(m);
                    tm.getRawRatings().add(rating);
                    favorites.add(tm);
                }
            }
        }
        favorites.sort(Comparator.comparing(TopMovie::getAverageRating).reversed());
        return favorites;
    }
    @Override
    public User getUser(String userName) {
        try {
            return dataMgr.getAllUsers().values().stream().filter(u -> u.getName().equals(userName)).findFirst().get();
        }
        catch (NoSuchElementException e){
            return null;
        }
    }
}
