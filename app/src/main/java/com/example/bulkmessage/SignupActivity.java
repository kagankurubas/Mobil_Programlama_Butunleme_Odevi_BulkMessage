package com.example.bulkmessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button signupButton, rdloginButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEditText = findViewById(R.id.signup_emailEditText);
        passwordEditText = findViewById(R.id.signup_passwordEditText);
        signupButton = findViewById(R.id.signup_signupButton);
        rdloginButton = findViewById(R.id.signup_rdloginButton);

        mAuth = FirebaseAuth.getInstance();

        rdloginButton.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
        signupButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Lütfen Tüm Alanları Doldurunuz.", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    Toast.makeText(SignupActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}