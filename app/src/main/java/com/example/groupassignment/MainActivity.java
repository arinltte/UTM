/*
UCCD 3223 Mobile Applications Development
June 2024 Trimester

Chen Jin Shen	2202076
Chin Whye Ting	2200559
Ong Jing Yang	2200327
Tan Zong Ting	2302731
*/

package com.example.groupassignment;

import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Each Arrow to Each Day

        findViewById(R.id.mondayArrow).setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewTimetableActivity.class);
            intent.putExtra("DAY", "Monday");
            startActivity(intent);
        });

        findViewById(R.id.tuesdayArrow).setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewTimetableActivity.class);
            intent.putExtra("DAY", "Tuesday");
            startActivity(intent);
        });

        findViewById(R.id.wednesdayArrow).setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewTimetableActivity.class);
            intent.putExtra("DAY", "Wednesday");
            startActivity(intent);
        });

        findViewById(R.id.thursdayArrow).setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewTimetableActivity.class);
            intent.putExtra("DAY", "Thursday");
            startActivity(intent);
        });

        findViewById(R.id.fridayArrow).setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewTimetableActivity.class);
            intent.putExtra("DAY", "Friday");
            startActivity(intent);
        });

        // To the setting section
        findViewById(R.id.setting).setOnClickListener(v -> {
            startActivity(new Intent(this, setting.class));
        });

        // To the create class section
        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(v -> {
            Intent intent6 = new Intent(this, AddTimetable.class);
            startActivity(intent6);
        });
    }

    private void openViewTimetableActivity(String day) {
        Intent intent = new Intent(this, ViewTimetableActivity.class);
        intent.putExtra("DAY", day);
        startActivity(intent);
    }
}
