package com.onlineeducationsystemorganization;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.ForgotPwd;
import com.onlineeducationsystemorganization.model.User;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class ForgotPwdActivity extends AppCompatActivity implements NetworkListener {

    private EditText etPhone,etOtp,etNewPwd,etResetPwd;
    private LinearLayout llMain,llForgot,llOTP,llReset;
    private TextView tvSendOTP,btnVerify,tvResend,tvTimer,tvSetPwd;
    private String phone="";
    CountDownTimer countDownTimer;
    private AppSharedPreference preference;
    private String userId="";
    private CountryCodePicker ccp;
    private String selectedCountryCode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        initUI();
    }

    private void initUI()
    {
        preference = AppSharedPreference.getInstance();
        etPhone =findViewById(R.id.etPhone);
        etNewPwd =findViewById(R.id.etNewPwd);
        etResetPwd =findViewById(R.id.etResetPwd);
        ccp=findViewById(R.id.ccp);
        selectedCountryCode =ccp.getSelectedCountryCodeWithPlus();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedCountryCode =ccp.getSelectedCountryCodeWithPlus();
            }
        });

        llMain =findViewById(R.id.llMain);
        llForgot =findViewById(R.id.llForgot);
        llReset =findViewById(R.id.llReset);
        llOTP =findViewById(R.id.llOTP);

        llForgot.setVisibility(View.VISIBLE);
        llReset.setVisibility(View.GONE);
        llOTP.setVisibility(View.GONE);

        etOtp =findViewById(R.id.etOtp);
        tvResend =findViewById(R.id.tvResend);
        tvTimer =findViewById(R.id.tvTimer);
        tvSetPwd =findViewById(R.id.tvSetPwd);
        tvSetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtils.isInternetAvailable(ForgotPwdActivity.this)) {
                    if (isValidResetPwd()) {

                        hintResetPwd();

                    }
                }

            }
        });
        countDownTimer();
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(ForgotPwdActivity.this)) {
                    countDownTimer();
                    hintResend();
                }
            }
        });
        tvSendOTP =findViewById(R.id.tvSendOTP);
        tvSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(ForgotPwdActivity.this)) {
                    if (isValidPhone()) {
                        hintPhone();
                    }
                }
            }
        });

        btnVerify =findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(ForgotPwdActivity.this)) {
                    if (isValid()) {
                        hintOtp();
                    }
                }
            }
        });
    }

    private void hintResetPwd()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("user_id", userId);
        params.put("new_password", etNewPwd.getText().toString());
        if (AppSharedPreference.getInstance().getString(ForgotPwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ForgotPwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<BaseBean> call = apiInterface.resetPwd(lang,params);
        ApiCall.getInstance().hitService(ForgotPwdActivity.this, call, this, ServerConstents.RESET);

    }

    private void hintResend()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("phone_no", selectedCountryCode+"-"+phone);
        if (AppSharedPreference.getInstance().getString(ForgotPwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ForgotPwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<BaseBean> call = apiInterface.resend(lang,params);
        ApiCall.getInstance().hitService(ForgotPwdActivity.this, call, this, ServerConstents.RESEND);

    }

    private void hintOtp()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("otp", etOtp.getText().toString());
        params.put("phone_no", selectedCountryCode+"-"+phone);
        if (AppSharedPreference.getInstance().getString(ForgotPwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ForgotPwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<User> call = apiInterface.otp(lang,params);
        ApiCall.getInstance().hitService(ForgotPwdActivity.this, call, this, ServerConstents.OTP);

    }
    private void hintPhone()
    {
        String lang="";
        phone =etPhone.getText().toString();
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("phone_no", selectedCountryCode+"-"+etPhone.getText().toString());
        if (AppSharedPreference.getInstance().getString(ForgotPwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ForgotPwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<ForgotPwd> call = apiInterface.forgotPWD(lang,params);
        ApiCall.getInstance().hitService(ForgotPwdActivity.this, call, this, ServerConstents.SEND_OTP);

    }



    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.SEND_OTP)
        {
            llOTP.setVisibility(View.VISIBLE);
            llForgot.setVisibility(View.GONE);
            llReset.setVisibility(View.GONE);
        }else if(requestCode == ServerConstents.RESEND){

                countDownTimer();
        }else if(requestCode == ServerConstents.OTP)
        {
            llForgot.setVisibility(View.GONE);
            llReset.setVisibility(View.VISIBLE);
            llOTP.setVisibility(View.GONE);
            User data=(User) response;
            if(data.getStatus()==ServerConstents.CODE_SUCCESS) {
               // Toast.makeText(ForgotPwdActivity.this,data.getMessage(), Toast.LENGTH_SHORT).show();

                userId =data.getData().get(0).getUserId()+"";
            }

        }else
        {
            BaseBean data=(BaseBean) response;
            if(data.getStatus()==ServerConstents.CODE_SUCCESS)
            {
                Toast.makeText(ForgotPwdActivity.this, data.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }
    private void countDownTimer() {
        if
        (countDownTimer != null)
        {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
       countDownTimer= new CountDownTimer(60000, 1000) {

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
            }

        }.start();
    }
    @Override
    public void onError(String response, int requestCode, int errorCode) {
        Toast.makeText(ForgotPwdActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }
    private boolean isValidResetPwd()
    {
        boolean bool = true;
        if (TextUtils.isEmpty(etNewPwd.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(ForgotPwdActivity.this, getString(R.string.toast_new_pwd), Toast.LENGTH_SHORT).show();
            // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }else if (TextUtils.isEmpty(etResetPwd.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(ForgotPwdActivity.this, getString(R.string.toast_confirm_pwd), Toast.LENGTH_SHORT).show();
            // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }else if(!etNewPwd.getText().toString().equals(etResetPwd.getText().toString()))
        {
            bool=false;
            Toast.makeText(ForgotPwdActivity.this, getString(R.string.toast_pwd_retype_pwd_same), Toast.LENGTH_SHORT).show();
        }
        return bool;
    }
    private boolean isValidPhone() {
        boolean bool = true;
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(ForgotPwdActivity.this, getString(R.string.toast_phone), Toast.LENGTH_SHORT).show();
            // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }
        return bool;
    }
    private boolean isValid()
    {
        boolean bool = true;
        if (TextUtils.isEmpty(etOtp.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(ForgotPwdActivity.this, getString(R.string.toast_otp), Toast.LENGTH_SHORT).show();
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
