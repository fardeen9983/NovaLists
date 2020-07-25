package com.example.novalists;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExtraActivity extends AppCompatActivity {

    TextView UserID;
    Button LogoutBtn;

    // Firebase Code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        LogoutBtn = findViewById(R.id.logoutbtn);
        UserID = findViewById(R.id.userId);

        UserID.setText(getIntent().getStringExtra("User ID"));

        LogoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(ExtraActivity.this,"User Logout..!!",Toast.LENGTH_LONG).show();
            Intent logout = new Intent(ExtraActivity.this,MainActivity.class);
            startActivity(logout);
        });
    }
}