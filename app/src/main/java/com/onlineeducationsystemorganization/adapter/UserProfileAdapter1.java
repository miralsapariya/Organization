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
import com.onlineeducationsystemorganization.interfaces.OnItemClick;

import java.util.ArrayList;

public class UserProfileAdapter1 extends RecyclerView.Adapter<UserProfileAdapter1.ViewHolder>
        {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public UserProfileAdapter1(Context context,
                               ArrayList<String> listProduct, OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public UserProfileAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_user_profile, parent, false);
        UserProfileAdapter1.ViewHolder viewHolder = new UserProfileAdapter1.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UserProfileAdapter1.ViewHolder holder, final int position) {
        final String data = listProduct.get(position);

        if(position ==0)
        {
            holder.img.setImageResource(R.mipmap.preferred_lang);
        }else if(position ==1) {
            holder.img.setImageResource(R.mipmap.my_profile);
        }else if(position ==2)
        {
            holder.img.setImageResource(R.mipmap.change_password);
        }else if(position == 3)
        {
            holder.img.setImageResource(R.mipmap.notification);
        }
        holder.tvCatName.setText(listProduct.get(position).toString());

        holder.llMain.setOnClickListener(new View.OnClickListener() {
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

    public  String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img,imgNext;

        public TextView tvCatName;
        public LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCatName =itemView.findViewById(R.id.tvCatName);
            img =itemView.findViewById(R.id.img);
            imgNext =itemView.findViewById(R.id.imgNext);
            llMain =itemView.findViewById(R.id.llMain);
        }
    }
}




