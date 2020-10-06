package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.CourseDetailActivity;
import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.TrendingCourseActivity;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.OnNewCourseClick;
import com.onlineeducationsystemorganization.model.SubCategory;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>
       {

    private ArrayList<SubCategory.SubCategory_> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public SubCategoryAdapter(Context context,
                              ArrayList<SubCategory.SubCategory_> listProduct, OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_subcat, parent, false);
        SubCategoryAdapter.ViewHolder viewHolder = new SubCategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubCategoryAdapter.ViewHolder holder, int position) {
        final  SubCategory.SubCategory_ data = listProduct.get(position);


        holder.tvNameCategory.setText(data.getSubCategoryName());

        Log.d("course list :: ", data.getCourseList().size()+"");

        SubAdpterCourses homeAdapter =
                    new SubAdpterCourses(context, data.getCourseList(), new OnNewCourseClick() {
                        @Override
                        public void onNewCourseClick(int pos) {

                            Intent intent =new Intent(context, CourseDetailActivity.class);
                            intent.putExtra("course_id", pos+"");
                            context.startActivity(intent);
                        }
                    });

            holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvHorizonatal.setHasFixedSize(true);
            holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
            holder.rvHorizonatal.setAdapter(homeAdapter);

        if(data.getCourseList().size() > 3){
            holder.tvViewAll.setVisibility(View.VISIBLE);
        }else
        {
            holder.tvViewAll.setVisibility(View.GONE);
        }

            holder.tvViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent =new Intent(context, TrendingCourseActivity.class);
                    intent.putExtra("title", data.getSubCategoryName());
                        intent.putExtra("from", "sub_cat");
                        intent.putExtra("subcat_id", data.getId()+"");
                    context.startActivity(intent);
                }
            });

    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  SubCategory.SubCategory_ getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameCategory,tvViewAll;
        private RecyclerView rvHorizonatal;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameCategory =itemView.findViewById(R.id.tvNameCategory);
            rvHorizonatal =itemView.findViewById(R.id.rvHorizonatal);
            tvViewAll =itemView.findViewById(R.id.tvViewAll);

        }
    }
}

