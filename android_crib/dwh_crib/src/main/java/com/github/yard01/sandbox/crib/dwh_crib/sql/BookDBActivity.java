package com.github.yard01.sandbox.crib.dwh_crib.sql;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dwh_crib.R;

import java.util.ArrayList;
import java.util.List;

public class BookDBActivity extends AppCompatActivity {
    private boolean splitterDown = false;
    private float xTouch, yTouch;
    private EditText editText;
    private String[] colNames;

    public class RowAdapter extends BaseAdapter {
        private List<String[]> data;
        public RowAdapter(List<String[]> data) {

            this.data = data;

            //if (data.size() == null);
        }

        @Override
        public int getCount() {
            return data.size() * data.get(0).length;
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textField;
            if (data.size() == 0) return null;
            if (convertView == null) {
                textField = new TextView(parent.getContext());
                textField.setLayoutParams(new GridView.LayoutParams(115, 85));
            }
            else textField = (TextView)convertView;

            int row = position / data.get(0).length;
            int col = position % data.get(0).length;

            textField.setText("" + data.get(row)[col]);
                //ll.addView(textField);

            //} else {
            //    textField = (TextView)convertView;
           // }
            //textField.setText("" + position);

            return textField;
            /*
            LinearLayout ll = new LinearLayout(parent.getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            String[] row = this.data.get(position);
            Log.d("grid", "" + position);
            for (int i = 0; i < row.length; i++) {
                TextView textField = new TextView(parent.getContext());
                textField.setText("" + data.get(position)[i]);
                ll.addView(textField);
            }
            return ll;*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dwh_crib_activity_book_db);
        //setContentView(R.layout.layout_from_indus_bafomet);
        //horizontalGridView = (HorizontalGridView) findViewById(R.id.gridView);
        //GridElementAdapter adapter = new GridElementAdapter(this);
        //horizontalGridView.setAdapter(adapter);
        //if (true) return;
        final RelativeLayout topLayout = findViewById(R.id.dwh_crib_sql_editor_top_layout);
        //final RelativeLayout bottomLayout = findViewById(R.id.dwh_crib_sql_editor_bottom_layout);
        editText = findViewById(R.id.dwh_crib_sql_editor);

        //editText.setText("select * from android_metadata");
        //editText.setText("select * from sqlite_master");
        editText.setText("select * from sat_books");

        final GridView gridView = findViewById(R.id.dwh_crib_sql_editor_grid);
        //gridView.
        View divider = findViewById(R.id.dwh_crib_sqleditor_divider);
        divider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: splitterDown = true;
                        xTouch = event.getX();
                        yTouch = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        /*
                        float dX = xTouch - event.getX();
                        float dY = yTouch - event.getY();

                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) editText.getLayoutParams();
                        Log.d("touch_ttt", "" + params.height);

                        params.height = topLayout.getHeight() + (int)dY;

                        editText.setLayoutParams(params);
                        xTouch = event.getX();
                        yTouch = event.getY();
                        //v.setY(v.getY() + (int)dY);
                        Log.d("touch", "" + topLayout.getHeight());*/
                        break;
                    case MotionEvent.ACTION_UP: splitterDown = false; break;
                }


                Log.d("touch", "" + event);
                return true;

            }
        });

        Button button = findViewById(R.id.dwh_crib_sql_editor_button_run);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cur = SQLTabContentCreator.db.rawQuery(editText.getText().toString(), null);
                colNames = cur.getColumnNames();
                int columnCount = cur.getColumnCount();
                List<String[]> data = new ArrayList<String[]>();
                data.add(colNames);
                while (cur.moveToNext()) {
                    String[] row = new String[columnCount];
                    for (int i = 0;  i < columnCount - 1; i++) row[i] = cur.getString(i);
                    data.add(row);
                }
                gridView.setNumColumns(columnCount);

                gridView.setAdapter(new RowAdapter(data));

            }
        });


    }
}
