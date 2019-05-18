package com.github.yard01.sandbox.crib.ui_crib.dialogs.page1;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ui_crib.R;
import com.github.yard01.sandbox.crib.ui_crib.TabContentCreator;
import com.github.yard01.sandbox.crib.ui_crib.dialogs.DialogsTabContentCreator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment  extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            //при notAlertDialog = true создается только Диалоговый Фрагмент
            if (getArguments() != null) {
                if (getArguments().getBoolean("notAlertDialog")) {
                    return super.onCreateDialog(savedInstanceState); // Dialog
                }
            }

            //В противном случае создается Alert внутри Диалогового Фрагмента
            //Показывается только содержимое Alert-а
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Alert Dialog");
            builder.setMessage("Alert Dialog inside DialogFragment");
            //String s = "";
            //builder.setItems({CharSequence. (""), ""}, null);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            });

            return builder.create(); //Dialog

        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialogs_page1_fragment, container, false);

        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);


            final EditText editText = view.findViewById(R.id.dialogs_inputEmail);

            if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("email")))
                editText.setText(getArguments().getString("email"));

            Button btnDone = view.findViewById(R.id.dialogs_buttonDone);
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView = ((Activity) DialogsTabContentCreator.getActivity(view)).findViewById(R.id.dialogFragment_textView);

                    //Log.d("edittext", ""+editText + "#" + textView);
                    //DialogListener dialogListener = (DialogListener) getActivity();
                    //dialogListener.onFinishEditDialog(editText.getText().toString());
                    if (TextUtils.isEmpty(editText.getText()+"")) {
                        textView.setText("Email was not entered");
                    } else
                        textView.setText("Email entered: " + editText.getText().toString());

                    dismiss();
                }
            });
        }

        @Override
        public void onResume() {
            super.onResume();

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Log.d("DialogFragment", "onCreate");

            boolean setFullScreen = false;
            if (getArguments() != null) {
                setFullScreen = getArguments().getBoolean("fullScreen");
                if (setFullScreen)
                    setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            }

        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }

        //public interface DialogListener {
        //    void onFinishEditDialog(String inputText);
        //}

}
