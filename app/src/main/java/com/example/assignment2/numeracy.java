package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class numeracy extends AppCompatActivity implements View.OnClickListener{
    private Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeracy);

        myButton = (Button) findViewById(R.id.backButtonNum);
        myButton.setOnClickListener(this);

        myButton = (Button) findViewById(R.id.quizButtonNum);
        myButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButtonNum:
                //Toast.makeText(numeracy.this, "numeracyBackButton button pressed", Toast.LENGTH_LONG).show();
                //System.out.println("numeracyButton button pressed");
                Intent intent = new Intent(numeracy.this, Menu.class);
                startActivity(intent);
                break;

            case R.id.quizButtonNum:
                //Toast.makeText(numeracy.this, "numeracyBackButton button pressed", Toast.LENGTH_LONG).show();
                //System.out.println("numeracyButton button pressed");
                Intent intent1 = new Intent(numeracy.this, numeracyQuiz.class);
                startActivity(intent1);
                break;
        }
    }
}