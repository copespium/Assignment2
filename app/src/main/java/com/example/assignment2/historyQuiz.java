package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class historyQuiz extends AppCompatActivity implements View.OnClickListener{

    TextView historyQ1TextView, historyQ2TextView, historyQ3TextView, historyQ4TextView, historyQ5TextView;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    RadioButton radioButton11, radioButton12, radioButton13, radioButton14, radioButton15;
    RadioButton radioButton16, radioButton17, radioButton18, radioButton19, radioButton20;
    RadioButton radioButton21, radioButton22, radioButton23, radioButton24, radioButton25;
    RadioButton radioButton6, radioButton7, radioButton8, radioButton9, radioButton10;
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5;
    static int totalNumQuestions = 5;
    private Button submitButton;
    List<QuestionAnswer> historyQAList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_quiz);

        List<QuestionAnswer> questionAnswerList = Database.getInstance().getQuestionAnswerList();
        historyQAList = questionAnswerList
                .stream()
                .filter(q -> q.getArea().equalsIgnoreCase("History"))
                .collect(Collectors.toList());

        System.out.println("History Question List: " + historyQAList);

        Collections.shuffle(historyQAList);

        System.out.println("Shuffled History Question List: " + historyQAList);

        submitButton = (Button) findViewById(R.id.submitButtonNum);
        submitButton.setOnClickListener(this);

        historyQ1TextView = (TextView) findViewById(R.id.numeracyQ1);
        historyQ1TextView.setText(historyQAList.get(0).getQuestion());
        historyQ2TextView = (TextView) findViewById(R.id.numeracyQ2);
        historyQ2TextView.setText(historyQAList.get(1).getQuestion());
        historyQ3TextView = (TextView) findViewById(R.id.numeracyQ3);
        historyQ3TextView.setText(historyQAList.get(2).getQuestion());
        historyQ4TextView = (TextView) findViewById(R.id.numeracyQ4);
        historyQ4TextView.setText(historyQAList.get(3).getQuestion());
        historyQ5TextView = (TextView) findViewById(R.id.numeracyQ5);
        historyQ5TextView.setText(historyQAList.get(4).getQuestion());

        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton5 = (RadioButton) findViewById(R.id.radioButton5);

        radioButton6 = (RadioButton) findViewById(R.id.radioButton6);
        radioButton7 = (RadioButton) findViewById(R.id.radioButton7);
        radioButton8 = (RadioButton) findViewById(R.id.radioButton8);
        radioButton9 = (RadioButton) findViewById(R.id.radioButton9);
        radioButton10 = (RadioButton) findViewById(R.id.radioButton10);

        radioButton11 = (RadioButton) findViewById(R.id.radioButton11);
        radioButton12 = (RadioButton) findViewById(R.id.radioButton12);
        radioButton13 = (RadioButton) findViewById(R.id.radioButton13);
        radioButton14 = (RadioButton) findViewById(R.id.radioButton14);
        radioButton15 = (RadioButton) findViewById(R.id.radioButton15);

        radioButton16 = (RadioButton) findViewById(R.id.radioButton16);
        radioButton17 = (RadioButton) findViewById(R.id.radioButton17);
        radioButton18 = (RadioButton) findViewById(R.id.radioButton18);
        radioButton19 = (RadioButton) findViewById(R.id.radioButton19);
        radioButton20 = (RadioButton) findViewById(R.id.radioButton20);

        radioButton21 = (RadioButton) findViewById(R.id.radioButton21);
        radioButton22 = (RadioButton) findViewById(R.id.radioButton22);
        radioButton23 = (RadioButton) findViewById(R.id.radioButton23);
        radioButton24 = (RadioButton) findViewById(R.id.radioButton24);
        radioButton25 = (RadioButton) findViewById(R.id.radioButton25);

        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
        radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
        radioGroup5 = (RadioGroup) findViewById(R.id.radioGroup5);

        String [] answerChoices;
        answerChoices = historyQAList.get(0).getAnswerChoices().split(",");
        radioButton1.setText(answerChoices[0]);
        radioButton2.setText(answerChoices[1]);
        radioButton3.setText(answerChoices[2]);
        radioButton4.setText(answerChoices[3]);
        radioButton5.setText(answerChoices[4]);

        answerChoices  = historyQAList.get(1).getAnswerChoices().split(",");
        radioButton6.setText(answerChoices[0]);
        radioButton7.setText(answerChoices[1]);
        radioButton8.setText(answerChoices[2]);
        radioButton9.setText(answerChoices[3]);
        radioButton10.setText(answerChoices[4]);

        answerChoices  = historyQAList.get(2).getAnswerChoices().split(",");
        radioButton11.setText(answerChoices[0]);
        radioButton12.setText(answerChoices[1]);
        radioButton13.setText(answerChoices[2]);
        radioButton14.setText(answerChoices[3]);
        radioButton15.setText(answerChoices[4]);

        answerChoices  = historyQAList.get(3).getAnswerChoices().split(",");
        radioButton16.setText(answerChoices[0]);
        radioButton17.setText(answerChoices[1]);
        radioButton18.setText(answerChoices[2]);
        radioButton19.setText(answerChoices[3]);
        radioButton20.setText(answerChoices[4]);

        answerChoices  = historyQAList.get(4).getAnswerChoices().split(",");
        radioButton21.setText(answerChoices[0]);
        radioButton22.setText(answerChoices[1]);
        radioButton23.setText(answerChoices[2]);
        radioButton24.setText(answerChoices[3]);
        radioButton25.setText(answerChoices[4]);

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

        int radioButtonID1 = radioGroup1.getCheckedRadioButtonId();
        View radioButton1 = radioGroup1.findViewById(radioButtonID1);
        int idx1 = radioGroup1.indexOfChild(radioButton1);
        RadioButton r1 = (RadioButton) radioGroup1.getChildAt(idx1);

        int radioButtonID2 = radioGroup2.getCheckedRadioButtonId();
        View radioButton2 = radioGroup2.findViewById(radioButtonID2);
        int idx2 = radioGroup2.indexOfChild(radioButton2);
        RadioButton r2 = (RadioButton) radioGroup2.getChildAt(idx2);

        int radioButtonID3 = radioGroup3.getCheckedRadioButtonId();
        View radioButton3 = radioGroup3.findViewById(radioButtonID3);
        int idx3 = radioGroup3.indexOfChild(radioButton3);
        RadioButton r3 = (RadioButton) radioGroup3.getChildAt(idx3);

        int radioButtonID4 = radioGroup4.getCheckedRadioButtonId();
        View radioButton4 = radioGroup4.findViewById(radioButtonID4);
        int idx4 = radioGroup4.indexOfChild(radioButton4);
        RadioButton r4 = (RadioButton) radioGroup4.getChildAt(idx4);

        int radioButtonID5 = radioGroup5.getCheckedRadioButtonId();
        View radioButton5 = radioGroup5.findViewById(radioButtonID5);
        int idx5 = radioGroup5.indexOfChild(radioButton5);
        RadioButton r5 = (RadioButton) radioGroup5.getChildAt(idx5);


        if (radioButtonID1 == -1 ||  radioButtonID2 == -1 || radioButtonID3 == -1 || radioButtonID4 == -1 || radioButtonID5 == -1) {
            Toast.makeText(historyQuiz.this, "Please answer all questions before submitting!", Toast.LENGTH_LONG).show();
        } else {

            String selectedQ1Ans = r1.getText().toString();
            String selectedQ2Ans = r2.getText().toString();
            String selectedQ3Ans = r3.getText().toString();
            String selectedQ4Ans = r4.getText().toString();
            String selectedQ5Ans = r5.getText().toString();

            if (historyQAList.get(0).getAnswer().contentEquals(selectedQ1Ans))
                numberOfCorrect += 1;
            if (historyQAList.get(1).getAnswer().contentEquals(selectedQ2Ans))
                numberOfCorrect += 1;
            if (historyQAList.get(2).getAnswer().contentEquals(selectedQ3Ans))
                numberOfCorrect += 1;
            if (historyQAList.get(3).getAnswer().contentEquals(selectedQ4Ans))
                numberOfCorrect += 1;
            if (historyQAList.get(4).getAnswer().contentEquals(selectedQ5Ans))
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
            Database.getInstance().addAttempt("History", pointsScored, sdf.format(date), String.valueOf(attemptList.size() + 1));

            Intent intent = new Intent(historyQuiz.this, endQuiz.class);
            intent.putExtra("numberOfCorrect", numberOfCorrect);
            intent.putExtra("numberOfWrong", numberOfWrong);
            intent.putExtra("pointsScored", pointsScored);
            intent.putExtra("overallPoints", overallPoints);
            startActivity(intent);
        }
    }

}