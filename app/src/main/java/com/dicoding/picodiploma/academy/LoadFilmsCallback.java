package com.dicoding.picodiploma.academy;

import android.database.Cursor;

public interface LoadFilmsCallback {
    void postExecute(Cursor films);
}
