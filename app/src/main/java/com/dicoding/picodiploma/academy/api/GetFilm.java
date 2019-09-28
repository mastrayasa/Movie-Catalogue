package com.dicoding.picodiploma.academy.api;

import java.util.List;

import com.dicoding.picodiploma.academy.entitas.Film;
import com.google.gson.annotations.SerializedName;

public class GetFilm {
    @SerializedName("page")
    String page;

    @SerializedName("results")
    List<Film> listFilm;


    public GetFilm() {
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Film> getListFilm() {
        return listFilm;
    }

    public void setListFilm(List<Film> listFilm) {
        this.listFilm = listFilm;
    }
}
