package com.dicoding.picodiploma.academy.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<GetFilm> getFilm( @Query("api_key") String apiKey, @Query("language") String Language);

    @GET("search/movie")
    Call<GetFilm> cariFilm(@Query("api_key") String apiKey, @Query("language") String Language, @Query("query") String query);

    @GET("discover/tv")
    Call<GetTv> getTv(@Query("api_key") String apiKey, @Query("language") String Language);

    @GET("search/tv")
    Call<GetTv> cariTv(@Query("api_key") String apiKey, @Query("language") String Language, @Query("query") String query);


}
