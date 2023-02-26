package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class Profile extends AppCompatActivity {

    private TextView usernameTextView, quizNameTextView, quizTakenDateTextView, pointsEarnedTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        List<Attempt> attemptList = Database.getInstance().getAttemptList();

        usernameTextView = (TextView) findViewById(R.id.username);
        usernameTextView.setText(Database.getInstance().getCurrentUser().getUserName());
        quizNameTextView = (TextView) findViewById(R.id.quizName);
        quizNameTextView.setText(attemptList.get(0).getArea());
        quizTakenDateTextView = (TextView) findViewById(R.id.quizTakenDate);
        quizTakenDateTextView.setText(attemptList.get(0).getDateTime());
        pointsEarnedTextView = (TextView) findViewById(R.id.pointsEarned);
        pointsEarnedTextView.setText(attemptList.get(0).getPoint());

    }
}