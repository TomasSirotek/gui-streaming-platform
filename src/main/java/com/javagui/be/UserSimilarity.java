package com.javagui.be;

public class UserSimilarity {
    private User user;
    private double similarity; //0.0 to 1.0 where 1.0 is max.

    public UserSimilarity(User user, double similarity) {
        this.user = user;
        this.similarity = similarity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public int getId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }

    public String getSimilarityPercent() {
        return Math.round(similarity * 100) + "%";
    }

    @Override
    public String toString() {
        return "user=" + user +
                ", similarity=" + getSimilarityPercent();
    }
}
