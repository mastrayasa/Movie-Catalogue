package com.dicoding.picodiploma.academy.helper;

import android.database.Cursor;

import com.dicoding.picodiploma.academy.database.DatabaseContract;
import com.dicoding.picodiploma.academy.entitas.Film;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Film> mapCursorToArrayList(Cursor cursor) {
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
}
