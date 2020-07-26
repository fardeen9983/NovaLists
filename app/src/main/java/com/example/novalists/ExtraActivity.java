package com.example.novalists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExtraActivity extends AppCompatActivity {

    TextView UserName;
    Button LogoutBtn;

    // General Variables
    String uid;

    // Classes
    User user;

    // Firebase Code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        UserName = findViewById(R.id.userName);
        LogoutBtn = findViewById(R.id.logoutbtn);

        currentState = firebaseAuth -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                uid = currentUser.getUid();
                myRef = database.getReference().child("Tech Nova/"+uid);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            user = snapshot.getValue(User.class);
                            assert user != null;
                        } else {
                            Toast.makeText(ExtraActivity.this,"User not Found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ExtraActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(ExtraActivity.this, "Please, Login..!!", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(ExtraActivity.this,MainActivity.class);
                startActivity(login);
            }
        };

        UserName.setText(user.getName());

        LogoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(ExtraActivity.this,"User Logout..!!",Toast.LENGTH_LONG).show();
            Intent logout = new Intent(ExtraActivity.this,MainActivity.class);
            startActivity(logout);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(currentState);
    }
}