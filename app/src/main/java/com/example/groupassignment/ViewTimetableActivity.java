/*
UCCD 3223 Mobile Applications Development
June 2024 Trimester

Chen Jin Shen	2202076
Chin Whye Ting	2200559
Ong Jing Yang	2200327
Tan Zong Ting	2302731
*/

package com.example.groupassignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class ViewTimetableActivity extends AppCompatActivity {

    private ListView timetableTextView;
    private TextView dayTextView;
    private DBHelper dbHelper;
    private CustomAdapter adapter;
    private ArrayList<String> timetableList;
    private ArrayList<Integer> timetableIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_timetable);

        timetableTextView = findViewById(R.id.timetableTextView);
        dayTextView = findViewById(R.id.dayTextView);
        dbHelper = new DBHelper(this);

        // Get username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", null);

        // Get day from Intent extras
        String day = getIntent().getStringExtra("DAY");

        // Set day in TextView
        if (day != null) {
            dayTextView.setText(day);
        } else {
            dayTextView.setText("Error 404");
        }

        // Set the urbanist font
        Typeface typeface = ResourcesCompat.getFont(this, R.font.urbanist);
        float textSize = 18;

        timetableList = new ArrayList<>();
        timetableIds = new ArrayList<>();
        adapter = new CustomAdapter(this, timetableList, getResources().getColor(R.color.black), textSize, typeface);
        timetableTextView.setAdapter(adapter);

        // Display timetable data
        displayTimetable(username, day);

        // Set a long-click listener to delete class
        timetableTextView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmDelete(position);
                return true;
            }
        });
    }

    private void displayTimetable(String username, String day) {
        int textColor = getResources().getColor(R.color.black);

        // Set to Different Background based on Day
        switch (day.toLowerCase()) {
            case "monday":
                timetableTextView.setBackgroundResource(R.drawable.rounded_corner_monday2);
                break;
            case "tuesday":
                timetableTextView.setBackgroundResource(R.drawable.rounded_corner_tuesday2);
                break;
            case "wednesday":
                textColor = getResources().getColor(R.color.white);
                timetableTextView.setBackgroundResource(R.drawable.rounded_corner_wednesday2);
                break;
            case "thursday":
                textColor = getResources().getColor(R.color.white);
                timetableTextView.setBackgroundResource(R.drawable.rounded_corner_thursday2);
                break;
            case "friday":
                textColor = getResources().getColor(R.color.white);
                timetableTextView.setBackgroundResource(R.drawable.rounded_corner_friday);
                break;
        }

        // Update adapter with the new text color
        adapter = new CustomAdapter(this, timetableList, textColor, 18, ResourcesCompat.getFont(this, R.font.urbanist));
        timetableTextView.setAdapter(adapter);

        // Display timetable data
        Cursor cursor = dbHelper.getUserTimetable(username, day);
        timetableList.clear();
        timetableIds.clear();

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex("id");
            int timeIndex = cursor.getColumnIndex("time");
            int activityIndex = cursor.getColumnIndex("activity");
            int remarksIndex = cursor.getColumnIndex("remarks");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIndex);
                String activity = cursor.getString(activityIndex);
                String time = cursor.getString(timeIndex);
                String remarks = cursor.getString(remarksIndex);

                StringBuilder entry = new StringBuilder();
                entry.append(activity).append("\n");
                entry.append(time).append("\n");
                entry.append("Remarks: ").append(remarks);

                timetableList.add(entry.toString());
                timetableIds.add(id);
            }

            cursor.close();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No timetable data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDelete(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Class")
                .setMessage("Are You Sure to Delete this Class?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTimetableEntry(position);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteTimetableEntry(int position) {
        int id = timetableIds.get(position);
        boolean deleted = dbHelper.deleteTimetableEntry(id);

        if (deleted) {
            Toast.makeText(this, "Class Deleted", Toast.LENGTH_SHORT).show();
            timetableList.remove(position);
            timetableIds.remove(position);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Failed to delete entry", Toast.LENGTH_SHORT).show();
        }
    }
}
