package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnNewCourseClick;
import com.onlineeducationsystemorganization.model.Home;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class HomeAdpterCourses extends RecyclerView.Adapter<HomeAdpterCourses.ViewHolder>
         {

    private ArrayList<Home.List1> listProduct;
    private LayoutInflater mInflater;
    private OnNewCourseClick onItemClick;
    private Context context;

    public HomeAdpterCourses(Context context,
                             ArrayList<Home.List1> listProduct, OnNewCourseClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public HomeAdpterCourses.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_home_course, parent, false);
        HomeAdpterCourses.ViewHolder viewHolder = new HomeAdpterCourses.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeAdpterCourses.ViewHolder holder, final int position) {
        final  Home.List1 data = listProduct.get(position);

        holder.tvName.setText(data.getCourseName());
        //holder.tvSubName.setText(data.get);
        holder.tvInstructorname.setText(data.getInstructorName());
        holder.tvOldPrice.setText(data.getCourseOldPrice());
        holder.tvOldPrice.setPaintFlags( holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.tvNewPrice.setText(data.getCoursePrice());
        AppUtils.loadImageWithPicasso(data.getImage() , holder.img, context, 0, 0);

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onNewCourseClick(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  Home.List1 getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvSubName,tvInstructorname,tvOldPrice,tvNewPrice;
        private ImageView img;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSubName =itemView.findViewById(R.id.tvSubName);
            tvName =itemView.findViewById(R.id.tvName);
            tvInstructorname =itemView.findViewById(R.id.tvInstructorname);
            tvOldPrice =itemView.findViewById(R.id.tvOldPrice);
            tvNewPrice =itemView.findViewById(R.id.tvNewPrice);
            img =itemView.findViewById(R.id.img);
            llMain =itemView.findViewById(R.id.llMain);

        }
    }
}

