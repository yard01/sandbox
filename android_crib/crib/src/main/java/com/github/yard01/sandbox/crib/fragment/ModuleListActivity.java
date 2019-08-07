package com.github.yard01.sandbox.crib.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.crib.fragment.R;

import com.github.yard01.sandbox.crib.R;
import com.github.yard01.sandbox.crib.fragment.modulelist.ModuleList;
//import com.github.yard01.com.github.yard01.com.github.yard01.com.github.yard01.sandbox.yard01.com.github.yard01.com.github.yard01.com.github.yard01.sandbox.crib.lib1.LibClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ModuleListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    //private View
    protected static boolean showModuleNumber;

    SimpleItemRecyclerViewAdapter adapter;

    protected void createContent() {
        ModuleList.addItem("", getString(R.string.loading) , "");
        setContentView(R.layout.activity_module_list);
        //AppBarLayout al;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ////////////////////////////////////////////////////
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

        }
        //LibClass lc;
        final View recyclerView = findViewById(R.id.item_list);
        //((RecyclerView)recyclerView).not
        assert recyclerView != null; //завалить программу, если recyclerView == null
        setupRecyclerView((RecyclerView) recyclerView); //запуск заполнения RecyclerView
        //Кнопка-поплавок////////////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createContent();
    }

    private void fillItems(SimpleItemRecyclerViewAdapter adapter) {
        final Context context = this;
        Single.just(0)
                .observeOn(Schedulers.io())
                .subscribe(t -> {
                    ModuleList.fillModuleList(context);
                    Single.just(0)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(t2 -> {
                                showModuleNumber = true;
                                adapter.notifyDataSetChanged();
                        }

                    );
                });

    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        /*SimpleItemRecyclerViewAdapter */
        adapter = new SimpleItemRecyclerViewAdapter(this, ModuleList.ITEMS, mTwoPane);
        recyclerView.setAdapter(adapter);//new SimpleItemRecyclerViewAdapter(this, ModuleList.ITEMS, mTwoPane));
        //запустим поиск модулей и асинхронное заполнение списка
        fillItems(adapter);

    }

    public static class SimpleItemRecyclerViewAdapter // адаптер (заполнялка) для recyclerView
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ModuleListActivity mParentActivity;
        private final List<ModuleList.DummyItem> mValues; //ссылка на список
        private final boolean mTwoPane;

        private final View.OnClickListener mOnClickListener =  new View.OnClickListener() {
            // Cлушатель нажатий на запись (а запись - это View,
            // созданная с помощью LayoutInflater из Layout-файла),
            // При нажатии на запись мы должны открыть детализацию этой записи
            @Override
            public void onClick(View view) {
                ModuleList.DummyItem item = (ModuleList.DummyItem) view.getTag(); // tag - это произвольные данные, здесь - Item
                if (item.id == null || "".equals(item.id)) return;
                if (mTwoPane) { //если у нас две панели, то надо заменить detail_container на fragment
                    Bundle arguments = new Bundle(); //структура ключ-значение для обмена информацией между объектами
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id); // ключ-значение
                    //////////ФРАГМЕНТ///////////////////////////////////////////////////////
                    //ItemDetailFragment fragment = new ItemDetailFragment(); // создали новый фрагмент
                    Fragment fragment = null;
                    try {
                        fragment = (Fragment) Class.forName(item.id//"com.github.yard01.sandbox.crib.ui_crib.UICribFragment"
                        ).newInstance();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        fragment = new ItemDetailFragment();
                    }
                    /////////////////////////////////////////////////////////////////////////

                    fragment.setArguments(arguments); // setArguments(Bundle) - метод предка - класса Fragment
                    mParentActivity.getSupportFragmentManager().beginTransaction()  //
                            .replace(R.id.item_detail_container, fragment)          // подменяем содержмое контейнера androidx.core.widget.NestedScrollView
                            .commit();                                              // фрагментом

                } else { //если панель одна, то просто показываем ItemDetailActivity
                    Context context = view.getContext(); // получили контекст
                    Intent intent = new Intent(context, ItemDetailActivity.class); //запускаем Activity ItemDetailActivity
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    context.startActivity(intent); //запустили Activity
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ModuleListActivity parent, //конструктор
                                      List<ModuleList.DummyItem> items,
                                      boolean twoPane) {
            mValues = items; // список элементов класса ModuleList.DummyItem, DummyItem - внутренний статический класс класса ModuleList
            mParentActivity = parent; // родительское окно
            mTwoPane = twoPane; // флаг "Две панели"
        }

        //ViewHolder - это паттерн, наследник абстрактного класса RecyclerView.ViewHolder
        //предполагает, что для каждого элемента списка создаётся объект, хранящий ссылки на View внутри этого элемента
        //ViewGroup - это класс группы представлений (View), общий предок для LinearLayout, FrameLayout и т.д.
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Создаем view для отображения строки из двух полей TextView используя layout-файл module_row_content.xmll
            //с помощью класса LayoutInflater
            View view = LayoutInflater.from(parent.getContext()) //ищем родительскую Activity
                    .inflate(R.layout.module_row_content, parent, false); //
            return new ViewHolder(view); //
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (showModuleNumber)
                holder.mIdView.setText(String.valueOf(position));//mValues.get(position).id);

            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position)); //Item
            //holder.itemView.setTag();
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        } //количество записей в списке

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView; // TextView для идентификатора
            final TextView mContentView; // TextView для содержимого строки

            ViewHolder(View view) { //конструктор
                super(view);
                // ищем контролы по идентификаторам на View
                // они описаны в module_row_content.xmll, который представляет собой горизонтальный LinearLayout с двумя TextView:
                // [@+id/id_text][@+id/content]
                mIdView = (TextView) view.findViewById(R.id.id_text); //
                mContentView = (TextView) view.findViewById(R.id.content); //
            }
        }
    }
}
