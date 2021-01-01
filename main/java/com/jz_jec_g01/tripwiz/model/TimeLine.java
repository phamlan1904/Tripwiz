package com.jz_jec_g01.tripwiz.model;

import android.graphics.Bitmap;

import java.util.Date;

public class TimeLine {
    private Date Date;
    private String Status;
    private Bitmap UserImage;
    private String UserName;
    private int Like;
    private String comment;

    public void setUserImage(Bitmap userImage) {
        UserImage = userImage;
    }

    public Bitmap getUserImage() {
        return UserImage;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getLike() {
        return Like;
    }

    public String getComment() {
        return comment;
    }

    public String getStatus() {
        return Status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public void setLike(int like) {
        Like = like;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
