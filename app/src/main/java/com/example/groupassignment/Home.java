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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private int clickCount = 0;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // User is already logged in, start MainActivity
            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close Home activity
        } else {
            // User is not logged in, proceed to Home
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_home);

            Button button1 = findViewById(R.id.bt_login);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Home.this, Login.class);
                    startActivity(intent);
                }
            });

            Button button2 = findViewById(R.id.bt_signUp);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Home.this, SignUp.class);
                    startActivity(intent);
                }
            });

            //Easter Egg
            TextView textView6 = findViewById(R.id.textView6);
            imageView = findViewById(R.id.imageView);
            textView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickCount++;
                    if (clickCount >= 5) {
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}
