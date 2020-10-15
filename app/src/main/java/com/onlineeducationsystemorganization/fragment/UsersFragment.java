package com.onlineeducationsystemorganization.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.AddUserActivity;
import com.onlineeducationsystemorganization.EditUserActivity;
import com.onlineeducationsystemorganization.MainActivity;
import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.adapter.UserListAdapter;
import com.onlineeducationsystemorganization.interfaces.DeleteItemInCart;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.UpdateItemInCart;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.UserList;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class UsersFragment extends BaseFragment implements NetworkListener , UpdateItemInCart, DeleteItemInCart {

    private RecyclerView recyclerView;
    private TextView tvNoRecord;
    private View view;
    private UserList data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_users, container, false);

        initUI();
        return view;
    }

    private void initUI()
    {
        tvNoRecord =view.findViewById(R.id.tvNoRecord);
        recyclerView=view.findViewById(R.id.recyclerView);

        ((MainActivity)activity).imgAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(activity, AddUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppUtils.isInternetAvailable(activity)) {
            getUsers();
        }
    }

    private void getUsers()
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<UserList> call = apiInterface.userList(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.USER_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.USER_LIST) {
            data=null;
            data = (UserList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                tvNoRecord.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                UserListAdapter cartListAdapter= new UserListAdapter(activity, data.getData().getUsers(),this,this);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(cartListAdapter);
            }
        }else if(requestCode == ServerConstents.DEFUALT_CAT)
        {
            BaseBean data = (BaseBean) response;
            Toast.makeText(activity, data.getMessage(), Toast.LENGTH_SHORT).show();
            getUsers();
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if(requestCode == ServerConstents.USER_LIST) {
            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else if(requestCode == ServerConstents.DEFUALT_CAT)
        {

        }
    }

    @Override
    public void onFailure() {
        tvNoRecord.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void updateCart(int pos, String pos2) {

        Intent intent=new Intent(activity, EditUserActivity.class);
        intent.putExtra("user_id", pos+"");
        startActivity(intent);

    }

    @Override
    public void deleteCart(int pos) {
        if (AppUtils.isInternetAvailable(activity)) {
            deleteUser(pos);
        }
    }
    private void deleteUser(int id)
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        final HashMap params = new HashMap<>();
        params.put("user_id", id);
        Call<BaseBean> call = apiInterface.deleteUser(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.DEFUALT_CAT);

    }
}