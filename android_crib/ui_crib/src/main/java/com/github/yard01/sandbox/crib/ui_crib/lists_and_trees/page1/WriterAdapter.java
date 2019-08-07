package com.github.yard01.sandbox.crib.ui_crib.lists_and_trees.page1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.ui_crib.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class WriterAdapter extends BaseExpandableListAdapter {

    final static String[] all_objects = {
            //I LEVEL - writers
            "A. Pushkin", //0
            "M. Lermontov", //1
            "L. Tolstoy", //2
            "N. Gogol", //3

            //II LEVEL - genres
            "poem", //4
            "rhyme", //5
            "novel",  //6
            "story", //7
            "play", //8

            //III LEVEL - book names
            "Ruslan and Ludmila", //9: 0 -> 4
            "Evgeny Onegin", //10:  0 -> 4
            "Poltava", //11: 0 -> 4
            "The Copper Rider", //12: 0 -> 4
            "The Winter Evening", //13: 0 -> 5
            "Autumn",//14: 0 -> 5
            "Snowstorm", //15  0-7
            "Queen of Spades", //16 0->7
            "The Captain Daugther", //17 0->6
            "Dubrovsky", //18 0->6

            "Borodino" , //19 1 -> 5
            "The Daemon", //20 1-> 4
            "Mtsyry", //21 1-> 4
            "Vadim", //22 1->6
            "Princess Ligovskaya", //23 1->6
            "Shtoss", //24 - 1->7
            "The Gypsies", //25 1->8
            "The Spaniards", //26 1->8

            "The Caucasian Prisoner", //27 2->7
            "The Cossacks", //28 2->7
            "Khaji-Murat", //29 2->7
            "War and Peace", //30 2->6
            "Anna Karenina", //31 2->6
            "Sunday", //32 2->6

            "Mirgorod", //33 3->7
            "Taras Bulba", //34 3->7
            "The Night Before Christmass", //35 3->7
            "The Inspector General", //36 3->8
            "The Dead Souls" //37 3->8
    };

    // writer
    //    genre
    //      book name

    final static int[][] tree_objects = {

            //Pushkin
            {0, 4, 9},
            {0, 4, 10},
            {0, 4, 11},
            {0, 4, 12},
            {0, 5, 13},
            {0, 5, 14},
            {0, 7, 15},
            {0, 7, 16},
            {0, 6, 17},
            {0, 6, 18},

            //Lermontov
            {1, 5, 19},
            {1, 4, 20},
            {1, 4, 21},
            {1, 6, 22},
            {1, 6, 23},
            {1, 7, 24},
            {1, 8, 25},
            {1, 8, 26},

            //Tolstoy
            {2, 7, 27},
            {2, 7, 28},
            {2, 7, 29},
            {2, 6, 30},
            {2, 6, 31},
            {2, 6, 32},

            //Gogol
            {3, 7, 33},
            {3, 7, 34},
            {3, 7, 35},
            {3, 8, 36},
            {3, 8, 37},

    };
    static int[] header_codes;
    static String[] headers;

    static {
        header_codes = getHeaderCodes();
        headers = new String[header_codes.length];
        int k = 0;
        for (int i : header_codes) {
            headers[k] = all_objects[i];
            k++;
        }
    }

    public static int[] getHeaderCodes()  {
        int[] result = new int[tree_objects.length];
        Arrays.sort(tree_objects, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int t = o1[0] - o2[0];
                if (t !=0) return t;
                t = o1[1] - o2[1];
                if (t !=0) return t;
                t = o1[2] - o2[2];
                if (t !=0) return t;
                return 0;
            }
        });

        int t = -1;
        int k = 0;
        for (int i = 0; i < tree_objects.length; i++) {
            if (t != tree_objects[i][0]) {
                result[k] = tree_objects[i][0];
                t = result[k];
                k++;

            }
        }

        return Arrays.copyOf(result, k);
    }

    public static int[] getChildrenCodes(int head_cd, int level) {
        int[] result = new int[tree_objects.length];
        int k = 0;
        int cd = -1;
        for (int i = 0; i < tree_objects.length; i++) {
            if (tree_objects[i][level] == head_cd) {
                if (cd != tree_objects[i][level + 1]) {
                    result[k] = tree_objects[i][level + 1];
                    cd = tree_objects[i][level + 1];
                    k++;

                }
            }
        }
        return Arrays.copyOf(result, k);
    }

    public static String[] getBooks(int writer_cd, int genre_cd) {

        String[] result = new String[tree_objects.length];
        int k = 0;
        for (int i = 0; i < tree_objects.length; i++) {
            if (tree_objects[i][0] == writer_cd && tree_objects[i][1] == genre_cd) {
                result[k] = all_objects[tree_objects[i][2]];
                k++;
            }
        }
        return Arrays.copyOf(result, k);
    }


    public static String[] getChilds() {
        return null;
    }

    @Override
    public int getGroupCount() {
        return headers.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ////////////////////////
        return 1; //??????
        ////////////////////////
    }

    @Override
    public Object getGroup(int groupPosition) {
        return  groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //convertView = inflater.inflate(R.layout.row_first, null); //первый уровень

        convertView = LayoutInflater.from(parent.getContext()) //в родительское окно встраивается View с LinearLayout, на котором лежат визуальные элементы строки
                .inflate(R.layout.lists_and_trees_grouprow, parent, false);

        TextView text = (TextView) convertView.findViewById(R.id.lists_and_trees_tree_grouptext); //текст

        text.setText(headers[groupPosition]); //установили текст в узле первого уровня дерева

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final SecondLevelView secondLevelELV = new SecondLevelView(parent.getContext()); // динамически создается внутренний ExpandableListView


        int[] codes = getChildrenCodes(header_codes[groupPosition], 0); //коды заголовков второго уровня - массив
        String[] genres = new String[codes.length];

        List<String[]> childData = new ArrayList<>(); //
        int k = 0;
        for (int c : codes) {
            genres[k] = all_objects[c];
            String books[] = getBooks(header_codes[groupPosition], c);
            childData.add(books);
            k++;
        }

        secondLevelELV.setAdapter(new SecondLevelAdapter(parent.getContext(), genres, childData)); //устанавливаем адаптер второго уровня и передаем в него заголовки и данные

        secondLevelELV.setGroupIndicator(null);

        // вешаем обработчик на onExpand
        secondLevelELV.setOnGroupExpandListener(new NodeAutocollapser(secondLevelELV));

        return secondLevelELV;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
