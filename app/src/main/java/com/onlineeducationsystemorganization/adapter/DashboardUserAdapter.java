package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.ApplyPromoCode;
import com.onlineeducationsystemorganization.interfaces.CheckOutInCart;
import com.onlineeducationsystemorganization.interfaces.DeleteItemInCart;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.model.UserDashBoard;

import java.util.ArrayList;

public class DashboardUserAdapter extends RecyclerView.Adapter<DashboardUserAdapter.ViewHolder> {

    private ArrayList<UserDashBoard.Mycourselist> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private DeleteItemInCart deleteItemInCart;
    private ApplyPromoCode applyPromoCode;
    private CheckOutInCart checkOutInCart;
    int amount=0;
    public DashboardUserAdapter(Context context,
                                ArrayList<UserDashBoard.Mycourselist> listProduct,
                                OnItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public DashboardUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_dashboard_user, parent, false);
        DashboardUserAdapter.ViewHolder viewHolder = new DashboardUserAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DashboardUserAdapter.ViewHolder holder, final int position) {
        final UserDashBoard.Mycourselist data = listProduct.get(position);

        holder.tvNAme.setText(data.getCourseName());
        holder.tvSatus.setText(data.getStatus()+"");
        holder.btnStatus.setText(data.getButton());
        holder.btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onGridClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public UserDashBoard.Mycourselist getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNAme, tvSatus;
        public Button btnStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNAme = itemView.findViewById(R.id.tvNAme);
            tvSatus = itemView.findViewById(R.id.tvSatus);
            btnStatus =itemView.findViewById(R.id.btnStatus);
        }
    }
}




