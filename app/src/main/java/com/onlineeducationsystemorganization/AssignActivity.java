package com.onlineeducationsystemorganization;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.CourseAsssignUserAdapter;
import com.onlineeducationsystemorganization.interfaces.DeleteItemInCart;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.AssignCourseUserList;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class AssignActivity extends BaseActivity implements NetworkListener, DeleteItemInCart {

    AssignCourseUserList data;
    StringBuilder sb;
    private ImageView imgBack;
    private String course_id;
    private TextView tvNoRecord;
    private RecyclerView recyclerView;
    private Button btnAssign;
    private int remainig_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initUI();
    }

    private void initUI() {
        course_id = getIntent().getExtras().getString("course_id");
        remainig_user = getIntent().getExtras().getInt("remainig_user");
        imgBack = findViewById(R.id.imgBack);
        recyclerView = findViewById(R.id.recyclerView);
        tvNoRecord = findViewById(R.id.tvNoRecord);
        btnAssign = findViewById(R.id.btnAssign);
        btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidForAssign()) {
                    callAssign();
                }

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtils.isInternetAvailable(AssignActivity.this)) {
            getAssignList();
        }
    }

    private void callAssign() {
        String lang = "";
        AppUtils.showDialog(AssignActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        params.put("user_ids", sb.toString());
        if (AppSharedPreference.getInstance().getString(AssignActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(AssignActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.assignUser(lang, AppSharedPreference.getInstance().
                getString(AssignActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(AssignActivity.this, call, this, ServerConstents.ASSIGN);

    }

    private void callUnAssign(String id) {
        String lang = "";
        AppUtils.showDialog(AssignActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        params.put("user_ids", id);
        if (AppSharedPreference.getInstance().getString(AssignActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(AssignActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.unAssignUser(lang, AppSharedPreference.getInstance().
                getString(AssignActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(AssignActivity.this, call, this, ServerConstents.UNASSIGN);

    }

    private boolean isValidForUnassing(int pos) {
        int checkIsCourseStarted = 0;
        int checkOfAssing = 0;
        int isDeletedUser = 0;
        boolean bool = true;

        if (data.getData().get(0).getUsers().get(pos).getIsCoursestart() == 1) {
            checkIsCourseStarted = checkIsCourseStarted + 1;
        }
        if (data.getData().get(0).getUsers().get(pos).getIsAssign() == 0) {
            checkOfAssing = checkOfAssing + 1;
        }

         if (data.getData().get(0).getUsers().get(pos).getUserDelete() == 1) {
            isDeletedUser = isDeletedUser + 1;
        }
        Log.d("--------------------->", checkIsCourseStarted + " " + checkOfAssing);
        if (checkIsCourseStarted > 0) {
            bool = false;
            Toast.makeText(AssignActivity.this, getString(R.string.toast_started_course), Toast.LENGTH_SHORT).show();
        } else if (checkOfAssing > 0) {
            bool = false;
            Toast.makeText(AssignActivity.this, getString(R.string.toast_assined_user), Toast.LENGTH_SHORT).show();

        } else if (isDeletedUser > 0) {
            bool = false;
            Toast.makeText(AssignActivity.this, getString(R.string.toast_delete_user), Toast.LENGTH_SHORT).show();

        }

        return bool;
    }

    private boolean isValidForAssign() {
        sb = new StringBuilder();

        int checkRemainingCounter = 0;
        int checkOfUnassing = 0;
        int isDeletedUser = 0;
        boolean bool = true;

        for (int i = 0; i < data.getData().get(0).getUsers().size(); i++) {
            if (data.getData().get(0).getUsers().get(i).isSelected()) {
                checkRemainingCounter = checkRemainingCounter + 1;
                sb = sb.append(data.getData().get(0).getUsers().get(i).getId()).append(",");

                if (data.getData().get(0).getUsers().get(i).getIsAssign() == 1) {
                    checkOfUnassing = checkOfUnassing + 1;
                }

                if (data.getData().get(0).getUsers().get(i).getUserDelete() == 1) {
                    isDeletedUser = isDeletedUser + 1;
                }

            }
        }
        if (checkRemainingCounter > remainig_user) {
            bool = false;
            Toast.makeText(AssignActivity.this, getString(R.string.toast_remaing), Toast.LENGTH_SHORT).show();
        } else if (checkOfUnassing > 0) {
            bool = false;
            Toast.makeText(AssignActivity.this, getString(R.string.toast_unassing_user), Toast.LENGTH_SHORT).show();

        } else if (isDeletedUser > 0) {
            bool = false;
            Toast.makeText(AssignActivity.this, getString(R.string.toast_delete_user), Toast.LENGTH_SHORT).show();

        }
        sb.deleteCharAt(sb.length() - 1).toString();
        Log.d("-------------->", checkRemainingCounter +
                " " + checkOfUnassing + " " + sb.toString());

        return bool;
    }

    @Override
    public void deleteCart(int pos) {
        if (isValidForUnassing(pos)) {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(AssignActivity.this,pos);

            //callUnAssign(data.getData().get(0).getUsers().get(pos).getId() + "");
        }
    }
    public  class ViewDialog {

        public void showDialog(Activity activity, final int pos){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_unassign_alert);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button btnApply =  dialog.findViewById(R.id.btnApply);
            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    callUnAssign(data.getData().get(0).getUsers().get(pos).getId() + "");
                }
            });

            Button btnCancel =  dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            dialog.show();
        }
    }

    private void getAssignList() {
        String lang = "";
        AppUtils.showDialog(AssignActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);

        if (AppSharedPreference.getInstance().getString(AssignActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(AssignActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<AssignCourseUserList> call = apiInterface.courseuserlist(lang, AppSharedPreference.getInstance().
                getString(AssignActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(AssignActivity.this, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.COURSE_LIST) {
            data = (AssignCourseUserList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                if (data.getData().size() == 0) {
                    tvNoRecord.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    tvNoRecord.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    CourseAsssignUserAdapter cartListAdapter = new CourseAsssignUserAdapter
                            (AssignActivity.this, data.getData().get(0).getUsers(),
                                    this);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    LinearLayoutManager manager = new LinearLayoutManager(AssignActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(cartListAdapter);
                }
            }
        } else if (requestCode == ServerConstents.ASSIGN) {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                Toast.makeText(AssignActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finish();
        } else if (requestCode == ServerConstents.UNASSIGN) {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                Toast.makeText(AssignActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }

            getAssignList();
        }
    }


    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if (requestCode == ServerConstents.COURSE_LIST) {
            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else if (requestCode == ServerConstents.ASSIGN) {
            Toast.makeText(AssignActivity.this, response, Toast.LENGTH_SHORT).show();
        } else if (requestCode == ServerConstents.UNASSIGN) {
            Toast.makeText(AssignActivity.this, response, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure() {
        tvNoRecord.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}