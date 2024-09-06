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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class setting extends AppCompatActivity {
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        usernameTextView = findViewById(R.id.usernameTextView);
        Button createButton = findViewById(R.id.btn_create);
        Button logoutButton = findViewById(R.id.btn_logout);
        Button feedbackButton = findViewById(R.id.btn_feedback);
        Button aboutButton = findViewById(R.id.btn_about);

        // Get the username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "Guest");

        // Set the username to the Title
        usernameTextView.setText("Hi, " + username);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear SharedPreferences to log out the user
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Clear all saved data
                editor.apply();

                // Redirect to Home activity
                Intent intent = new Intent(setting.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Close the SettingsActivity
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to Home activity
                Intent intent = new Intent(setting.this, AddTimetable.class);
                startActivity(intent);
            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to Home activity
                Intent intent = new Intent(setting.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to Home activity
                Intent intent = new Intent(setting.this, AboutUs.class);
                startActivity(intent);
            }
        });
    }
}
