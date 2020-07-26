package com.example.novalists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    // General Variables
    EditText UserEmail, Password;
    Button LoginBtn;
    String uid;

    // Classes
    User user;

    // Firebase Code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener currentState;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Connection with Front End Values
        UserEmail = findViewById(R.id.userEmail);
        Password = findViewById(R.id.password);
        LoginBtn = findViewById(R.id.loginbtn);

        // Firebase Code
        mAuth = FirebaseAuth.getInstance();

//    // Check if user is signed in (non-null) and update UI accordingly.
//    FirebaseUser currentUser = mAuth.getCurrentUser();

        currentState = firebaseAuth -> {
            currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                uid = currentUser.getUid();
                myRef = database.getReference().child("Tech Nova/"+uid);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            user = snapshot.getValue(User.class);
                            assert user != null;
//                                Toast.makeText(LoginActivity.this, "Welcome: " + user.getName(), Toast.LENGTH_LONG).show();
                                Intent extra = new Intent(LoginActivity.this, ExtraActivity.class);
                                startActivity(extra);
                        } else {
                            Toast.makeText(LoginActivity.this,"User not Found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Please, Login..!!", Toast.LENGTH_SHORT).show();
            }
        };

        LoginBtn.setOnClickListener(view -> {
            // User Input Values to the variables
            final String userEmail = UserEmail.getText().toString().trim();
            final String password = Password.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            currentUser= mAuth.getCurrentUser();
                            assert currentUser != null;
                            uid = currentUser.getUid();
                            myRef = database.getReference().child("Tech Nova/"+uid);
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()) {
                                        user = snapshot.getValue(User.class);
                                        assert user != null;
//                                Toast.makeText(LoginActivity.this, "Welcome: " + user.getName(), Toast.LENGTH_LONG).show();
                                        Intent extra = new Intent(LoginActivity.this, ExtraActivity.class);
                                        startActivity(extra);
                                    } else {
                                        Toast.makeText(LoginActivity.this,"User not Found", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed..!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(currentState);
    }
}