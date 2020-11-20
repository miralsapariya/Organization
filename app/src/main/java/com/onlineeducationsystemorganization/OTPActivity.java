package com.onlineeducationsystemorganization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.User;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

public class OTPActivity extends BaseActivity implements NetworkListener {

    private String phone,response_code;
    private EditText etOtp;
    private LinearLayout llMain;
    private TextView btnVerify,tvTimer,tvResend;
    private AppSharedPreference preference;
    private CountryCodePicker ccp;
    private String selectedCountryCode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initUI();
    }

    private void initUI()
    {
        preference = AppSharedPreference.getInstance();
        etOtp =findViewById(R.id.etOtp);
        llMain =findViewById(R.id.llMain);





        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        // get data via the key
        phone  = extras.getString("phone");
        response_code =extras.getString("response_code");
        selectedCountryCode =extras.getString("country_code");

        btnVerify=findViewById(R.id.btnVerify);
        tvTimer =findViewById(R.id.tvTimer);
        countDownTimer();

        if(response_code.equals("403"))
        {
            if (AppUtils.isInternetAvailable(OTPActivity.this)) {
                    hintResend();
            }
        }
        tvResend =findViewById(R.id.tvResend);
        tvResend.setEnabled(false);
      //  tvResend.setVisibility(View.GONE);
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(OTPActivity.this)) {
                        hintResend();
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtils.isInternetAvailable(OTPActivity.this)) {
                    if (isValid()) {
                        hintOtp();
                    }
                }
            }
        });
    }

    private void hintResend()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("phone_no", selectedCountryCode+"-"+phone);
        if (AppSharedPreference.getInstance().getString(OTPActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(OTPActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<BaseBean> call = apiInterface.resend(lang,params);
        ApiCall.getInstance().hitService(OTPActivity.this, call, this, ServerConstents.RESEND);

    }

    private void hintOtp()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("otp", etOtp.getText().toString());
        params.put("phone_no", selectedCountryCode+"-"+phone);
        if (AppSharedPreference.getInstance().getString(OTPActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(OTPActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<User> call = apiInterface.otp(lang,params);
        ApiCall.getInstance().hitService(OTPActivity.this, call, this, ServerConstents.LOGIN);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        if(requestCode == ServerConstents.RESEND)
        {
            BaseBean data=(BaseBean) response;
            if(data.getStatus()==ServerConstents.CODE_SUCCESS)
            {
              /*  Intent intent = new Intent(OTPActivity.this,MainActivity.class);
                startActivity(intent);
                finish();*/
              if(!response_code.equals("403")) {
                  countDownTimer();
                  tvResend.setEnabled(false);
                  tvResend.setVisibility(View.GONE);
              }
            }
        }else
        {
            User data=(User) response;
            if(data.getStatus()==ServerConstents.CODE_SUCCESS) {
                ArrayList<User.Datum> res = data.getData();

                preference.putString(OTPActivity.this, AppSharedPreference.USER_TYPE, res.get(0).getUser_type()+"");
                preference.putString(OTPActivity.this, AppSharedPreference.USERID, res.get(0).getUserId() + "");
                preference.putString(OTPActivity.this, AppSharedPreference.NAME, res.get(0).getName() + "");
                preference.putString(OTPActivity.this, AppSharedPreference.EMAIL, res.get(0).getEmail());
                preference.putString(OTPActivity.this, AppSharedPreference.FIRST_NAME, res.get(0).getFirstName() + "");
                preference.putString(OTPActivity.this, AppSharedPreference.LAST_NAME, res.get(0).getLastName() + "");
                preference.putString(OTPActivity.this, AppSharedPreference.PROFILE_PIC, res.get(0).getProfilePicture());
                preference.putString(OTPActivity.this, AppSharedPreference.PHONE, res.get(0).getPhoneNo());
                preference.putString(OTPActivity.this, AppSharedPreference.ACCESS_TOKEN, res.get(0).getToken());
                preference.putString(OTPActivity.this,AppSharedPreference.COMPANY_NAME,res.get(0).getOrganization_name());
                preference.putString(OTPActivity.this,AppSharedPreference.COMPANY_URL,res.get(0).getSubdomain());

                if(res.get(0).getUser_type() ==AppConstant.USER_TYPE_ADMIN) {
                    Intent i = new Intent(OTPActivity.this,
                            MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(OTPActivity.this,
                            MainUserActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
            }

        }

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

        Toast.makeText(OTPActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }

    private void countDownTimer() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tvTimer.setText("Time Remaining (" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds) + ")");
            }

            public void onFinish() {
                // mTextField.setText("done!");
                tvTimer.setText("Time Remaining (00:00)");
                tvResend.setEnabled(true);
                tvResend.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    private boolean isValid() {
        boolean bool = true;
        if (TextUtils.isEmpty(etOtp.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(OTPActivity.this, getString(R.string.toast_otp), Toast.LENGTH_SHORT).show();
            // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }
        return bool;
    }

    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
    }
}
