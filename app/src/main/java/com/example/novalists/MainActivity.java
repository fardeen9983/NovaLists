package com.example.novalists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // General Variables
    Button LoginBtn, RegisterBtn;
    String uid;

    // Firebase Code
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    FirebaseAuth.AuthStateListener currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        LoginBtn = findViewById(R.id.loginbtn);
        RegisterBtn = findViewById(R.id.registerbtn);

//    // Check if user is signed in (non-null) and update UI accordingly.
//    FirebaseUser currentUser = mAuth.getCurrentUser();
        currentState = (FirebaseAuth firebaseAuth) -> {
            if (currentUser != null) {
                uid = currentUser.getUid();
//                Toast.makeText(MainActivity.this, "Welcome Back..!!", Toast.LENGTH_SHORT).show();
                Intent extra = new Intent(MainActivity.this, ExtraActivity.class);
                extra.putExtra("UserID", uid);
                startActivity(extra);
            } else {
                Toast.makeText(MainActivity.this, "Welcome..!!", Toast.LENGTH_SHORT).show();
            }
        };

        LoginBtn.setOnClickListener((View view) -> {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
        });

        RegisterBtn.setOnClickListener((View view) -> {
            Intent register = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(register);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(currentState);
    }


}