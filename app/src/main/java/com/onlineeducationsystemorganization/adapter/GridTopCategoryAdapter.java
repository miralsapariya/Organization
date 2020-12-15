package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.Category;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class GridTopCategoryAdapter extends RecyclerView.Adapter<GridTopCategoryAdapter.ViewHolder>
       {

    private ArrayList<Category.TopCategoriesList> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public GridTopCategoryAdapter(Context context,
                                  ArrayList<Category.TopCategoriesList> listProduct,
                                  OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public GridTopCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_grid_top_cat, parent, false);
        GridTopCategoryAdapter.ViewHolder viewHolder = new GridTopCategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GridTopCategoryAdapter.ViewHolder holder, final int position) {
        final Category.TopCategoriesList data = listProduct.get(position);
        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClick.onGridClick(position);
            }
        });
        AppUtils.loadImageWithPicasso(data.getCategoryImg() , holder.imageView, context, 0, 0);
        holder.title.setText(data.getCategoryName());

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Category.TopCategoriesList getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        private RelativeLayout rlMain;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            title =itemView.findViewById(R.id.title);
            rlMain =itemView.findViewById(R.id.rlMain);
            imageView =itemView.findViewById(R.id.imageView);

        }
    }
}



