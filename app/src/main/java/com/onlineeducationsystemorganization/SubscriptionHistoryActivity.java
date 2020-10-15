package com.onlineeducationsystemorganization;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.SubscriptionHistoryAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.SubscriptionHistory;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class SubscriptionHistoryActivity extends BaseActivity implements NetworkListener {

    private ImageView imgBack;
    private TextView tvCategory,tvSubCategory,tvCourses;
    private String inquiry_id;
    SubscriptionHistory  data;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    private void initUI()
    {
        inquiry_id =getIntent().getExtras().getString("inquiry_id");
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

        if (AppUtils.isInternetAvailable(SubscriptionHistoryActivity.this)) {
            getInquiries();
        }
    }

    private void getInquiries()
    {
        String lang="";
        AppUtils.showDialog(SubscriptionHistoryActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("inquiry_id", inquiry_id);
        if (AppSharedPreference.getInstance().getString(SubscriptionHistoryActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SubscriptionHistoryActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<SubscriptionHistory> call = apiInterface.subscriptionListHistory(lang,AppSharedPreference.getInstance().
                getString(SubscriptionHistoryActivity.this,
                        AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(SubscriptionHistoryActivity.this, call, this, ServerConstents.COURSE_LIST);
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            data = (SubscriptionHistory) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                tvCategory.setText(data.getData().get(0).getSubscriptionlist().getCategory());
                tvCourses.setText(data.getData().get(0).getSubscriptionlist().getCourseName());
                tvSubCategory.setText(data.getData().get(0).getSubscriptionlist().getSubCategory());

                SubscriptionHistoryAdapter inquirieAdapter= new
                        SubscriptionHistoryAdapter(SubscriptionHistoryActivity.this, data.getData().get(0).getSubscriptionlist().getSubscriptionhistorylist());
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(SubscriptionHistoryActivity.this);
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