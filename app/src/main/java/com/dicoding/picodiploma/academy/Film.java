package com.dicoding.picodiploma.academy;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {

    String title;
    String des;
    String rilis;
    String score;
    int cover;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public String getRilis() {
        return rilis;
    }

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Film() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.des);
        dest.writeString(this.rilis);
        dest.writeString(this.score);
        dest.writeInt(this.cover);
    }

    protected Film(Parcel in) {
        this.title = in.readString();
        this.des = in.readString();
        this.rilis = in.readString();
        this.score = in.readString();
        this.cover = in.readInt();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
