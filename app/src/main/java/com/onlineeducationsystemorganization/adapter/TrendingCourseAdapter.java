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
import com.onlineeducationsystemorganization.interfaces.AddItemInCart;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.CourseList;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class TrendingCourseAdapter extends RecyclerView.Adapter<TrendingCourseAdapter.ViewHolder> {

    private ArrayList<CourseList.Courseslist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private AddItemInCart addItemInCart;

    public TrendingCourseAdapter(Context context,
                                 ArrayList<CourseList.Courseslist> listProduct, OnItemClick onItemClick, AddItemInCart addItemInCart) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick =onItemClick;
        this.addItemInCart =addItemInCart;
    }

    @Override
    public TrendingCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_trending_course, parent, false);
        TrendingCourseAdapter.ViewHolder viewHolder = new TrendingCourseAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TrendingCourseAdapter.ViewHolder holder, final int position) {
        final CourseList.Courseslist data = listProduct.get(position);

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onItemClick.onGridClick(position);
            }
        });

        holder.tvCourse.setText(data.getCourseName());
        holder.tvPrice.setText(data.getCoursePrice());
        holder.catName.setText(data.getCategoryName());
        holder.tvDate.setText(data.getPublishOn());
        holder.tvPriceOld.setText(data.getCourseOldPrice());
        holder.tvPriceOld.setPaintFlags( holder.tvPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if(!data.getInstructorName().equals("")) {
            holder.tvDescription.setText(data.getInstructorName());
        } else {
            holder.tvDescription.setVisibility(View.GONE);
        }
        AppUtils.loadImageWithPicasso(data.getImage() , holder.img, context, 0, 0);

        holder.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemInCart.addToCart(position);
            }
        });

        if(data.getIs_added() ==1|| data.getIs_purchased() == 1) {
            holder.imgCart.setVisibility(View.GONE);
        }else {
            holder.imgCart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public CourseList.Courseslist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCourse, tvPrice,catName,tvDate,tvPriceOld,tvDescription;
        private LinearLayout llMain;
        private ImageView img,imgCart;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvCourse = itemView.findViewById(R.id.tvCourse);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            llMain = itemView.findViewById(R.id.llMain);
            catName = itemView.findViewById(R.id.catName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPriceOld = itemView.findViewById(R.id.tvPriceOld);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            img =itemView.findViewById(R.id.img);
            imgCart =itemView.findViewById(R.id.imgCart);
        }
    }
}




