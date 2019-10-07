package com.dicoding.picodiploma.academy.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.dicoding.picodiploma.academy.widget.StackRemoteViewsFactory;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
