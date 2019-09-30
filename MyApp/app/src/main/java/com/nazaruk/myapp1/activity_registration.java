package com.nazaruk.myapp1;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class activity_registration extends AppCompatActivity {

    public EditText name;
    public EditText phone;
    public EditText email;
    public EditText pass;
    public Button btn;
    public TextView tv;
    public FirebaseAuth fAuth;

    static final String emPatt = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    static final String psPatt = ".{8,}";
    static final String pnPatt = "[+]380[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btn = findViewById(R.id.button2);
        tv = findViewById(R.id.textView2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        userID = databaseReference.push().getKey();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonToSingUp();
            }
        });


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_registration.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void addUser(String nm, final String pn, final String em, final String ps) {
        final User user = new User(em, nm, pn);
        databaseReference.child(userID).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            createUser(em, ps);
                        } else {
                            Toast.makeText(activity_registration.this, R.string.somethingGoneWrong,
                                    LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

    private void createUser(String em, String ps) {
        fAuth.createUserWithEmailAndPassword(em, ps)
                .addOnCompleteListener(activity_registration.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(activity_registration.this,
                                            R.string.emailAlreeadyExist,
                                            LENGTH_LONG).show();
                                } else {
                                    startActivity(new Intent(activity_registration.this,
                                            activity_welcome.class));
                                }
                            }
                        });
    }

    private void buttonToSingUp() {
        final String nm = name.getText().toString();
        final String pn = phone.getText().toString();
        final String em = email.getText().toString();
        final String ps = pass.getText().toString();

        if (nm.isEmpty()) {
            name.setError("Please enter name!");
            name.requestFocus();
        } else if (pn.isEmpty()) {
            phone.setError("Please enter phone!");
            phone.requestFocus();
        } else if (em.isEmpty()) {
            email.setError("Please enter your email!");
            email.requestFocus();
        } else if (ps.isEmpty()) {
            pass.setError("Please enter password!");
            pass.requestFocus();
        } else if (!pn.matches(pnPatt)) {
            Toast.makeText(activity_registration.this, R.string.correctPhone,
                    LENGTH_SHORT).show();
        } else if (!em.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            Toast.makeText(activity_registration.this, R.string.invalidEmail,
                    LENGTH_SHORT).show();
        } else if (!ps.matches(psPatt)) {
            Toast.makeText(activity_registration.this, R.string.correctPassword,
                    LENGTH_SHORT).show();
        } else if (!(em.isEmpty() && ps.isEmpty())) {
            addUser(nm, pn, em, ps);
        } else {
            Toast.makeText(activity_registration.this, R.string.somethingGoneWrong,
                    LENGTH_SHORT).show();
        }
    }

}