package com.onlineeducationsystemorganization;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.Exam;
import com.onlineeducationsystemorganization.model.Restart;
import com.onlineeducationsystemorganization.model.Result;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;
import com.onlineeducationsystemorganization.util.DownloadTask;
import com.onlineeducationsystemorganization.widget.NonScrollExpandableListView;

import java.util.HashMap;

import retrofit2.Call;

public class ReportActivity extends BaseActivity implements NetworkListener {

    private NonScrollExpandableListView expandableView;
    private String course_id = "";
    private Result data;
    private ImageView img,imgBack;
    private TextView tvCongrat,tvDescription,tvDownload,tryAgain,tvBack;
    private static final int PERMISSION_PHOTO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initToolbar();
        initUI();
    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(ReportActivity.this,MyCoursesActivity.class);

        intent.putExtra("from", "thankyou");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initUI()
    {
        course_id = getIntent().getExtras().getString("course_id");
        tvDescription =findViewById(R.id.tvDescription);
        tvDownload =findViewById(R.id.tvDownload);
        tvCongrat=findViewById(R.id.tvCongrat);
        tryAgain=findViewById(R.id.tryAgain);
        img=findViewById(R.id.img);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(ReportActivity.this,MyCoursesActivity.class);

                intent.putExtra("from", "thankyou");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        tvBack =findViewById(R.id.tvBack);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ReportActivity.this,MyCoursesActivity.class);

                intent.putExtra("from", "thankyou");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        if (AppUtils.isInternetAvailable(ReportActivity.this)) {
            callResult();
        }
        /*ArrayList<Header> listDataHeader = new ArrayList<Header>();
        HashMap<Header, ArrayList<ContentItem>> listDataChild = new HashMap<Header, ArrayList<ContentItem>>();

        Header h1 =new Header("Add-on",true);
        listDataHeader.add(h1);

        ArrayList<ContentItem> contentItems = new ArrayList<ContentItem>();
        ContentItem c1=new ContentItem();
        c1.setName("Minced pork");
        contentItems.add(c1);

        listDataChild.put(listDataHeader.get(0), contentItems);

        expandableView =findViewById(R.id.expandableView);
        ExpandedReportAdapter expandedCourseDetail =new ExpandedReportAdapter(ReportActivity.this, listDataHeader, listDataChild);
        expandableView.setAdapter(expandedCourseDetail);
        expandableView.expandGroup(0);
        expandedCourseDetail.notifyDataSetChanged();*/



    }


    private void callTryAgain(String quizId){
        String lang = "";
        AppUtils.showDialog(ReportActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        params.put("quiz_result_id", quizId);
        if (AppSharedPreference.getInstance().getString(ReportActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ReportActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<Restart> call = apiInterface.restart(lang, AppSharedPreference.getInstance().
                getString(ReportActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(ReportActivity.this, call, this, ServerConstents.RESTART);

    }
    private void callResult()
    {
        String lang = "";
        AppUtils.showDialog(ReportActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        if (AppSharedPreference.getInstance().getString(ReportActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ReportActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<Exam> call = apiInterface.resultQuiz(lang, AppSharedPreference.getInstance().
                getString(ReportActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(ReportActivity.this, call, this, ServerConstents.RESULT);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.RESULT) {
            data = (Result) response;
            tvDescription.setText(data.getData().get(0).getPassMessage());
            tvCongrat.setText(data.getData().get(0).getCongratulationMessage());
            if(data.getData().get(0).getDownloadCertificateBtn().length() >1)
            {

                tvDownload.setVisibility(View.VISIBLE);
                tvDownload.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        if ( ContextCompat.checkSelfPermission(ReportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(ReportActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                            ActivityCompat.requestPermissions(ReportActivity.this,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
                        } else {
                            new DownloadTask(ReportActivity.this, data.getData().get(0).getCertificateLink());
                        }
                    }
                });
            }else
            {
                tvDownload.setVisibility(View.GONE);
            }

            if(data.getData().get(0).getTryAgainBtn().length()>1)
            {
                tryAgain.setVisibility(View.VISIBLE);
                tryAgain.setText(data.getData().get(0).getTryAgainBtn());
                tryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AppUtils.isInternetAvailable(ReportActivity.this)) {
                            callTryAgain(data.getData().get(0).getQuizResultId()+"");
                        }
                    }
                });

            }else
            {
                tryAgain.setVisibility(View.GONE);
            }

            if(data.getData().get(0).getIs_pass().equalsIgnoreCase("1"))
            {
                img.setImageResource(R.mipmap.win);
            }else
            {
                img.setImageResource(R.drawable.ic_sad);
            }
        }else
        {
           Restart  data = (Restart) response;
           if(data.getData().get(0).getQuizRestart().equalsIgnoreCase("yes"))
           {
               Intent intent =new Intent(ReportActivity.this,ExamActivity.class);
               intent.putExtra("course_id", course_id);
               startActivity(intent);
               finish();
           }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_PHOTO:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        new DownloadTask(ReportActivity.this, data.getData().get(0).getCertificateLink());

                    }else if(grantResults[0] == PackageManager.PERMISSION_DENIED)
                    {

                    }
                }
                break;
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }
}
