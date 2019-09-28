package com.dicoding.picodiploma.academy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dicoding.picodiploma.academy.entitas.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmHelper {

    private static final String DATABASE_TABLE =  DatabaseContract.MovieColumns.TABLE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static FilmHelper INSTANCE;
    private static SQLiteDatabase database;

    private FilmHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FilmHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FilmHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public boolean isFavorite(String id){

        boolean a = false;

        String mSelection = DatabaseContract.MovieColumns._ID + "=?";
        String[] mSelectionArgs = new String[]{id};

        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                mSelection,
                mSelectionArgs,
                null,
                null,
                DatabaseContract.MovieColumns._ID + " DESC",
                null);
        cursor.moveToFirst();


        if (cursor.getCount() > 0) {
            a =  true;
        }

        cursor.close();
        return a;
    }


    public List<Film> getAll() {

        List<Film> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.MovieColumns._ID + " DESC",
                null);
        cursor.moveToFirst();
        Film film;

        if (cursor.getCount() > 0) {
            do {
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

                arrayList.add(film);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;

    }

    public long insert(Film film) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.MovieColumns._ID, film.getId());
        args.put(DatabaseContract.MovieColumns.title, film.getTitle());
        args.put(DatabaseContract.MovieColumns.overview, film.getOverview());
        args.put(DatabaseContract.MovieColumns.release_date, film.getRelease_date());
        args.put(DatabaseContract.MovieColumns.vote_average, film.getVote_average());
        args.put(DatabaseContract.MovieColumns.popularity, film.getPopularity());
        args.put(DatabaseContract.MovieColumns.original_title, film.getOriginal_title());
        args.put(DatabaseContract.MovieColumns.backdrop_path, film.getBackdrop_path());
        args.put(DatabaseContract.MovieColumns.poster_path, film.getPoster_path());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateNote(Film film) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.MovieColumns.title, film.getTitle());
        args.put(DatabaseContract.MovieColumns.overview, film.getOverview());
        args.put(DatabaseContract.MovieColumns.release_date, film.getRelease_date());
        args.put(DatabaseContract.MovieColumns.vote_average, film.getVote_average());
        args.put(DatabaseContract.MovieColumns.popularity, film.getPopularity());
        args.put(DatabaseContract.MovieColumns.original_title, film.getOriginal_title());
        args.put(DatabaseContract.MovieColumns.backdrop_path, film.getBackdrop_path());
        return database.update(DATABASE_TABLE, args, DatabaseContract.MovieColumns._ID + "= '" + film.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(DATABASE_TABLE, DatabaseContract.MovieColumns._ID + " = '" + id + "'", null);
    }
}

