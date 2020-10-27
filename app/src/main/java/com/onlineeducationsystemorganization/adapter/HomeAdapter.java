package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.CourseDetailActivity;
import com.onlineeducationsystemorganization.PopularInstructorActivity;
import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.SubCategoryActivity;
import com.onlineeducationsystemorganization.TrendingCourseActivity;
import com.onlineeducationsystemorganization.interfaces.OnInstructorsClick;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.OnNewCourseClick;
import com.onlineeducationsystemorganization.interfaces.OnViewAllClick;
import com.onlineeducationsystemorganization.model.Home;
import com.onlineeducationsystemorganization.network.ServerConstents;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>
        implements OnItemClick {

    private ArrayList<Home.Datum> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private OnViewAllClick onViewAllClick;
    private Context context;
    ArrayList<Home.List1> list;

    public HomeAdapter(Context context,
                       ArrayList<Home.Datum> listProduct, OnItemClick onItemClick, OnViewAllClick onViewAllClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
        this.onViewAllClick = onViewAllClick;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_home, parent, false);
        HomeAdapter.ViewHolder viewHolder = new HomeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.ViewHolder holder, final int position) {
        final  Home.Datum data = listProduct.get(position);

        holder.tvNameCategory.setText(data.getTitle());
        if(data.getType().equals(ServerConstents.HOME_TYPE_COURSE) )
        {
            HomeAdpterCourses homeAdapter =
                    new HomeAdpterCourses(context, data.getList(), new OnNewCourseClick() {
                        @Override
                        public void onNewCourseClick(int pos) {
                            Intent intent =new Intent(context, CourseDetailActivity.class);
                            intent.putExtra("course_id", data.getList().get(pos).getId()+"");
                            context.startActivity(intent);

                        }
                    });

            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setHasFixedSize(true);
            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
            holder.rvHorizonatal.setAdapter(homeAdapter);

            if(data.getList().size() > 3){
                holder.tvViewAll.setVisibility(View.VISIBLE);
            }else
            {
                holder.tvViewAll.setVisibility(View.GONE);
            }

            holder.tvViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context, TrendingCourseActivity.class);
                    intent.putExtra("title", data.getTitle());
                    if(position == 0)
                    intent.putExtra("from", "new_courses");
                    else
                        intent.putExtra("from", "trending_courses");
                    context.startActivity(intent);
                }
            });
        }


        else if(data.getType().equals(ServerConstents.HOME_TYPE_CATEGORY))
        {
            list=new ArrayList<>();
            list=data.getList();
            HomeAdpterCategory homeAdapter =
                    new HomeAdpterCategory(context, data.getList(),this);

            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setHasFixedSize(true);
            holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
            holder.rvHorizonatal.setAdapter(homeAdapter);

            if(data.getList().size() > 3){
                holder.tvViewAll.setVisibility(View.VISIBLE);
            }else
            {
                holder.tvViewAll.setVisibility(View.GONE);
            }

            holder.tvViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onViewAllClick.onViewAll();

                }
            });

        }else {
            HomeAdapterInstructor1 homeAdapter =
                    new HomeAdapterInstructor1(context, data.getList(), new OnInstructorsClick() {
                        @Override
                        public void onInstructorClick(int pos) {

                        }
                    });

            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setHasFixedSize(true);
            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
            holder.rvHorizonatal.setAdapter(homeAdapter);

            if(data.getList().size() > 3){
                holder.tvViewAll.setVisibility(View.VISIBLE);
            }else
            {
                holder.tvViewAll.setVisibility(View.GONE);
            }

            holder.tvViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent =new Intent(context, PopularInstructorActivity.class);
                    context.startActivity(intent);

                }
            });
        }

         }

    @Override
    public void onGridClick(int pos) {

        Intent intent=new Intent(context, SubCategoryActivity.class);
        intent.putExtra("cat_id", list.get(pos).getId()+"");
        intent.putExtra("cat_name", list.get(pos).getCategoryName());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  Home.Datum getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameCategory,tvViewAll;
        private RecyclerView rvHorizonatal;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameCategory =itemView.findViewById(R.id.tvNameCategory);
            tvViewAll =itemView.findViewById(R.id.tvViewAll);

            rvHorizonatal =itemView.findViewById(R.id.rvHorizonatal);

        }
    }
}

