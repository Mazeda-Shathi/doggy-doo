package com.example.doggydo;

public class userHelperForRating {
    public String getRatingpoint() {
        return ratingpoint;
    }

    public void setRatingpoint(String ratingpoint) {
        this.ratingpoint = ratingpoint;
    }

    String ratingpoint;
    public userHelperForRating(String ratingpoint){
        this.ratingpoint=ratingpoint;
    }
}
