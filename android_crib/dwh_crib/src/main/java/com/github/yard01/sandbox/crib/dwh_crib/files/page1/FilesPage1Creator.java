package com.github.yard01.sandbox.crib.dwh_crib.files.page1;

import android.os.Environment;
import android.util.Log;
import android.view.View;

public class FilesPage1Creator {
    private static final String LOG_TAG = "external_path";

    public static void createContent(View rootView) {

        Log.d(LOG_TAG, "External Storage Directory: " + Environment.getExternalStorageDirectory().getPath());
        Log.d(LOG_TAG, "Root Diretory: " + Environment.getRootDirectory());
        Log.d(LOG_TAG, "External Storage State: " +Environment.getExternalStorageState());
        Log.d(LOG_TAG, "Public Directory Pictures: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        Log.d(LOG_TAG, "Public Directory Downloads: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        Log.d(LOG_TAG, "Cache Directory Downloads: " + Environment.getDownloadCacheDirectory());
        Log.d(LOG_TAG, "Internal Application path: " + rootView.getContext().getApplicationContext().getFilesDir());
        Log.d(LOG_TAG, "External Application path: " + rootView.getContext().getExternalFilesDir(null));
        Log.d(LOG_TAG, "External Application path for rington: " + rootView.getContext().getExternalFilesDir(Environment.DIRECTORY_RINGTONES));
        Log.d(LOG_TAG, "External Application path for movies: " + rootView.getContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES));



    }
}
