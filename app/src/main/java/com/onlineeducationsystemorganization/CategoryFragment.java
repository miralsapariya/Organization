package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.CategoryAdapter;
import com.onlineeducationsystemorganization.adapter.GridTopCategoryAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.OnSubItemClick;
import com.onlineeducationsystemorganization.model.Category;
import com.onlineeducationsystemorganization.model.User1;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi1;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;


public class CategoryFragment extends BaseActivity implements OnItemClick, NetworkListener {

    private RecyclerView recyclerView,rvSubCat;
    private TextView tvNameCategory;
    Category data;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        init();
    }

    @Override
    public void onGridClick(int pos) {

        Intent intent = new Intent(CategoryFragment.this, SubCategoryActivity.class);
        intent.putExtra("cat_id", data.getData().get(0).getAllCategoriesList().get(pos).getId()+"");
        intent.putExtra("cat_name", data.getData().get(0).getAllCategoriesList().get(pos).getCategoryName());
        startActivity(intent);
    }

    private void init()
    {

        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (AppUtils.isInternetAvailable(CategoryFragment.this)) {
            getCategory();
        }

        recyclerView =findViewById(R.id.recyclerView);
        rvSubCat =findViewById(R.id.rvSubCat);
        tvNameCategory =findViewById(R.id.tvNameCategory);

    }

    private void getCategory()
    {
        String lang="";
        AppUtils.showDialog(CategoryFragment.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.
                getConnection(ApiInterface.class, ServerConstents.API_URL1);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(CategoryFragment.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(CategoryFragment.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<User1> call = apiInterface.categoryList(lang,params);
        ApiCall.getInstance().hitService(CategoryFragment.this, call, this, ServerConstents.CATEGORY);
    }




    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.CATEGORY) {
            data= (Category) response;
            tvNameCategory.setText(data.getData().get(0).getCategoryLabel());
            //top cat
            int numberOfColumns = 3;
            recyclerView.setLayoutManager(new GridLayoutManager(CategoryFragment.this, numberOfColumns));
            GridTopCategoryAdapter adapter = new GridTopCategoryAdapter(CategoryFragment.this, data.getData().get(0).getTopCategoriesList(), this);
            recyclerView.setAdapter(adapter);

            //lower cat
            CategoryAdapter homeAdapter =
                    new CategoryAdapter(CategoryFragment.this, data.getData().get(0).getAllCategoriesList(), new OnSubItemClick() {
                        @Override
                        public void onSubGridClick(int pos) {

                        }
                    });

            RecyclerView.LayoutManager mLayoutManager =
                    new LinearLayoutManager(CategoryFragment.this);
            rvSubCat.setLayoutManager(mLayoutManager);
            rvSubCat.setItemAnimator(new DefaultItemAnimator());
            rvSubCat.setHasFixedSize(true);
            rvSubCat.setAdapter(homeAdapter);
        }

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        Toast.makeText(CategoryFragment.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }
}
