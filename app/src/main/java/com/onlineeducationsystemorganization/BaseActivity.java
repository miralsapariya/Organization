package com.onlineeducationsystemorganization;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity
{

    private Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLanguage();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    private void setLanguage()
    {
        if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED) != null) {

            if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


            } else {

                String languageToLoad = "ar"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            }
        }
    }
}
