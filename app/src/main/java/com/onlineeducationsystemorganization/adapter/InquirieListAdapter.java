package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.OnSubItemClick;
import com.onlineeducationsystemorganization.model.Inquirie;

import java.util.ArrayList;

public class InquirieListAdapter extends RecyclerView.Adapter<InquirieListAdapter.ViewHolder> {

    private ArrayList<Inquirie.Inquirylist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private OnSubItemClick onSubItemClick;

    public InquirieListAdapter(Context context,
                               ArrayList<Inquirie.Inquirylist> listProduct,
                               OnItemClick onItemClick, OnSubItemClick onSubItemClick
                               ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
        this.onSubItemClick =onSubItemClick;
    }

    @Override
    public InquirieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_inquire_list, parent, false);
        InquirieListAdapter.ViewHolder viewHolder = new InquirieListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InquirieListAdapter.ViewHolder holder, final int position) {
        final Inquirie.Inquirylist data = listProduct.get(position);

        holder.tvDate.setText(data.getInquiryDate());
        holder.tvCategory.setText(data.getCategory());
        holder.tvSubCategory.setText(data.getSubCategory());
        holder.tvCourses.setText(data.getCourseName());
        holder.tvUser.setText(data.getNoOfUser()+"");
        holder.tvPrice.setText(data.getPrice()+"");
        holder.tvAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onGridClick(position);
            }
        });
        holder.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onSubItemClick.onSubGridClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Inquirie.Inquirylist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate,tvCategory,tvSubCategory,tvCourses,tvUser,
                tvPrice,tvAddMore,tvHistory;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCategory =itemView.findViewById(R.id.tvCategory);
            tvSubCategory = itemView.findViewById(R.id.tvSubCategory);
            tvCourses =itemView.findViewById(R.id.tvCourses);
            tvUser =itemView.findViewById(R.id.tvUser);
            tvPrice =itemView.findViewById(R.id.tvPrice);
            tvAddMore =itemView.findViewById(R.id.tvAddMore);
            tvHistory=itemView.findViewById(R.id.tvHistory);
       }
    }
}




