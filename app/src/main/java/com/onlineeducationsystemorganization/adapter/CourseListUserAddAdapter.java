package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Courses;
import java.util.ArrayList;

public class CourseListUserAddAdapter extends RecyclerView.Adapter<CourseListUserAddAdapter.ViewHolder> {

    private ArrayList<Courses.Courseslist> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public CourseListUserAddAdapter(Context context,
                                    ArrayList<Courses.Courseslist> listProduct
                           ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public CourseListUserAddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_add_user_courses, parent, false);
        CourseListUserAddAdapter.ViewHolder viewHolder = new CourseListUserAddAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CourseListUserAddAdapter.ViewHolder holder, final int position) {
        final Courses.Courseslist data = listProduct.get(position);
        holder.checkboxCourseList.setText(data.getCourseName());


        holder.checkboxCourseList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    data.setSelected(true);
                }else {
                    data.setSelected(false);
                }
            }
        });
       /* if(data.getIs_started() == 1) {
            holder.checkboxCourseList.setEnabled(false);
        }*/

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Courses.Courseslist getItem(int id) {
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




