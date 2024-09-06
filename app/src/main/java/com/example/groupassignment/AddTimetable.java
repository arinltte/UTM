/*
UCCD 3223 Mobile Applications Development
June 2024 Trimester

Chen Jin Shen	2202076
Chin Whye Ting	2200559
Ong Jing Yang	2200327
Tan Zong Ting	2302731
*/

package com.example.groupassignment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddTimetable extends AppCompatActivity {
    private Spinner daySpinner;
    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private EditText courseNameEditText;
    private EditText remarksEditText;
    private final Calendar startTime = Calendar.getInstance();
    private final Calendar endTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_timetable   );

        startTimeTextView = findViewById(R.id.StartTime);
        endTimeTextView = findViewById(R.id.EndTime);
        daySpinner = findViewById(R.id.daySpinner);
        courseNameEditText = findViewById(R.id.courseid);
        remarksEditText = findViewById(R.id.Remarks);
        Button savebutton = findViewById(R.id.button);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTimetable(v);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
    }

    // Start Time Picker
    public void showStartTimePicker(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker timePicker, int hourOfDay, int minute) {
                        startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        startTime.set(Calendar.MINUTE, minute);
                        startTimeTextView.setText(String.format("Start Time: %02d:%02d", hourOfDay, minute));

                        // Validate End Time
                        if (endTime.getTimeInMillis() < startTime.getTimeInMillis()) {
                            showInvalidTimeToast();
                            endTimeTextView.setText("End Time: Not Set");
                        }
                    }
                },
                startTime.get(Calendar.HOUR_OF_DAY),
                startTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    // End Time Picker
    public void showEndTimePicker(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker timePicker, int hourOfDay, int minute) {
                        endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        endTime.set(Calendar.MINUTE, minute);
                        endTimeTextView.setText(String.format("End Time: %02d:%02d", hourOfDay, minute));

                        // Validate End Time Against Start Time
                        if (endTime.getTimeInMillis() < startTime.getTimeInMillis()) {
                            showInvalidTimeToast();
                            endTimeTextView.setText("End Time: Not Set");
                        }
                    }
                },
                endTime.get(Calendar.HOUR_OF_DAY),
                endTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    // Invalid Time Toast
    private void showInvalidTimeToast() {
        Toast.makeText(this, "End Time Cannot Be Earlier than Start Time", Toast.LENGTH_SHORT).show();
    }

    public void saveTimetable(View view) {
        String day = daySpinner.getSelectedItem().toString();
        String start = startTimeTextView.getText().toString().replace("Start Time: ", "").trim();
        String end = endTimeTextView.getText().toString().replace("End Time: ", "").trim();
        String activity = courseNameEditText.getText().toString().trim();
        String remarks = remarksEditText.getText().toString().trim();

        if (activity.isEmpty()) {
            Toast.makeText(this, "Please Enter the Course Name", Toast.LENGTH_SHORT).show();
            return; // Exit the method if course name is empty
        }

        if (start.equals("Not Set") || end.equals("Not Set")) {
            Toast.makeText(this, "Please Enter Start and End Times.", Toast.LENGTH_SHORT).show();
        } else {
            String formattedTime = end.equals("Not Set") ? start : start + " - " + end;

            // Retrieve the username from SharedPreferences
            String user = getUsername();

            if (user == null) {
                Toast.makeText(this, "User not found. Please log in again.", Toast.LENGTH_SHORT).show();
                return;
            }

            DBHelper dbHelper = new DBHelper(this);
            boolean isInserted = dbHelper.insertTimetableData(user, day, formattedTime, activity, remarks);

            if (isInserted) {
                Toast.makeText(this, "Class Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ViewTimetableActivity.class);
                intent.putExtra("USERNAME", user);
                intent.putExtra("DAY", day);
                startActivity(intent);

                // Clear the form fields
                startTimeTextView.setText("");
                endTimeTextView.setText("");
                courseNameEditText.setText("");
                remarksEditText.setText("");
                daySpinner.setSelection(0); // Reset to the first item in the spinner
            } else {
                Toast.makeText(this, "Failed to save timetable.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("USERNAME", null);
    }
};