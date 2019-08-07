package com.github.yard01.sandbox.crib.ui_crib.dialogs.page1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui_crib.R;
import com.github.yard01.sandbox.crib.ui_crib.TabContentCreator;
import com.github.yard01.sandbox.crib.ui_crib.dialogs.DialogsTabContentCreator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class DialogsPage1Creator {

    //public static TextView

    public static void createContent(final View view) {
        Button btnEmbedDialogFragment, btnDialogFragment, btnDialogFragmentFullScreen, btnAlertDialogFragment;
        TextView textView;
        textView = view.findViewById(R.id.dialogFragment_textView);
        btnEmbedDialogFragment = view.findViewById(R.id.btnEmbedDialogFragment);
        btnDialogFragment = view.findViewById(R.id.btnDialogFragment);
        btnDialogFragmentFullScreen = view.findViewById(R.id.btnDialogFragmentFullScreen);
        btnAlertDialogFragment = view.findViewById(R.id.btnAlertDialogFragment);
        final AppCompatActivity activity = (AppCompatActivity)DialogsTabContentCreator.getActivity(view);

        View.OnClickListener clickListener =  new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                //int R_id_btnEmbedDialogFragment = R.id.btnEmbedDialogFragment;
                //switch (view.getId()) {
                MyDialogFragment dialogFragment = new MyDialogFragment();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle(); //параметры этой структуры ключ-значение определяют вид диалога
                Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");

                if (view.getId() == R.id.btnEmbedDialogFragment) {
                    //обычное использование фрагмента - замена FrameLayout, лежащего на view, на фрагмент MyDialogFragment (dialogFragment)
                    ft.replace(R.id.dialogs_frameLayout, dialogFragment);
                    ft.commit();
                }
                else if (view.getId() == R.id.btnDialogFragment) {
                    // notAlertDialog = true
                    //появится Диалоговый Фрагмент - модальное окно
                    bundle.putBoolean("notAlertDialog", true);
                    dialogFragment.setArguments(bundle);
                    if (prev != null) { // закрывае предыдущий диалог с тегом dialog
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    dialogFragment.show(ft, "dialog"); //commit транзакции
                }
                else if (view.getId() == R.id.btnDialogFragmentFullScreen) {

                    bundle.putString("email", "xyz@gmail.com");
                    bundle.putBoolean("fullScreen", true); // то же самое, что и в предыдущем случае, но в полнорежимном экране
                    bundle.putBoolean("notAlertDialog", true); // но в полнорежимном экране

                    dialogFragment.setArguments(bundle);


                    //ft = getSupportFragmentManager().beginTransaction();
                    //prev = getSupportFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    dialogFragment.show(ft, "dialog");//commit транзакции
                }
                else if (view.getId() == R.id.btnAlertDialogFragment) {
                    //появится Alert
                    if (prev != null) {
                       ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    dialogFragment.show(ft, "dialog");//commit транзакции

                }


            }
        };

        btnEmbedDialogFragment.setOnClickListener(clickListener);
        btnDialogFragment.setOnClickListener(clickListener);
        btnDialogFragmentFullScreen.setOnClickListener(clickListener);
        btnAlertDialogFragment.setOnClickListener(clickListener);

    }

}
