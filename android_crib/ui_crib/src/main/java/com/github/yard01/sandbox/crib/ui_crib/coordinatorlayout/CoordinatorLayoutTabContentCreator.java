package com.github.yard01.sandbox.crib.ui_crib.coordinatorlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui_crib.R;
import com.google.android.material.behavior.SwipeDismissBehavior;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CoordinatorLayoutTabContentCreator {


    public static class RecyclerViewAdapter // адаптер (заполнялка) для recyclerView
            extends RecyclerView.Adapter<RecyclerViewAdapter.RowViewHolder> {

        public static int ITEM_COUNT = 100;



        private final SwipeDismissBehavior.OnDismissListener onDismissListener =  new SwipeDismissBehavior.OnDismissListener() {
            @Override public void onDismiss(View view) {
                Toast.makeText(view.getContext(),
                        "Card swiped !!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDragStateChanged(int state) {}
        };

        public RecyclerViewAdapter() {
            //swipe.setListener(onDismissListener);
        }

        @NonNull
        @Override
        public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //Здесь создается ViewHolder строки данных на основе layout - файла
            //Log.d("inflater", "" + parent.getContext());
            View view = LayoutInflater.from(parent.getContext()) //в родительское окно встраивается View с LinearLayout, на котором лежат визуальные элементы строки
                    .inflate(R.layout.recyclerview_card_row, parent, false);  //
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

                //CardView cardView = (CardView) view.findViewById(R.id.recyclerview_cardview);
                //CoordinatorLayout.LayoutParams coordinatorParams =
                //        (CoordinatorLayout.LayoutParams) cardView.getLayoutParams();

                //SwipeDismissBehavior<CardView> swipe =  new SwipeDismissBehavior();

                //swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);
                //swipe.setListener(onDismissListener);
                //coordinatorParams.setBehavior(swipe);

                mContentView = (TextView) view.findViewById(R.id.recyclerview_card_text); //
            }
        }
    }

    public static void createContent(FragmentManager fragmentManager, final View view) {
        /*Button btn = view.findViewById(R.id.recyclerview_button_schow);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), RecyclerViewActivity.class);
                view.getContext().startActivity(i);
            }
        });*/

        RecyclerViewAdapter adapter = new  RecyclerViewAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview1);
        recyclerView.setAdapter(adapter);//new SimpleItemRecyclerViewAdapter(this, ModuleList.ITEMS, mTwoPane));
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.main_toolbar);//R.id.recyclerview_toolbar);
        toolbar.inflateMenu(R.menu.ui_crib_recycler_menu);
        //TB.me
        //com.google.android.material.appbar.AppBarLayout abl;
        //abl.createContextMenu();

    }

    public static class AvatarImageBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

/*
        @Override
        public boolean layoutDependsOn( 
        CoordinatorLayout parent,
                CircleImageView, child,
        View dependency) {

            return dependency instanceof Toolbar;
        }   

        public boolean onDependentViewChanged( 
        CoordinatorLayout parent,
        CircleImageView avatar,
        View dependency) { 
            modifyAvatarDependingDependencyState(avatar, dependency);
        }

        private void modifyAvatarDependingDependencyState(
                CircleImageView avatar, View dependency) {
            //  avatar.setY(dependency.getY());
            //  avatar.setBlahBlah(dependency.blah / blah);
        }
        */
    }

}
