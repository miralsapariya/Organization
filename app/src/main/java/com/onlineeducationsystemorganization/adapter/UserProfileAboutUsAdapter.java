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
import com.onlineeducationsystemorganization.interfaces.OnSubItemClick;

import java.util.ArrayList;

public class UserProfileAboutUsAdapter extends RecyclerView.Adapter<UserProfileAboutUsAdapter.ViewHolder>
         {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnSubItemClick onItemClick;
    private Context context;

    public UserProfileAboutUsAdapter(Context context,
                                     ArrayList<String> listProduct, OnSubItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public UserProfileAboutUsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_user_profile, parent, false);
        UserProfileAboutUsAdapter.ViewHolder viewHolder = new UserProfileAboutUsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UserProfileAboutUsAdapter.ViewHolder holder, final int position) {
        final String data = listProduct.get(position);
        if(position == 0)
        {
            holder.img.setImageResource(R.mipmap.my_profile);
        }
        /*else if(position ==1)
        {
            holder.img.setImageResource(R.mipmap.faq);
        }*/
        else if(position ==1)
        {
            holder.img.setImageResource(R.mipmap.privacy_policy);
        }else if(position == 2){
            holder.img.setImageResource(R.mipmap.terms_condition);
        }else
        {
            holder.img.setImageResource(R.mipmap.terms_condition);
        }

        holder.tvCatName.setText(listProduct.get(position).toString());

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onItemClick.onSubGridClick(position);
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
        public LinearLayout llMain;
        public TextView tvCatName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCatName =itemView.findViewById(R.id.tvCatName);
            img =itemView.findViewById(R.id.img);
            imgNext =itemView.findViewById(R.id.imgNext);
            llMain =itemView.findViewById(R.id.llMain);

        }
    }
}




