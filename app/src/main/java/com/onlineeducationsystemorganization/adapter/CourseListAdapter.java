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
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.Courses;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private ArrayList<Courses.Courseslist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;


    public CourseListAdapter(Context context,
                             ArrayList<Courses.Courseslist> listProduct,
                             OnItemClick onItemClick
                           ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;

    }

    @Override
    public CourseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_courses, parent, false);
        CourseListAdapter.ViewHolder viewHolder = new CourseListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CourseListAdapter.ViewHolder holder, final int position) {
        final Courses.Courseslist data = listProduct.get(position);

        AppUtils.loadImageWithPicasso(data.getCourseImage(), holder.img, context, 0, 0);

        holder.tvCourseName.setText(data.getCourseName());
        holder.tvAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onGridClick(position);
            }
        });
        holder.tvTotalUser.setText(data.getTotalUser()+"");
        holder.tvAssigned.setText(data.getTotalAssignedUser()+"");

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Courses.Courseslist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCourseName,tvAssign,tvTotalUser,tvAssigned,tvRemainig;
        private LinearLayout llMain;
        private ImageView img;


        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvAssign =itemView.findViewById(R.id.tvAssign);
            tvTotalUser = itemView.findViewById(R.id.tvTotalUser);
            tvAssigned =itemView.findViewById(R.id.tvAssigned);
            tvRemainig =itemView.findViewById(R.id.tvRemainig);
            img = itemView.findViewById(R.id.img);
       }
    }
}




