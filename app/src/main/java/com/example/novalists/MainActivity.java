package com.example.novalists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    Button LoginBtn, RegisterBtn;
    // Firebase Code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginBtn = findViewById(R.id.loginbtn);
        RegisterBtn = findViewById(R.id.registerbtn);

        // Firebase Code
        mAuth = FirebaseAuth.getInstance();

//    // Check if user is signed in (non-null) and update UI accordingly.
//    FirebaseUser currentUser = mAuth.getCurrentUser();

        currentState = firebaseAuth -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                String uid = currentUser.getUid();
                Toast.makeText(MainActivity.this, "User Already Logged In..!!", Toast.LENGTH_SHORT).show();
                Intent extra = new Intent(MainActivity.this,ExtraActivity.class);
                extra.putExtra("User ID",uid);
                startActivity(extra);
            } else {
                Toast.makeText(MainActivity.this, "Welcome..!!", Toast.LENGTH_SHORT).show();
            }
        };

        LoginBtn.setOnClickListener(view -> {
            Intent login = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(login);
        });

        RegisterBtn.setOnClickListener(view -> {
            Intent register = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(register);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(currentState);
    }
}