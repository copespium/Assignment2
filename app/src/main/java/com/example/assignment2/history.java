package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class history extends AppCompatActivity implements View.OnClickListener{
    private Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        myButton = (Button) findViewById(R.id.backButtonHis);
        myButton.setOnClickListener(this);

        myButton = (Button) findViewById(R.id.quizButtonHis);
        myButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButtonHis:
                //Toast.makeText(history.this, "numeracyBackButton button pressed", Toast.LENGTH_LONG).show();
                //System.out.println("numeracyButton button pressed");
                Intent intent = new Intent(history.this, Menu.class);
                startActivity(intent);
                break;
            case R.id.quizButtonHis:
                //Toast.makeText(numeracy.this, "numeracyBackButton button pressed", Toast.LENGTH_LONG).show();
                //System.out.println("numeracyButton button pressed");
                Intent intent1 = new Intent(history.this, historyQuiz.class);
                startActivity(intent1);
                break;
        }
    }
}