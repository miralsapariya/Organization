package com.onlineeducationsystemorganization;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.MyCourseList;
import com.onlineeducationsystemorganization.adapter.MyCoursesAdapter;
import com.onlineeducationsystemorganization.interfaces.DownloadClick;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnCardViewClick;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.OnResetCourse;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;
import com.onlineeducationsystemorganization.util.DownloadTask;

import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;

public class MyCoursesActivity extends BaseActivity implements OnItemClick, NetworkListener, OnCardViewClick, DownloadClick, OnResetCourse {
    private static final int PERMISSION_PHOTO = 1;
    MyCourseList data;
    private View view;
    private RecyclerView rvMyCourses;
    private MyCoursesAdapter myCoursesAdapter;
    private TextView tvNoData;
    private String link = "";
    private int courseIDSelect;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    @Override
    public void onResume() {
        super.onResume();
        initUI();
        if (AppSharedPreference.getInstance().getString(MyCoursesActivity.this, AppSharedPreference.LANGUAGE_SELECTED)!= null && AppSharedPreference.getInstance().getString(MyCoursesActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ARABIC_LANG)) {
            String languageToLoad = "ar"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, MyCoursesActivity.this.getBaseContext().getResources().getDisplayMetrics());

        }
    }

    private void initUI() {
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvMyCourses = findViewById(R.id.rvMyCourses);
        tvNoData = findViewById(R.id.tvNoData);
        if (AppUtils.isInternetAvailable(MyCoursesActivity.this)) {
            getMyCourseList();
        }else {
            AppUtils.showAlertDialog(MyCoursesActivity.this,getString(R.string.no_internet),getString(R.string.alter_net));
        }
    }
    private void getMyCourseList() {
        String lang = "";
        AppUtils.showDialog(MyCoursesActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(MyCoursesActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(MyCoursesActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<MyCourseList> call = apiInterface.myCourseList(lang, AppSharedPreference.getInstance().
                getString(MyCoursesActivity.this, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(MyCoursesActivity.this, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.COURSE_LIST) {
            data = (MyCourseList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                myCoursesAdapter = new MyCoursesAdapter(MyCoursesActivity.this
                        , data.getData().get(0).getCourseslist(), this, this, this, this);
                rvMyCourses.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(MyCoursesActivity.this);
                rvMyCourses.setLayoutManager(manager);
                rvMyCourses.setAdapter(myCoursesAdapter);
            }
        } else {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                Intent intent = new Intent(MyCoursesActivity.this, UserCourseDetailActivity.class);
                intent.putExtra("course_id", courseIDSelect + "");
                startActivity(intent);
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if (requestCode == ServerConstents.COURSE_LIST) {
            tvNoData.setVisibility(View.VISIBLE);
            rvMyCourses.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onCardClick(int pos) {
        Intent intent = new Intent(MyCoursesActivity.this, UserCourseDetailActivity.class);
        intent.putExtra("course_id", data.getData().get(0).getCourseslist().get(pos).getCourseId() + "");
        startActivity(intent);
    }

    @Override
    public void onGridClick(int pos) {
        if (AppConstant.COURSE_STATUS_START.equals(data.getData().get(0).getCourseslist().get(pos).getCourse_status())) {
            Intent intent = new Intent(MyCoursesActivity.this, UserCourseDetailActivity.class);
            intent.putExtra("course_id", data.getData().get(0).getCourseslist().get(pos).getCourseId() + "");
            startActivity(intent);
        } else if (AppConstant.COURSE_STATUS_RESUME.equals(data.getData().get(0).getCourseslist().get(pos).getCourse_status())) {
            Intent intent = new Intent(MyCoursesActivity.this, LessionSlideActivity.class);
            intent.putExtra("course_id", data.getData().get(0).getCourseslist().get(pos).getCourseId() + "");
            intent.putExtra("section_id", data.getData().get(0).getCourseslist().get(pos).getNext_section_id() + "");
            intent.putExtra("slide_id", data.getData().get(0).getCourseslist().get(pos).getNext_slide_id() + "");
            startActivity(intent);
        }
    }

    @Override
    public void onDownload(int pos) {
        link = data.getData().get(0).getCourseslist().get(pos).getCertificate_link();
        if (ContextCompat.checkSelfPermission(MyCoursesActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MyCoursesActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyCoursesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
        } else {
            new DownloadTask(MyCoursesActivity.this, data.getData().get(0).getCourseslist().get(pos).getCertificate_link());
        }
    }

    @Override
    public void onReset(int pos) {
        if (AppUtils.isInternetAvailable(MyCoursesActivity.this)) {
            courseIDSelect = data.getData().get(0).getCourseslist().get(pos).getCourseId();
            resetCourse(data.getData().get(0).getCourseslist().get(pos).getCourseId());
        }
    }

    private void resetCourse(int Id) {

        String lang = "";
        AppUtils.showDialog(MyCoursesActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", Id + "");
        if (AppSharedPreference.getInstance().getString(MyCoursesActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(MyCoursesActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.resetCourse(lang, AppSharedPreference.getInstance().
                getString(MyCoursesActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(MyCoursesActivity.this, call, this, ServerConstents.RESET);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_PHOTO:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        new DownloadTask(MyCoursesActivity.this, link);

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }
                break;
        }
    }

}
