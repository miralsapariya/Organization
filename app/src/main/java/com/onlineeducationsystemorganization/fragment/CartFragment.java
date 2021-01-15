package com.onlineeducationsystemorganization.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.ThankYouInquiry;
import com.onlineeducationsystemorganization.adapter.CartListAdapter;
import com.onlineeducationsystemorganization.interfaces.DeleteItemInCart;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.UpdateItemInCart;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.CartList;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class CartFragment extends BaseFragment implements NetworkListener
        ,OnItemClick, DeleteItemInCart, UpdateItemInCart
{
    private RecyclerView recyclerView;
    private TextView tvNoRecord;
    private View view;
    private Button btnInquire;
    private CartList  data;
    private TextView tvNoOfItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_cart, container, false);

        init();
        return view;
    }
    private void init()
    {
        tvNoRecord =view.findViewById(R.id.tvNoRecord);
        tvNoOfItem =view.findViewById(R.id.tvNoOfItem);
        btnInquire =view.findViewById(R.id.btnInquire);
        btnInquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(activity)) {
                    callInquire();
                }else {
                    AppUtils.showAlertDialog(activity,activity.getString(R.string.no_internet),activity.getString(R.string.alter_net));
                }
            }
        });
        recyclerView=view.findViewById(R.id.recyclerView);
        if (AppUtils.isInternetAvailable(activity)) {
            getCartList();
        }else {
            AppUtils.showAlertDialog(activity,activity.getString(R.string.no_internet),activity.getString(R.string.alter_net));
        }
    }

    private void callInquire()
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
        Call<BaseBean> call = apiInterface.addInquiry(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.INQUIRY);

    }
    private void getCartList()
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<CartList> call = apiInterface.getCartList(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.CART);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.CART) {
            data = (CartList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                tvNoRecord.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                tvNoOfItem.setText(data.getData().get(0).getCartItemQty()+" "+activity.getString(R.string.items));
                CartListAdapter  cartListAdapter= new CartListAdapter(activity, data.getData().get(0).getList(),this,this,this);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(cartListAdapter);
            }
        }else if(requestCode == ServerConstents.UPDATE)
        {
            BaseBean data = (BaseBean) response;
            Toast.makeText(activity, data.getMessage(),Toast.LENGTH_SHORT).show();
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                getCartList();
            }
        }else if(requestCode == ServerConstents.INQUIRY)
        {
            BaseBean data = (BaseBean) response;
            Toast.makeText(activity, data.getMessage(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, ThankYouInquiry.class);
            startActivity(intent);

        }
            else
        {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                getCartList();
            }
        }
    }

    @Override
    public void updateCart(int pos, String pos2) {
        if (AppUtils.isInternetAvailable(activity)) {
            updateCartList(data.getData().get(0).getList().get(pos).getCourseid()+"",pos2);
        }else {
            AppUtils.showAlertDialog(activity,activity.getString(R.string.no_internet),activity.getString(R.string.alter_net));
        }
    }

    @Override
    public void onGridClick(int pos) {

    }


    public void updateCartList(String courseId, String noOfUser) {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", courseId);
        params.put("no_of_user", noOfUser);
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.updateCart(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.UPDATE);

    }
    @Override
    public void deleteCart(int pos) {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("cart_id", data.getData().get(0).getList().get(pos).getCartid());
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.deleteFromCart(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.DELETE);

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if(requestCode == ServerConstents.CART) {
            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            tvNoOfItem.setText("0 "+activity.getString(R.string.items));
        }else
        {
            Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure() {

    }
}