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
import com.onlineeducationsystemorganization.model.InstructorList;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class PopularInstructorAdapter extends RecyclerView.Adapter<PopularInstructorAdapter.ViewHolder>
{

    private ArrayList<InstructorList.PopularInstructorList> listProduct;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private Context context;

    public PopularInstructorAdapter(Context context,
                                    ArrayList<InstructorList.PopularInstructorList>
                                    listProduct, OnItemClick onItemClick) {
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
        final  InstructorList.PopularInstructorList data = listProduct.get(position);
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClick.onGridClick(position);
            }
        });
        AppUtils.loadImageWithPicasso(data.getProfilePicture() , holder.imgUser,
                context, 0, 0);

        holder.tvNAme.setText(data.getName());
        holder.tvCatName.setText(data.getCategoryName());
        holder.tvNoOfStudent.setText(data.getTotalStudents()+" "+context.getString(R.string.students));
        holder.tvNoOfCourses.setText(data.getTotalCourse()+" "+context.getString(R.string.courses));

    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public  InstructorList.PopularInstructorList getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNAme,tvCatName,tvNoOfStudent,tvNoOfCourses;
        private LinearLayout llMain;
        private ImageView imgUser;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNAme =itemView.findViewById(R.id.tvNAme);
            tvCatName =itemView.findViewById(R.id.tvCatName);
            tvNoOfStudent =itemView.findViewById(R.id.tvNoOfStudent);
            tvNoOfCourses =itemView.findViewById(R.id.tvNoOfCourses);
            llMain =itemView.findViewById(R.id.llMain);
            imgUser=itemView.findViewById(R.id.imgUser);
        }
    }
}

