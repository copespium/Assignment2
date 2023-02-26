package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Password extends AppCompatActivity implements View.OnClickListener{
    private Button changePasswordButton;
    private TextView myTextV, oldPwTextV, newPwTextV;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        mAuth = FirebaseAuth.getInstance();

        myTextV = (TextView) findViewById(R.id.mybackButton);
        myTextV.setOnClickListener(this);

        oldPwTextV = (TextView) findViewById(R.id.oldPw);
        newPwTextV = (TextView) findViewById(R.id.newPw);

        changePasswordButton = (Button) findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mybackButton:
                Intent intent = new Intent(Password.this, Menu.class);
                startActivity(intent);
                break;
        }
        switch (view.getId()) {
            case R.id.changePasswordButton:
                changePassword();
                break;
        }


    }


    //TODO
    private void changePassword () {
        mAuth = FirebaseAuth.getInstance();

        System.out.println("logged in as: " + mAuth.getCurrentUser().getUid() + ", " + mAuth.getCurrentUser().getEmail());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), oldPwTextV.getText().toString());

        String newPass = newPwTextV.getText().toString();

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Password.this, "Password updated!", Toast.LENGTH_LONG).show();
                                        System.out.println("Password updated!" + mAuth.getCurrentUser().getEmail());
                                    } else {
                                        Toast.makeText(Password.this, "Error password not updated", Toast.LENGTH_LONG).show();
                                        System.out.println("Error password not updated" + mAuth.getCurrentUser().getEmail());
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Password.this, "Error auth failed", Toast.LENGTH_LONG).show();
                            System.out.println("Error auth failed" + mAuth.getCurrentUser().getEmail());
                        }
                    }
                });
    }
}