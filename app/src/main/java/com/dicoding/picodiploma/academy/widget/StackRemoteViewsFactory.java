package com.dicoding.picodiploma.academy.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dicoding.picodiploma.academy.MovieFavoriteWidget;
import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.database.FilmHelper;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private FilmHelper filmHelper;
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    public StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_placeholder));

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
}

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));
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
