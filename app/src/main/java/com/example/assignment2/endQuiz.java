package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class endQuiz extends AppCompatActivity {

    TextView usernameQuizTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_quiz);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int numberOfCorrect = extras.getInt("numberOfCorrect");
            int numberOfWrong = extras.getInt("numberOfWrong");
            int pointsScored = extras.getInt("pointsScored");
            int overallPoints = extras.getInt("overallPoints");

            System.out.println("Number of correct: " + numberOfCorrect);
            System.out.println("Number of wrong: " + numberOfWrong);
            System.out.println("points scored on this quiz: " + pointsScored);
            System.out.println("overall points: " + overallPoints);
        }

        usernameQuizTextView = (TextView) findViewById(R.id.usernameQuiz);
        usernameQuizTextView.setText(Database.getInstance().getCurrentUser().getUserName());
    }


}