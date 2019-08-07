package com.github.yard01.sandbox.crib.integration_crib.tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.integration_crib.R;

public class TaskDemoActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integration_crib_activity_task_demo3);
        Button button = findViewById(R.id.integration_crib_task_btn_open4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaskDemoActivity3.this, TaskDemoActivity4.class);
                TaskDemoActivity3.this.startActivity(i);

            }
        });
        Toast.makeText(this, "onCreate() of " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tv = findViewById(R.id.integration_crib_task_tv3);
        tv.setText(TasksTabContentCreator.getTaskList(this));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy() of " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }


}
