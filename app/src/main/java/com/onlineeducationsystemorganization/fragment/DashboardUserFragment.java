package com.onlineeducationsystemorganization.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.onlineeducationsystemorganization.CourseDetailActivity;
import com.onlineeducationsystemorganization.LessionSlideActivity;
import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.UserCourseDetailActivity;
import com.onlineeducationsystemorganization.adapter.DashboardUserAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.UserDashBoard;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;
import com.onlineeducationsystemorganization.util.DownloadTask;

import java.util.ArrayList;

import retrofit2.Call;

public class DashboardUserFragment extends BaseFragment implements NetworkListener , OnItemClick {

    private View view;
    UserDashBoard data;
    private PieChart chart;
    private RecyclerView recyclerView;
    private TextView tvTotCourse,tvComplateCourse,tvMyCourse;
    private String link="";
    private static final int PERMISSION_PHOTO = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_dashboard_user, container, false);
        // Inflate the layout for this fragment
        initUI();
        return view;
    }

    private void initUI()
    {
        recyclerView =view.findViewById(R.id.recyclerView);
        tvMyCourse =view.findViewById(R.id.tvMyCourse);
        chart = view.findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDrawCenterText(false);
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(false);
        chart.animateY(1400, Easing.EaseInOutQuad);
        chart.setDrawHoleEnabled(false);
        chart.getLegend().setEnabled(true);
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(12f);
        chart.setDrawEntryLabels(false);

        tvTotCourse=view.findViewById(R.id.tvTotCourse);
        tvComplateCourse=view.findViewById(R.id.tvComplateCourse);

        if (AppUtils.isInternetAvailable(
                activity)) {
            getCompletedCourses();
        }

    }
    @Override
    public void onGridClick(int pos) {
        Log.d("-------", "=============");

        link=data.getData().get(0).getMycourselist().get(pos).getDownloadLink();
        if(!data.getData().get(0).getMycourselist().get(pos).getDownloadLink().equals(""))
        {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
            } else {
                new DownloadTask(activity, data.getData().get(0).getMycourselist().get(pos).getDownloadLink());
            }
        }else
        {
            if(data.getData().get(0).getMycourselist().get(pos).getCourseStatus().equals("2")){
                Intent intent =new Intent(activity, LessionSlideActivity.class);
                intent.putExtra("course_id", data.getData().get(0).getMycourselist().get(pos).getCourseId()+"");
                intent.putExtra("section_id", data.getData().get(0).getMycourselist().get(pos).getSectionId()+"");
                intent.putExtra("slide_id", data.getData().get(0).getMycourselist().get(pos).getSlideId()+"");
                startActivity(intent);
            }else if(data.getData().get(0).getMycourselist().get(pos).getCourseStatus().equals("1")){
                Intent intent = new Intent(activity, UserCourseDetailActivity.class);
                intent.putExtra("course_id", data.getData().get(0).getMycourselist().get(pos).getCourseId() + "");
                startActivity(intent);
            }else {
                Intent intent = new Intent(activity, CourseDetailActivity.class);
                intent.putExtra("course_id", data.getData().get(0).getMycourselist().get(pos).getCourseId() + "");
                startActivity(intent);
            }
        }

    }
    private void getCompletedCourses()
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<UserDashBoard> call = apiInterface.getUSerDashboard(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.COURSE_LIST);

    }
    @Override
    public void
    onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.COURSE_LIST) {
            data = (UserDashBoard) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                if(data.getData().get(0).getMycourselist().size() ==0)
                {
                    tvMyCourse.setVisibility(View.GONE);
                }
                DashboardUserAdapter dashboardAdapter =
                        new DashboardUserAdapter(activity, data.getData().get(0).getMycourselist(),this);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(dashboardAdapter);

                tvTotCourse.setText(data.getData().get(0).getTotalCourses()+"");
                tvComplateCourse.setText(data.getData().get(0).getTotalCompletedCourse()+"");

                setData(chart,data.getData().get(0).getChartDataPer().getNotStartedPer(),
                        data.getData().get(0).getChartData().getNotStarted(),
                        data.getData().get(0).getChartDataPer().getCompletedPer(),
                        data.getData().get(0).getChartData().getCompleted(),
                        data.getData().get(0).getChartDataPer().getInProgressPer(),
                        data.getData().get(0).getChartData().getInProgress()
                );
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }
    private void setData(PieChart chart, float count1,int conunt11,
                         float count2,int conunt22, float count3,int conunt33) {
        ArrayList<PieEntry> entries=new ArrayList<>();
        ArrayList<Integer> colors=new ArrayList<>();
        for (int i=0; i < 3; i++) {
            if (i == 0) {
                //if (count1 > 0) {
                colors.add(getResources().getColor(R.color.colorPrimary));
                entries.add(new PieEntry(count1,getString(R.string.not_started)+"("+conunt11+")"));
                // }
            } else if(i==1){
                //if (count2 > 0) {
                colors.add(getResources().getColor(R.color.chart2));
                entries.add(new PieEntry(count2,getString(R.string.completed)+"("+conunt22+")"));
                // }
            }else {
                //if (count3 > 0) {
                colors.add(getResources().getColor(R.color.chart1));
                entries.add(new PieEntry(count3,getString(R.string.in_progress)+"("+conunt33+")"));
                //}
            }
        }

        PieDataSet dataSet=new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data=new PieData(dataSet);
        data.setValueFormatter(new NonZeroChartValueFormatter(0));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //text on pie
        data.setDrawValues(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
    }
    class NonZeroChartValueFormatter extends DefaultValueFormatter {

        NonZeroChartValueFormatter(int digits) {
            super(digits);
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                        ViewPortHandler viewPortHandler) {
            if (value > 0) {
                return mFormat.format(value);
            } else {
                return "";
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
                        new DownloadTask(activity, link);

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }
                break;
        }
    }
}