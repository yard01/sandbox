package com.github.yard01.sandbox.crib.dwh_crib.sql;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dwh_crib.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;

public class SQLTabContentCreator {
    public static final String LOG_TAG = "SQL_crib";
    public static final String DBNAME = "DWHCribBookDB";

    private static DBHelper helper;
    private static TextView infoText;

    public static void setDb(SQLiteDatabase db) {
        SQLTabContentCreator.db = db;
    }

    public static SQLiteDatabase db;

    public static String[] getScriptStrings(Context context, String path) {
        try {
            InputStream is = context.getAssets().open(path);
            String newLine = System.getProperty("line.separator");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder result = new StringBuilder();
            boolean flag = false;
            for (String line; (line = reader.readLine()) != null; ) {
                result//.append(flag? newLine: "")
                        .append(line.trim());
                //flag = true;
            }
            //result.toString().split(";");
            return result.toString().split(";");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class DBHelper extends SQLiteOpenHelper {
        Context context;



        public DBHelper(Context context) {
            super (context, DBNAME, null, 2);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
// создаем БД
            SQLTabContentCreator.setDb(db);
            //SQLTabContentCreator.createDB(context);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(LOG_TAG, "--- onUpgrade database ---");
        }
    }

    private static boolean execSQL(Context context, String[] sqlStrings) {

            for (String script : sqlStrings) {
                try {
                    if ("".equals(script)) continue;
                    db.execSQL(script);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error: " + e.getMessage() + ", in: " + script, Toast.LENGTH_LONG).show();
                    return false;
                }

            }
            showInfo(context);
            return true;
    }


    private static void createDBStructure(Context context) {
        String[] ddl = getScriptStrings(context, "scripts/create.sql");
        if (execSQL(context, ddl))
            Toast.makeText(context, "Tables were created", Toast.LENGTH_SHORT ).show();

    }

    private static void dropTables(Context context) {
        String[] ddl = getScriptStrings(context, "scripts/drop.sql");
        if (execSQL(context, ddl))
            Toast.makeText(context, "Tables were droped", Toast.LENGTH_SHORT ).show();

    }

    private static void dropDB(Context context) {
        context.deleteDatabase(DBNAME);
        Toast.makeText(context, "The Database was droped", Toast.LENGTH_SHORT ).show();

    }

    private static void createDB(Context context) {
        if (helper != null) helper.close();
        helper = new DBHelper(context);
        db =  helper.getWritableDatabase();
        Toast.makeText(context, "The Database was created", Toast.LENGTH_SHORT ).show();

    }


    private static void fillDB(Context context) {
        String[] insert = getScriptStrings(context, "scripts/fill.sql");
        if (execSQL(context, insert))
            Toast.makeText(context, "Tables were filled", Toast.LENGTH_SHORT ).show();

    }

    private static String getDBInfo(Context context) {

        Cursor cur = db.rawQuery("SELECT TYPE, NAME FROM sqlite_master ORDER BY TYPE, NAME", null);
        StringBuilder sb = new StringBuilder();
        String separator = System.getProperty("line.separator");
        while (cur.moveToNext()) {
            String type  = cur.getString(0);
            String name = cur.getString(1);
            String rows_cnt = "";
            if ("table".equals(type) || "view".equals(type)) {
                Cursor cnt_cur = db.rawQuery("SELECT COUNT(*) CNT FROM " + name, null);
                if (cnt_cur.moveToNext()) {
                    long cnt = cnt_cur.getLong(0);
                    rows_cnt = ", row count =  " +cnt;
                }
            }
            sb.append( type + ": " + name + rows_cnt);
            sb.append(separator);
        }

        return sb.toString();
    }

    private static void showInfo(Context context) {
        infoText.setText(getDBInfo(context));
    }

    //dbHelper = new DBHelper( this );
    public static void createContent(FragmentManager fragmentManager, View view) {
        infoText = view.findViewById(R.id.dwh_crib_sql_btn_dbinfo);

        createDB(view.getContext());

        getDBInfo(view.getContext());

        Button btnCreateDB = view.findViewById(R.id.dwh_crib_sql_btn_createdb);
        btnCreateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDB(v.getContext());
                Toast.makeText(v.getContext(),"The database was created", Toast.LENGTH_SHORT);
            }
        });

        Button btnCreateStructure = view.findViewById(R.id.dwh_crib_sql_btn_createstructuredb);
        btnCreateStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDBStructure(v.getContext());
            }
        });

        Button btnDropTables = view.findViewById(R.id.dwh_crib_sql_btn_droptables);
        btnDropTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropTables(v.getContext());
            }
        });

        Button btnDropDB = view.findViewById(R.id.dwh_crib_sql_btn_dropdb);
        btnDropDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDB(v.getContext());
            }
        });

        Button btnFillDB = view.findViewById(R.id.dwh_crib_sql_btn_filldb);
        btnFillDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDB(v.getContext());
            }
        });

        Button openSQLEditor = view.findViewById(R.id.dwh_crib_sql_btn_opendb);
        openSQLEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), BookDBActivity.class);
                //Bundle bundle = new Bundle();
                //i.putExtras(bundle);
                v.getContext().startActivity(i);
            }
        });
    }
}
