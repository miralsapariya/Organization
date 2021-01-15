package com.onlineeducationsystemorganization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.HomeSearchAdapter;
import com.onlineeducationsystemorganization.adapter.SuggestionArrayAdpter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.DefaultCategory;
import com.onlineeducationsystemorganization.model.Suggestion;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi1;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class HomeSearchActivity extends BaseActivity implements OnItemClick, NetworkListener {

    DefaultCategory data;
    private RecyclerView recyclerView;
    private AppCompatAutoCompleteTextView etSearch;
    private HomeSearchAdapter homeSearchAdapter;
    private CoordinatorLayout llMain;
    private ImageView imgBack;
    private Suggestion suggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initToolbar();
        initUI();
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recyclerView);
        llMain = findViewById(R.id.llMain);
        imgBack = findViewById(R.id.imgBack);
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
        if (requestCode == ServerConstents.DEFUALT_CAT) {
            data = (DefaultCategory) response;
            homeSearchAdapter =
                    new HomeSearchAdapter(HomeSearchActivity.this, data.getData().get(0).getCategories(), this);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager manager = new LinearLayoutManager(HomeSearchActivity.this);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(homeSearchAdapter);
        }else
        {
            suggestion =(Suggestion) response;
            SuggestionArrayAdpter suggestionArrayAdpter=new SuggestionArrayAdpter(HomeSearchActivity.this,suggestion.getData());
          /*  ArrayAdapter
                    <Suggestion.Datum> adapter = new ArrayAdapter<Suggestion.Datum>
                    (this, android.R.layout.select_dialog_item, suggestion.getData());
            */etSearch.setThreshold(1); //will start working from first character
            etSearch.setAdapter(suggestionArrayAdpter);
            suggestionArrayAdpter.notifyDataSetChanged();
            etSearch.showDropDown();
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }

    private void getDefaultCategory() {
        if (AppUtils.isInternetAvailable(HomeSearchActivity.this)) {
            hintDefulatCategory();
        }else {
            AppUtils.showAlertDialog(HomeSearchActivity.this,getString(R.string.no_internet),getString(R.string.alter_net));
        }
    }

    private void hintDefulatCategory() {
        String lang = "";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);

        if (AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<DefaultCategory> call = apiInterface.getDefaultCategory(lang);
        ApiCall.getInstance().hitService(HomeSearchActivity.this, call, this, ServerConstents.DEFUALT_CAT);
    }

    @Override
    public void onGridClick(int pos) {
        Intent intent = new Intent(HomeSearchActivity.this, SearchResultActivity.class);
        intent.putExtra("cat_id", data.getData().get(0).getCategories().get(pos).getId() + "");
        startActivity(intent);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initToolbar() {
        etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() >1) {
                    getSuggestion(s.toString().trim());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
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

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                            AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                        if (event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            etSearch.setText("");
                            return true;
                        }
                    } else {
                        if (event.getRawX() <= (etSearch.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width() + 45)) {
                            etSearch.setText("");
                            return true;

                        }
                    }
                }
                return false;
            }
        });

        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hideKeyboard();
                if (!TextUtils.isEmpty(etSearch.getText().toString())) {
                    if(suggestion.getData().get(i).getType().equalsIgnoreCase("course")) {
                        Intent intent = new Intent(HomeSearchActivity.this, CourseDetailActivity.class);
                        intent.putExtra("course_id", suggestion.getData().get(i).getId()+"");
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(HomeSearchActivity.this, InstructorProfileActivity.class);
                        intent.putExtra("instructor_id", suggestion.getData().get(i).getId()+"");
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
    }
    private void getSuggestion(String s)
    {
        String lang = "";

        AppUtils.showDialog(this, getString(R.string.pls_wait));
        final HashMap params = new HashMap<>();
        params.put("search_keyword", s);
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);
        if (AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(HomeSearchActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<Suggestion> call = apiInterface.getSuggestion(lang, AppSharedPreference.getInstance().
                getString(HomeSearchActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(HomeSearchActivity.this, call, this, ServerConstents.SUGGESTION);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etSearch.setText("");

    }
}
