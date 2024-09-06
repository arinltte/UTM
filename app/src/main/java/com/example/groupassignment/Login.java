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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button btlogin;
    TextView signUp;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.et_username1);
        password = findViewById(R.id.et_password1);
        btlogin = findViewById(R.id.bt_login1);
        signUp = findViewById(R.id.textView_sign_up);
        DB = new DBHelper(this);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }  else {
                    // Check if the user is "admin"
                    if (user.equals("admin") && pass.equals("admin")) {
                        Intent intent = new Intent(getApplicationContext(), DisplayFeedbackActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                        if (checkuserpass) {
                            // Save login state to SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("USERNAME", user);
                            editor.apply();

                            Toast.makeText(Login.this, "Welcome to UTM", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Invalid Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}
