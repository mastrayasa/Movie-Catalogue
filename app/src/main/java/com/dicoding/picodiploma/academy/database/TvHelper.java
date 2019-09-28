package com.dicoding.picodiploma.academy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dicoding.picodiploma.academy.entitas.Tv;
import java.util.ArrayList;
import java.util.List;

public class TvHelper {

    private String susu;
    private static final String DATABASE_TABLE =  DatabaseContract.TvColumns.TABLE_TV;
    private static DatabaseHelper dataBaseHelper;
    private static TvHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
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


    public List<Tv> getAll() {

        List<Tv> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                DatabaseContract.TvColumns._ID + " DESC",
                null);
        cursor.moveToFirst();
        Tv tv;

        if (cursor.getCount() > 0) {
            do {
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

                arrayList.add(tv);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;

    }

    public long insert(Tv film) {
        ContentValues args = new ContentValues();

        args.put(DatabaseContract.TvColumns._ID, film.getId());
        args.put(DatabaseContract.TvColumns.name, film.getName());
        args.put(DatabaseContract.TvColumns.overview, film.getOverview());
        args.put(DatabaseContract.TvColumns.release_date, film.getRelease_date());
        args.put(DatabaseContract.TvColumns.vote_average, film.getVote_average());
        args.put(DatabaseContract.TvColumns.popularity, film.getPopularity());
        args.put(DatabaseContract.TvColumns.original_name, film.getOriginal_name());
        args.put(DatabaseContract.TvColumns.backdrop_path, film.getBackdrop_path());
        args.put(DatabaseContract.TvColumns.poster_path, film.getPoster_path());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateNote(Tv film) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.TvColumns.name, film.getName());
        args.put(DatabaseContract.TvColumns.overview, film.getOverview());
        args.put(DatabaseContract.TvColumns.release_date, film.getRelease_date());
        args.put(DatabaseContract.TvColumns.vote_average, film.getVote_average());
        args.put(DatabaseContract.TvColumns.popularity, film.getPopularity());
        args.put(DatabaseContract.TvColumns.original_name, film.getOriginal_name());
        args.put(DatabaseContract.TvColumns.backdrop_path, film.getBackdrop_path());
        return database.update(DATABASE_TABLE, args, DatabaseContract.TvColumns._ID + "= '" + film.getId() + "'", null);
    }



    public int delete(int id) {
        return database.delete(DATABASE_TABLE, DatabaseContract.TvColumns._ID + " = '" + id + "'", null);
    }
}
