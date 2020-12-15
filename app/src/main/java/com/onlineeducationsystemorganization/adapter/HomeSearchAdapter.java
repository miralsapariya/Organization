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
import com.onlineeducationsystemorganization.model.DefaultCategory;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class HomeSearchAdapter extends RecyclerView.Adapter<HomeSearchAdapter.ViewHolder>
         {

    private ArrayList<DefaultCategory.Category> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public HomeSearchAdapter(Context context,
                             ArrayList<DefaultCategory.Category> listProduct, OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public HomeSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_home_search, parent, false);
        HomeSearchAdapter.ViewHolder viewHolder = new HomeSearchAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeSearchAdapter.ViewHolder holder, final int position) {
        final DefaultCategory.Category data = listProduct.get(position);

        AppUtils.loadImageWithPicasso(data.getCategoryIcon() , holder.img, context, 0, 0);

        holder.tvCatName.setText(data.getCategoryName()+" ("+data.getTotal_course()+" "+context.getString(R.string.courses)+")");
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

    public  DefaultCategory.Category getItem(int id) {
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




