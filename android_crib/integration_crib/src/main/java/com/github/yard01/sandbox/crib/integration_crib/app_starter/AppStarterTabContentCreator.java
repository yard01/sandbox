package com.github.yard01.sandbox.crib.integration_crib.app_starter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.integration_crib.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class AppStarterTabContentCreator {

    public static void createContent(FragmentManager fragmentManager, View rootView) {
        //Примеры неявного вызова Activity других приложений
        //Вызов Internet-браузера
        //rootView.
        Button btnBrowser = rootView.findViewById(R.id.integration_crib_app_starter_btn_browser);
        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("about:blank"));
                v.getContext().startActivity(i);
                //startActivity(i);
            }
        });

        //Вызов почтовой программы
        Button btnEmail = rootView.findViewById(R.id.integration_crib_app_starter_btn_email);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent mailClient = new Intent(Intent.ACTION_VIEW);
                //mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
                //mailClient.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //v.getContext().startActivity(mailClient);
                //Intent.

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"youremail@yahoo.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "subject");
                email.putExtra(Intent.EXTRA_TEXT, "message");
                email.setType("message/rfc822");
                v.getContext().startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });

        //Вызов галереи
        Button btnGalery = rootView.findViewById(R.id.integration_crib_app_starter_btn_gallery);

        //final FragmentActivity dummy = new FragmentActivity();

        btnGalery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                File outputDir =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);// Environment.getExternalStorageDirectory(); //Environment.getDownloadCacheDirectory();
                //gallery.p
                //Bundle args = new Bundle();
                //args.put
                //args.putBinder();
                try {

                    File outputFile = File.createTempFile("integration_crib_img_tmp", ".jpeg", outputDir);

                    InputStream is = v.getContext().getAssets().open("pictures/serov_girl_with_peaches.jpeg");
                    OutputStream os =  new FileOutputStream(outputFile); // v.getContext().openFileOutput("integration_crib_img_tmp.jpeg",  Context.MODE_PRIVATE);// new FileOutputStream(outputFile);

                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    os.write(buffer);
                    os.close();
                    is.close();

                    v.getContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "", null);

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    values.put(MediaStore.MediaColumns.DATA, outputFile.getPath());// outputFile.getPath());
                    v.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); //.EXTERNAL_CONTENT_URI, values);
                    //v.getContext().startActivity(Intent.createChooser(gallery, "Gallery Application"));//gallery); //Intent.createChooser(galery, "Share Image!"));
                    Log.d("intent", "" + gallery);


                    ((FragmentActivity)v.getContext()).startActivityForResult(Intent.createChooser(gallery, "Gallery Application"), 1);

                    //dummy.startActivityForResult(Intent.createChooser(gallery, "Gallery Application"), 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //Проверка настройки ассоциации с PNG-файлами
        Button btnAssociate = rootView.findViewById(R.id.integration_crib_app_starter_btn_associate);
        btnAssociate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputDir =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);// Environment.getExternalStorageDirectory(); //Environment.getDownloadCacheDirectory();

                File outputFile = null;
                try {
                    outputFile = File.createTempFile("integration_crib_png_tmp", ".png", outputDir);
                    InputStream is = v.getContext().getAssets().open("pictures/car_cartoon.png");
                    OutputStream os =  new FileOutputStream(outputFile); // v.getContext().openFileOutput("integration_crib_img_tmp.jpeg",  Context.MODE_PRIVATE);// new FileOutputStream(outputFile);

                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    os.write(buffer);
                    os.close();
                    is.close();

                    Intent i = new Intent(Intent.ACTION_VIEW); //для нашего Activity PngFileViewerDemo тип action - ACTION_VIEW

                    i.setData(Uri.fromFile(outputFile));
                    v.getContext().startActivity(i);
                    //((FragmentActivity)v.getContext()).startActivityForResult(Intent.createChooser(gallery, "Gallery Application"), 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

}
