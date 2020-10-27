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
import com.onlineeducationsystemorganization.interfaces.DeleteWhishList;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.MyWhishList;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class WhishListAdapter extends RecyclerView.Adapter<WhishListAdapter.ViewHolder> {

    private ArrayList<MyWhishList.Courseslist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private DeleteWhishList deleteWhishList;

    public WhishListAdapter(Context context,
                            ArrayList<MyWhishList.Courseslist> listProduct,
                            OnItemClick onItemClick, DeleteWhishList deleteWhishList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
        this.deleteWhishList=deleteWhishList;
    }

    @Override
    public WhishListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_wishlist, parent, false);
        WhishListAdapter.ViewHolder viewHolder = new WhishListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final WhishListAdapter.ViewHolder holder, final int position) {
        final MyWhishList.Courseslist data = listProduct.get(position);

        AppUtils.loadImageWithPicasso(data.getImage() , holder.img, context, 0, 0);

        holder.tvCourse.setText(data.getCourseName());
        holder.tvPrice.setText(data.getCoursePrice());
        holder.tvMainCat.setText(data.getCategoryName());
        holder.tvInstructor.setText(data.getInstructorName());
        holder.tvPublishOn.setText(data.getPublishOn());
        holder.tvOldPrice.setText(data.getCourseOldPrice());

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClick.onGridClick(data.getId());
            }
        });

        holder.imgWhishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWhishList.deleteWishList(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public MyWhishList.Courseslist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCourse, tvPrice,tvMainCat,tvOldPrice,tvPublishOn,tvInstructor;
        private LinearLayout llMain;
        private ImageView img,imgWhishList;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvCourse = itemView.findViewById(R.id.tvCourse);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvMainCat = itemView.findViewById(R.id.tvMainCat);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvPublishOn = itemView.findViewById(R.id.tvPublishOn);
            tvInstructor =itemView.findViewById(R.id.tvInstructor);
            img =itemView.findViewById(R.id.img);
            imgWhishList =itemView.findViewById(R.id.imgWhishList);
        }
    }
}




