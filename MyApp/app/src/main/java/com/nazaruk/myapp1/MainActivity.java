package com.nazaruk.myapp1;



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
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    public EditText email;
    public EditText pass;
    public Button btnIn;
    public TextView tvUp;
    public FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.loginEmail);
        pass = findViewById(R.id.loginPass);
        btnIn = findViewById(R.id.singInButton);
        tvUp = findViewById(R.id.moveToSingUpActivity);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = fAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(MainActivity.this,
                            R.string.loginSucceful, LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, activity_welcome.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this,
                            R.string.pleaseLogin, LENGTH_SHORT).show();
                }
            }
        };

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonToLogin();
            }
        });

        tvUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, activity_registration.class);
                startActivity(i);
            }
        });


    }

    private void buttonToLogin() {
        String em = email.getText().toString();
        String ps = pass.getText().toString();
        if (em.isEmpty()) {
            email.setError("Please enter email!");
            email.requestFocus();
        } else if (ps.isEmpty()) {
            pass.setError("Please enter password!");
            pass.requestFocus();
        } else if (ps.isEmpty() && em.isEmpty()) {
            Toast.makeText(MainActivity.this, R.string.emptyFields,
                    LENGTH_SHORT).show();
        } else if (!(ps.isEmpty() && em.isEmpty())) {
            login(em, ps);
        }
    }

    private void login(String em, String ps) {
        fAuth.signInWithEmailAndPassword(em, ps).addOnCompleteListener(MainActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    R.string.incorrectData,
                                    LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(MainActivity.this,
                                    activity_welcome.class));
                        }

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MainActivity.this, activity_registration.class);
        startActivity(i);
    }
}