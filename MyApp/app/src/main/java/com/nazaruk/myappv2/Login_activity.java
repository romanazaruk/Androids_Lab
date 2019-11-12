package com.nazaruk.myappv2;

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

public class Login_activity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button buttonToSingIN;
    private TextView moveToSingUP;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPass);
        buttonToSingIN = findViewById(R.id.singInButton);
        moveToSingUP = findViewById(R.id.moveToSingUpActivity);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = Login_activity.this.firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(Login_activity.this,
                            R.string.loginSucceful, LENGTH_SHORT).show();
                    Intent i = new Intent(Login_activity.this,
                            FilmListActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Login_activity.this,
                            R.string.pleaseLogin, LENGTH_SHORT).show();
                }
            }
        };
        buttonToSingIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonToLogin();
            }
        });
        moveToSingUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login_activity.this, Registration_activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    private void buttonToLogin() {
        String em = email.getText().toString();
        String ps = password.getText().toString();
        if (em.isEmpty()) {
            email.setError("Please enter email!");
            email.requestFocus();
        } else if (ps.isEmpty()) {
            password.setError("Please enter password!");
            password.requestFocus();
        } else if (ps.isEmpty() && em.isEmpty()) {
            Toast.makeText(Login_activity.this, R.string.emptyFields,
                    LENGTH_SHORT).show();
        } else if (!(ps.isEmpty() && em.isEmpty())) {
            login(em, ps);
        }
    }

    private void login(String em, String ps) {
        firebaseAuth.signInWithEmailAndPassword(em, ps)
                .addOnCompleteListener(Login_activity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Login_activity.this,
                                            R.string.incorrectData,
                                            LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(Login_activity.this,
                                            FilmListActivity.class));
                                }

                            }
                        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Login_activity.this, Registration_activity.class);
        startActivity(i);
    }
}

