/*
UCCD 3223 Mobile Applications Development
June 2024 Trimester

Chen Jin Shen	2202076
Chin Whye Ting	2200559
Ong Jing Yang	2200327
Tan Zong Ting	2302731
*/

package com.example.groupassignment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {
    private EditText etName, etFeedback;
    private Button btnSubmitFeedback;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback);

        etName = findViewById(R.id.etName);
        etFeedback = findViewById(R.id.etFeedback);
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        dbHelper = new DBHelper(this);

        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String feedback = etFeedback.getText().toString().trim();

                if (!name.isEmpty() && !feedback.isEmpty()) {
                    saveFeedback(name, feedback);

                    // Clear the Field After Feedback is Submitted
                    etName.setText("");
                    etFeedback.setText("");
                } else {
                    Toast.makeText(FeedbackActivity.this, "Please Fill in All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveFeedback(String name, String feedback) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("feedback", feedback);
        long result = db.insert("feedback_table", null, contentValues);

        if (result != -1) {
            Toast.makeText(this, "Thanks for Your Feedback", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Submit Feedback", Toast.LENGTH_SHORT).show();
        }
    }
}
