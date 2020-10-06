package com.onlineeducationsystemorganization.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.onlineeducationsystemorganization.MainActivity;
import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.adapter.HomeAdapter;
import com.onlineeducationsystemorganization.adapter.ImageAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.OnViewAllClick;
import com.onlineeducationsystemorganization.model.Home;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi1;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;


public class HomeFragment extends BaseFragment  implements OnItemClick, OnViewAllClick, NetworkListener {

    private View view;
    private ImageAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private HomeAdapter homeAdapter;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_home, container, false);

        //initViewPager();
        iniUI();

        return  view;
    }

    private void iniUI()
    {
         viewPager = view.findViewById(R.id.view_pager);

        if (AppUtils.isInternetAvailable(activity)) {
                hintHome();
        }


        recyclerView =view.findViewById(R.id.recyclerView);

    }

    private void hintHome()
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<Home> call = apiInterface.getHome(lang,params);

        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.HOME);

    }

    @Override
    public void onGridClick(int pos) {

    }



    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        Home data=(Home) response;
        if(data.getStatus()==ServerConstents.CODE_SUCCESS)
        {
            adapter = new ImageAdapter(activity,data.getData().get(0).getBannersList());
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);

            //

            data.getData().remove(0);
            HomeAdapter homeAdapter =
                    new HomeAdapter(activity, data.getData(),this,this);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager manager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(homeAdapter);

        }

    }

    @Override
    public void onViewAll() {
        ((MainActivity)activity).gotoCategory();
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }
}

