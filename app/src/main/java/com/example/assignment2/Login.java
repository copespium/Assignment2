package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton;
    private EditText editTextUserName, editTextPassword;
    private ProgressBar progressBar2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextUserName = (EditText) findViewById(R.id.editUsername);
        editTextPassword = (EditText) findViewById(R.id.editPassword);

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        TextView mySignUp = (TextView) findViewById(R.id.textSignUp);
        mySignUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.loginButton:
                loginUser();
                break;
        }
    }

    private void loginUser(){
        String userName = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (userName.isEmpty()){
            editTextUserName.setError("user name is required!");
            editTextUserName.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }


        progressBar2.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(userName + "@abc.com", password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login.this, "Login is successful!", Toast.LENGTH_LONG).show();
                                Database.getInstance().loadCurrentUserAttempts();
                                startActivity(new Intent(Login.this, Menu.class));
                                progressBar2.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(Login.this, "Failed to login! Try again!", Toast.LENGTH_LONG).show();
                                progressBar2.setVisibility(View.GONE);
                            }
                    }
                });

    }
}