package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.onlineeducationsystemorganization.util.AppSharedPreference;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        show();
    }

    private void show() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (AppSharedPreference.getInstance().getString(SplashActivity.this, AppSharedPreference.USERID) == null) {
                    Intent i = new Intent(SplashActivity.this,
                            CompanyUrlActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    if(AppSharedPreference.getInstance().getString(SplashActivity.this, AppSharedPreference.USER_TYPE).equalsIgnoreCase("1")) {
                        Intent i = new Intent(SplashActivity.this,
                                MainActivity.class);
                        startActivity(i);
                        finish();
                    }else
                    {
                        Intent i = new Intent(SplashActivity.this,
                                MainUserActivity.class);
                        startActivity(i);
                        finish();
                    }
                }


            }
        }, SPLASH_SCREEN_TIME_OUT);
    }



}

