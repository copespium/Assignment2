package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animated);
        Animation animation1;
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        ImageView q = (ImageView) findViewById(R.id.startingImage);
        TextView text = (TextView) findViewById(R.id.startingScreenText);
        q.startAnimation(animation);
        text.startAnimation(animation1);
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finishscreen();
            }
        };
        Database.getInstance().init();
        Timer t = new Timer();
        t.schedule(task, 3000);
    }
    private void finishscreen() {
        this.finish();
    }
}
