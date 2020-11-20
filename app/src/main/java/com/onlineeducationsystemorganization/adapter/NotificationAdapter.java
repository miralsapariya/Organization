package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.DeleteItemInCart;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.UpdateItemInCart;
import com.onlineeducationsystemorganization.model.NotificationList;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<NotificationList.Datum> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private DeleteItemInCart deleteItemInCart;
    private UpdateItemInCart updateItemInCart;
    int amount=0;


    public NotificationAdapter(Context context,
                               ArrayList<NotificationList.Datum> listProduct,
                               OnItemClick onItemClick
    ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick =onItemClick;
    }


    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_notification, parent, false);
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.ViewHolder holder, final int position) {
        final NotificationList.Datum data = listProduct.get(position);

        holder.tvNotification.setText(data.getTitle());
        holder.tvDescription.setText(data.getDescription());

        holder.imgAction.setOnClickListener(new View.OnClickListener() {
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

    public NotificationList.Datum getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNotification,  tvDescription;
        private LinearLayout llMain;
        private ImageView imgAction;

        public ViewHolder(View itemView) {
            super(itemView);

            imgAction = itemView.findViewById(R.id.imgAction);
            tvNotification = itemView.findViewById(R.id.tvNotification);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}





