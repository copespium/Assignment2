package com.example.assignment2;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Database {

    private FirebaseAuth mAuth;

    DatabaseReference usersRef = FirebaseDatabase.getInstance(" https://assignment2-fba50-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Users");

    DatabaseReference attemptsRef = FirebaseDatabase.getInstance(" https://assignment2-fba50-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Attempts");

    DatabaseReference questionAnswerRef = FirebaseDatabase.getInstance(" https://assignment2-fba50-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("QuestionAnswers");

    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<Attempt> attemptList = new ArrayList<>();

    private static ArrayList<QuestionAnswer> questionAnswerList = new ArrayList<>();

    private static User currentUser = new User();

    private static final Database ourInstance = new Database();
    public static Database getInstance() {
        return ourInstance;
    }
    private Database() {}

    public void init(){

        mAuth = FirebaseAuth.getInstance();

        questionAnswerRef.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                QuestionAnswer questionAnswer = dataSnapshot.getValue(QuestionAnswer.class);
                questionAnswerList.add(questionAnswer);
                System.out.println("@Printing all Question Answer: " + questionAnswerList);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
            // ...
        });

        usersRef.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                User user = dataSnapshot.getValue(User.class);
                String UID = dataSnapshot.getKey();

                if (userIsLoggedIn() && UID.equalsIgnoreCase(mAuth.getCurrentUser().getUid()))
                    currentUser = user;
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
            // ...
        });

        generateQuestionPoolList();
    }

    public void loadCurrentUserAttempts(){

        //clear attempt list from previous user so can load attempt list for new logged in user
        attemptList.clear();

        attemptsRef.child(mAuth.getCurrentUser().getUid())
                .orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Attempt attempt = dataSnapshot.getValue(Attempt.class);
                attemptList.add(attempt);
                System.out.println("@Printing all attempts: " + attemptList);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
            // ...
        });
    }

    public boolean userIsLoggedIn(){
        return mAuth.getCurrentUser() != null;
    }

    public void signOut(){
        mAuth.signOut();
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public ArrayList<Attempt> getAttemptList() {
        return attemptList;
    }

    public ArrayList<QuestionAnswer> getQuestionAnswerList() {
        return questionAnswerList;
    }

    //adds to the database
    public void addAttempt(String area, int point, String dateTime, String attemptNum) {

        Attempt attempt = new Attempt (area, point, dateTime);

        attemptsRef.child(mAuth.getCurrentUser().getUid())
                .child(attemptNum)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                attemptsRef.child(mAuth.getCurrentUser().getUid()).child(attemptNum).setValue(attempt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled
            }
        });
    }

    //adds to the database
    public void addQuestionAnswer(QuestionAnswer questionAnswer, String id) {
        FirebaseDatabase.getInstance(" https://assignment2-fba50-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("QuestionAnswers")
                .child(id)
                .setValue(questionAnswer).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete (@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            ;
                        } else {
                            ;
                        }
                    }
                });
    }

    //one time call this method to generate the pool list into database
    public void generateQuestionPoolList() {
        //set question answer for numeracy (To set 15 questions per area)
        QuestionAnswer numeracyQA = new QuestionAnswer();
        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("5*2");
        numeracyQA.setAnswer("10");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "1");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("8/4");
        numeracyQA.setAnswer("2");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "2");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("12+24");
        numeracyQA.setAnswer("36");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "3");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("102-84");
        numeracyQA.setAnswer("18");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "4");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("36/8");
        numeracyQA.setAnswer("4");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "5");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("100/5");
        numeracyQA.setAnswer("20");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "6");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("80*5");
        numeracyQA.setAnswer("400");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "7");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("100*5");
        numeracyQA.setAnswer("500");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "8");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("21/7");
        numeracyQA.setAnswer("3");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "9");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("242+20");
        numeracyQA.setAnswer("262");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "10");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("58-7");
        numeracyQA.setAnswer("51");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "11");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("123-3");
        numeracyQA.setAnswer("120");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "12");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("36/6");
        numeracyQA.setAnswer("6");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "13");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("108*2");
        numeracyQA.setAnswer("216");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "14");

        numeracyQA.setArea("Numeracy");
        numeracyQA.setQuestion("400+1");
        numeracyQA.setAnswer("401");
        //not required to set answer choices for numeracy since no answer choices required
        addQuestionAnswer(numeracyQA, "15");


        //set question answer for history
        QuestionAnswer historyQA = new QuestionAnswer();
        historyQA.setArea("History");
        historyQA.setQuestion("Who is founder of Singapore");
        historyQA.setAnswer("Stamford Raffles");
        historyQA.setAnswerChoices("Stamford Raffles,Regina,Jeremy,Florine,Asamimi");
        addQuestionAnswer(historyQA,"16");

        historyQA.setArea("History");
        historyQA.setQuestion("Who is founder of Coogie Run");
        historyQA.setAnswer("Regina");
        historyQA.setAnswerChoices("Anemimi,Regina,BAD4,FattyBomBom,Asamimi");
        addQuestionAnswer(historyQA,"17");

        historyQA.setArea("History");
        historyQA.setQuestion("When did Singapore gain its independence");
        historyQA.setAnswer("1965");
        historyQA.setAnswerChoices("1965,1900,2010,1955,1982");
        addQuestionAnswer(historyQA,"18");

        historyQA.setArea("History");
        historyQA.setQuestion("What was Singapore originally known as?");
        historyQA.setAnswer("Temasek");
        historyQA.setAnswerChoices("Temasek,Johor Lama,Palembang,It had no name,Utama");
        addQuestionAnswer(historyQA,"19");

        historyQA.setArea("History");
        historyQA.setQuestion("Who was Singapore's first Prime Minister?");
        historyQA.setAnswer("Lee Kuan Yew");
        historyQA.setAnswerChoices("Goh Chok Tong,Singapore does not have a Prime Minister,Lee Hsien Loong,Lee Kuan Yew,Lee Xiao Long");
        addQuestionAnswer(historyQA,"20");

        historyQA.setArea("History");
        historyQA.setQuestion("Q6");
        historyQA.setAnswer("A");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"21");

        historyQA.setArea("History");
        historyQA.setQuestion("Q7");
        historyQA.setAnswer("B");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"22");

        historyQA.setArea("History");
        historyQA.setQuestion("Q8");
        historyQA.setAnswer("C");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"23");

        historyQA.setArea("History");
        historyQA.setQuestion("Q9");
        historyQA.setAnswer("D");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"24");

        historyQA.setArea("History");
        historyQA.setQuestion("Q10");
        historyQA.setAnswer("E");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"25");

        historyQA.setArea("History");
        historyQA.setQuestion("Q11");
        historyQA.setAnswer("A");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"26");

        historyQA.setArea("History");
        historyQA.setQuestion("Q12");
        historyQA.setAnswer("B");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"27");

        historyQA.setArea("History");
        historyQA.setQuestion("Q13");
        historyQA.setAnswer("C");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"28");

        historyQA.setArea("History");
        historyQA.setQuestion("Q14");
        historyQA.setAnswer("D");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"29");

        historyQA.setArea("History");
        historyQA.setQuestion("Q15");
        historyQA.setAnswer("E");
        historyQA.setAnswerChoices("A,B,C,D,E");
        addQuestionAnswer(historyQA,"30");

    }

}
