package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.InquiryHistory;

import java.util.ArrayList;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryAdapter.ViewHolder> {

    private ArrayList<InquiryHistory.Inquiryhistorylist> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public InquiryAdapter(Context context,
                          ArrayList<InquiryHistory.Inquiryhistorylist> listProduct
                           ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;

    }

    @Override
    public InquiryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_inquiry_history, parent, false);
        InquiryAdapter.ViewHolder viewHolder = new InquiryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InquiryAdapter.ViewHolder holder, final int position) {
        final InquiryHistory.Inquiryhistorylist data = listProduct.get(position);

        holder.tvDate.setText(data.getInquiryDate());
        holder.tvUser.setText(data.getNoOfUser()+"");
        holder.tvPrice.setText(data.getPrice()+"");

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public InquiryHistory.Inquiryhistorylist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate,tvUser,tvPrice;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvDate =itemView.findViewById(R.id.tvDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvUser =itemView.findViewById(R.id.tvUser);
       }
    }
}




