package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.onlineeducationsystemorganization.adapter.ExpandedUserCourseDetail;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnChildItemClick;
import com.onlineeducationsystemorganization.model.SectionCourse;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class UserCourseDetailActivity extends BaseActivity implements OnChildItemClick, NetworkListener {

    private ExpandableListView expandableView;
    private String course_id;
    SectionCourse data;
    private TextView tvCourseTitle,tvNoOfSection;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_course_detail);
        initToolbar();
        initUI();
    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void getCourseDetail()
    {
        String lang="";
        AppUtils.showDialog(UserCourseDetailActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        if (AppSharedPreference.getInstance().getString(UserCourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(UserCourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<SectionCourse> call = apiInterface.startCourse(lang,AppSharedPreference.getInstance().
                getString(UserCourseDetailActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(UserCourseDetailActivity.this, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            data = (SectionCourse) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                expandableView =findViewById(R.id.expandableView);
                ExpandedUserCourseDetail expandedCourseDetail =new
                        ExpandedUserCourseDetail
                        (UserCourseDetailActivity.this, data.getData().get(0).getCourseslist(),this);
                expandableView.setAdapter(expandedCourseDetail);
                expandableView.expandGroup(0);
                expandedCourseDetail.notifyDataSetChanged();

                tvCourseTitle.setText(data.getData().get(0).getSectionTitle());
                tvNoOfSection.setText(data.getData().get(0).getTotalsections());
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }

    private void initUI()
    {
        tvCourseTitle =findViewById(R.id.tvCourseTitle);
        tvNoOfSection =findViewById(R.id.tvNoOfSection);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        course_id=getIntent().getExtras().getString("course_id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtils.isInternetAvailable(UserCourseDetailActivity.this)) {
            getCourseDetail();
        }else {
            AppUtils.showAlertDialog(UserCourseDetailActivity.this,getString(R.string.no_internet),getString(R.string.alter_net));
        }
    }

    @Override
    public void onChildClick(int pos, int childPos) {

        Intent intent = new Intent(UserCourseDetailActivity.this,LessionSlideActivity.class);
        intent.putExtra("course_id", course_id);
        intent.putExtra("section_id", data.getData().get(0).getCourseslist().get(pos).getSectionId()+"");
        intent.putExtra("slide_id", data.getData().get(0).getCourseslist().get(pos).getSectionSlideDetails().get(childPos).getSlideId()+"");
        startActivity(intent);
    }
}
