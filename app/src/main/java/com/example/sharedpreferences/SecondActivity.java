package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int time = 7;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, time);
        System.out.println("/// Calendar date " + calendar.get(Calendar.DATE));
    }
}