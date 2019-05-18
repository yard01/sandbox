package com.github.yard01.sandbox.crib.ui_crib;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommonTools {

    public static String readStringAsset(final Context context, final String path, final String encoding) {
        BufferedReader reader = null;
        final StringBuilder result = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(path), encoding));
            String read_line;
            while ((read_line = reader.readLine()) != null) {
                result.append(read_line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    public static String readFileToString(Context context, String fileName) {

        try {
            //
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    context.openFileInput(fileName)));
            String str = "";
            StringBuilder builder = new StringBuilder();
            while ((str = br.readLine()) != null) {
                builder.append(str);
            }
            br.close();

            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
        ///context.openFileOutput()
    }

}
