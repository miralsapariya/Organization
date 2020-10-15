package com.onlineeducationsystemorganization;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.CourseListUserEditAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.Courses;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class SelectCoursesEditUserActivity extends BaseActivity
        implements NetworkListener, OnItemClick {

    private ImageView imgBack;
    private RecyclerView recyclerView;
    private TextView tvNoRecord,tvDone;
    private Courses data;
    private String  user_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }
    private void initUI()
    {
        user_id = getIntent().getExtras().getString("user_id");

        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvNoRecord =findViewById(R.id.tvNoRecord);
        recyclerView=findViewById(R.id.recyclerView);
        tvDone =findViewById(R.id.tvDone);
        if (AppUtils.isInternetAvailable(SelectCoursesEditUserActivity.this)) {
            getCourses();
        }
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.ASSIGN_COURSES="";
                AppConstant.ASSIGN_COURSES_Id="";
                int cnt=0;
                /*for(int i=0;i<data.getData().get(0).getCourseslist().size();i++)
                {
                    Log.d("IS SELCTED :::: ", ""+data.getData().get(0).getCourseslist().get(i).isSelected());
                }*/
                StringBuilder sb=new StringBuilder();
                StringBuilder sb1=new StringBuilder();
                for(int i=0;i<data.getData().get(0).getCourseslist().size();i++)
                {
                    if(data.getData().get(0).getCourseslist().get(i).isSelected())
                    {
                        cnt=cnt+ (cnt+1);
                        Log.d("IS SELCTED :::: ", ""+data.getData().get(0).getCourseslist().get(i).isSelected()+" "+cnt);
                        sb=sb.append(data.getData().get(0).getCourseslist().get(i).getId()).append(",");
                        sb1=sb1.append(data.getData().get(0).getCourseslist().get(i).getCourseName()).append(",");

                    }
                }
                if(cnt >0) {
                    AppConstant.ASSIGN_COURSES_Id = sb.deleteCharAt(sb.length() - 1).toString();
                    AppConstant.ASSIGN_COURSES = sb1.deleteCharAt(sb1.length() - 1).toString();

                Log.d(";;;;;; ",  AppConstant.ASSIGN_COURSES_Id);
                }
                finish();
            }
        });

    }
    private void getCourses()
    {
        String lang="";
        AppUtils.showDialog(SelectCoursesEditUserActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("user_id", user_id);
        if (AppSharedPreference.getInstance().getString(SelectCoursesEditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SelectCoursesEditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<Courses> call = apiInterface.courseList(lang,AppSharedPreference.getInstance().
                getString(SelectCoursesEditUserActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(SelectCoursesEditUserActivity.this, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            data = (Courses) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                if(data.getData().size() ==0)
                {
                    tvNoRecord.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else
                {
                    tvNoRecord.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    CourseListUserEditAdapter cartListAdapter= new CourseListUserEditAdapter(SelectCoursesEditUserActivity.this, data.getData().get(0).getCourseslist());
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    LinearLayoutManager manager = new LinearLayoutManager(SelectCoursesEditUserActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(cartListAdapter);
                }
            }
        }
    }

    @Override
    public void onGridClick(int pos) {

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {
        tvNoRecord.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}