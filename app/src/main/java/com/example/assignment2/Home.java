package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener{

    private Button signUpButton;
    private EditText editTextUserName, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        editTextUserName = (EditText) findViewById(R.id.editUsername);
        editTextEmail = (EditText) findViewById(R.id.editEmail);
        editTextPassword = (EditText) findViewById(R.id.editPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        TextView myLogin = (TextView) findViewById(R.id.textLogin);
        myLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View v){
        System.out.println("@on click");
        switch (v.getId()){
            case R.id.signUpButton:
                System.out.println("@Sign up button pressed");
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String userName = editTextUserName.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

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

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(userName + "@abc.com", password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User (userName, email);

                            FirebaseDatabase.getInstance(" https://assignment2-fba50-default-rtdb.asia-southeast1.firebasedatabase.app")
                                    .getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){
                                        @Override
                                        public void onComplete (@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(Home.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(Home.this, Login.class));
                                            } else {
                                                Toast.makeText(Home.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(Home.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
}