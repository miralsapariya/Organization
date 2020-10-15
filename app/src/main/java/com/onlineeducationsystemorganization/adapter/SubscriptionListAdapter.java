package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnSubItemClick;
import com.onlineeducationsystemorganization.model.Subscription;

import java.util.ArrayList;

public class SubscriptionListAdapter extends RecyclerView.Adapter<SubscriptionListAdapter.ViewHolder> {

    private ArrayList<Subscription.Subscriptionlist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnSubItemClick onSubItemClick;

    public SubscriptionListAdapter(Context context,
                                   ArrayList<Subscription.Subscriptionlist> listProduct,
                                    OnSubItemClick onSubItemClick
                               ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onSubItemClick =onSubItemClick;
    }

    @Override
    public SubscriptionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_subscription_list, parent, false);
        SubscriptionListAdapter.ViewHolder viewHolder = new SubscriptionListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubscriptionListAdapter.ViewHolder holder, final int position) {
        final Subscription.Subscriptionlist data = listProduct.get(position);

        holder.tvDate.setText(data.getSubscriptionDate());
        holder.tvCategory.setText(data.getCategory());
        holder.tvSubCategory.setText(data.getSubCategory());
        holder.tvCourses.setText(data.getCourseName());
        holder.tvUser.setText(data.getNoOfUser()+"");
        holder.tvPrice.setText(data.getPrice()+"");

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

    public Subscription.Subscriptionlist getItem(int id) {
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




