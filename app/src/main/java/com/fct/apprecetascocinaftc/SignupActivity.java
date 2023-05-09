package com.fct.apprecetascocinaftc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Create an instance of the Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Register data to the Firebase Realtime Database
        DatabaseReference usersRef = database.getReference("users");
        String userId = "abc123";
        usersRef.child(userId).child("name").setValue("John");
    }
}
