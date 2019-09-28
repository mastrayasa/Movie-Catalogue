package com.dicoding.picodiploma.academy.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.picodiploma.academy.BuildConfig;
import com.dicoding.picodiploma.academy.api.ApiInterface;
import com.dicoding.picodiploma.academy.api.GetFilm;
import com.dicoding.picodiploma.academy.api.GetTv;
import com.dicoding.picodiploma.academy.database.FilmHelper;
import com.dicoding.picodiploma.academy.database.TvHelper;
import com.dicoding.picodiploma.academy.entitas.Film;
import com.dicoding.picodiploma.academy.entitas.Tv;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<List<Film>> listFilm = new MutableLiveData<>();
    private MutableLiveData<List<Tv>> listTv = new MutableLiveData<>();


    public void setListTv(ApiInterface mApiInterface) {

        Call<GetTv> kontakCall = mApiInterface.getTv(API_KEY, "en-US");
        kontakCall.enqueue(new Callback<GetTv>() {
            @Override
            public void onResponse(Call<GetTv> call, Response<GetTv> response) {

                List<Tv> list_tvs;
                list_tvs = response.body().getListTv();
                listTv.postValue(list_tvs);
            }

            @Override
            public void onFailure(Call<GetTv> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }


    public void searchTv(ApiInterface mApiInterface, String query) {

        Call<GetTv> kontakCall = mApiInterface.searchTv(API_KEY, "en-US",query);
        kontakCall.enqueue(new Callback<GetTv>() {
            @Override
            public void onResponse(Call<GetTv> call, Response<GetTv> response) {

                List<Tv> list_tvs;
                list_tvs = response.body().getListTv();
                listTv.postValue(list_tvs);
            }

            @Override
            public void onFailure(Call<GetTv> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    public LiveData<List<Tv>> getTvs() {
        return listTv;
    }


    public void setListFilm(ApiInterface mApiInterface) {

        Call<GetFilm> kontakCall = mApiInterface.getFilm(API_KEY, "en-US");
        kontakCall.enqueue(new Callback<GetFilm>() {
            @Override
            public void onResponse(Call<GetFilm> call, Response<GetFilm> response) {

                List<Film> list_films;
                list_films = response.body().getListFilm();
                listFilm.postValue(list_films);
            }

            @Override
            public void onFailure(Call<GetFilm> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }


    public void searchFilm(ApiInterface mApiInterface, String query) {

        Call<GetFilm> kontakCall = mApiInterface.searchMovie(API_KEY, "en-US",query);
        kontakCall.enqueue(new Callback<GetFilm>() {
            @Override
            public void onResponse(Call<GetFilm> call, Response<GetFilm> response) {

                List<Film> list_films;
                list_films = response.body().getListFilm();
                listFilm.postValue(list_films);
            }

            @Override
            public void onFailure(Call<GetFilm> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    public void clearMovie(){
        //List<Film> list_films = new ArrayList<Film>();
        listFilm.postValue(null);
    }


    public LiveData<List<Film>> getFilms() {

        return listFilm;
    }


    public void setListTvFavorite(TvHelper tvHelper) {

        listTv.postValue(tvHelper.getAll());

    }


    public void setListFilmFavorite(FilmHelper filmHelper) {

        listFilm.postValue(filmHelper.getAll());

    }


}
