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

import com.onlineeducationsystemorganization.adapter.SubscriptionListAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnSubItemClick;
import com.onlineeducationsystemorganization.model.Subscription;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class SubscriptionActivity extends AppCompatActivity implements
        NetworkListener, OnSubItemClick {

    private ImageView imgBack;
    private RecyclerView recyclerView;
    private TextView tvNoRecord;
    private EditText etSearch;
    private LinearLayout llMain;
    Subscription  data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();

    }
    private void getSubscription(String search)
    {
        String lang="";
        AppUtils.showDialog(SubscriptionActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("search_keyword", search);
        if (AppSharedPreference.getInstance().getString(SubscriptionActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SubscriptionActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<Subscription> call = apiInterface.subscriptionList(lang,AppSharedPreference.getInstance().
                getString(SubscriptionActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(SubscriptionActivity.this, call, this, ServerConstents.COURSE_LIST);
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            data = (Subscription) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                if(data.getData().size() ==0)
                {
                    tvNoRecord.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else
                {
                    tvNoRecord.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    SubscriptionListAdapter cartListAdapter= new
                            SubscriptionListAdapter(SubscriptionActivity.this, data.getData().get(0).getSubscriptionlist(),this);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    LinearLayoutManager manager = new LinearLayoutManager(SubscriptionActivity.this);
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
    public void onSubGridClick(int pos) {
        Intent intent =new Intent(SubscriptionActivity.this,SubscriptionHistoryActivity.class);
        intent.putExtra("inquiry_id", data.getData().get(0).getSubscriptionlist().get(pos).getId()+"");
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtils.isInternetAvailable(SubscriptionActivity.this)) {
            getSubscription("");
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
                    if (AppUtils.isInternetAvailable(SubscriptionActivity.this)) {
                        getSubscription("");
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
                    if (AppSharedPreference.getInstance().getString(SubscriptionActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                            AppSharedPreference.getInstance().getString(SubscriptionActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
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

                        getSubscription(etSearch.getText().toString());
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