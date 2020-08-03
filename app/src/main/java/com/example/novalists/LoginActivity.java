package com.example.novalists;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // General Variables
    EditText UserEmail, Password;
    Button LoginBtn;
    String uid;
    

    // Firebase Code
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseAuth.AuthStateListener currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Connection with Front End Values
        UserEmail = findViewById(R.id.userEmail);
        Password = findViewById(R.id.password);
        LoginBtn = findViewById(R.id.loginbtn);

//    // Check if user is signed in (non-null) and update UI accordingly.
//    FirebaseUser currentUser = mAuth.getCurrentUser();

        currentState = (FirebaseAuth firebaseAuth) -> {
            if(currentUser != null){
                uid = currentUser.getUid();
                Intent extra = new Intent(LoginActivity.this, ExtraActivity.class);
                extra.putExtra("UserID",uid);
                startActivity(extra);
            } else {
                Toast.makeText(LoginActivity.this, "Please, Login..!!", Toast.LENGTH_SHORT).show();
            }
        };

        LoginBtn.setOnClickListener((View view) -> {
            // User Input Values to the variables
            final String userEmail = UserEmail.getText().toString().trim();
            final String password = Password.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener(this, (Task<AuthResult> task) -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            currentUser= mAuth.getCurrentUser();
                            assert currentUser != null;
                            uid = currentUser.getUid();
                            Intent extra = new Intent(LoginActivity.this, ExtraActivity.class);
                            extra.putExtra("UserID",uid);
                            startActivity(extra);
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
//        mAuth.addAuthStateListener(currentState);
    }
}