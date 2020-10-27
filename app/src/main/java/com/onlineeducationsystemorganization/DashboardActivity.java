package com.onlineeducationsystemorganization;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    float[] yData = {30, 60, 500,231};
    float[] yData1 = {40, 70, 300,251};
    String[] xData = {"Jan", "Feb" , "Mac" , "Apr"};
    private TextView tvCustom;
    private Calendar myCalendarStart,myCalnederEnd;
    LineChart lineChart ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CreateGraph();
        initUI();

    }
    private void initUI()
    {
        myCalendarStart =Calendar.getInstance();
        myCalnederEnd =Calendar.getInstance();
        tvCustom =findViewById(R.id.tvCustom);
        tvCustom.setOnClickListener(this);
    }

    private void CreateGraph() {


        lineChart= findViewById(R.id.chart);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (int z = 0; z < 3; z++) {

            ArrayList<Entry> yEntrys = new ArrayList<>();
            ArrayList<String> xEntrys = new ArrayList<>();


            for (int i = 0; i < yData.length; i++) {
                if(z==0)
                yEntrys.add(new Entry(i, yData[i]));
                else
                    yEntrys.add(new Entry(i, yData1[i]));
            }

            for (int i = 1; i < xData.length; i++) {
                xEntrys.add(xData[i]);
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


    }
    private ArrayList<String> getDate()
    {
        ArrayList<String> list =new ArrayList<>();
        list.add("Jan");
        list.add("Feb");
        list.add("Mar");
        list.add("Apr");
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvCustom :
                showDateDialog();
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
    private String setStartDate(final TextView tvFrom,final TextView tvTo) {
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

                String myFormat="yyyy-MM-dd";
                startDate[0]=new SimpleDateFormat(myFormat).format(myCalendar.getTime());
                tvFrom.setText(startDate[0]);
                if (!tvTo.getText().toString().equals(getString(R.string.to_date))) {
                    if (compareDate(startDate[0], tvTo.getText().toString(), "before")) {
                        //apiCallReport(startDate[0], etEndDate.getText().toString());
                    }else
                    {
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

                String myFormat="yyyy-MM-dd";
                startDate[0]=new SimpleDateFormat(myFormat).format(myCalendar.getTime());
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
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