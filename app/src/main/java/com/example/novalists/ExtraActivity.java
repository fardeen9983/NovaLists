package com.example.novalists;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseAuth.AuthStateListener currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        UserName = findViewById(R.id.userName);
        LogoutBtn = findViewById(R.id.logoutbtn);

//        currentState = firebaseAuth -> {
//            if(currentUser != null){
//                uid = currentUser.getUid();
//                myRef = database.getReference().child("Tech Nova/"+uid);
//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()) {
//                            user = snapshot.getValue(User.class);
//                            assert user != null;
//                            Toast.makeText(ExtraActivity.this,"Welcome, " + user.getName(), Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(ExtraActivity.this,"User not Found", Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(ExtraActivity.this, "Error", Toast.LENGTH_LONG).show();
//                    }
//                });
//            } else {
//                Toast.makeText(ExtraActivity.this, "Please, Login..!!", Toast.LENGTH_SHORT).show();
//                Intent login = new Intent(ExtraActivity.this,MainActivity.class);
//                startActivity(login);
//            }
//        };

        uid = getIntent().getStringExtra("UserID");
        if (uid != null) {
            myRef = database.getReference().child("Tech Nova/" + uid);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        user = snapshot.getValue(User.class);
                        assert user != null;
                        Toast.makeText(ExtraActivity.this, "Welcome, " + user.getName(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ExtraActivity.this, "User not Found", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ExtraActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(ExtraActivity.this, "Please, Login..!!", Toast.LENGTH_SHORT).show();
            Intent main = new Intent(ExtraActivity.this, MainActivity.class);
            startActivity(main);
        }

        // Working Option but doesn't getting values
//        try {
//            UserName.setText(user.getName());
//        } catch (NullPointerException ignored){
//
//        }
        if (user != null)
            UserName.setText(user.getName());

        LogoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(ExtraActivity.this, "User Logout..!!", Toast.LENGTH_LONG).show();
            Intent logout = new Intent(ExtraActivity.this, MainActivity.class);
            startActivity(logout);
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
////        mAuth.addAuthStateListener(currentState);
//    }
}