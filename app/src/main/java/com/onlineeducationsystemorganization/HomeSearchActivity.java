package com.onlineeducationsystemorganization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.onlineeducationsystemorganization.adapter.HomeSearchAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.DefaultCategory;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi1;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import retrofit2.Call;

public class HomeSearchActivity extends BaseActivity implements OnItemClick, NetworkListener {

    private RecyclerView recyclerView;
    private EditText etSearch;
    private HomeSearchAdapter homeSearchAdapter;
    DefaultCategory data;
    private CoordinatorLayout llMain;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initToolbar();
        initUI();
    }

    private void initUI()
    {
        recyclerView =findViewById(R.id.recyclerView);
        llMain =findViewById(R.id.llMain);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getDefaultCategory();
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.DEFUALT_CAT) {

            data= (DefaultCategory) response;

            homeSearchAdapter =
                    new HomeSearchAdapter(HomeSearchActivity.this, data.getData().get(0).getCategories(), this);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager manager = new LinearLayoutManager(HomeSearchActivity.this);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(homeSearchAdapter);
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }

    private void getDefaultCategory()
    {
        if (AppUtils.isInternetAvailable(HomeSearchActivity.this)) {
            hintDefulatCategory();
        }
    }

    private void hintDefulatCategory()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);

        if (AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<DefaultCategory> call = apiInterface.getDefaultCategory(lang);
        ApiCall.getInstance().hitService(HomeSearchActivity.this, call, this, ServerConstents.DEFUALT_CAT);
    }

    @Override
    public void onGridClick(int pos) {
        Intent intent = new Intent(HomeSearchActivity.this, SearchResultActivity.class);
        intent.putExtra("cat_id", data.getData().get(0).getCategories().get(pos).getId()+"");
        startActivity(intent);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initToolbar() {
        etSearch =findViewById(R.id.etSearch);

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                            AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                        if(event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            etSearch.setText("");
                            return true;

                      }
                      }else {

                        Log.d("----cliclcllclcl ", "=== "+event.getRawX() +" "+etSearch.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width());

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


                        Intent intent = new Intent(HomeSearchActivity.this, SearchResultActivity.class);
                        intent.putExtra("searchKeyword", etSearch.getText().toString());
                        startActivity(intent);
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
}
