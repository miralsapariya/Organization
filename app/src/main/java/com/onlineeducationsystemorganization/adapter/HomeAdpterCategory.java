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
import com.onlineeducationsystemorganization.model.Home;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class HomeAdpterCategory extends RecyclerView.Adapter<HomeAdpterCategory.ViewHolder>
        {

    private ArrayList<Home.List1> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public HomeAdpterCategory(Context context,
                              ArrayList<Home.List1> listProduct, OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public HomeAdpterCategory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_home_category, parent, false);
        HomeAdpterCategory.ViewHolder viewHolder = new HomeAdpterCategory.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeAdpterCategory.ViewHolder holder, final int position) {
        final  Home.List1 data = listProduct.get(position);

        holder.llMAin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClick.onGridClick(position);
            }
        });

        holder.tvName.setText(data.getCategoryName());
        holder.tvNoOfCourses.setText(data.getTotalCourse()+" "+context.getResources().getString(R.string.courses));
        AppUtils.loadImageWithPicasso(data.getCategoryIcon() , holder.img, context, 0, 0);

    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  Home.List1 getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvNoOfCourses;
        public LinearLayout llMAin;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName =itemView.findViewById(R.id.tvName);
            tvNoOfCourses =itemView.findViewById(R.id.tvNoOfCourses);
            llMAin =itemView.findViewById(R.id.llMAin);
            img =itemView.findViewById(R.id.img);
        }
    }
}


