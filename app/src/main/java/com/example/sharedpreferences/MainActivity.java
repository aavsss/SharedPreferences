package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button btnCheckDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Views
        btnCheckDate = findViewById(R.id.btnCheckDate);

        //Two receipts
        Receipt homeReceipt = new Receipt("Home", 1000);
        Receipt schoolReceipt = new Receipt("School", 1500);
        ArrayList<Receipt> list = new ArrayList<>(Arrays.asList(homeReceipt, schoolReceipt));

        //Today's date
        Date todayDate = new Date();
        Date tomorrowDate = new Date(todayDate.getTime() + (1000*60*60*24));

        //Instantiating shared preferences.
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.sharedpreferences.date", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("Today", "31th of every month. 2 a month. Ends on Never. Sunday, Monday, Next Month: August");
        editor.putString("Tomorrow", "31th of every month. 2 a month. Ends on Never. Sunday, Monday, Next Month: August");
        editor.apply();

        Map<String, String> keyss = (Map<String, String>) sharedPreferences.getAll();

        //Syncing with the firebase database
        HashMap<String, String> firebaseMap = getHashMapsOfIds();
        for(Map.Entry<String, String> fData : firebaseMap.entrySet()){
            for(Map.Entry<String, String> sharedPrefDate : keyss.entrySet()){
                if(sharedPrefDate.getKey().equals(fData.getKey())){
                    editor.putString(sharedPrefDate.getKey(), fData.getValue());
                    editor.apply();
                }
            }
        }

        //OnClickListeners
        btnCheckDate.setOnClickListener(v -> {
            System.out.println("/// Second Run");
            Map<String, String> keys = (Map<String, String>) sharedPreferences.getAll();
            for(Map.Entry<String, String> sharedPrefDate : keys.entrySet()){
                System.out.println("/// Key: " + sharedPrefDate.getKey() + " :Value: " + sharedPrefDate.getValue());
            }
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        });

//        testing();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String, String> keys = (Map<String, String>) sharedPreferences.getAll();
        System.out.println("/// First Run");
        for(Map.Entry<String, String> sharedPrefDate : keys.entrySet()){
            System.out.println("/// Key: " + sharedPrefDate.getKey() + " :Value: " + sharedPrefDate.getValue());

            Reocurrence recurrenceMain = new Reocurrence(sharedPrefDate.getValue());
            if(recurrenceMain.checkReocurrence()){
                editor.putString(sharedPrefDate.getKey(), recurrenceMain.nextMonthString());
            }else{
                Date todayDate = new Date();
                Date newDate = new Date(todayDate.getTime() + (1000*60*60*24*7));
                editor.putString(sharedPrefDate.getKey(), newDate.toString());
            }
        }
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public HashMap<String, String> getHashMapsOfIds(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Today", "7th of every month. 2 a month. Ends on Never. Sunday, Monday, Next Month: August");
        hashMap.put("Tomorrow", "7th of every month. 2 a month. Ends on Never. Sunday, Monday, Next Month: August");
        return hashMap;
    }

    public void testing(){
        Reocurrence reocurrence = new Reocurrence("7th of every month. 2 a month. Ends on Never. Sunday, Monday, Next Month: August");
        System.out.println("/// time " + reocurrence.getTime());
        System.out.println("/// period " + reocurrence.getPeriod());
        System.out.println("/// many " + reocurrence.getMany());
        System.out.println("/// end " + reocurrence.getEnd());
        System.out.println("/// month " + reocurrence.getMonth());
        System.out.println("/// nextMonth " + reocurrence.nextMonthString());
        reocurrence.checkReocurrence();
    }
}