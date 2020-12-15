package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.DashboardReport;

import java.util.ArrayList;

public class DashboardReportAdapter extends RecyclerView.Adapter<DashboardReportAdapter.ViewHolder> {

    private ArrayList<DashboardReport.Useractivitylist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public DashboardReportAdapter(Context context,
                                  ArrayList<DashboardReport.Useractivitylist> listProduct, OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick=onItemClick;
    }

    @Override
    public DashboardReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_dashboard_report, parent, false);
        DashboardReportAdapter.ViewHolder viewHolder = new DashboardReportAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DashboardReportAdapter.ViewHolder holder, final int position) {
        final DashboardReport.Useractivitylist data = listProduct.get(position);
        holder.tvUsername.setText(data.getUserName());
        holder.tvSlide.setText(context.getString(R.string.slide)+": "+data.getSlide());
        holder.tvAttemp.setText(context.getString(R.string.attempts)+": "+data.getAttempts());
        holder.tvStatus.setText(data.getStatus());
        if(TextUtils.isEmpty(data.getBtnStatus())) {
            holder.btnStatus.setVisibility(View.GONE);
        }else {
            holder.btnStatus.setVisibility(View.VISIBLE);
        }
        holder.btnStatus.setText(data.getButton());
        //download-1
        //view course -2
        //----- " "
        holder.btnStatus.setOnClickListener(new View.OnClickListener() {
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

    public DashboardReport.Useractivitylist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername,tvSlide,tvAttemp,tvStatus;
        Button btnStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvSlide = itemView.findViewById(R.id.tvSlide);
            tvAttemp=itemView.findViewById(R.id.tvAttemp);
            tvStatus =itemView.findViewById(R.id.tvStatus);
            btnStatus =itemView.findViewById(R.id.btnStatus);
       }
    }
}




