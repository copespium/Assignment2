package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Profile extends AppCompatActivity {

    private TextView usernameTextView, pointsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        List<Attempt> attemptList = Database.getInstance().getAttemptList();

        //sum all previous attempt points
        int overallPoints = attemptList.stream()
                .map(x -> x.getPoint())
                .reduce(0, (a, b) -> a + b);

        usernameTextView = (TextView) findViewById(R.id.username);
        usernameTextView.setText(Database.getInstance().getCurrentUser().getUserName());
        pointsTextView = (TextView) findViewById(R.id.points);
        pointsTextView.setText(String.valueOf(overallPoints));

        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.linearLayout);
        for (Attempt a : attemptList) {
            TextView textView = new TextView(Profile.this);
            textView.setText(a.getArea() + " area - attempt started on " + a.getDateTime());
            parentLayout.addView(textView);

            TextView pointsTextView = new TextView(Profile.this);
            pointsTextView.setText("- points earned " + String.valueOf(a.getPoint()));
            parentLayout.addView(pointsTextView);
        }
    }
}