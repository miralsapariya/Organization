package com.onlineeducationsystemorganization;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.onlineeducationsystemorganization.adapter.DashboardReportAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.DashboardReport;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;
import com.onlineeducationsystemorganization.util.DownloadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;

public class DashboardReportActivity extends BaseActivity implements View.OnClickListener , NetworkListener {

    RecyclerView recyclerView;
    LineChart lineChart ;
    private String mode= AppConstant.MONTHLY ;
    private String course_id;
    private ImageView imgBack;
    private TextView tvTotUser,tvCompleteCourses,tvInProgress,tvNotStarted,
            tvStaticMyCourses;
    private TextView tvDaily,tvWeekly,tvMonthly,tvYearly,tvCustom;
    private Calendar myCalendarStart,myCalnederEnd;
    DashboardReport data;
    private String link;
    private static final int PERMISSION_PHOTO = 1;
    private TextView tvLblY,tvLblX,tvCourseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initUI();
    }
    private void initUI() {
        course_id=getIntent().getExtras().getString("course_id");
        imgBack=findViewById(R.id.imgBack);
        tvCourseName =findViewById(R.id.tvCourseName);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTotUser =findViewById(R.id.tvTotUser);
        tvCompleteCourses=findViewById(R.id.tvCompleteCourses);
        tvInProgress=findViewById(R.id.tvInProgress);
        tvNotStarted=findViewById(R.id.tvNotStarted);
        tvStaticMyCourses=findViewById(R.id.tvStaticMyCourses);
        tvDaily=findViewById(R.id.tvDaily);
        tvDaily.setOnClickListener(this);
        tvWeekly=findViewById(R.id.tvWeekly);
        tvWeekly.setOnClickListener(this);
        tvMonthly=findViewById(R.id.tvMonthly);
        tvMonthly.setOnClickListener(this);
        tvYearly=findViewById(R.id.tvYearly);
        tvYearly.setOnClickListener(this);
        myCalendarStart = Calendar.getInstance();
        myCalnederEnd =Calendar.getInstance();
        tvCustom =findViewById(R.id.tvCustom);
        tvCustom.setOnClickListener(this);
        tvLblY=findViewById(R.id.tvLblY);
        tvLblX=findViewById(R.id.tvLblX);

        lineChart = findViewById(R.id.chart);
        recyclerView=findViewById(R.id.recyclerView);
        setChart("", "");
    }
    private ArrayList<String> getDate(ArrayList<DashboardReport.CourseYaxisLabel> list)
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
                tvDaily.setBackground(getResources().getDrawable(R.drawable.button_select ));
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
                setChart("","");

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
                setChart("","");

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
                setChart("","");

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
                setChart("","");

                break;
        }
    }

    private void setChart( String startDate, String endDate)
    {
            String lang = "";
            AppUtils.showDialog(this, getString(R.string.pls_wait));
            final HashMap params = new HashMap<>();
            params.put("course_id",course_id);

            if(startDate.length() >0 && endDate.length() >0) {
                params.put("start_date", startDate);
                params.put("end_date", endDate);
            }
                params.put("mode", mode);


            ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
            if (AppSharedPreference.getInstance().getString(DashboardReportActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                    AppSharedPreference.getInstance().getString(DashboardReportActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                lang = AppConstant.ENG_LANG;
            } else {
                lang = AppConstant.ARABIC_LANG;
            }

            Call<DashboardReport> call = apiInterface.getReportDashBoard(lang, AppSharedPreference.getInstance().
                    getString(DashboardReportActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
            ApiCall.getInstance().hitService(DashboardReportActivity.this, call, this, ServerConstents.DASHBOARD);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_PHOTO:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        new DownloadTask(DashboardReportActivity.this, link);

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }
                break;
        }
    }
    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.DASHBOARD)
        {
            data= (DashboardReport) response;

            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                tvCourseName.setText(data.getData().get(0).getCourseName()+"");
                tvTotUser.setText(data.getData().get(0).getTotalUsers()+"");
                tvCompleteCourses.setText(data.getData().get(0).getTotalCompletedCourse()+"");
                tvInProgress.setText(data.getData().get(0).getTotalInProgressCourse()+"");
                tvNotStarted.setText(data.getData().get(0).getNotStartedCourse()+"");
                //
                if(data.getData().get(0).getUseractivitylist().size()>0) {
                    DashboardReportAdapter adapter = new DashboardReportAdapter(DashboardReportActivity.this, data.getData().get(0).getUseractivitylist(), new OnItemClick() {
                        @Override
                        public void onGridClick(int pos) {
                            if (data.getData().get(0).getUseractivitylist().get(pos).getBtnStatus().equalsIgnoreCase("1")) {
                                link = data.getData().get(0).getUseractivitylist().get(pos).getDownloadLink();
                                if (ContextCompat.checkSelfPermission(DashboardReportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                                        ContextCompat.checkSelfPermission(DashboardReportActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(DashboardReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
                                } else {
                                    new DownloadTask(DashboardReportActivity.this, data.getData().get(0).getUseractivitylist().get(pos).getDownloadLink());
                                }
                            } else {
                                Intent intent = new Intent(DashboardReportActivity.this, CourseDetailActivity.class);
                                intent.putExtra("course_id", course_id + "");
                                startActivity(intent);
                            }
                        }
                    });

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setLayoutManager(new LinearLayoutManager(DashboardReportActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }else{
                    tvStaticMyCourses.setVisibility(View.GONE);
                }
                //
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

                    LineDataSet dataSet = new LineDataSet(yEntrys, data.getData().get(0).getCoursechartData().get(i).getCourseName()+"");
                    dataSet.setColor(Color.parseColor("#e98074"));
                    dataSet.setCircleColor(Color.parseColor("#e98074"));
                    dataSet.setLineWidth(1f);
                    dataSet.setCircleRadius(5f);
                    dataSet.setDrawCircleHole(false);
                    dataSet.setDrawValues(false);
                    dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    dataSets.add(dataSet);

                    XAxis xAxis = lineChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);
                    xAxis.setYOffset(0f);
                    xAxis.setLabelRotationAngle(90);
                    // xAxis.setGranularityEnabled(true);
                    //xAxis.setLabelCount(8,true); //4 is the number of values to be shown.
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(getDate(data.getData().get(0).getCoursechartData().get(i).getCourseYaxisLabel())));

                    YAxis rightAxis = lineChart.getAxisRight();
                    rightAxis.setEnabled(false);


                }

               /* Legend l = lineChart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.VERTICAL);
                l.setDrawInside(false);
                l.setXEntrySpace(7f);
                l.setYEntrySpace(0f);
                l.setYOffset(0f);*/

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
                lineChart.setPinchZoom(false);
                lineChart.setTouchEnabled(false);
                lineChart.getLegend().setWordWrapEnabled(true);
                lineChart.setData(pieData);

                lineChart.invalidate();
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }

    private void showDateDialog()
    {
        final Dialog dialog = new Dialog(DashboardReportActivity.this);
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
                    setChart(tvFrom.getText().toString(),tvTo.getText().toString());
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
                startDate[0]=new SimpleDateFormat(myFormat).format(myCalendar.getTime());
                tvFrom.setText(startDate[0]);
                if (!tvTo.getText().toString().equals(getString(R.string.to_date))) {
                    if (compareDate(startDate[0], tvTo.getText().toString(), "before")) {
                        //apiCallReport(startDate[0], etEndDate.getText().toString());
                    }else
                    {
                        tvFrom.setText("");
                        Toast.makeText(DashboardReportActivity.this, getString(R.string.date_toast1), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        new DatePickerDialog(Objects.requireNonNull(DashboardReportActivity.this), R.style.DialogTheme1,
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
                startDate[0]=new SimpleDateFormat(myFormat).format(myCalendar.getTime());
                tvTo.setText(startDate[0]);
                if (!tvFrom.getText().toString().equals(R.string.from_date)) {
                    if (compareDate(tvFrom.getText().toString(), startDate[0], "after")) {
                        String from=tvFrom.getText().toString();
                        String to=startDate[0];
                        // apiCallReport(from, to);
                    } else {
                        tvTo.setText("");
                        Toast.makeText(DashboardReportActivity.this, getString(R.string.date_toast), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        new DatePickerDialog(Objects.requireNonNull(DashboardReportActivity.this),
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