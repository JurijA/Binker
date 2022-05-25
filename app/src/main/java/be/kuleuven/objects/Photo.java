package be.kuleuven.objects;

import android.graphics.Bitmap;

import java.sql.Timestamp;

public class Photo {

    private final Bitmap bitmapPhoto;
    private final User user;
    private final String beverage;
    private final Timestamp timestamp;
    private Integer likecount;

    public Photo(Bitmap bitmap, User user, String beverage, Timestamp timestamp, Integer likecount) {
        this.bitmapPhoto = bitmap;
        this.user = user;
        this.beverage = beverage;
        this.timestamp = timestamp;
        this.likecount = likecount;
    }

    public Bitmap getBitmapPhoto() {
        return bitmapPhoto;
    }

    public User getUser() {
        return user;
    }

    public String getBeverage() {
        return beverage;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }


}

