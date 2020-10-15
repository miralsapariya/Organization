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

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.ViewHolder>
        {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public UserProfileAdapter(Context context,
                              ArrayList<String> listProduct, OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public UserProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_user_profile, parent, false);
        UserProfileAdapter.ViewHolder viewHolder = new UserProfileAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UserProfileAdapter.ViewHolder holder, final int position) {
        final String data = listProduct.get(position);

        if(position ==0)
        {
            holder.img.setImageResource(R.mipmap.preferred_lang);
        }else if(position ==1)
        {
            holder.img.setImageResource(R.mipmap.dashboard);
        }

        else if(position ==2)
        {
            holder.img.setImageResource(R.mipmap.my_courses);
        }else if(position ==3)
        {holder.img.setImageResource(R.mipmap.dashboard);
        }
        else if(position ==4)
        {holder.img.setImageResource(R.mipmap.subscribe);
        }else if(position ==5) {
            holder.img.setImageResource(R.mipmap.my_profile);
        }else if(position ==6)
        {
            holder.img.setImageResource(R.mipmap.change_password);
        }else if(position == 7)
        {
            holder.img.setImageResource(R.mipmap.whish_list_bottom);
        }else if(position == 8)
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




