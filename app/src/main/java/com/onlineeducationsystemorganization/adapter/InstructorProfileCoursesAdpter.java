package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnInstructorsClick;
import com.onlineeducationsystemorganization.model.InstructorProfile;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class InstructorProfileCoursesAdpter extends RecyclerView.Adapter<InstructorProfileCoursesAdpter.ViewHolder> {

    private ArrayList<InstructorProfile.List> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnInstructorsClick onItemClick;

    public InstructorProfileCoursesAdpter(Context context,
                                          ArrayList<InstructorProfile.List> listProduct, OnInstructorsClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick =onItemClick;
    }



    @Override
    public InstructorProfileCoursesAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_instructor_profile_course, parent, false);
        InstructorProfileCoursesAdpter.ViewHolder viewHolder = new InstructorProfileCoursesAdpter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InstructorProfileCoursesAdpter.ViewHolder holder, final int position) {
        final InstructorProfile.List data = listProduct.get(position);

        AppUtils.loadImageWithPicasso
                (data.getImage() , holder.img, context, 0, 0);
        holder.tvName.setText(data.getCourseName());
        holder.tvInstructorname.setText(data.getInstructorName());
        holder.tvPublistOn.setText(data.getPublishOn());
        holder.tvData.setText(data.getCategoryName());
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onInstructorClick(position);
            }
        });
       /* ArrayList<String> list =new ArrayList<>();
        list.add("Data science");
        list.add("Data science");
        list.add("Data science");

        InstructorProfileRowDataAdapter homeAdapter =
                new InstructorProfileRowDataAdapter(context, list);

        holder.rvHorizonatal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvHorizonatal.setHasFixedSize(true);
        holder.rvHorizonatal.setItemAnimator(new DefaultItemAnimator());
        holder.rvHorizonatal.setAdapter(homeAdapter);
        holder.rvHorizonatal.addOnItemTouchListener(mScrollTouchListener);*/
        holder.tvNewPrice.setText(data.getCoursePrice()+"");
        holder.tvOldPrice.setText(data.getCourseOldPrice()+"");
        holder.tvOldPrice.setPaintFlags( holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public InstructorProfile.List getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView rvHorizonatal;
        TextView tvOldPrice,tvNewPrice,tvName,tvInstructorname,tvPublistOn,tvData;
        ImageView img;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
            rvHorizonatal = itemView.findViewById(R.id.rvHorizonatal);
            tvOldPrice =itemView.findViewById(R.id.tvOldPrice);
            tvName =itemView.findViewById(R.id.tvName);
            tvInstructorname =itemView.findViewById(R.id.tvInstructorname);
            tvPublistOn =itemView.findViewById(R.id.tvPublistOn);
            tvNewPrice =itemView.findViewById(R.id.tvNewPrice);
            tvData =itemView.findViewById(R.id.tvData);
            llMain =itemView.findViewById(R.id.llMain);
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




