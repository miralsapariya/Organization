package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.DeleteItemInCart;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.AssignCourseUserList;

import java.util.ArrayList;

public class CourseAsssignUserAdapter extends RecyclerView.Adapter<CourseAsssignUserAdapter.ViewHolder> {

    private ArrayList<AssignCourseUserList.User> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private DeleteItemInCart deleteItemInCart;

    public CourseAsssignUserAdapter(Context context,
                                    ArrayList<AssignCourseUserList.User> listProduct,
                                    DeleteItemInCart deleteItemInCart
                           ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.deleteItemInCart = deleteItemInCart;
    }

    @Override
    public CourseAsssignUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_assign_courses, parent, false);
        CourseAsssignUserAdapter.ViewHolder viewHolder = new CourseAsssignUserAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CourseAsssignUserAdapter.ViewHolder holder, final int position) {
        final AssignCourseUserList.User data = listProduct.get(position);

        if(data.getUserDelete() == 1)
        {
            holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.primary_trans));
        }
        holder.tvEmail.setText(data.getEmail());
        if(data.getIsAssign() ==1 || data.getUserDelete() ==1)
        {
            holder.tvStatus.setText(context.getString(R.string.assigend));
            holder.imgUnassign.setVisibility(View.VISIBLE);
        }else {
            holder.tvStatus.setText(context.getString(R.string.unassigned));
            holder.imgUnassign.setVisibility(View.INVISIBLE);
        }
        holder.checkBoxName.setText(data.getName());

        holder.imgUnassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemInCart.deleteCart(position);
            }
        });
        holder.checkBoxName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    data.setSelected(true);
                }else
                {
                    data.setSelected(false);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public AssignCourseUserList.User getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmail,tvStatus;
        private LinearLayout llMain;
        private ImageView imgUnassign;
        private CheckBox checkBoxName;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgUnassign = itemView.findViewById(R.id.imgUnassign);
            checkBoxName =itemView.findViewById(R.id.checkBoxName);
       }
    }
}




