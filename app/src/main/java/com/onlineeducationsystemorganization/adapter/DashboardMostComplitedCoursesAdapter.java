package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Dashboard;

import java.util.ArrayList;

public class DashboardMostComplitedCoursesAdapter extends RecyclerView.Adapter<DashboardMostComplitedCoursesAdapter.ViewHolder> {

    private ArrayList<Dashboard.MostCompletedCourse> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public DashboardMostComplitedCoursesAdapter(Context context,
                                                ArrayList<Dashboard.MostCompletedCourse> listProduct
                           ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;

    }

    @Override
    public DashboardMostComplitedCoursesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_horizonatal, parent, false);
        DashboardMostComplitedCoursesAdapter.ViewHolder viewHolder = new DashboardMostComplitedCoursesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DashboardMostComplitedCoursesAdapter.ViewHolder holder, final int position) {
        final Dashboard.MostCompletedCourse data = listProduct.get(position);

        float f=Float.parseFloat(data.getPercentOfCourse());

        holder.circularProgressbar.setProgress((int)f);
        holder.tvCourseName.setText(data.getCourseName());
        holder.tvComplite.setText(data.getCountOfCourse());
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Dashboard.MostCompletedCourse getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar circularProgressbar;
        TextView tvCourseName,tvComplite;

        public ViewHolder(View itemView) {
            super(itemView);

            circularProgressbar = itemView.findViewById(R.id.circularProgressbar);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvComplite =itemView.findViewById(R.id.tvComplite);
       }
    }
}




