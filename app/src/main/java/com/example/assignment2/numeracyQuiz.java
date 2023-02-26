package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class numeracyQuiz extends AppCompatActivity implements View.OnClickListener{

    TextView numeracyQ1TextView, numeracyQ2TextView, numeracyQ3TextView, numeracyQ4TextView, numeracyQ5TextView;
    TextView answerBox1TextView, answerBox2TextView, answerBox3TextView, answerBox4TextView, answerBox5TextView;

    private Button submitButton;

    static int totalNumQuestions = 5;
    List<QuestionAnswer> numeracyQAList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeracy_quiz);

        List<QuestionAnswer> questionAnswerList = Database.getInstance().getQuestionAnswerList();
        numeracyQAList = questionAnswerList
                .stream()
                .filter(q -> q.getArea().equalsIgnoreCase("Numeracy"))
                .collect(Collectors.toList());

        System.out.println("Numeracy Question List: " + numeracyQAList);

        Collections.shuffle(numeracyQAList);

        System.out.println("Shuffled Numeracy Question List: " + numeracyQAList);

        submitButton = (Button) findViewById(R.id.submitButtonNum);
        submitButton.setOnClickListener(this);

        numeracyQ1TextView = (TextView) findViewById(R.id.numeracyQ1);
        numeracyQ1TextView.setText(numeracyQAList.get(0).getQuestion());
        numeracyQ2TextView = (TextView) findViewById(R.id.numeracyQ2);
        numeracyQ2TextView.setText(numeracyQAList.get(1).getQuestion());
        numeracyQ3TextView = (TextView) findViewById(R.id.numeracyQ3);
        numeracyQ3TextView.setText(numeracyQAList.get(2).getQuestion());
        numeracyQ4TextView = (TextView) findViewById(R.id.numeracyQ4);
        numeracyQ4TextView.setText(numeracyQAList.get(3).getQuestion());
        numeracyQ5TextView = (TextView) findViewById(R.id.numeracyQ5);
        numeracyQ5TextView.setText(numeracyQAList.get(4).getQuestion());

        answerBox1TextView = (TextView) findViewById(R.id.answerBox1);
        answerBox2TextView = (TextView) findViewById(R.id.answerBox2);
        answerBox3TextView = (TextView) findViewById(R.id.answerBox3);
        answerBox4TextView = (TextView) findViewById(R.id.answerBox4);
        answerBox5TextView = (TextView) findViewById(R.id.answerBox5);
    }


    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.submitButtonNum:
                calculateResults();
                break;
        }
    }

    private void calculateResults(){
        int numberOfCorrect = 0;
        if (numeracyQAList.get(0).getAnswer().contentEquals(answerBox1TextView.getText()))
            numberOfCorrect += 1;
        if (numeracyQAList.get(1).getAnswer().contentEquals(answerBox2TextView.getText()))
            numberOfCorrect += 1;
        if (numeracyQAList.get(2).getAnswer().contentEquals(answerBox3TextView.getText()))
            numberOfCorrect += 1;
        if (numeracyQAList.get(3).getAnswer().contentEquals(answerBox4TextView.getText()))
            numberOfCorrect += 1;
        if (numeracyQAList.get(4).getAnswer().contentEquals(answerBox5TextView.getText()))
            numberOfCorrect += 1;

        int numberOfWrong = totalNumQuestions - numberOfCorrect;
        int pointsScored = numberOfCorrect * 5 - numberOfWrong * 2;

        //prevent negative points
        if (pointsScored < 0)
            pointsScored = 0;

        List<Attempt> attemptList = Database.getInstance().getAttemptList();

        //sum all previous attempt points
        int existingPoints = attemptList.stream()
                .map(x -> x.getPoint())
                .reduce(0, (a, b) -> a + b);

        int overallPoints = existingPoints + pointsScored;

        System.out.println("Number of correct: " + numberOfCorrect);
        System.out.println("Number of wrong: " + numberOfWrong);
        System.out.println("points scored on this quiz: " + pointsScored);
        System.out.println("existing points: " + existingPoints);
        System.out.println("overall points: " + overallPoints);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        Date date = new Date();
        System.out.println(sdf.format(date));

        //write attempt record to database
        Database.getInstance().addAttempt("Numeracy", pointsScored, sdf.format(date), String.valueOf(attemptList.size() + 1));

        Intent intent = new Intent(numeracyQuiz.this, endQuiz.class);
        intent.putExtra("numberOfCorrect",numberOfCorrect);
        intent.putExtra("numberOfWrong",numberOfWrong);
        intent.putExtra("pointsScored",pointsScored);
        intent.putExtra("overallPoints",overallPoints);
        intent.putExtra("area", "Numeracy");
        startActivity(intent);
    }
}