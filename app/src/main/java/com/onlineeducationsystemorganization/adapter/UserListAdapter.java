package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.DeleteItemInCart;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.UpdateItemInCart;
import com.onlineeducationsystemorganization.model.UserList;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private ArrayList<UserList.User> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private DeleteItemInCart deleteItemInCart;
    private UpdateItemInCart updateItemInCart;
    int amount=0;


    public UserListAdapter(Context context,
                           ArrayList<UserList.User> listProduct,
                           DeleteItemInCart deleteItemInCart,
                           UpdateItemInCart updateItemInCart
    ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.deleteItemInCart = deleteItemInCart;
        this.updateItemInCart =updateItemInCart;

    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_user_list, parent, false);
        UserListAdapter.ViewHolder viewHolder = new UserListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UserListAdapter.ViewHolder holder, final int position) {
        final UserList.User data = listProduct.get(position);

        holder.tvUserName.setText(data.getName());
        holder.tvPhone.setText(data.getPhoneNo());

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItemInCart.updateCart(data.getId(),position+"");
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemInCart.deleteCart(data.getId());
            }
        });


    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public UserList.User getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName,  tvPhone, tvEdit,tvDelete;
        private LinearLayout llMain;


        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDelete=itemView.findViewById(R.id.tvDelete);
        }
    }
}





