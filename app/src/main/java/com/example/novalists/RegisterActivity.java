package com.example.novalists;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    // General Variables
    EditText UserEmail, Password;
    Button RegisterBtn;

    // General Variables
    String uid;

    // Firebase Code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseAuth.AuthStateListener currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Connection with Front End Values
        UserEmail = findViewById(R.id.userEmail);
        Password = findViewById(R.id.password);
        RegisterBtn = findViewById(R.id.registerbtn);

//    // Check if user is signed in (non-null) and update UI accordingly.
//    FirebaseUser currentUser = mAuth.getCurrentUser();

        currentState = firebaseAuth -> {
            if(currentUser != null){
                uid = currentUser.getUid();
//                Toast.makeText(RegisterActivity.this, "User Already Logged In..!!", Toast.LENGTH_SHORT).show();
                Intent extra = new Intent(RegisterActivity.this,ExtraActivity.class);
                startActivity(extra);
            } else {
                Toast.makeText(RegisterActivity.this, "Please, Register..!!", Toast.LENGTH_SHORT).show();
            }
        };

        RegisterBtn.setOnClickListener(view -> {
            // User Input Values to the variables
            final String userEmail = UserEmail.getText().toString().trim();
            final String password = Password.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        uid = user.getUid();
//                        Toast.makeText(RegisterActivity.this, "User Created..!!", Toast.LENGTH_SHORT).show();
                        Intent userDetails = new Intent(RegisterActivity.this,RegisterUserDetailsActivity.class);
                        userDetails.putExtra("User Email",userEmail);
                        userDetails.putExtra("UserID",uid);
                        startActivity(userDetails);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterActivity.this, "Registration failed..!!", Toast.LENGTH_SHORT).show();
                    }
            });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mAuth.addAuthStateListener(currentState);
    }
}