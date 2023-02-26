package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.List;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ImageButton ib;

    private TextView usernameMenuTextView;

    private Button myButton;

    private TextView myTextView;
    private TextView myTextView1;
    private String usernameTextView;
    TextView totalPointsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        List<Attempt> attemptList = Database.getInstance().getAttemptList();

        //sum all previous attempt points
        int overallPoints = attemptList.stream()
                .map(x -> x.getPoint())
                .reduce(0, (a, b) -> a + b);

        System.out.println("@overall points: " + String.valueOf(overallPoints));


        usernameTextView = (Database.getInstance().getCurrentUser().getUserName());

        ib = (ImageButton) findViewById(R.id.numeracyButton);
        ib.setOnClickListener(this);

        ib = (ImageButton) findViewById(R.id.historyButton);
        ib.setOnClickListener(this);

        myButton = (Button) findViewById(R.id.profilebutton);
        myButton.setOnClickListener(this);

        myTextView = (TextView) findViewById(R.id.signOutButton);

        myTextView1 = (TextView) findViewById(R.id.changepwbutton);
        myTextView1.setOnClickListener(this);

        //int finalOverallPoints = overallPoints;
        myTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
                builder.setTitle(usernameTextView+ ", you have a total of " +String.valueOf(overallPoints)+" points. You will now be logged out.");

                // Set up the input
                //final EditText input = new EditText(Menu.this);
                // Specify the type of input expected
                //input.setInputType(InputType.TYPE_CLASS_NUMBER);
                //builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent3 = new Intent(Menu.this, Login.class);
                        startActivity(intent3);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        usernameMenuTextView = (TextView) findViewById(R.id.usernameMenu);
        usernameMenuTextView.setText(Database.getInstance().getCurrentUser().getUserName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.changepwbutton:
                Intent intent3 = new Intent(Menu.this, Password.class);
                startActivity(intent3);
                break;
        }
    }


        //TODO
        private void changePassword () {
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