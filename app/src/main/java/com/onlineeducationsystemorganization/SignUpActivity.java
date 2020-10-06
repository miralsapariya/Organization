package com.onlineeducationsystemorganization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.User;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

public class SignUpActivity extends BaseActivity implements NetworkListener
{
    private EditText etName,etEmail,etPhone,etPassword,etCompanyName,etURL;
    private LinearLayout llMain;
    private TextView tvLogin,btnSignUp;
    private ImageView imgBack;
    private Spinner spinnerCountry;
    private CountryCodePicker ccp;
    private String selectedCountryCode="",selectedCountry="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
    }

    private void initUI() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etCompanyName=findViewById(R.id.etCompanyName);
        etURL=findViewById(R.id.etURL);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llMain = findViewById(R.id.llMain);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, CompanyUrlActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtils.isInternetAvailable(SignUpActivity.this)) {
                    if (isValid()) {
                        hintRegister();
                    }
                }
            }
        });

        ccp=findViewById(R.id.ccp);
        selectedCountryCode =ccp.getSelectedCountryCodeWithPlus();
        selectedCountry =ccp.getSelectedCountryName();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedCountryCode =ccp.getSelectedCountryCodeWithPlus();
                selectedCountry =ccp.getSelectedCountryName();
            }
        });


    }
    private void hintRegister()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("name", etName.getText().toString());
        params.put("email", etEmail.getText().toString());
        params.put("password", etPassword.getText().toString());
        params.put("phone_no", selectedCountryCode+"-"+etPhone.getText().toString());
        params.put("device_token", "1234");
        params.put("device_type", ServerConstents.DEVICE_TYPE);
        params.put("organization_name", etCompanyName.getText().toString());
        params.put("subdomain", etURL.getText().toString());
        params.put("country_name", selectedCountry);
        if (AppSharedPreference.getInstance().getString(SignUpActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SignUpActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<User> call = apiInterface.register(lang,params);
        ApiCall.getInstance().hitService(SignUpActivity.this, call, this, ServerConstents.LOGIN);
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        User data=(User) response;
        if(responseCode == 403)
        {
           Toast.makeText(SignUpActivity.this, data.getStatus(), Toast.LENGTH_SHORT).show();
        }

        AppConstant.registerData =data;
        Intent intent =new Intent(SignUpActivity.this, OTPActivity.class);
        intent.putExtra("name", etName.getText().toString());
        intent.putExtra("password", etPassword.getText().toString());
        intent.putExtra("email", etEmail.getText().toString());
        intent.putExtra("phone", etPhone.getText().toString());
        intent.putExtra("country_code", selectedCountryCode);
        intent.putExtra("response_code", "200");

        startActivity(intent);
        finish();

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();

        if(errorCode == 403) {
            Intent intent = new Intent(SignUpActivity.this, OTPActivity.class);
            intent.putExtra("name", etName.getText().toString());
            intent.putExtra("password", etPassword.getText().toString());
            intent.putExtra("email", etEmail.getText().toString());
            intent.putExtra("phone", etPhone.getText().toString());
            intent.putExtra("country_code", selectedCountryCode);
            intent.putExtra("response_code", "403");

            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onFailure() {

    }

    private boolean isValid()
    {
        boolean bool=true;
        if(TextUtils.isEmpty(etName.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_name), Toast.LENGTH_SHORT).show();
           // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }
        else if(AppUtils.countWordsUsingSplit(etName.getText().toString()) <= 1)
        {

             bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_full_name), Toast.LENGTH_SHORT).show();

        }

        else if(TextUtils.isEmpty(etEmail.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_email), Toast.LENGTH_SHORT).show();

            //L.showSnackbar(llLogin, getString(R.string.toast_pwd));
        }
        else if(! AppUtils.validEmail(etEmail.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_valid_email), Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(etPhone.getText().toString()))
        { bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_phone), Toast.LENGTH_SHORT).show();

        }else if(etPhone.getText().toString().length() < 10)
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_phone_length), Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(etCompanyName.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_comapany_name), Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(etURL.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_comapany_url), Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(etPassword.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_password), Toast.LENGTH_SHORT).show();

        }else if(!isValidPassword(etPassword.getText().toString())) {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_pwd_match), Toast.LENGTH_SHORT).show();

        }
        return bool;
    }
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
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

}
