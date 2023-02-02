package com.example.bulkmessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class KayitVeGirisAktivitesi extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ve_giris_aktivitesi);

        findViewById(R.id.kayitvegiris_signup).setOnClickListener(v -> {
            startActivity(new Intent(KayitVeGirisAktivitesi.this, SignupActivity.class));
        });
        findViewById(R.id.kayitvegiris_login).setOnClickListener(v -> {
            startActivity(new Intent(KayitVeGirisAktivitesi.this, LoginActivity.class));
        });

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(KayitVeGirisAktivitesi.this, MainActivity.class));
            Toast.makeText(KayitVeGirisAktivitesi.this, "Yönlendiriliyor", Toast.LENGTH_SHORT).show();
        }
    }
}