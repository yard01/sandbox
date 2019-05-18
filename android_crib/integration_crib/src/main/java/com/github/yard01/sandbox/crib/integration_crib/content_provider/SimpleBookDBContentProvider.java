package com.github.yard01.sandbox.crib.integration_crib.content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SimpleBookDBContentProvider extends ContentProvider {
//при работе с ContentProvider используется URI
//content://<authority>/<path>/<id>
//content:// - это стандартное начало для адреса провайдера
//<authority> - идентификатор провайдера, фактически имя БД
//<path> - идентификатор данных, которые предоставляет провайдер (таблица), если структура БД сложная, то path может быть составным,
//         например, contacts/phone, books/author
//<id> - идентификатор записи, с которой идёт работа
//если <id> не указан, то работа идёт со всеми записями таблицы

    final String LOG_TAG = "SIMPLE_DB_LOG";
    // // Константы для БД
    // БД
    static final String DB_NAME = "simple_bookdb";
    static final int DB_VERSION = 1;

    // Таблица
    static final String BOOK_TABLE = "books";
    // Поля
    static final String BOOK_ID = "_id";
    static final String BOOK_NAME = "name";
    static final String BOOK_AUTHOR = "author";
    // Скрипт создания таблицы
    static final String DB_CREATE = "create table " + BOOK_TABLE + "("
            + BOOK_ID + " integer primary key autoincrement, "
            + BOOK_NAME + " text, " + BOOK_AUTHOR + " text" + ");";

    // // Uri
    // authority
    static final String AUTHORITY = "com.github.yard01.sandbox.crib.providers.SimpleBookDB";
    // path
    static final String BOOK_PATH = "booklist";
    // Общий Uri
    public static final Uri CONTACT_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + BOOK_PATH);
    // Типы данных
    // набор строк
    static final String BOOK_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."       + AUTHORITY + "." + BOOK_PATH;
    // одна строка
    static final String BOOK_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + BOOK_PATH;
    //// UriMatcher
    // общий Uri
    static final int URI_BOOKS = 1;

    // Uri с указанным ID
    static final int URI_BOOKS_ID = 2;

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            for (int i = 1; i <= 3; i++) {
                cv.put(BOOK_NAME, "Book name " + i);
                cv.put(BOOK_AUTHOR, "Author " + i);
                db.insert(BOOK_TABLE, null, cv);
            }
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher; // UriMatcher - это парсер для проверки разных вариантов URI

    static {
        //UriMatcher будет работать со строками вида
        //content://<AUTHORITY>/<BOOK_PATH>
        //content://<AUTHORITY>/<BOOK_PATH>/<Числовое значение>
        //# - маска числа, * - маска любого символа
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, BOOK_PATH, URI_BOOKS);
        uriMatcher.addURI(AUTHORITY, BOOK_PATH + "/#", URI_BOOKS_ID);
    }
    @Override
    public boolean onCreate() {
        Log.d(LOG_TAG, "onCreate");
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(LOG_TAG, "query, " + uri.toString());
        // проверяем Uri
        switch (uriMatcher.match(uri)) {
            case URI_BOOKS: // общий Uri
                Log.d(LOG_TAG, "URI_BOOKS");
                // если сортировка не указана, ставим свою - по имени
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = BOOK_NAME + " ASC";
                }
                break;
            case URI_BOOKS_ID: // Uri с ID
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_BOOKS_ID, " + id);
                // добавляем ID к условию выборки
                if (TextUtils.isEmpty(selection)) {
                    selection = BOOK_ID + " = " + id;
                } else {
                    selection = selection + " AND " + BOOK_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(BOOK_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CONTACT_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),
                CONTACT_CONTENT_URI);
        return cursor;
        //return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) { //Тип получаемых данных
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_BOOKS:
                return BOOK_CONTENT_TYPE;
            case URI_BOOKS_ID:
                return BOOK_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(LOG_TAG, "insert, " + uri.toString());
        if (uriMatcher.match(uri) != URI_BOOKS)
            throw new IllegalArgumentException("Wrong URI: " + uri);
        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(BOOK_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились    getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
