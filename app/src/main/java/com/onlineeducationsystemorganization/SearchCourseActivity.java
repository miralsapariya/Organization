package com.onlineeducationsystemorganization;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.CourseListAdapter;
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

public class SearchCourseActivity extends BaseActivity implements  NetworkListener, OnItemClick {


    private RecyclerView recyclerView;
    private TextView tvNoRecord;
    private Courses data;
    private EditText etSearch;
    private CoordinatorLayout llMain;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    private void initUI()
    {
        recyclerView =findViewById(R.id.recyclerView);
        tvNoRecord=findViewById(R.id.tvNoRecord);
        etSearch =findViewById(R.id.etSearch);
        etSearch =findViewById(R.id.etSearch);
        llMain=findViewById(R.id.llMain);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (AppSharedPreference.getInstance().getString(SearchCourseActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                            AppSharedPreference.getInstance().getString(SearchCourseActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                        if(event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            etSearch.setText("");
                            return true;
                        }
                    }else {
                        if(event.getRawX() <= (etSearch.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()+45)){
                            etSearch.setText("");
                            return true;

                        }
                    }
                }
                return false;
            }
        });

        etSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard();
                    if(!TextUtils.isEmpty(etSearch.getText().toString())) {

            getCourses(etSearch.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etSearch.setText("");

    }

    private void getCourses(String search)
    {
        String lang="";
        AppUtils.showDialog(SearchCourseActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("user_id", AppSharedPreference.getInstance().getString(SearchCourseActivity.this, AppSharedPreference.USERID));
        params.put("search_keyword", search);
        if (AppSharedPreference.getInstance().getString(SearchCourseActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SearchCourseActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<Courses> call = apiInterface.courseList(lang,AppSharedPreference.getInstance().
                getString(SearchCourseActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(SearchCourseActivity.this, call, this, ServerConstents.COURSE_LIST);
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
                    CourseListAdapter cartListAdapter= new CourseListAdapter(SearchCourseActivity.this, data.getData().get(0).getCourseslist(),this);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    LinearLayoutManager manager = new LinearLayoutManager(SearchCourseActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(cartListAdapter);
                }
            }
        }
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

    }

    @Override
    public void onGridClick(int pos) {

    }

}
