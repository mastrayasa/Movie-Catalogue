package com.dicoding.picodiploma.academy;

import com.google.gson.annotations.SerializedName;

public class PostPutDelFilm {

    @SerializedName("status")
    String status;
    @SerializedName("result")
    Film mFilm;
    @SerializedName("message")
    String message;

    public PostPutDelFilm() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Film getFilm() {
        return mFilm;
    }

    public void setFilm(Film mFilm) {
        this.mFilm = mFilm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
