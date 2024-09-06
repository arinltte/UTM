/*
UCCD 3223 Mobile Applications Development
June 2024 Trimester

Chen Jin Shen	2202076
Chin Whye Ting	2200559
Ong Jing Yang	2200327
Tan Zong Ting	2302731
*/

package com.example.groupassignment;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DisplayFeedbackActivity extends AppCompatActivity {
    private ListView lvFeedback;
    private DBHelper dbHelper;
    private CustomAdapter adapter;
    private ArrayList<String> feedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_feedback);

        lvFeedback = findViewById(R.id.lvFeedback);
        dbHelper = new DBHelper(this);
        feedbackList = new ArrayList<>();

        loadFeedback();
    }

    // To Display the Submitted Feedback
    private void loadFeedback() {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM feedback_table", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String feedback = cursor.getString(cursor.getColumnIndex("feedback"));
                feedbackList.add("Name: " + name + "\nFeedback: " + feedback + "\n");
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feedbackList);
        lvFeedback.setAdapter(adapter);
    }
}
