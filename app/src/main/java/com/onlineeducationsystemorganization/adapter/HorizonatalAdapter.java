package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Home;

import java.util.ArrayList;

public class HorizonatalAdapter extends RecyclerView.Adapter<HorizonatalAdapter.ViewHolder>
  {

    private ArrayList<Home.List1> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public HorizonatalAdapter(Context context,
                              ArrayList<Home.List1> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
    }

    @Override
    public HorizonatalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_horizonatal, parent, false);
        HorizonatalAdapter.ViewHolder viewHolder = new HorizonatalAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HorizonatalAdapter.ViewHolder holder, final int position) {
        final  Home.List1 data = listProduct.get(position);

    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  Home.List1 getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv,tvComplite;

        public ViewHolder(View itemView) {
            super(itemView);

            tv =itemView.findViewById(R.id.tv);
            tvComplite =itemView.findViewById(R.id.tvComplite);

        }
    }
}

