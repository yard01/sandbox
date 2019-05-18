package com.github.yard01.sandbox.crib.integration_crib.tasks;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.integration_crib.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;

public class TasksTabContentCreator {
    public static final String LOG_TAG = "INTEGRATION_TASK";

    public static String getTaskList(Context context) {

        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        //final List<String> taskList = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.AppTask> tasks = manager.getAppTasks();

            for (ActivityManager.AppTask task : tasks) {
                //task.finishAndRemoveTask();
                //if (true) break;
                ActivityManager.RecentTaskInfo rti = task.getTaskInfo();
                //rti.baseIntent.getComponent()
                sb.append("ID = ")
                        .append(rti.id)
                        .append(",")
                        .append("Description = ")
                        .append(rti.taskDescription.getLabel())
                        .append(",")
                        .append("BaseIntent = ")
                        .append(rti.baseIntent)
                        //.append(",")
                        .append("\n\r")
                ;
                //taskList.add(task.getTaskInfo().id )
            };
        } else {
            List<ActivityManager.RunningTaskInfo> tasks =  manager.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo task : tasks) {
                sb.append("id = ")
                        .append(task.id)
                        .append(",")
                        .append("description = ")
                        .append(task.description)
                        //.append(",")
                        //.append("BaseActivity = ")
                        //.append(""+ rti.baseActivity.toString())
                        //.append(",")
                        .append("\n\r")
                ;
            }
        }
        return sb.toString();
    }

    public static void createContent(FragmentManager fragmentManager, final View rootView) {

        TextView textOutput = rootView.findViewById(R.id.integration_crib_task_info);
        textOutput.setText(getTaskList(rootView.getContext()));
        Button btn = (Button)rootView.findViewById(R.id.integration_crib_task_btn_open1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(rootView.getContext(), TaskDemoActivity1.class);
                rootView.getContext().startActivity(i);
            }
        });
    }


}
