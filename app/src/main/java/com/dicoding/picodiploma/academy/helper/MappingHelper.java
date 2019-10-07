package com.dicoding.picodiploma.academy.helper;

import android.database.Cursor;

import com.dicoding.picodiploma.academy.database.DatabaseContract;
import com.dicoding.picodiploma.academy.entitas.Film;
import com.dicoding.picodiploma.academy.entitas.Tv;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Film> mapCursorToArrayListFilms(Cursor cursor) {
        ArrayList<Film> filmArrayList = new ArrayList<>();
        Film film;

        while (cursor.moveToNext()) {
            film = new Film();
            film.setId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID)));
            film.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.title)));
            film.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.overview)));
            film.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.release_date)));
            film.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.vote_average)));
            film.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.popularity)));
            film.setOriginal_title(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.original_title)));
            film.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.backdrop_path)));
            film.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.poster_path)));


            filmArrayList.add(film);
        }

        return filmArrayList;
    }


    public static ArrayList<Tv> mapCursorToArrayListTvs(Cursor cursor) {
        ArrayList<Tv> tvArrayList = new ArrayList<>();
        Tv tv;
        try {
            while (cursor.moveToNext()) {
                tv = new Tv();
                tv.setId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns._ID)));
                tv.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.name)));
                tv.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.overview)));
                tv.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.release_date)));
                tv.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.vote_average)));
                tv.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.popularity)));
                tv.setOriginal_name(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.original_name)));
                tv.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.backdrop_path)));
                tv.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.poster_path)));


                tvArrayList.add(tv);
            }
        } finally {
            cursor.close();
        }

        return tvArrayList;
    }
}
