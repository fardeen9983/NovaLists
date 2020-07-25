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

    // General Variables
    EditText UserEmail, Password;
    Button LoginBtn;
    // Firebase Code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connection with Front End Values
        UserEmail = findViewById(R.id.userEmail);
        Password = findViewById(R.id.password);
        LoginBtn = findViewById(R.id.loginbtn);

        // Firebase Code
        mAuth = FirebaseAuth.getInstance();

//    // Check if user is signed in (non-null) and update UI accordingly.
//    FirebaseUser currentUser = mAuth.getCurrentUser();

        currentState = firebaseAuth -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                String uid = currentUser.getUid();
                Toast.makeText(MainActivity.this, "User ID: " + uid, Toast.LENGTH_SHORT).show();
                Intent extra = new Intent(MainActivity.this,ExtraActivity.class);
                extra.putExtra("User ID",uid);
                startActivity(extra);
            } else {
                Toast.makeText(MainActivity.this, "Please, Login..!!", Toast.LENGTH_SHORT).show();
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            String uid = user.getUid();
                            Toast.makeText(MainActivity.this, "User ID: " + uid, Toast.LENGTH_SHORT).show();
                            Intent extra = new Intent(MainActivity.this,ExtraActivity.class);
                            extra.putExtra("User ID",uid);
                            startActivity(extra);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed..!!", Toast.LENGTH_SHORT).show();
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