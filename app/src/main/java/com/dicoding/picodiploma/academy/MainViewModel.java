package com.dicoding.picodiploma.academy;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.academy.api.ApiInterface;
import com.dicoding.picodiploma.academy.database.FilmHelper;
import com.dicoding.picodiploma.academy.database.TvHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<List<Film>> listFilm = new MutableLiveData<>();
    private MutableLiveData<List<Tv>> listTv = new MutableLiveData<>();



    void setListTv(ApiInterface mApiInterface) {

        Call<GetTv> kontakCall = mApiInterface.getTv(API_KEY,"en-US");
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
    LiveData<List<Tv>> getTvs() {
        return listTv;
    }


    void setListFilm(ApiInterface mApiInterface) {

        Call<GetFilm> kontakCall = mApiInterface.getFilm(API_KEY,"en-US");
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






    LiveData<List<Film>> getFilms() {
        return listFilm;
    }





    void setListTvFavorite(TvHelper tvHelper) {

        listTv.postValue(tvHelper.getAll());

    }


    void setListFilmFavorite(FilmHelper filmHelper) {

        listFilm.postValue(filmHelper.getAll());

    }






}
