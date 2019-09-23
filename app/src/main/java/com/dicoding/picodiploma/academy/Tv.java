package com.dicoding.picodiploma.academy;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Tv implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("vote_average")
    private String vote_average;
    @SerializedName("name")
    private String name;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("original_name")
    private String original_name;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String release_date;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.vote_average);
        dest.writeString(this.name);
        dest.writeString(this.popularity);
        dest.writeString(this.poster_path);
        dest.writeString(this.original_name);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
    }

    public Tv() {
    }

    protected Tv(Parcel in) {
        this.id = in.readString();
        this.vote_average = in.readString();
        this.name = in.readString();
        this.popularity = in.readString();
        this.poster_path = in.readString();
        this.original_name = in.readString();
        this.backdrop_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
    }

    public static final Creator<Tv> CREATOR = new Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel source) {
            return new Tv(source);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
        }
    };
}
