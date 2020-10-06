package com.onlineeducationsystemorganization;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.HomeAdapterInstructor;
import com.onlineeducationsystemorganization.adapter.InstructorProfileCoursesAdpter;
import com.onlineeducationsystemorganization.interfaces.OnInstructorsClick;

import java.util.ArrayList;

public class InstructorProfileActivity extends BaseActivity implements OnInstructorsClick {

    private InstructorProfileCoursesAdpter instructorProfileCoursesAdpter;
    private HomeAdapterInstructor popularInstructorAdapter;
    private RecyclerView rvCourses,rvPopularInsructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_instructor_profile);

        initUI();
    }

    private void initUI()
    {

        ArrayList<String> list =new ArrayList<>();
        list.add("djkjf");
        list.add("hfjd");
        list.add("dkjfkjd");
        list.add("dkjd");

        rvCourses =findViewById(R.id.rvCourses);

        instructorProfileCoursesAdpter =
                new InstructorProfileCoursesAdpter(InstructorProfileActivity.this, list);

        rvCourses.setLayoutManager(new LinearLayoutManager(InstructorProfileActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvCourses.setHasFixedSize(true);
        rvCourses.setItemAnimator(new DefaultItemAnimator());
        rvCourses.setAdapter(instructorProfileCoursesAdpter);

        rvPopularInsructor =findViewById(R.id.rvPopularInsructor);
        //popularInstructorAdapter = new HomeAdapterInstructor(InstructorProfileActivity.this,list,this);
        rvPopularInsructor.setLayoutManager(new LinearLayoutManager(InstructorProfileActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvPopularInsructor.setHasFixedSize(true);
        rvPopularInsructor.setItemAnimator(new DefaultItemAnimator());
        rvPopularInsructor.setAdapter(popularInstructorAdapter);
    }

    @Override
    public void onInstructorClick(int pos) {

    }
}
