package com.nazaruk.myapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_welcome extends AppCompatActivity {

    public TextView tv;
    public Button btn;
    public FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private long backPressedTime;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv = findViewById(R.id.welcome);
        btn = findViewById(R.id.logout);
        fAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        FirebaseUser fUser = fAuth.getCurrentUser();

        readData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(activity_welcome.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(activity_welcome.this, "Logout!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void readData() {
        FirebaseUser fUser = fAuth.getCurrentUser();
        databaseReference.orderByChild("email").equalTo(fUser.getEmail()).addChildEventListener(new ChildEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                User user = dataSnapshot.getValue(User.class);
                tv.setText(getString(R.string.welcome) + user.getUsername());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        final int time = 2000;
        if (backPressedTime + time > System.currentTimeMillis()) {
            fAuth.getInstance().signOut();
            finishAffinity();
            finish();
        } else {
            Toast.makeText(getBaseContext(), R.string.pressToExit, Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();

    }

}

