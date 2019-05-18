package com.github.yard01.sandbox.crib.ui_crib.dialogs.page0;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ui_crib.R;
import com.github.yard01.sandbox.crib.ui_crib.dialogs.DialogsTabContentCreator;

import androidx.appcompat.app.AlertDialog;

public class DialogsPage0Creator {

    public static void createContent(final View view) {
        Button button1 = (Button) view.findViewById(R.id.dialogs_alerts_button1);// .one_button_alert);
        Button button2 = (Button) view.findViewById(R.id.dialogs_alerts_button2);
        Button button3 = (Button) view.findViewById(R.id.dialogs_alerts_button3);
        //view.getb
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder( view.getContext());
                builder.setTitle("Sample Alert");
                builder.setMessage("One Action Button Alert");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(((Activity)view.getContext()).getApplicationContext(),"Positive button [OK] is clicked",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
                Log.d("alert", "alert from " + DialogsTabContentCreator.getActivity(view)+ " : " + view.getContext());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        DialogsTabContentCreator.getActivity(view));
                builder.setTitle("Sample Alert");
                builder.setMessage("Two Action Buttons Alert Dialog");
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(view.getContext(),"Negative button [No] is clicked",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(view.getContext(),"Positive button [Yes] is clicked",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                       view.getContext());
                builder.setTitle("Sample Alert");

                builder.setMessage("Three Action Buttons Alert Dialog");
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(view.getContext(),"Negative button [No] is clicked",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(view.getContext(),"Positive button [Yes] is clicked",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNeutralButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(view.getContext(),"Neutral button [Cancel] is clicked",Toast.LENGTH_LONG).show();
                            }
                        });

                builder.show();
            }
        });
    }
}
