package com.dicoding.picodiploma.academy.database;

import android.provider.BaseColumns;


public class DatabaseContract {

    static final class MovieColumns implements BaseColumns {

        static String TABLE_MOVIE = "movies";
        static String title = "title";
        static String overview = "overview";
        static String release_date = "release_date";
        static String vote_average = "vote_average";
        static String popularity = "popularity";
        static String poster_path = "poster_path";
        static String original_title = "original_title";
        static String backdrop_path = "backdrop_path";
    }


    static final class TvColumns implements BaseColumns {

        static String TABLE_TV = "tvs";
        static String name = "name";
        static String overview = "overview";
        static String release_date = "release_date";
        static String vote_average = "vote_average";
        static String popularity = "popularity";
        static String poster_path = "poster_path";
        static String original_name = "original_name";
        static String backdrop_path = "backdrop_path";
    }
}
