package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.Dashboard;

import java.util.ArrayList;

public class DashboardCoursesAdapter extends RecyclerView.Adapter<DashboardCoursesAdapter.ViewHolder> {

    private ArrayList<Dashboard.Mycourselist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public DashboardCoursesAdapter(Context context,
                                   ArrayList<Dashboard.Mycourselist> listProduct, OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick=onItemClick;
    }

    @Override
    public DashboardCoursesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_dashboard_action_courses, parent, false);
        DashboardCoursesAdapter.ViewHolder viewHolder = new DashboardCoursesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DashboardCoursesAdapter.ViewHolder holder, final int position) {
        final Dashboard.Mycourselist data = listProduct.get(position);
        holder.tvCourse.setText(data.getCourseName());
        holder.tvDate.setText(context.getString(R.string.subscription_date)+": "+data.getSubscriptionDate());
        holder.tvStatus.setText(context.getString(R.string.status)+": "+data.getStatus());
        holder.imgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onGridClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Dashboard.Mycourselist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourse,tvDate,tvStatus;
        ImageView imgReport;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCourse = itemView.findViewById(R.id.tvCourse);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus =itemView.findViewById(R.id.tvStatus);
            imgReport =itemView.findViewById(R.id.imgReport);
       }
    }
}




