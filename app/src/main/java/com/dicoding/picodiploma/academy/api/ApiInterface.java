package com.dicoding.picodiploma.academy.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<GetFilm> getFilm( @Query("api_key") String apiKey, @Query("language") String Language);

    @GET("discover/tv")
    Call<GetTv> getTv(@Query("api_key") String apiKey, @Query("language") String Language);


}
