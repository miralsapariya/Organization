package com.onlineeducationsystemorganization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.InquirieListAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.OnSubItemClick;
import com.onlineeducationsystemorganization.model.Inquirie;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class InquiriesActivity extends AppCompatActivity implements
        NetworkListener, OnItemClick, OnSubItemClick {

    private ImageView imgBack;
    private RecyclerView recyclerView;
    private TextView tvNoRecord;
    private EditText etSearch;
    private LinearLayout llMain;
    Inquirie  data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiries);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();

    }
    private void getInquiries(String search)
    {
        String lang="";
        AppUtils.showDialog(InquiriesActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("search_keyword", search);
        if (AppSharedPreference.getInstance().getString(InquiriesActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(InquiriesActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<Inquirie> call = apiInterface.inquireList(lang,AppSharedPreference.getInstance().
                getString(InquiriesActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(InquiriesActivity.this, call, this, ServerConstents.COURSE_LIST);
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            data = (Inquirie) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                if(data.getData().size() ==0)
                {
                    tvNoRecord.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else
                {
                    tvNoRecord.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    InquirieListAdapter cartListAdapter= new
                            InquirieListAdapter(InquiriesActivity.this, data.getData().get(0).getInquirylist(),this,this);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    LinearLayoutManager manager = new LinearLayoutManager(InquiriesActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(cartListAdapter);
                }
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onGridClick(int pos) {
        Intent intent =new Intent(InquiriesActivity.this,EditInquiryActivity.class);
        intent.putExtra("data", data.getData().get(0).getInquirylist().get(pos));
        startActivity(intent);
    }

    @Override
    public void onSubGridClick(int pos) {
        Intent intent =new Intent(InquiriesActivity.this,InquirieHistoryActivity.class);
        intent.putExtra("course_id", data.getData().get(0).getInquirylist().get(pos).getCourseId()+"");
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtils.isInternetAvailable(InquiriesActivity.this)) {
            getInquiries("");
        }
    }

    private void initUI()
    {
        recyclerView =findViewById(R.id.recyclerView);
        tvNoRecord =findViewById(R.id.tvNoRecord);
        llMain=findViewById(R.id.llMain);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etSearch=findViewById(R.id.etSearch);

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if(cs.toString().length() ==0)
                {
                    Log.d("=====emapty:: ", "========");
                    hideKeyboard();
                    if (AppUtils.isInternetAvailable(InquiriesActivity.this)) {
                        getInquiries("");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
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
                    if (AppSharedPreference.getInstance().getString(InquiriesActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                            AppSharedPreference.getInstance().getString(InquiriesActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                        if(event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                           // etSearch.setText("");
                            hideKeyboard();
                            if(!TextUtils.isEmpty(etSearch.getText().toString())) {
                                getInquiries(etSearch.getText().toString());
                            }
                            return true;
                        }
                    }else {
                        if(event.getRawX() <= (etSearch.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()+45)){
                            //etSearch.setText("");
                            hideKeyboard();
                            if(!TextUtils.isEmpty(etSearch.getText().toString())) {
                                getInquiries(etSearch.getText().toString());
                            }
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

                        getInquiries(etSearch.getText().toString());
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
}