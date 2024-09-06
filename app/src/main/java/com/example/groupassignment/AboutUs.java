/*
UCCD 3223 Mobile Applications Development
June 2024 Trimester

Chen Jin Shen	2202076
Chin Whye Ting	2200559
Ong Jing Yang	2200327
Tan Zong Ting	2302731
*/

package com.example.groupassignment;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);

        TextView linkTextView = findViewById(R.id.bryan);
        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL to open
                String url = "https://www.instagram.com/bryanc_1803/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        TextView linkTextView2 = findViewById(R.id.js);
        linkTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL to open
                String url = "https://www.instagram.com/jinshenn/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        TextView linkTextView3 = findViewById(R.id.jy);
        linkTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL to open
                String url = "https://www.instagram.com/jing_yang03/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        TextView linkTextView4 = findViewById(R.id.zt);
        linkTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL to open
                String url = "https://www.instagram.com/zongting23/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}