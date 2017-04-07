package easytest.catophot.ru.androidexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("DEBUG_TAG", "debug message");
        Log.v("VERBOSE_TAG", "verbose message");
        Log.v("VERBOSE_TAG", "verbose message again");
    }
}
