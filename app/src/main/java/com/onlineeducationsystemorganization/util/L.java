package com.onlineeducationsystemorganization.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.onlineeducationsystemorganization.BuildConfig;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class L {

    private static ProgressDialog mProgressDialog;

    public static void hideTitleBar(Activity context) {

        context.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        Log.d(" ==> ", "calculateNoOfColumns: " + noOfColumns);
        return 2;
    }

    public static void hideTitleBarCompletly(Activity context) {
        context.requestWindowFeature(Window.FEATURE_NO_TITLE);
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public static void print(String msg) {
        String TAG = "Megathy";
        if (BuildConfig.DEBUG)
            Log.d(TAG, msg);
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String getEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getText(TextView txtView) {
        return txtView.getText().toString().trim();
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    public static void showSimpleProgressDialog(Context context) {
        showSimpleProgressDialog(context, null, "Loading...", false);
    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();

                }
            }
            mProgressDialog = null;
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean isNetworkAvailable(Context context) {

        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getTimestame() {
        Long tsLong = System.currentTimeMillis() / 1000;

        return tsLong.toString();
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }

    public static String getLocalContry(Context context) {
        return context.getResources().getConfiguration().locale.getCountry();
    }

    public static String getTimeZone() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
//        return tz.getID();
        return "Asia/Riyadh";
    }



    public static void hideKeyboard(Context ctx) {

        if (ctx != null) {
            InputMethodManager inputManager = (InputMethodManager) ctx
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            // check if no view has focus:
            View v = ((Activity) ctx).getCurrentFocus();
            if (v == null)
                return;

            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }



    public static String convertTimeFormat(String time) {
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a, dd/MM/yyyy ", Locale.US);
        Date _24HourDt = null;
        try {
            _24HourDt = _24HourSDF.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return _12HourSDF.format(_24HourDt);
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        //(?=.*\d)
       // final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        //final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z#@$!%*?&]{6,}$";
        final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z#@$!%*?&0-9]{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean stringContainsNumber( String s )
    {
        Pattern p = Pattern.compile( "[0-9]" );
        Matcher m = p.matcher( s );

        return m.find();
    }
    public static  void showSnackbar(View view, String msg)
    {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static Date sotreTimeToDate(String storeTime) {
        Date cal = null;
        try {
            cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(storeTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static Calendar convertStringToCalendar(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static String getFormatedDateTime(String dateStr, String strReadFormat, String strWriteFormat) {

        String formattedDate = dateStr;

        DateFormat readFormat = new SimpleDateFormat(strReadFormat, Locale.US);
        DateFormat writeFormat = new SimpleDateFormat(strWriteFormat, Locale.US);

        Date date = null;

        try {
            date = readFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            formattedDate = writeFormat.format(date);
        }

        return formattedDate;
    }


    public static int getHours(String time) {
        int returnTime = 0;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.US);
            final Date dateObj = sdf.parse(time);
            returnTime = Integer.parseInt(new SimpleDateFormat("HH", Locale.US).format(dateObj));
        } catch (final ParseException e) {
            e.printStackTrace();
        }

        return returnTime;
    }

    public static String getFormat12(String time) {
        String returnTime = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H", Locale.US);
            final Date dateObj = sdf.parse(time);
            returnTime = new SimpleDateFormat("hh:mm a", Locale.US).format(dateObj);

        } catch (final ParseException e) {
            e.printStackTrace();
        }

        return returnTime;
    }


    public static String getDate(Calendar cal) {

        DateFormat dateFormat = new SimpleDateFormat("HH", Locale.US);
//        System.out.println(dateFormat.format(cal.getTime())); //2016/11/16 12:08:43
        return dateFormat.format(cal.getTime());
    }

    public static String convertDateToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return df.format(date);
    }


}
