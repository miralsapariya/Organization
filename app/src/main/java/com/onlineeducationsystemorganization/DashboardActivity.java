package com.onlineeducationsystemorganization;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.onlineeducationsystemorganization.adapter.ComplitedCourseListAdapter;
import com.onlineeducationsystemorganization.adapter.DashboardCoursesAdapter;
import com.onlineeducationsystemorganization.adapter.DashboardMostComplitedCoursesAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.CompletedCourses;
import com.onlineeducationsystemorganization.model.Dashboard;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;

public class DashboardActivity extends BaseActivity implements View.OnClickListener , NetworkListener {

    float[] yData = {30, 60, 500,231};
    float[] yData1 = {40, 70, 300,251};
    String[] xData = {"Jan", "Feb" , "Mac" , "Apr"};
    private TextView tvCustom;
    private Calendar myCalendarStart,myCalnederEnd;
    LineChart lineChart ;
    private ImageView imgBack;
    private LinearLayout llPopup;
    private String mode=AppConstant.MONTHLY ;
    View popupView;
    RecyclerView rv,recyclerViewMostComaplete,recyclerView;
    PopupWindow popupWindow;
    Dashboard data;
    CompletedCourses  data1;
    private TextView tvTotCourse,tvActiveCourses,tvComplateCourse,tvNoOfUser,tvLblX,tvLblY;
    private TextView tvDaily,tvWeekly,tvMonthly,tvYearly;

    ComplitedCourseListAdapter complitedCourseListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //CreateGraph();
        initUI();

    }
    private void initUI()
    {
        lineChart= findViewById(R.id.chart);
        tvLblX =findViewById(R.id.tvLblX);
        tvLblY=findViewById(R.id.tvLblY);
        llPopup=findViewById(R.id.llPopup);
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView= layoutInflater.inflate(R.layout.dialog_courses, null);
        popupWindow= new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);

        rv=popupView.findViewById(R.id.rvCourses);
        recyclerViewMostComaplete =findViewById(R.id.recyclerViewMostComaplete);
        recyclerView=findViewById(R.id.recyclerView);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                android.R.color.transparent));
        popupWindow.setTouchable(true);
        llPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow
                        .showAsDropDown(findViewById(R.id.llPopup));
            }
        });
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myCalendarStart =Calendar.getInstance();
        myCalnederEnd =Calendar.getInstance();
        tvCustom =findViewById(R.id.tvCustom);
        tvCustom.setOnClickListener(this);
        tvTotCourse =findViewById(R.id.tvTotCourse);
        tvActiveCourses =findViewById(R.id.tvActiveCourses);
        tvComplateCourse =findViewById(R.id.tvComplateCourse);
        tvNoOfUser =findViewById(R.id.tvNoOfUser);
        tvDaily=findViewById(R.id.tvDaily);
        tvDaily.setOnClickListener(this);
        tvWeekly=findViewById(R.id.tvWeekly);
        tvWeekly.setOnClickListener(this);
        tvMonthly=findViewById(R.id.tvMonthly);
        tvMonthly.setOnClickListener(this);
        tvYearly=findViewById(R.id.tvYearly);
        tvYearly.setOnClickListener(this);

        if (AppUtils.isInternetAvailable(
                DashboardActivity.this)) {
                getCompletedCourses();
        }
    }

    private void getCompletedCourses()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        if (AppSharedPreference.getInstance().getString(DashboardActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(DashboardActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<CompletedCourses> call = apiInterface.getCompleteCourses(lang,AppSharedPreference.getInstance().
                getString(DashboardActivity.this, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(DashboardActivity.this, call, this, ServerConstents.COURSE_LIST);
    }

    private void CreateGraph() {

       /* ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (int z = 0; z < 3; z++) {

            ArrayList<Entry> yEntrys = new ArrayList<>();
           // ArrayList<String> xEntrys = new ArrayList<>();

            for (int i = 0; i < yData.length; i++) {
                if(z==0)
                yEntrys.add(new Entry(i, yData[i]));
                else
                    yEntrys.add(new Entry(i, yData1[i]));
            }


            LineDataSet dataSet = new LineDataSet(yEntrys, "jjjjj");
            dataSet.setColor(Color.parseColor("#7500ca"));
            dataSet.setCircleColor(Color.parseColor("#7500ca"));
            dataSet.setLineWidth(1f);
            dataSet.setCircleRadius(5f);
            dataSet.setDrawCircleHole(false);
            dataSet.setDrawValues(false);
            dataSets.add(dataSet);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(getDate()));

            YAxis rightAxis = lineChart.getAxisRight();
            rightAxis.setEnabled(false);

        }
            LineData pieData = new LineData(dataSets);
            lineChart.getXAxis().setDrawGridLines(false);
            lineChart.getAxisLeft().setDrawGridLines(true);
            lineChart.getAxisRight().setDrawGridLines(false);
            lineChart.getAxisLeft().setDrawAxisLine(true);
            lineChart.getAxisRight().setDrawAxisLine(false);
            lineChart.getAxisLeft().setDrawLabels(true);
            lineChart.getAxisRight().setDrawLabels(true);
            lineChart.getLegend().setEnabled(true);
            lineChart.setDescription(null);
            lineChart.setData(pieData);
            lineChart.invalidate();
*/

    }
    private ArrayList<String> getDate(ArrayList<Dashboard.CourseYaxisLabel> list)
    {
        ArrayList<String> list1=new ArrayList<>();

        for(int i=0;i<list.size();i++)
        {
           // Log.d("XXXXXXXXXXXXXXXXXXXXX ", list.get(i).getYdata());
            list1.add(list.get(i).getYdata());
        }
        return list1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvCustom :
                tvDaily.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvDaily.setPadding(0, 20,0, 20);
                tvCustom.setBackground(getResources().getDrawable(R.drawable.button));
                tvCustom.setPadding(0, 20,0, 20);
                tvWeekly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvWeekly.setPadding(0, 20,0, 20);
                tvMonthly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvMonthly.setPadding(0, 20,0, 20);
                tvYearly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvYearly.setPadding(0, 20,0, 20);
                mode=AppConstant.CUSTOM;
                showDateDialog();
                break;
            case R.id.tvDaily:
                tvDaily.setBackground(getResources().getDrawable(R.drawable.button));
                tvDaily.setPadding(0, 20,0, 20);
                tvCustom.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvCustom.setPadding(0, 20,0, 20);
                tvWeekly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvWeekly.setPadding(0, 20,0, 20);
                tvMonthly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvMonthly.setPadding(0, 20,0, 20);
                tvYearly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvYearly.setPadding(0, 20,0, 20);
                mode=AppConstant.DAILY;
                    setChart(data1.getData().get(0).getCoursechartDropdownlist(),"","");
                break;
            case R.id.tvWeekly:
                tvDaily.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvDaily.setPadding(0, 20,0, 20);
                tvCustom.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvCustom.setPadding(0, 20,0, 20);
                tvWeekly.setBackground(getResources().getDrawable(R.drawable.button));
                tvWeekly.setPadding(0, 20,0, 20);
                tvMonthly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvMonthly.setPadding(0, 20,0, 20);
                tvYearly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvYearly.setPadding(0, 20,0, 20);
                mode=AppConstant.WEEKLY;
                    setChart(data1.getData().get(0).getCoursechartDropdownlist(),"","");
                break;
            case R.id.tvMonthly:
                tvDaily.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvDaily.setPadding(0, 20,0, 20);
                tvCustom.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvCustom.setPadding(0, 20,0, 20);
                tvWeekly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvWeekly.setPadding(0, 20,0, 20);
                tvMonthly.setBackground(getResources().getDrawable(R.drawable.button));
                tvMonthly.setPadding(0, 20,0, 20);
                tvYearly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvYearly.setPadding(0, 20,0, 20);
                    mode=AppConstant.MONTHLY;
                    setChart(data1.getData().get(0).getCoursechartDropdownlist(),"","");
              break;
            case R.id.tvYearly:
                tvDaily.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvDaily.setPadding(0, 20,0, 20);
                tvCustom.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvCustom.setPadding(0, 20,0, 20);
                tvWeekly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvWeekly.setPadding(0, 20,0, 20);
                tvMonthly.setBackground(getResources().getDrawable(R.drawable.button_select));
                tvMonthly.setPadding(0, 20,0, 20);
                tvYearly.setBackground(getResources().getDrawable(R.drawable.button));
                tvYearly.setPadding(0, 20,0, 20);
                    mode=AppConstant.YEARLY;
                    setChart(data1.getData().get(0).getCoursechartDropdownlist(),"","");
                    break;
        }
    }

    private void showDateDialog()
    {
        final Dialog dialog = new Dialog(DashboardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_date);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        final TextView tvFrom=dialog.findViewById(R.id.tvFrom);
        final TextView tvTo=dialog.findViewById(R.id.tvTo);
        ImageView imgFrom=dialog.findViewById(R.id.imgFrom);
        imgFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStartDate(tvFrom,tvTo);
            }
        });
        ImageView imgTo =dialog.findViewById(R.id.imgTo);
        imgTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setEndDate(tvFrom, tvTo);
            }
        });
        Button btnApply=dialog.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(!TextUtils.isEmpty(tvFrom.getText().toString()) && !TextUtils.isEmpty(tvTo.getText().toString()))
                {
                    if(tvTo.getText().toString().equals(getString(R.string.to_date)))
                      tvTo.setText("");
                    setChart(data1.getData().get(0).getCoursechartDropdownlist(),tvFrom.getText().toString(),tvTo.getText().toString());
                }
            }
        });
        Button btnCancel=dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void
    onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.COURSE_LIST) {
              data1= (CompletedCourses) response;
            if (data1.getStatus() == ServerConstents.CODE_SUCCESS) {

                tvTotCourse.setText(data1.getData().get(0).getTotalCourses()+"");
                tvComplateCourse.setText(data1.getData().get(0).getTotalCompletedCourse()+"");
                tvNoOfUser.setText(data1.getData().get(0).getTotalUsers()+"");
                tvActiveCourses.setText(data1.getData().get(0).getTotalActiveCourse()+"");

                for(int i=0;i< data1.getData().get(0).getCoursechartDropdownlist().size();i++)
                {
                    data1.getData().get(0).getCoursechartDropdownlist().get(i).setSelected(true);
                }
                complitedCourseListAdapter=new ComplitedCourseListAdapter(DashboardActivity.this, data1.getData().get(0).getCoursechartDropdownlist(), new OnItemClick() {
                    @Override
                    public void onGridClick(int pos) {
                        setChart(data1.getData().get(0).getCoursechartDropdownlist(),"","");
                    }
                });
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.setLayoutManager(new LinearLayoutManager(this));
                rv.setHasFixedSize(true);
                rv.setAdapter(complitedCourseListAdapter);
                rv.getAdapter().notifyDataSetChanged();
                setChart(data1.getData().get(0).getCoursechartDropdownlist(),"","");
            }
        }
        else if(requestCode == ServerConstents.DASHBOARD)
        {
            data= (Dashboard) response;

            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                //
                DashboardMostComplitedCoursesAdapter adapter=new DashboardMostComplitedCoursesAdapter(DashboardActivity.this, data.getData().get(0).getMostCompletedCourses());
                recyclerViewMostComaplete.setItemAnimator(new DefaultItemAnimator());
                recyclerViewMostComaplete.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerViewMostComaplete.setAdapter(adapter);
                recyclerViewMostComaplete.getAdapter().notifyDataSetChanged();

                DashboardCoursesAdapter adapter1=new
                        DashboardCoursesAdapter(DashboardActivity.this,
                        data.getData().get(0).getMycourselist(),new OnItemClick() {
                    @Override
                    public void onGridClick(int pos) {
                        Intent intent = new Intent(DashboardActivity.this, DashboardReportActivity.class);
                        intent.putExtra("course_id", data.getData().get(0).getMycourselist().get(pos).getCourseId() + "");
                        startActivity(intent);
                    }
                });
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter1);
                recyclerView.getAdapter().notifyDataSetChanged();

                lineChart.clear();
                tvLblY.setText(getString(R.string.no_of_courses));
                tvLblX.setText(data.getData().get(0).getCoursechartData().get(0).getXChartLabel());
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                for(int i=0;i<data.getData().get(0).getCoursechartData().size();i++)
                {
                    ArrayList<Entry> yEntrys = new ArrayList<>();
                    for (int i1 = 0; i1 < data.getData().get(0).getCoursechartData().get(i).getCourseYaxisLabel().size(); i1++) {
                         yEntrys.add(new Entry(i1, data.getData().get(0).getCoursechartData().get(i).getCourseXaxisDataval().get(i1).getXdata()));
                        //yEntrys.add(new Entry(i1,100));
                    }

                    LineDataSet dataSet = new LineDataSet(yEntrys, data.getData().get(0).getCoursechartData().get(i).getCourse_name());
                    dataSet.setColor(Color.parseColor(data.getData().get(0).getCoursechartData().get(i).getCourse_color_code()));
                    dataSet.setCircleColor(Color.parseColor(data.getData().get(0).getCoursechartData().get(i).getCourse_color_code()));
                    dataSet.setLineWidth(1f);
                    dataSet.setCircleRadius(5f);
                    dataSet.setDrawCircleHole(false);
                    dataSet.setDrawValues(false);
                    dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    dataSets.add(dataSet);

                    XAxis xAxis = lineChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setGranularity(1f);
                    xAxis.setYOffset(0f);
                    xAxis.setLabelRotationAngle(90);

                    //xAxis.setLabelCount(8,true); //4 is the number of values to be shown.
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(getDate(data.getData().get(0).getCoursechartData().get(i).getCourseYaxisLabel())));

                    YAxis rightAxis = lineChart.getAxisRight();
                    rightAxis.setEnabled(false);
                }

                LineData pieData = new LineData(dataSets);
                lineChart.getAxisLeft().setStartAtZero(true);
                lineChart.getAxisLeft().setAxisMinValue(0);
                lineChart.getXAxis().setYOffset(9);
                lineChart.getXAxis().setDrawGridLines(false);
                lineChart.getAxisLeft().setDrawGridLines(true);
                lineChart.getAxisRight().setDrawGridLines(false);
                lineChart.getAxisLeft().setDrawAxisLine(true);
                lineChart.getAxisRight().setDrawAxisLine(false);
                lineChart.getAxisLeft().setDrawLabels(true);
                lineChart.getAxisRight().setDrawLabels(true);
                lineChart.getLegend().setEnabled(false);
                lineChart.setDescription(null);
                lineChart.setPinchZoom(false);
                lineChart.setTouchEnabled(false);
                //lineChart.getLegend().setWordWrapEnabled(true);
                lineChart.setData(pieData);

                lineChart.invalidate();
            }
        }
    }

    private void setChart(ArrayList<CompletedCourses.CoursechartDropdownlist> list,String startDate,String endDate)
    {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<list.size();i++)
        { Log.d(";;;;;;;;;;;;;;;;;::: ", list.get(i).isSelected()+"");
            if(list.get(i).isSelected())
            sb.append(list.get(i).getId()+",");
        }
        if(sb.length()>1) {
            Log.d("idsssssssssssssss ==== ", sb.toString());
            String lang = "";
            AppUtils.showDialog(this, getString(R.string.pls_wait));
            final HashMap params = new HashMap<>();
            params.put("course_id", sb.toString().substring(0, sb.toString().length() - 1));

            if(startDate.length() >0 && endDate.length() >0) {
                params.put("start_date", startDate);
                params.put("end_date", endDate);
            }else if(startDate.length() >0 && endDate.length() ==0) {
                params.put("start_date", startDate);
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                params.put("end_date", date);
            }
                params.put("mode", mode);


            ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
            if (AppSharedPreference.getInstance().getString(DashboardActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                    AppSharedPreference.getInstance().getString(DashboardActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                lang = AppConstant.ENG_LANG;
            } else {
                lang = AppConstant.ARABIC_LANG;
            }

            Call<Dashboard> call = apiInterface.getDashBoard(lang, AppSharedPreference.getInstance().
                    getString(DashboardActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
            ApiCall.getInstance().hitService(DashboardActivity.this, call, this, ServerConstents.DASHBOARD);
        }else
        {
            lineChart.clear();
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }

    private String setStartDate(final TextView tvFrom, final TextView tvTo) {
        final String[] startDate={""};
        final Calendar myCalendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                myCalendarStart= (Calendar)myCalendar.clone();

                String myFormat="dd/MM/yyyy";
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                startDate[0]=new SimpleDateFormat(myFormat,locale).format(myCalendar.getTime());
                tvFrom.setText(startDate[0]);
                //tvTo.setText(startDate[0]);
                if (!tvTo.getText().toString().equals(getString(R.string.to_date))) {
                    if (compareDate(startDate[0], tvTo.getText().toString(), "before")) {
                        //apiCallReport(startDate[0], etEndDate.getText().toString());
                    }else
                    {
                        tvFrom.setText("");
                        Toast.makeText(DashboardActivity.this, getString(R.string.date_toast1), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        new DatePickerDialog(Objects.requireNonNull(DashboardActivity.this), R.style.DialogTheme1,
                date,
                myCalendarStart.get(Calendar.YEAR),
                myCalendarStart.get(Calendar.MONTH),
                myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

        return startDate[0];
    }

    private String setEndDate(final TextView tvFrom,final TextView tvTo) {
        final String[] startDate={""};
       final  Calendar myCalendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                myCalnederEnd= (Calendar)myCalendar.clone();

                String myFormat="dd/MM/yyyy";
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                startDate[0]=new SimpleDateFormat(myFormat,locale).format(myCalendar.getTime());
                tvTo.setText(startDate[0]);
                if (!tvFrom.getText().toString().equals(R.string.from_date)) {
                    if (compareDate(tvFrom.getText().toString(), startDate[0], "after")) {
                        String from=tvFrom.getText().toString();
                        String to=startDate[0];
                       // apiCallReport(from, to);
                    } else {
                        tvTo.setText("");
                        Toast.makeText(DashboardActivity.this, getString(R.string.date_toast), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        new DatePickerDialog(Objects.requireNonNull(DashboardActivity.this),
                R.style.DialogTheme1, date,
                myCalnederEnd.get(Calendar.YEAR), myCalnederEnd.get(Calendar.MONTH),
                myCalnederEnd.get(Calendar.DAY_OF_MONTH)).show();

        return startDate[0];
    }

    private boolean compareDate(String startDate, String endDate, String afterBefore) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate;
        Date convertedDate2;
        try {
            convertedDate=dateFormat.parse(startDate);
            convertedDate2=dateFormat.parse(endDate);
            if (afterBefore.equalsIgnoreCase("after")) {
                return convertedDate2.after(convertedDate) || convertedDate2.equals(convertedDate);
            } else {
                return convertedDate.before(convertedDate2) || convertedDate.equals(convertedDate2);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}