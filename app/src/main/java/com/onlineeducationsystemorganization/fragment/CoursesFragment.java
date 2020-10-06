package com.onlineeducationsystemorganization.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.adapter.CourseListAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.Courses;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class CoursesFragment extends BaseFragment implements OnItemClick, NetworkListener {

    private RecyclerView recyclerView;
    private TextView tvNoRecord;
    private View view;
    private Courses data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_courses, container, false);
        init();
        return view;
    }
    private void init()
    {
        tvNoRecord =view.findViewById(R.id.tvNoRecord);
        recyclerView=view.findViewById(R.id.recyclerView);
        if (AppUtils.isInternetAvailable(activity)) {
            getCourses();
        }
    }

    private void getCourses()
    {
            String lang="";
            AppUtils.showDialog(activity, getString(R.string.pls_wait));
            ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
            final HashMap params = new HashMap<>();
            params.put("user_id", AppSharedPreference.getInstance().
                    getString(activity, AppSharedPreference.USERID));
            if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                    AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                lang = AppConstant.ENG_LANG;
            }else
            {
                lang= AppConstant.ARABIC_LANG;
            }
            Call<Courses> call = apiInterface.courseList(lang,AppSharedPreference.getInstance().
                    getString(activity, AppSharedPreference.ACCESS_TOKEN),params);
            ApiCall.getInstance().hitService(activity, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            data = (Courses) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
               /* Courses.Courseslist c= new Courses.Courseslist();
                c.setCourseName("PHP");

                Courses.Courseslist c1= new Courses.Courseslist();
                c1.setCourseName("PHP");
                data.getData().set(0).getCourseslist().add(c);
                data.getData().get(0).getCourseslist().add(c1);*/
                if(data.getData().size() ==0)
                {
                    tvNoRecord.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else
                {
                    tvNoRecord.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    CourseListAdapter cartListAdapter= new CourseListAdapter(activity, data.getData().get(0).getCourseslist(),this);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    LinearLayoutManager manager = new LinearLayoutManager(activity);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(cartListAdapter);
                }
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if(requestCode == ServerConstents.COURSE_LIST) {
            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onGridClick(int pos) {

    }
}