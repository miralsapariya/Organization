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
import com.onlineeducationsystemorganization.model.GlobalSearch;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private ArrayList<GlobalSearch.Courseslist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private AddItemInCart addItemInCart;

    public SearchResultAdapter(Context context,
                               ArrayList<GlobalSearch.Courseslist> listProduct, OnItemClick onItemClick, AddItemInCart addItemInCart) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick =onItemClick;
        this.addItemInCart =addItemInCart;
    }

    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_search_result, parent, false);
        SearchResultAdapter.ViewHolder viewHolder = new SearchResultAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchResultAdapter.ViewHolder holder, final int position) {
        final GlobalSearch.Courseslist data = listProduct.get(position);

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
        holder.tvDescription.setText(data.getInstructorName());
        AppUtils.loadImageWithPicasso(data.getImage() , holder.img, context, 0, 0);
        holder.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addItemInCart.addToCart(position);
            }
        });
        if(data.getIs_added()==1|| data.getIs_purchased() == 1)
        {
            holder.imgCart.setVisibility(View.GONE);
        }else
        {
            holder.imgCart.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public GlobalSearch.Courseslist getItem(int id) {
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




