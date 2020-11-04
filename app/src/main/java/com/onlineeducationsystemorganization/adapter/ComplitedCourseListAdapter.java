package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.CompletedCourses;

import java.util.ArrayList;

public class ComplitedCourseListAdapter extends RecyclerView.Adapter<ComplitedCourseListAdapter.ViewHolder> {

    private ArrayList<CompletedCourses.CoursechartDropdownlist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public ComplitedCourseListAdapter(Context context,
                                      ArrayList<CompletedCourses.CoursechartDropdownlist> listProduct
                                      ,OnItemClick onItemClick
                           ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick=onItemClick;
    }



    @Override
    public ComplitedCourseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_comaplete_courses, parent, false);
        ComplitedCourseListAdapter.ViewHolder viewHolder = new ComplitedCourseListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ComplitedCourseListAdapter.ViewHolder holder, final int position) {
        final CompletedCourses.CoursechartDropdownlist data = listProduct.get(position);
        holder.checkboxCourseList.setText(data.getCourseName());
        Log.d("----------------------------", ""+holder.checkboxCourseList.isChecked());

        if(holder.checkboxCourseList.isChecked() ==true)
        {
            data.setSelected(true);
        }else
        {
            data.setSelected(false);
        }
        holder.checkboxCourseList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    Log.d("================ ", "trueeee");
                    data.setSelected(true);
                    onItemClick.onGridClick(position);
                }else {
                    Log.d("================ ", "falseeeee");
                    data.setSelected(false);
                    onItemClick.onGridClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public CompletedCourses.CoursechartDropdownlist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkboxCourseList;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            checkboxCourseList = itemView.findViewById(R.id.checkboxCourseList);
       }
    }
}




