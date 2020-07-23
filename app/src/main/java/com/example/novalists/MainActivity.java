package com.example.novalists;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // General Variables
    EditText UserID, Password;
    Button LoginBtn;
    // Firebase Code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        FirebaseApp.getInstance();
        // Connection with Front End Values
        UserID = findViewById(R.id.userID);
        Password = findViewById(R.id.password);
        LoginBtn = findViewById(R.id.loginbtn);

        // User Input Values to the variables
        final String userId = UserID.getText().toString().trim();
        final String password = Password.getText().toString().trim();

        // Connection to the Database
        myRef = database.getReference().child("Tech Nova/"+ userId);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(), "Value: " + value, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }
}