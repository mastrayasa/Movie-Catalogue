package com.dicoding.picodiploma.academy.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {



    public static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 8;


    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL)",
            DatabaseContract.MovieColumns.TABLE_MOVIE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.title,
            DatabaseContract.MovieColumns.overview,
            DatabaseContract.MovieColumns.release_date,
            DatabaseContract.MovieColumns.vote_average,
            DatabaseContract.MovieColumns.popularity,
            DatabaseContract.MovieColumns.poster_path,
            DatabaseContract.MovieColumns.original_title,
            DatabaseContract.MovieColumns.backdrop_path
    );



    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL," +
                    " %s TEXT  NULL)",
            DatabaseContract.TvColumns.TABLE_TV,
            DatabaseContract.TvColumns._ID,
            DatabaseContract.TvColumns.name,
            DatabaseContract.TvColumns.overview,
            DatabaseContract.TvColumns.release_date,
            DatabaseContract.TvColumns.vote_average,
            DatabaseContract.TvColumns.popularity,
            DatabaseContract.TvColumns.poster_path,
            DatabaseContract.TvColumns.original_name,
            DatabaseContract.TvColumns.backdrop_path
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MovieColumns.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TvColumns.TABLE_TV);
        onCreate(db);
    }


}
