package com.github.yard01.sandbox.crib.ui_crib.coordinatorlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ui_crib.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_recyclerview);
        RecyclerViewAdapter adapter = new  RecyclerViewAdapter();
        RecyclerView recyclerView = findViewById(R.id.recyclerview1);
        recyclerView.setAdapter(adapter);//new SimpleItemRecyclerViewAdapter(this, ModuleList.ITEMS, mTwoPane));

        //setContentView(R.layout.activity_recycler);
    }

    public static class RecyclerViewAdapter // адаптер (заполнялка) для recyclerView
            extends RecyclerView.Adapter<RecyclerViewAdapter.RowViewHolder> {

        public static int ITEM_COUNT = 100;
        @NonNull
        @Override
        public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //Здесь создается ViewHolder строки данных на основе layout - файла
            //Log.d("inflater", "" + parent.getContext());
            View view = LayoutInflater.from(parent.getContext()) //в родительское окно встраивается View с LinearLayout, на котором лежат визуальные элементы строки
                    .inflate(R.layout.recycler_view_row, parent, false);  //
            return new RowViewHolder(view); //ViewHolder(view); //
        }

        @Override
        public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
            //событие привязки к ViewHolder
            //здесь данные помещаются в control-ы ViewHolder
            holder.mContentView.setText("№ " + position);

        }

        @Override
        public int getItemCount() {
            return ITEM_COUNT;
        }

        class RowViewHolder extends RecyclerView.ViewHolder {
            //final TextView mIdView; // TextView для идентификатора
            final TextView mContentView; // TextView для содержимого строки

            RowViewHolder(View view) { //конструктор
                super(view);
                // ищем контролы по идентификаторам на View
                // они описаны в module_row_content.xmll, который представляет собой горизонтальный LinearLayout с двумя TextView:
                // [@+id/id_text][@+id/content]

                //mIdView = (TextView) view.findViewById(R.id.id_text); //
                mContentView = (TextView) view.findViewById(R.id.recyclerview_text); //
            }
        }
    }

}
