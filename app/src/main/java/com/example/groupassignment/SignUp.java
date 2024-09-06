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

public class SignUp extends AppCompatActivity {
    EditText username, password, retypePassword;
    Button register;
    TextView signIn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        retypePassword = findViewById(R.id.et_repassword);
        register = findViewById(R.id.bt_register);
        signIn = findViewById(R.id.textView_sign_in);
        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = retypePassword.getText().toString();

                if (user.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkUsername(user);
                        if (!checkuser) {
                            Boolean insert = DB.insertData(user, pass);
                            if (insert) {
                                // Save login state to SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("USERNAME", user); // Save the username
                                editor.apply();

                                Toast.makeText(SignUp.this, "Welcome to UTM", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish(); // Close SignUp activity
                            } else {
                                Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUp.this, "User Already Exists, Please Sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Password Does Not Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
