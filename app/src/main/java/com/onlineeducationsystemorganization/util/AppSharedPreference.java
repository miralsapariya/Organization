package com.onlineeducationsystemorganization.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreference {

    private static AppSharedPreference appSharedPreference = null;
    public static String ISFIRSTTIMERUN = "isfirst";
    private Context context;
    public static String USERID = "USER_ID";
    public static String NAME="name";
    public static String FIRST_NAME = "first_name";
    public static String LAST_NAME="last_name";
    public static String DEVICE_TOKEN = "device_token";
    public static String ACCESS_TOKEN = "access_token";
    public static String LANGUAGE_SELECTED = "language_selected";
    public static final String FULL_NAME = "full_name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PROFILE_PIC = "profile_name";
    public static final String PWD = "pwd";
    public static final String COMPANY_NAME="company_name";
    public static final String COMPANY_URL="company_url";

    public static AppSharedPreference getInstance() {
        if (appSharedPreference == null) {
            return new AppSharedPreference();
        } else {
            return appSharedPreference;
        }
    }


    public int getInt(Context context, String key) {
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        int value = sharedPref.getInt(key, 0);
        return value;
    }

    public void putInt(Context context, String key, int value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putString(Context context, String key, String value) {
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();
    }

    public String getString(Context context, String key) {
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        String value = sharedPref.getString(key, null);
        return value;
    }

    public void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        boolean value = sharedPref.getBoolean(key, false);
        return value;
    }

    public void clearAllPrefs(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }
}
