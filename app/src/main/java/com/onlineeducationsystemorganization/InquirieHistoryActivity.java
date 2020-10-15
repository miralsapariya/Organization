package com.onlineeducationsystemorganization;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.InquiryAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.InquiryHistory;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class InquirieHistoryActivity extends BaseActivity implements NetworkListener {

    private ImageView imgBack;
    private TextView tvCategory,tvSubCategory,tvCourses;
    private String course_id;
    InquiryHistory  data;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquirie_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    private void initUI()
    {
        course_id =getIntent().getExtras().getString("course_id");
        recyclerView =findViewById(R.id.recyclerView);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCategory =findViewById(R.id.tvCategory);
        tvSubCategory =findViewById(R.id.tvSubCategory);
        tvCourses=findViewById(R.id.tvCourses);

        if (AppUtils.isInternetAvailable(InquirieHistoryActivity.this)) {
            getInquiries();
        }
    }

    private void getInquiries()
    {
        String lang="";
        AppUtils.showDialog(InquirieHistoryActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        if (AppSharedPreference.getInstance().getString(InquirieHistoryActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(InquirieHistoryActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<InquiryHistory> call = apiInterface.inquireHistory(lang,AppSharedPreference.getInstance().
                getString(InquirieHistoryActivity.this,
                        AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(InquirieHistoryActivity.this, call, this, ServerConstents.COURSE_LIST);
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            data = (InquiryHistory) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                tvCategory.setText(data.getData().get(0).getInquirylist().getCategory());
                tvCourses.setText(data.getData().get(0).getInquirylist().getCourseName());
                tvSubCategory.setText(data.getData().get(0).getInquirylist().getSubCategory());

                InquiryAdapter inquirieAdapter= new
                        InquiryAdapter(InquirieHistoryActivity.this, data.getData().get(0).getInquirylist().getInquiryhistorylist());
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(InquirieHistoryActivity.this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(inquirieAdapter);
            }
        }

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }
}