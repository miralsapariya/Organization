package com.onlineeducationsystemorganization;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.Inquirie;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class EditInquiryActivity extends BaseActivity implements NetworkListener {

    private TextView tvCategory,tvSubCategory,tvCourses;
    private EditText etNoOfUser,etPrice;
    private Inquirie.Inquirylist data;
    private Button btnSubmit,btnReset;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inquiry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();

    }
    private void initUI()
    {
        data=(Inquirie.Inquirylist)getIntent().getSerializableExtra("data");
        tvCategory=findViewById(R.id.tvCategory);
        tvSubCategory=findViewById(R.id.tvSubCategory);
        tvCourses=findViewById(R.id.tvCourses);
        etNoOfUser=findViewById(R.id.etNoOfUser);
        etPrice=findViewById(R.id.etPrice);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setData();
        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(EditInquiryActivity.this)) {
                    if(!TextUtils.isEmpty(etNoOfUser.getText().toString()) && Integer.parseInt(etNoOfUser.getText().toString()) != 0)
                    editUser();
                    else
                        Toast.makeText(EditInquiryActivity.this, getString(R.string.toast_no_of_user), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
            }
        });

        etNoOfUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if(etNoOfUser.getText().toString().length() >0) {
                    double u = Integer.parseInt(etNoOfUser.getText().toString()) *data.getSinglePrice();
                    Log.d("==== ", Integer.parseInt(etNoOfUser.getText().toString()) + " " + data.getPrice());
                    Log.d("------------", u + "");
                    etPrice.setText(u + "");
                    if(Integer.parseInt(etNoOfUser.getText().toString()) == 0)
                    {
                        etNoOfUser.setText("" );
                        etPrice.setText("");
                    }
                }

                else{
                    etPrice.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

    }
    private void editUser()
    {
        String lang="";
        AppUtils.showDialog(EditInquiryActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", data.getCourseId()+"");
        params.put("inquiry_id", data.getInquiryId()+"");
        params.put("no_of_user", etNoOfUser.getText().toString());
        if (AppSharedPreference.getInstance().getString(EditInquiryActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(EditInquiryActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.editInquiry(lang,AppSharedPreference.getInstance().
                getString(EditInquiryActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(EditInquiryActivity.this, call, this, ServerConstents.COURSE_LIST);
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
         BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                Toast.makeText(EditInquiryActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        Toast.makeText(EditInquiryActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }

    private void setData()
    {
        tvCategory.setText(data.getCategory());
        tvSubCategory.setText(data.getSubCategory());
        tvCourses.setText(data.getCourseName());
       // etNoOfUser.setText(data.getNoOfUser() +"");
       // etPrice.setText(data.getPrice()+"");
    }
}