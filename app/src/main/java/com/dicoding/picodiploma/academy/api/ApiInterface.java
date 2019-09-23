package com.dicoding.picodiploma.academy.api;

import com.dicoding.picodiploma.academy.Film;
import com.dicoding.picodiploma.academy.GetFilm;
import com.dicoding.picodiploma.academy.GetTv;
import com.dicoding.picodiploma.academy.PostPutDelFilm;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<GetFilm> getFilm( @Query("api_key") String apiKey, @Query("language") String Language);

    @GET("discover/tv")
    Call<GetTv> getTv(@Query("api_key") String apiKey, @Query("language") String Language);


}
