package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.onlineeducationsystemorganization.adapter.HomeSearchAdapter;
import com.onlineeducationsystemorganization.adapter.SearchResultAdapter;
import com.onlineeducationsystemorganization.interfaces.AddItemInCart;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.DefaultCategory;
import com.onlineeducationsystemorganization.model.GlobalSearch;
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
public class SearchResultActivity extends AppCompatActivity
        implements NetworkListener, OnItemClick,  AddItemInCart, SwipeRefreshLayout.OnRefreshListener{

    String searchKeyword="",cat_id="";
    private ImageView imgBack;
    private RecyclerView rvSearch,recyclerView;
    private TextView tvNoData;
    private SearchResultAdapter searchResultAdapter;
    private ArrayList<GlobalSearch.Courseslist> list;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    private void initUI()
    {
        searchKeyword= getIntent().getStringExtra("searchKeyword");
        cat_id =getIntent().getStringExtra("cat_id");
        recyclerView=findViewById(R.id.recyclerView);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvSearch=findViewById(R.id.rvSearch);
        tvNoData =findViewById(R.id.tvNoData);

     //   swipeRefresh = findViewById(R.id.swipeRefresh);
        //swipeRefresh.setOnRefreshListener(this);

        rvSearch.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(SearchResultActivity.this);
        rvSearch.setLayoutManager(mLayoutManager);
        searchResultAdapter = new SearchResultAdapter(SearchResultActivity.this
                , new ArrayList<GlobalSearch.Courseslist> () , this,this);
        rvSearch.setAdapter(searchResultAdapter);

        if (AppUtils.isInternetAvailable(SearchResultActivity.this)) {
            hintGetSearchResult();
        }

    }
    private void getDefaultCategory() {
        if (AppUtils.isInternetAvailable(SearchResultActivity.this)) {
            hintDefulatCategory();
        }
    }
    private void hintDefulatCategory() {
        String lang = "";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);

        if (AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<DefaultCategory> call = apiInterface.getDefaultCategory(lang);
        ApiCall.getInstance().hitService(SearchResultActivity.this, call, this, ServerConstents.DEFUALT_CAT);
    }
    private void hintGetSearchResult()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);

        if (AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else {
            lang= AppConstant.ARABIC_LANG;
        }
        final HashMap params = new HashMap<>();
        if(searchKeyword == null ||  searchKeyword.equals(""))
        {

        }else {
            params.put("search_keyword", searchKeyword);
        }

        if(cat_id == null || cat_id.equals(""))
        {

        }else
        {
            params.put("cat_id", cat_id);
        }
      //  page_no
      //  page_limit
        Call<GlobalSearch> call = apiInterface.getDefaultCategory(lang,AppSharedPreference.getInstance().
                getString(SearchResultActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(SearchResultActivity.this, call, this, ServerConstents.CATEGORY);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.CART){

            BaseBean baseBean =(BaseBean) response;
            Toast.makeText(SearchResultActivity.this, baseBean.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }else if (requestCode == ServerConstents.DEFUALT_CAT) {
            final DefaultCategory data = (DefaultCategory) response;
            HomeSearchAdapter homeSearchAdapter =
                    new HomeSearchAdapter(SearchResultActivity.this, data.getData().get(0).getCategories(), new OnItemClick() {
                        @Override
                        public void onGridClick(int pos) {
                            Intent intent = new Intent(SearchResultActivity.this, SearchResultActivity.class);
                            intent.putExtra("cat_id", data.getData().get(0).getCategories().get(pos).getId() + "");
                            startActivity(intent);
                            finish();
                        }
                    });
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager manager = new LinearLayoutManager(SearchResultActivity.this);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(homeSearchAdapter);
        }else {
            GlobalSearch data = (GlobalSearch) response;
            list = new ArrayList<>();
            list.addAll(data.getData().get(0).getCourseslist());

            if (data.getData().get(0).getCourseslist().size() > 0) {
                rvSearch.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);

                searchResultAdapter = new SearchResultAdapter
                        (SearchResultActivity.this, data.getData().get(0).getCourseslist(), this, this);
                rvSearch.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(SearchResultActivity.this);
                rvSearch.setLayoutManager(manager);
                rvSearch.setAdapter(searchResultAdapter);
                getDefaultCategory();
            } else {
                rvSearch.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
                getDefaultCategory();
            }
        }
    }

    @Override
    public void onGridClick(int pos) {

        Intent intent =new Intent(SearchResultActivity.this,CourseDetailActivity.class);
        intent.putExtra("course_id", list.get(pos).getId()+"");
        startActivity(intent);
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

        if(requestCode == ServerConstents.CART){
            Toast.makeText(SearchResultActivity.this, response, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(SearchResultActivity.this, response, Toast.LENGTH_SHORT).show();
            rvSearch.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
            getDefaultCategory();
        }
    }

    @Override
    public void onFailure() {

    }
    private void hintAddToCart(int pos)
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();

        params.put("course_id",list.get(pos).getId()+"");
        if (AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<BaseBean> call = apiInterface.addToCart(lang,AppSharedPreference.getInstance().
                getString(SearchResultActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(SearchResultActivity.this, call, this, ServerConstents.CART);
    }
    @Override
    public void addToCart(int pos) {

        if (AppUtils.isInternetAvailable(SearchResultActivity.this)) {
            if (AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.USERID) == null) {
                AppUtils.loginAlert(SearchResultActivity.this);
            }else {
                hintAddToCart(pos);
            }
        }

    }
}
