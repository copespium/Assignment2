package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    private TextView usernameTextView, pointsTextView;
    private TextView myTextV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        myTextV = (TextView) findViewById(R.id.mybackButton);
        myTextV.setOnClickListener(this);
        List<Attempt> attemptList = Database.getInstance().getAttemptList();

        //sum all previous attempt points
        int overallPoints = attemptList.stream()
                .map(x -> x.getPoint())
                .reduce(0, (a, b) -> a + b);

        usernameTextView = (TextView) findViewById(R.id.username);
        usernameTextView.setText(Database.getInstance().getCurrentUser().getUserName());
        pointsTextView = (TextView) findViewById(R.id.points);
        pointsTextView.setText(String.valueOf(overallPoints));

        //sort by date
        //attemptList.sort( (a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime()));
        //sort by area
        //attemptList.sort( (a1, a2) -> a1.getArea().compareTo(a2.getArea()));

        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.linearLayout);
        for (Attempt a : attemptList) {

            TextView textView = new TextView(Profile.this);
            textView.setSingleLine(false);
            textView.setText(a.getArea() + " area - attempt started on " + a.getDateTime());
            parentLayout.addView(textView);

            TextView pointsTextView = new TextView(Profile.this);
            pointsTextView.setSingleLine(false);
            pointsTextView.setText("- Points earned : " + String.valueOf(a.getPoint()) + "\n");
            parentLayout.addView(pointsTextView);
        }




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mybackButton:
                Intent intent = new Intent(Profile.this, Menu.class);
                startActivity(intent);
                break;
        }
    }
}