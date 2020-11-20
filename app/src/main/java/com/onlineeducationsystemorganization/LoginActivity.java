package com.onlineeducationsystemorganization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

public class LoginActivity extends BaseActivity implements NetworkListener {

    private EditText etEmail,etPassword;
    private TextView btnLogin,tvSignUP,tvForgotPwd;
    private LinearLayout llMain;
    private AppSharedPreference preference;
    private ImageView imgBack;
    private String company_user_id="";
    private String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI()
    {
        android_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        company_user_id=getIntent().getExtras().getString("company_user_id");

        preference = AppSharedPreference.getInstance();
        etEmail =findViewById(R.id.etEmail);
        etPassword =findViewById(R.id.etPassword);
        llMain =findViewById(R.id.llMain);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnLogin =findViewById(R.id.btnLogin);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FAILED ", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("TOKEN ::", token);
                       // Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                        AppSharedPreference.getInstance().putString(LoginActivity.this, AppSharedPreference.DEVICE_TOKEN, token);
                    }
                });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(LoginActivity.this)) {
                    if (isValid()) {
                        hintLogin();
                    }
                }
            }
        });
        tvSignUP =findViewById(R.id.tvSignUP);
        tvSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvForgotPwd =findViewById(R.id.tvForgotPwd);
        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPwdActivity.class);
                startActivity(intent);

            }
        });
    }

    private void hintLogin()
    {
        String t=AppSharedPreference.getInstance().getString(LoginActivity.this, AppSharedPreference.DEVICE_TOKEN);

        Log.d("____________________ ", t);
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("email", etEmail.getText().toString());
        params.put("password", etPassword.getText().toString());
        params.put("device_token", AppSharedPreference.getInstance().getString(LoginActivity.this, AppSharedPreference.DEVICE_TOKEN));
        params.put("device_id", android_id);
        params.put("device_type", ServerConstents.DEVICE_TYPE);
        params.put("company_user_id", company_user_id+"");

        if (AppSharedPreference.getInstance().getString(LoginActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(LoginActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
           lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<User> call = apiInterface.login(lang,params);

        ApiCall.getInstance().hitService(LoginActivity.this, call, this, ServerConstents.LOGIN);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        User data=(User) response;
        if(data.getStatus()==ServerConstents.CODE_SUCCESS)
        {
            ArrayList<User.Datum> res= data.getData();

            preference.putString(LoginActivity.this, AppSharedPreference.PWD, etPassword.getText().toString()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.USERID, res.get(0).getUserId()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.NAME, res.get(0).getName()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.EMAIL, res.get(0).getEmail());
            preference.putString(LoginActivity.this, AppSharedPreference.USER_TYPE, res.get(0).getUser_type()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.FIRST_NAME, res.get(0).getFirstName()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.LAST_NAME, res.get(0).getLastName()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.PROFILE_PIC, res.get(0).getProfilePicture());
            preference.putString(LoginActivity.this,AppSharedPreference.PHONE, res.get(0).getPhoneNo());
            preference.putString(LoginActivity.this, AppSharedPreference.ACCESS_TOKEN, "Bearer"+" "+res.get(0).getToken());
            preference.putString(LoginActivity.this,AppSharedPreference.COMPANY_NAME,res.get(0).getOrganization_name());
            preference.putString(LoginActivity.this,AppSharedPreference.COMPANY_URL,res.get(0).getSubdomain());

            //org admin
            if(res.get(0).getUser_type() ==AppConstant.USER_TYPE_ADMIN) {
                Intent i = new Intent(LoginActivity.this,
                        MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }else
            {
                Intent i = new Intent(LoginActivity.this,
                        MainUserActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {

    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        //(?=.*\d)
        // final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        //final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z#@$!%*?&]{6,}$";
        final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!%*?&])[A-Za-z#@$!%*?&0-9]{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private boolean isValid()
    {
        boolean bool=true;
         if(TextUtils.isEmpty(etEmail.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(LoginActivity.this, getString(R.string.toast_email), Toast.LENGTH_SHORT).show();

            //L.showSnackbar(llLogin, getString(R.string.toast_pwd));
        }else if(! AppUtils.validEmail(etEmail.getText().toString()))
         {
             bool=false;
             hideKeyboard();
             Toast.makeText(LoginActivity.this, getString(R.string.toast_valid_email), Toast.LENGTH_SHORT).show();

         }else if(TextUtils.isEmpty(etPassword.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(LoginActivity.this, getString(R.string.toast_password), Toast.LENGTH_SHORT).show();

        }/*else if(!isValidPassword(etPassword.getText().toString())) {
             bool=false;
             hideKeyboard();
             Toast.makeText(LoginActivity.this, getString(R.string.toast_pwd_match), Toast.LENGTH_SHORT).show();

         }*/
        return bool;
    }

    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
    }
}
