package com.onlineeducationsystemorganization;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

public class ChangePwdActivity extends BaseActivity implements NetworkListener {

    private EditText etOldPwd,etNewPwd,etConfirmPwd;
    private TextView btnContinue;
    private LinearLayout llMain;
    private AppSharedPreference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        initUI();
    }

    private void changePwd()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("current_password", etOldPwd.getText().toString());
        params.put("new_password", etNewPwd.getText().toString());
        params.put("confirm_password", etConfirmPwd.getText().toString());

        if (AppSharedPreference.getInstance().getString(ChangePwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ChangePwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<BaseBean> call = apiInterface.changePwd(lang,
                AppSharedPreference.getInstance().
                        getString(ChangePwdActivity.this, AppSharedPreference.ACCESS_TOKEN),

                params);

        ApiCall.getInstance().hitService(ChangePwdActivity.this, call, this, ServerConstents.CHANGE_PWD);

    }
    private void hintLogin()
    {

        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("email", AppSharedPreference.getInstance().getString(ChangePwdActivity.this, AppSharedPreference.EMAIL));
        params.put("password", etNewPwd.getText().toString());
        params.put("device_token", "1234");
        params.put("device_type", ServerConstents.DEVICE_TYPE);
        params.put("company_user_id", preference.getString(ChangePwdActivity.this, AppSharedPreference.COMAPANY_USER_ID));
        if (AppSharedPreference.getInstance().getString(ChangePwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ChangePwdActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<User> call = apiInterface.login(lang,params);

        ApiCall.getInstance().hitService(ChangePwdActivity.this, call, this, ServerConstents.LOGIN);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        if(requestCode == ServerConstents.LOGIN)
        {
            User data=(User) response;
            if(data.getStatus()==ServerConstents.CODE_SUCCESS)
            {
                ArrayList<User.Datum> res= data.getData();

                preference.putString(ChangePwdActivity.this, AppSharedPreference.PWD, etConfirmPwd.getText().toString()+"");
                preference.putString(ChangePwdActivity.this, AppSharedPreference.USERID, res.get(0).getUserId()+"");
                preference.putString(ChangePwdActivity.this, AppSharedPreference.NAME, res.get(0).getName()+"");
                preference.putString(ChangePwdActivity.this, AppSharedPreference.EMAIL, res.get(0).getEmail());

                preference.putString(ChangePwdActivity.this, AppSharedPreference.FIRST_NAME, res.get(0).getFirstName()+"");
                preference.putString(ChangePwdActivity.this, AppSharedPreference.LAST_NAME, res.get(0).getLastName()+"");
                preference.putString(ChangePwdActivity.this, AppSharedPreference.PROFILE_PIC, res.get(0).getProfilePicture());
                preference.putString(ChangePwdActivity.this,AppSharedPreference.PHONE, res.get(0).getPhoneNo());
                preference.putString(ChangePwdActivity.this, AppSharedPreference.ACCESS_TOKEN, "Bearer"+" "+res.get(0).getToken());

              finish();
            }
        }else {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                Toast.makeText(ChangePwdActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                // finish();
                hintLogin();
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        Toast.makeText(ChangePwdActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }

    private boolean isValid()
    {
        boolean bool=true;
        if(TextUtils.isEmpty(etOldPwd.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_old_pwd), Toast.LENGTH_SHORT).show();

            //L.showSnackbar(llLogin, getString(R.string.toast_pwd));
        }else if(TextUtils.isEmpty(etNewPwd.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_new_pwd), Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(etConfirmPwd.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_confirm_pwd), Toast.LENGTH_SHORT).show();

        }else if(!etNewPwd.getText().toString().equals(etConfirmPwd.getText().toString()))
        {
            bool=false;
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_pwd_retype_pwd_same), Toast.LENGTH_SHORT).show();
        }else if(!isValidPassword(etNewPwd.getText().toString())) {
            bool=false;
            hideKeyboard();
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_pwd_match), Toast.LENGTH_SHORT).show();

        }
        return bool;
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

    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
    }

    private void initUI()
    {
        preference = AppSharedPreference.getInstance();
        llMain =findViewById(R.id.llMain);
        etOldPwd =findViewById(R.id.etOldPwd);
        etNewPwd =findViewById(R.id.etNewPwd);
        etConfirmPwd =findViewById(R.id.etConfirmPwd);

        btnContinue =findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(ChangePwdActivity.this)) {
                    if (isValid()) {
                        changePwd();
                    }
                }
            }
        });
    }
}
