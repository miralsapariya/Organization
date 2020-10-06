package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.CourseDetail;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class CourseDetailCourseIncludesAdapter extends RecyclerView.Adapter<CourseDetailCourseIncludesAdapter.ViewHolder>
         {

    private ArrayList<CourseDetail.CourseInclude> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public CourseDetailCourseIncludesAdapter(Context context,
                                             ArrayList<CourseDetail.CourseInclude> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context =context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public CourseDetailCourseIncludesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_course_detail_includes, parent, false);
        CourseDetailCourseIncludesAdapter.ViewHolder viewHolder = new CourseDetailCourseIncludesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CourseDetailCourseIncludesAdapter.ViewHolder holder, int position) {
        final CourseDetail.CourseInclude data = listProduct.get(position);

        AppUtils.loadImageWithPicasso(data.getIncludeIcon() , holder.img, context, 0, 0);
        holder.tvCatName.setText(data.getIncludeTitle());
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  CourseDetail.CourseInclude getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView tvCatName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCatName =itemView.findViewById(R.id.tvCatName);
            img =itemView.findViewById(R.id.img);

        }
    }
}




