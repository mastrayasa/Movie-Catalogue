package com.dicoding.picodiploma.academy.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<GetFilm> getFilm( @Query("api_key") String apiKey, @Query("language") String Language);

    @GET("search/movie")
    Call<GetFilm> searchMovie(@Query("api_key") String apiKey, @Query("language") String Language, @Query("query") String query);

    @GET("discover/tv")
    Call<GetTv> getTv(@Query("api_key") String apiKey, @Query("language") String Language);

    @GET("search/tv")
    Call<GetTv> searchTv(@Query("api_key") String apiKey, @Query("language") String Language, @Query("query") String query);

    @GET("discover/movie")
    Call<GetFilm> getFilmRelease( @Query("api_key") String apiKey, @Query("primary_release_date.gte") String gte, @Query("primary_release_date.lte") String lte);


}
