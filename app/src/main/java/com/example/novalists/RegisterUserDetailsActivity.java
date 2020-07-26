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

public class RegisterUserDetailsActivity extends AppCompatActivity {

    // General Variables
    EditText UserName, UserEmail, UserContact;
    Button AddBtn;
    private Integer personalList = 0;
    private Integer sentList = 0;
    private Integer receivedList = 0;

    // Classes
    User user;

    // Firebase Code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener currentState;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_details);

        // Connection with Front End Values
        UserName = findViewById(R.id.userName);
        UserEmail = findViewById(R.id.userEmail);
        UserContact = findViewById(R.id.userContact);
        AddBtn = findViewById(R.id.addbtn);

        // Firebase Code
        mAuth = FirebaseAuth.getInstance();

//    // Check if user is signed in (non-null) and update UI accordingly.
//    FirebaseUser currentUser = mAuth.getCurrentUser();

        currentState = firebaseAuth -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                uid = currentUser.getUid();
//                Toast.makeText(RegisterActivity.this, "User Already Logged In..!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterUserDetailsActivity.this, "Please, Register..!!", Toast.LENGTH_SHORT).show();
                Intent register = new Intent(RegisterUserDetailsActivity.this,MainActivity.class);
                startActivity(register);
            }
        };

        AddBtn.setOnClickListener(view -> {
            final String name = UserName.getText().toString().trim();
            final String email = UserEmail.getText().toString().trim();
            final String contact = UserContact.getText().toString().trim();

            user = new User(name,email,contact,personalList,sentList,receivedList,uid);

            myRef = database.getReference().child("Tech Nova/"+uid);
            myRef.setValue(user);

            Toast.makeText(RegisterUserDetailsActivity.this,"User Added..!!",Toast.LENGTH_LONG).show();
            Intent extra = new Intent(RegisterUserDetailsActivity.this,ExtraActivity.class);
            startActivity(extra);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(currentState);
    }
}