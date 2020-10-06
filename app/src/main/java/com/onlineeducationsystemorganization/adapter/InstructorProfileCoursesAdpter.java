package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;

import java.util.ArrayList;

public class InstructorProfileCoursesAdpter extends RecyclerView.Adapter<InstructorProfileCoursesAdpter.ViewHolder> {

    private ArrayList<String> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public InstructorProfileCoursesAdpter(Context context,
                                          ArrayList<String> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public InstructorProfileCoursesAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_instructor_profile_course, parent, false);
        InstructorProfileCoursesAdpter.ViewHolder viewHolder = new InstructorProfileCoursesAdpter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InstructorProfileCoursesAdpter.ViewHolder holder, final int position) {
        final String data = listProduct.get(position);

        ArrayList<String> list =new ArrayList<>();
        list.add("Data science");
        list.add("Data science");
        list.add("Data science");

        InstructorProfileRowDataAdapter homeAdapter =
                new InstructorProfileRowDataAdapter(context, list);

        holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvHorizonatal.setHasFixedSize(true);
        holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
        holder.rvHorizonatal.setAdapter(homeAdapter);
        holder.rvHorizonatal.addOnItemTouchListener(mScrollTouchListener);

        holder.tvOldPrice.setText("$ 500");
        holder.tvOldPrice.setPaintFlags( holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);



    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public String getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView rvHorizonatal;
        TextView tvOldPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            rvHorizonatal = itemView.findViewById(R.id.rvHorizonatal);
            tvOldPrice =itemView.findViewById(R.id.tvOldPrice);
        }
    }

    RecyclerView.OnItemTouchListener mScrollTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            int action = e.getAction();
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    rv.getParent().requestDisallowInterceptTouchEvent(true);
                    break;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };
}




