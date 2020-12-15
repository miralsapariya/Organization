package com.onlineeducationsystemorganization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.CompanyUrl;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class CompanyUrlActivity extends AppCompatActivity implements NetworkListener {

    private EditText etURL;
    private LinearLayout llMain;
    private TextView btnNext,tvSignUP;
    private AppSharedPreference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_url);
        initUI();
    }

    private void initUI() {
        preference = AppSharedPreference.getInstance();

        etURL = findViewById(R.id.etURL);
        llMain = findViewById(R.id.llMain);
        btnNext =findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(CompanyUrlActivity.this)) {
                    if (isValid()) {
                        hintComapanyURL();
                    }
                }
            }
        });

        tvSignUP =findViewById(R.id.tvSignUP);
        tvSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyUrlActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void hintComapanyURL()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("subdomain", etURL.getText().toString());

        if (AppSharedPreference.getInstance().getString(CompanyUrlActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(CompanyUrlActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<CompanyUrl> call = apiInterface.companyUrl(lang,params);

        ApiCall.getInstance().hitService(CompanyUrlActivity.this, call, this, ServerConstents.LOGIN);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        CompanyUrl data=(CompanyUrl) response;
        if(data.getStatus()==ServerConstents.CODE_SUCCESS)
        {
            preference.putString(CompanyUrlActivity.this, AppSharedPreference.COMAPANY_USER_ID, data.getData().get(0).getUserId()+"");

            Intent i = new Intent(CompanyUrlActivity.this,
                    LoginActivity.class);
            i.putExtra("company_user_id", data.getData().get(0).getUserId()+"");
            startActivity(i);
            //finish();
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

        Toast.makeText(CompanyUrlActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }

    private boolean isValid() {
        boolean bool = true;
        if (TextUtils.isEmpty(etURL.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(CompanyUrlActivity.this, getString(R.string.toast_company_url), Toast.LENGTH_SHORT).show();
            //L.showSnackbar(llLogin, getString(R.string.toast_pwd));
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