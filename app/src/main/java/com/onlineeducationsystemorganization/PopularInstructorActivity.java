package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.PopularInstructorAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.InstructorList;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi1;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class PopularInstructorActivity extends BaseActivity implements OnItemClick, NetworkListener {

    private RecyclerView rvInstructor;
    private PopularInstructorAdapter popularInstructorAdapter;
    private InstructorList data;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_instructor);

        initToolBar();
        initUI();
    }
    private void initToolBar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initUI()
    {
        rvInstructor =findViewById(R.id.rvInstructor);
        if (AppUtils.isInternetAvailable(PopularInstructorActivity.this)) {
            getList();
        }
    }

    private void getList()
    {
        String lang = "";
        AppUtils.showDialog(PopularInstructorActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);
        final HashMap params = new HashMap<>();

        if (AppSharedPreference.getInstance().getString(PopularInstructorActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(PopularInstructorActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<InstructorList> call = apiInterface.getInstructorList(lang, AppSharedPreference.getInstance().
                getString(PopularInstructorActivity.this, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(PopularInstructorActivity.this,
                call, this, ServerConstents.WHISH_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.WHISH_LIST) {
            data= (InstructorList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                popularInstructorAdapter = new PopularInstructorAdapter(PopularInstructorActivity.this, data.getData().get(0).getPopularInstructorList(), this);
                rvInstructor.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(PopularInstructorActivity.this);
                rvInstructor.setLayoutManager(manager);
                rvInstructor.setAdapter(popularInstructorAdapter);
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onGridClick(int pos) {

        Intent intent =new Intent(PopularInstructorActivity.this,InstructorProfileActivity.class);
        intent.putExtra("instructor_id", data.getData().get(0).getPopularInstructorList().get(pos).getId()+"");
        startActivity(intent);

    }
}
