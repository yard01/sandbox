package com.github.yard01.sandbox.crib.integration_crib.content_provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.integration_crib.R;

import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class ContentProviderClient {
    final String LOG_TAG = "myLogs";

    final Uri BOOKS_URI = Uri
            .parse("content://com.github.yard01.sandbox.crib.providers.SimpleBookDB/booklist");

    final String BOOK_NAME = "name";
    final String BOOK_AUTHOR = "author";

    CursorAdapter adapter;

    public static class BookCursorAdapter extends SimpleCursorAdapter {

        public BookCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }
    }

    public CursorAdapter getAdapter() {
        return adapter;
    }


    public ContentProviderClient(Context context) {
        //setContentView(R.layout.main);
        //context.getContentResolver()

        Cursor cursor = context.getContentResolver().query(BOOKS_URI, null, null,
                null, null);

        //startManagingCursor(cursor);
//        cursor.moveToFirst();
        String from[] = { "name", "author" };

        int to[] = { R.id.integration_contentprovider_text1,  R.id.integration_contentprovider_text2};
        adapter = new SimpleCursorAdapter(context,
                R.layout.integration_content_provider_item, cursor, from, to, 0);

        //ListView lvContact = (ListView) ((View)context).findViewById(R.id.integration_contentprovider_listview);
        //lvContact.setAdapter(adapter);
    }


    public void onClickInsert(View v) {
        ContentValues cv = new ContentValues();
        cv.put(BOOK_NAME, "name 4");
        cv.put(BOOK_AUTHOR, "author 4");
        Uri newUri = v.getContext().getContentResolver().insert(BOOKS_URI, cv);
        Log.d(LOG_TAG, "insert, result Uri : " + newUri.toString());
    }

    public void onClickUpdate(View v) {
        ContentValues cv = new ContentValues();
        cv.put(BOOK_NAME, "name 5");
        cv.put(BOOK_AUTHOR, "email 5");
        Uri uri = ContentUris.withAppendedId(BOOKS_URI, 2);
        int cnt = v.getContext().getContentResolver().update(uri, cv, null, null);        Log.d(LOG_TAG, "update, count = " + cnt);
    }
    public void onClickDelete(View v) {
        Uri uri = ContentUris.withAppendedId(BOOKS_URI, 3);
        int cnt = v.getContext().getContentResolver().delete(uri, null, null);
        Log.d(LOG_TAG, "delete, count = " + cnt);
    }

    public void onClickError(View v) {
        Uri uri =
                //Uri.parse("content://com.github.yard01.sandbox.crib.providers.SimpleBookDB/games");
                //Uri.parse("content://android.provider.Contacts");

        //Uri.parse(CalendarContract.Calendars.CONTENT_URI.toString());
        Uri.parse(ContactsContract.Contacts.CONTENT_URI.toString());
//CalendarContract.CONTENT_URI.toString()+"/Events");
        //Uri.parse("content://android.provider.Contacts");

        try {
            Cursor cursor = v.getContext().getContentResolver().query(uri, null, null, null, null);
            while (cursor.moveToNext()) {
                cursor.getString(0);
            }
            Log.d(LOG_TAG, uri.toString()  + " : " + cursor.moveToNext());
        } catch (Exception ex) {
            Log.d(LOG_TAG, "Error: " + ex.getClass() + ", " + ex.getMessage());
        }

    }
}
