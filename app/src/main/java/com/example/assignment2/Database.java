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

//    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<Attempt> attemptList = new ArrayList<>();

    private static ArrayList<QuestionAnswer> questionAnswerList = new ArrayList<>();

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

//        usersRef.orderByValue().addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//                User user = dataSnapshot.getValue(User.class);
//                System.out.println("The " + dataSnapshot.getKey() + " user details is " + dataSnapshot.getValue());
//                System.out.println("Username: " + user.getUserName() + "email: " + user.getEmail());
//                users.add(user);
//                System.out.println("@Printing all users: " + users);
//            }
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {}
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//            // ...
//        });

        //generateQuestionPoolList();

    }

    public void loadCurrentUserAttempts(){

        attemptsRef.orderByValue().addChildEventListener(new ChildEventListener() {
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


    //mAuth.signOut();

//    public ArrayList<User> getUsers() {
//        return users;
//    }

    public ArrayList<Attempt> getAttemptList() {
        return attemptList;
    }

    public ArrayList<QuestionAnswer> getQuestionAnswerList() {
        return questionAnswerList;
    }

    //adds to the database
    public void addAttempt(String area, int point, String dateTime) {

        Attempt attempt = new Attempt (area, point, dateTime);

        attemptsRef.child(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                attemptsRef.setValue(attempt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled
            }
        });
    }

    //adds to the database
    public void addQuestionAnswer(QuestionAnswer questionAnswer, String id) {

//        questionAnswerRef.child(id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                questionAnswerRef.setValue(questionAnswer);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // if the data is not added or it is cancelled
//            }
//        });


        FirebaseDatabase.getInstance(" https://assignment2-fba50-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("QuestionAnswers")
                .child(id)
                .setValue(questionAnswer).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete (@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //Toast.makeText(Home.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);
                            //startActivity(new Intent(Home.this, Login.class));
                        } else {
                            //Toast.makeText(Home.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);
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



        //set question answer for history
        QuestionAnswer historyQA = new QuestionAnswer();
        historyQA.setArea("History");
        historyQA.setQuestion("Who is founder of Singapore");
        historyQA.setAnswer("Stamford Raffles");
        historyQA.setAnswerChoices("Stamford Raffles, Regina, Jeremy, Florine, Asamimi");

        addQuestionAnswer(historyQA,"2");
    }

}
