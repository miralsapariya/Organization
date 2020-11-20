package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.NotificationAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.NotificationList;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import retrofit2.Call;

public class NotificationActivity extends AppCompatActivity implements NetworkListener {

    public RecyclerView rvNotification;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }
    private void initUI()
    {
        rvNotification =findViewById(R.id.rvNotification);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (AppUtils.isInternetAvailable(
                NotificationActivity.this)) {
            getNotification();
        }
    }
    private void getNotification()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);

        if (AppSharedPreference.getInstance().getString(NotificationActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(NotificationActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<NotificationList> call = apiInterface.getNotification(lang,AppSharedPreference.getInstance().
                getString(NotificationActivity.this, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(NotificationActivity.this, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            final NotificationList data = (NotificationList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                NotificationAdapter inquirieAdapter= new
                        NotificationAdapter(NotificationActivity.this, data.getData(), new OnItemClick() {
                    @Override
                    public void onGridClick(int pos) {
                        if(data.getData().get(pos).getCourseType().equalsIgnoreCase("2"))
                        {
                            Intent intent =new Intent(NotificationActivity.this,DashboardReportActivity.class);
                            intent.putExtra("course_id", data.getData().get(pos).getCourseId()+"");
                            startActivity(intent);
                        }else  if(data.getData().get(pos).getCourseType().equalsIgnoreCase("1"))
                        {
                            Intent intent =new Intent(NotificationActivity.this, MyCoursesActivity.class);
                            startActivity(intent);
                        }else if(data.getData().get(pos).getCourseType().equalsIgnoreCase("3"))
                        {
                            Intent intent =new Intent(NotificationActivity.this, SubscriptionActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                rvNotification.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(NotificationActivity.this);
                rvNotification.setLayoutManager(manager);
                rvNotification.setAdapter(inquirieAdapter);
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