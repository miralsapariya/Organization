package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;

import java.util.ArrayList;

public class PopularInstructorAdapter extends RecyclerView.Adapter<PopularInstructorAdapter.ViewHolder>
{

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public PopularInstructorAdapter(Context context,
                                    ArrayList<String> listProduct, OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public PopularInstructorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_instructor, parent, false);
        PopularInstructorAdapter.ViewHolder viewHolder = new PopularInstructorAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PopularInstructorAdapter.ViewHolder holder, final int position) {
        final  String data = listProduct.get(position);
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
        public TextView tvCourse;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCourse =itemView.findViewById(R.id.tvCourse);
            llMain =itemView.findViewById(R.id.llMain);

        }
    }
}

