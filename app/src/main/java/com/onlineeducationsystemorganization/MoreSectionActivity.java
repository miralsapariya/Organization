package com.onlineeducationsystemorganization;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.onlineeducationsystemorganization.adapter.ExpandedCourseDetail;
import com.onlineeducationsystemorganization.model.CourseDetail;
import com.onlineeducationsystemorganization.widget.NonScrollExpandableListView;

import java.util.ArrayList;

public class MoreSectionActivity extends AppCompatActivity {

    private NonScrollExpandableListView expandableView;
    ArrayList<CourseDetail.SectionDetail> listData = new ArrayList<CourseDetail.SectionDetail>();
    private ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_section);

        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        expandableView =findViewById(R.id.expandableView);
        listData = (ArrayList<CourseDetail.SectionDetail>) getIntent().getSerializableExtra("listData");

        for(int i=0;i<1;i++)
        {
           listData.remove(i);
        }
        expandableView = findViewById(R.id.expandableView);
        ExpandedCourseDetail expandedCourseDetail = new ExpandedCourseDetail(MoreSectionActivity.this,listData );
        expandableView.setAdapter(expandedCourseDetail);
        expandableView.expandGroup(0);
        expandedCourseDetail.notifyDataSetChanged();
    }
}
