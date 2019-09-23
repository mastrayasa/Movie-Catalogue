package com.dicoding.picodiploma.academy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTv {
    @SerializedName("page")
    String page;

    @SerializedName("results")
    List<Tv> listTv;


    public GetTv() {
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Tv> getListTv() {
        return listTv;
    }

    public void setListTv(List<Tv> listTv) {
        this.listTv = listTv;
    }
}
