package com.dicoding.picodiploma.academy.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.dicoding.picodiploma.academy.database.DatabaseContract.AUTHORITY;
import static com.dicoding.picodiploma.academy.database.DatabaseContract.MovieColumns.TABLE_MOVIE;

public class FilmProvider extends ContentProvider {


    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FilmHelper filmHelper;

    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", MOVIE_ID);
    }


    @Override
    public boolean onCreate() {
        filmHelper = FilmHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {


        Log.e("Cursor", "luar: " );
        filmHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                Log.e("Cursor", "MOVIE: " );
                cursor = filmHelper.queryProvider();
                break;
            case MOVIE_ID:
                Log.e("Cursor", "MOVIE_ID: " );
                cursor = filmHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                Log.e("Cursor", "default: " );
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        int deleted;

        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                Log.e("Tag Hapus", "3" );
                deleted = filmHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                Log.e("Tag Hapus", "1" + s );
                deleted = 0;
                break;
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
