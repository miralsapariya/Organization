package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.TrendingCourseAdapter;
import com.onlineeducationsystemorganization.interfaces.AddItemInCart;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.CourseList;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.RestApi1;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

public class TrendingCourseActivity extends BaseActivity implements OnItemClick, AddItemInCart, NetworkListener {


    private TrendingCourseAdapter trendingCourseAdapter;
    private RecyclerView rvTrendingCourse;
    private String title="",from="",subcat_id="";
    private ImageView imgBack;
    private TextView tvTitle,tvNoData;
    private ArrayList<CourseList.Courseslist> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_course);

        initToolBar();
        initUI();

    }

    private void initToolBar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvTitle =findViewById(R.id.tvTitle);
    }

    @Override
    public void onGridClick(int pos) {


        Intent intent =new Intent(TrendingCourseActivity.this,CourseDetailActivity.class);
        intent.putExtra("course_id", courseList.get(pos).getId()+"");
        startActivity(intent);

    }

    private void initUI()
    {
        rvTrendingCourse =findViewById(R.id.rvTrendingCourse);
        tvNoData=findViewById(R.id.tvNoData);
        title = getIntent().getExtras().getString("title");
        from =getIntent().getExtras().getString("from");

        subcat_id=getIntent().getExtras().getString("subcat_id");


        tvTitle.setText(title);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (AppUtils.isInternetAvailable(TrendingCourseActivity.this)) {
                hintCourseList();
        }



    }
    private void hintAddToCart(int pos)
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();

        params.put("course_id",courseList.get(pos).getId()+"");
        if (AppSharedPreference.getInstance().getString(TrendingCourseActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(TrendingCourseActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<BaseBean> call = apiInterface.addToCart(lang,AppSharedPreference.getInstance().
                getString(TrendingCourseActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(TrendingCourseActivity.this, call, this, ServerConstents.CART);
    }

    private void hintCourseList()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);
        final HashMap params = new HashMap<>();

        if (AppSharedPreference.getInstance().getString(TrendingCourseActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(TrendingCourseActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        if(from.equals("new_courses"))
        {
            params.put("is_trending", "0");
        }else if(from.equals("trending_courses"))
        {
            params.put("is_trending", "1");
        }else
        {
            params.put("sub_category_id", subcat_id);
        }

        Call<CourseList> call = apiInterface.getCourseList(lang,AppSharedPreference.getInstance().
                getString(TrendingCourseActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(TrendingCourseActivity.this, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        if(requestCode == ServerConstents.CART){

            BaseBean baseBean =(BaseBean) response;
            Toast.makeText(TrendingCourseActivity.this, baseBean.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }else {
            CourseList data = (CourseList) response;
            courseList = new ArrayList<>();
            courseList.addAll(data.getData().get(0).getCourseslist());

            if (data.getData().get(0).getCourseslist().size() > 0) {
                rvTrendingCourse.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);

                trendingCourseAdapter = new TrendingCourseAdapter(TrendingCourseActivity.this, data.getData().get(0).getCourseslist(), this, this);
                rvTrendingCourse.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(TrendingCourseActivity.this);
                rvTrendingCourse.setLayoutManager(manager);
                rvTrendingCourse.setAdapter(trendingCourseAdapter);
            } else {
                rvTrendingCourse.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

        if(requestCode == ServerConstents.CART){
            Toast.makeText(TrendingCourseActivity.this, response, Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(TrendingCourseActivity.this, response, Toast.LENGTH_SHORT).show();
            rvTrendingCourse.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onFailure() {

    }

    @Override
    public void addToCart(int pos) {

        if (AppUtils.isInternetAvailable(TrendingCourseActivity.this)) {
            if (AppSharedPreference.getInstance().getString(TrendingCourseActivity.this, AppSharedPreference.USERID) == null) {
                AppUtils.loginAlert(TrendingCourseActivity.this);
            }else {
                hintAddToCart(pos);
            }
        }

    }
}
