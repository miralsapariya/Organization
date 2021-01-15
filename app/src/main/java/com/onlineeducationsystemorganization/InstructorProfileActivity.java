package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.HomeAdapterInstructor;
import com.onlineeducationsystemorganization.adapter.InstructorProfileCoursesAdpter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnInstructorsClick;
import com.onlineeducationsystemorganization.model.InstructorProfile;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi1;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class InstructorProfileActivity extends BaseActivity implements
        OnInstructorsClick, NetworkListener
{
    private InstructorProfileCoursesAdpter instructorProfileCoursesAdpter;
    private HomeAdapterInstructor popularInstructorAdapter;
    private RecyclerView rvCourses,rvPopularInsructor;
    private ImageView imgUser;
    private TextView tvCourses,tvSubscriber,tvCatgory,tvName,tvDescription;
    private String instructor_id="";
    private InstructorProfile data;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_profile);
        initUI();
    }

    private void initUI()
    {
        instructor_id =getIntent().getExtras().getString("instructor_id");

        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgUser =findViewById(R.id.imgUser);
        tvCourses =findViewById(R.id.tvCourses);
        tvSubscriber =findViewById(R.id.tvSubscriber);
        tvCatgory=findViewById(R.id.tvCatgory);
        tvName =findViewById(R.id.tvName);
        tvDescription =findViewById(R.id.tvDescription);
        rvCourses =findViewById(R.id.rvCourses);

        rvPopularInsructor =findViewById(R.id.rvPopularInsructor);
        //
        if (AppUtils.isInternetAvailable(InstructorProfileActivity.this)) {
            getList();
        }else {
            AppUtils.showAlertDialog(InstructorProfileActivity.this,getString(R.string.no_internet),getString(R.string.alter_net));
        }
    }

    private void getList()
    {
        String lang = "";
        AppUtils.showDialog(InstructorProfileActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);
        final HashMap params = new HashMap<>();
        params.put("instructor_id",instructor_id);

        if (AppSharedPreference.getInstance().getString(InstructorProfileActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(InstructorProfileActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<InstructorProfile> call = apiInterface.getInstructorProfile(lang, AppSharedPreference.getInstance().
                getString(InstructorProfileActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(InstructorProfileActivity.this,
                call, this, ServerConstents.WHISH_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.WHISH_LIST) {
            data = (InstructorProfile) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                AppUtils.loadImageWithPicasso(data.getData().get(0).getProfile().getProfilePicture() , imgUser, InstructorProfileActivity.this, 0, 0);

                tvCourses.setText(data.getData().get(0).getProfile().getTotalCourse()+"");
                tvSubscriber.setText(data.getData().get(0).getProfile().getTotalStudents()+"");
                tvCatgory.setText(data.getData().get(0).getProfile().getCategoryName());
                tvName.setText(data.getData().get(0).getProfile().getName());
                // tvDescription.setText(data.getData().get(0).getProfile().getDescription());
                //
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvDescription.setText(Html.fromHtml(data.getData().get(0).getProfile().getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvDescription.setText(Html.fromHtml(data.getData().get(0).getProfile().getDescription()));
                }
                rvCourses.setLayoutManager(new LinearLayoutManager(InstructorProfileActivity.this, LinearLayoutManager.HORIZONTAL, false));
                rvCourses.setHasFixedSize(true);
                rvCourses.setItemAnimator(new DefaultItemAnimator());
                instructorProfileCoursesAdpter =
                        new InstructorProfileCoursesAdpter
                                (InstructorProfileActivity.this, data.getData().get(1).getList(), new OnInstructorsClick() {
                                    @Override
                                    public void onInstructorClick(int pos) {
                                        Intent intent =new Intent(InstructorProfileActivity.this,CourseDetailActivity.class);
                                        intent.putExtra("course_id", data.getData().get(1).getList().get(pos).getId()+"");
                                        startActivity(intent);
                                    }
                                });
                rvCourses.setAdapter(instructorProfileCoursesAdpter);
                //
                popularInstructorAdapter = new
                        HomeAdapterInstructor(InstructorProfileActivity.this,
                        data.getData().get(2).getList(), new OnInstructorsClick() {
                    @Override
                    public void onInstructorClick(int pos) {
                        Intent intent =new Intent(InstructorProfileActivity.this,InstructorProfileActivity.class);
                        intent.putExtra("instructor_id", data.getData().get(2).getList().get(pos).getId()+"");
                        startActivity(intent);
                    }
                });
                rvPopularInsructor.setLayoutManager(new LinearLayoutManager(InstructorProfileActivity.this, LinearLayoutManager.HORIZONTAL, false));
                rvPopularInsructor.setHasFixedSize(true);
                rvPopularInsructor.setItemAnimator(new DefaultItemAnimator());
                rvPopularInsructor.setAdapter(popularInstructorAdapter);
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
    public void onInstructorClick(int pos) {

    }
}
