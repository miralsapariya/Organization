package com.onlineeducationsystemorganization;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ThankYouInquiry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you_inquiry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
}