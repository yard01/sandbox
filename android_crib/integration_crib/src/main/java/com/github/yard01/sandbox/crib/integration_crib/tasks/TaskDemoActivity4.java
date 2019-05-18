package com.github.yard01.sandbox.crib.integration_crib.tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.integration_crib.R;

public class TaskDemoActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integration_crib_activity_task_demo4);
        Button button = findViewById(R.id.integration_crib_task_btn_close);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDemoActivity4.this.finish();
                ///Intent i = new Intent(TaskDemoActivity.this, TaskDemoActivity1.class);
                //TaskDemoActivity2.this.startActivity(i);

            }
        });

        button = findViewById( R.id.integration_crib_task_btn_remove );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager manager = (ActivityManager)TaskDemoActivity4.this.getSystemService(Context.ACTIVITY_SERVICE);
                ActivityManager.AppTask task = manager.getAppTasks().get(0);
                task.finishAndRemoveTask();
            }
        });
        Toast.makeText(this, "onCreate() of " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tv = findViewById(R.id.integration_crib_task_tv4);
        tv.setText(TasksTabContentCreator.getTaskList(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy() of " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }
}
