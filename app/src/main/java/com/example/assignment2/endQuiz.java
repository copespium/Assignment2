package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class endQuiz extends AppCompatActivity implements View.OnClickListener {

    TextView usernameQuizTextView;

    TextView correctQuizTextView;

    TextView incorrectQuizTextView;

    TextView pointsTextView;
    TextView totalPointsTextView;
    private Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_quiz);

        myButton = (Button) findViewById(R.id.buttonConfirm);
        myButton.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        int numberOfCorrect = 0;
        int numberOfWrong = 0;
        int pointsScored = 0;
        int overallPoints = 0;
        if (extras != null) {
            numberOfCorrect = extras.getInt("numberOfCorrect");
            numberOfWrong = extras.getInt("numberOfWrong");
            pointsScored = extras.getInt("pointsScored");
            overallPoints = extras.getInt("overallPoints");

            System.out.println("Number of correct: " + numberOfCorrect);
            System.out.println("Number of wrong: " + numberOfWrong);
            System.out.println("points scored on this quiz: " + pointsScored);
            System.out.println("overall points: " + overallPoints);
        }

        usernameQuizTextView = (TextView) findViewById(R.id.usernameQuiz);
        usernameQuizTextView.setText(Database.getInstance().getCurrentUser().getUserName());

        correctQuizTextView = (TextView) findViewById(R.id.noOfCorrect);
        correctQuizTextView.setText(String.valueOf(numberOfCorrect));

        incorrectQuizTextView = (TextView) findViewById(R.id.noOfinCorrect);
        incorrectQuizTextView.setText(String.valueOf(numberOfWrong));

        pointsTextView = (TextView) findViewById(R.id.pointsAttempt);
        pointsTextView.setText(String.valueOf(pointsScored));

        totalPointsTextView = (TextView) findViewById(R.id.pointsOverall);
        totalPointsTextView.setText(String.valueOf(overallPoints));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonConfirm:
                Intent intent = new Intent(endQuiz.this, Menu.class);
                startActivity(intent);
                break;
        }
    }
}