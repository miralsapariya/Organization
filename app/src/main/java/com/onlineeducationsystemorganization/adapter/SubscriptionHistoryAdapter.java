package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.SubscriptionHistory;

import java.util.ArrayList;

public class SubscriptionHistoryAdapter extends RecyclerView.Adapter<SubscriptionHistoryAdapter.ViewHolder> {

    private ArrayList<SubscriptionHistory.Subscriptionhistorylist> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public SubscriptionHistoryAdapter(Context context,
                                      ArrayList<SubscriptionHistory.Subscriptionhistorylist> listProduct
                           ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;

    }

    @Override
    public SubscriptionHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_subscription_history, parent, false);
        SubscriptionHistoryAdapter.ViewHolder viewHolder = new SubscriptionHistoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubscriptionHistoryAdapter.ViewHolder holder, final int position) {
        final SubscriptionHistory.Subscriptionhistorylist data = listProduct.get(position);

        holder.tvDate.setText(data.getInquiryDate());
        holder.tvUser.setText(data.getNoOfUser()+"");
        holder.tvPrice.setText(data.getPrice()+"");
        holder.tvSubDate.setText(data.getSubscriptionDate());
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public SubscriptionHistory.Subscriptionhistorylist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate,tvUser,tvPrice,tvSubDate;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvDate =itemView.findViewById(R.id.tvDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvUser =itemView.findViewById(R.id.tvUser);
            tvSubDate =itemView.findViewById(R.id.tvSubDate);
       }
    }
}




