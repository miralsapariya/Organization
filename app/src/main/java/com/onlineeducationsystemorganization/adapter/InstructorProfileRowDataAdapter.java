package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;

import java.util.ArrayList;

public class InstructorProfileRowDataAdapter extends RecyclerView.Adapter<InstructorProfileRowDataAdapter.ViewHolder> {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public InstructorProfileRowDataAdapter(Context context,
                                           ArrayList<String> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public InstructorProfileRowDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_instructor_profile_data, parent, false);
        InstructorProfileRowDataAdapter.ViewHolder viewHolder = new InstructorProfileRowDataAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InstructorProfileRowDataAdapter.ViewHolder holder, final int position) {
        final String data = listProduct.get(position);

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvData;

        public ViewHolder(View itemView) {
            super(itemView);

            tvData = itemView.findViewById(R.id.tvData);
        }
    }


}




