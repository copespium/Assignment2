package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Password extends AppCompatActivity implements View.OnClickListener{
    private TextView myTextV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        myTextV = (TextView) findViewById(R.id.mybackButton);
        myTextV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mybackButton:
                Intent intent = new Intent(Password.this, Menu.class);
                startActivity(intent);
                break;
        }
    }
}