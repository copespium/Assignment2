package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ImageButton ib;

    private Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ib = (ImageButton) findViewById(R.id.numeracyButton);
        ib.setOnClickListener(this);

        ib = (ImageButton) findViewById(R.id.historyButton);
        ib.setOnClickListener(this);

        myButton = (Button) findViewById(R.id.profilebutton);
        myButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.numeracyButton:
                //Toast.makeText(Menu.this, "numeracyButton button pressed", Toast.LENGTH_LONG).show();
                //System.out.println("numeracyButton button pressed");
                Intent intent = new Intent(Menu.this, numeracy.class);
                startActivity(intent);
                break;

            case R.id.historyButton:
                //Toast.makeText(Menu.this, "historyButton button pressed", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(Menu.this, history.class);
                startActivity(intent1);
                break;
            case R.id.profilebutton:
                //Toast.makeText(Menu.this, "historyButton button pressed", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(Menu.this, Profile.class);
                startActivity(intent2);
                break;
        }
    }


    //TODO
    private void changePassword(){
        mAuth = FirebaseAuth.getInstance();

        System.out.println("logged in as: " + mAuth.getCurrentUser().getUid() + ", " + mAuth.getCurrentUser().getEmail());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");

        String newPass = "1234";

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
                                        Toast.makeText(Menu.this, "Password updated!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Menu.this, "Error password not updated", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Menu.this, "Error auth failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}