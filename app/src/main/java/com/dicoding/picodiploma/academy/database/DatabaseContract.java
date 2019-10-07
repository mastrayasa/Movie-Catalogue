package com.dicoding.picodiploma.academy.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class DatabaseContract {

    private static final String SCHEME = "content";
    public static final String AUTHORITY = "com.dicoding.picodiploma.academy";

    public static final class MovieColumns implements BaseColumns {

        public static String TABLE_MOVIE = "movies";
        public static String title = "title";
        public static String overview = "overview";
        public static String release_date = "release_date";
        public static String vote_average = "vote_average";
        public static String popularity = "popularity";
        public static String poster_path = "poster_path";
        public static String original_title = "original_title";
        public static String backdrop_path = "backdrop_path";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();


    }


    public static final class TvColumns implements BaseColumns {

        public static String TABLE_TV = "tvs";
        public static String name = "name";
        public static String overview = "overview";
        public static String release_date = "release_date";
        public static String vote_average = "vote_average";
        public static String popularity = "popularity";
        public static String poster_path = "poster_path";
        public static String original_name = "original_name";
        public static String backdrop_path = "backdrop_path";

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build();
    }


    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
