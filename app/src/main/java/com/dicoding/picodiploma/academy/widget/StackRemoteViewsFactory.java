package com.dicoding.picodiploma.academy.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.academy.ItemClickSupport;
import com.dicoding.picodiploma.academy.MovieFavoriteWidget;
import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.database.FilmHelper;
import com.dicoding.picodiploma.academy.entitas.Film;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import static com.dicoding.picodiploma.academy.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.dicoding.picodiploma.academy.helper.MappingHelper.mapCursorToArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

   // private FilmHelper filmHelper;
   // private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private ContentResolver resolver;
    ArrayList<Film> listFilms;

    public StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.e("Widget", "onDataSetChanged: " );
        final long identityToken = Binder.clearCallingIdentity();

        try {
            resolver = mContext.getContentResolver();
            Cursor films = resolver.query(CONTENT_URI, null, null, null, null);

            Log.e("Widget", films.getCount() + " list 2" );

            listFilms = mapCursorToArrayList(films);

            Log.e("Widget", listFilms.size() + " list" );
        } finally {
            Binder.restoreCallingIdentity(identityToken);
        }





        /*mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));*/

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listFilms.size();
}

    @Override
    public RemoteViews getViewAt(int position) {



        final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        String image = "https://image.tmdb.org/t/p/w342" + listFilms.get(position).getPoster_path();
        Log.e("img", image );

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(image)
                    .submit(512, 512)
                    .get();

            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }



        Bundle extras = new Bundle();
        extras.putInt(MovieFavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
