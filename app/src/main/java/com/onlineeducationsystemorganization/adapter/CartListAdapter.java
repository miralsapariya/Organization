package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.DeleteItemInCart;
import com.onlineeducationsystemorganization.interfaces.OnItemClick;
import com.onlineeducationsystemorganization.interfaces.UpdateItemInCart;
import com.onlineeducationsystemorganization.model.CartList;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private ArrayList<CartList.List> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private DeleteItemInCart deleteItemInCart;
    private UpdateItemInCart updateItemInCart;
    int amount=0;
    int check = 0;

    public CartListAdapter(Context context,
                           ArrayList<CartList.List> listProduct,
                           OnItemClick onItemClick,
                           DeleteItemInCart deleteItemInCart,
                           UpdateItemInCart updateItemInCart
                           ) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
        this.deleteItemInCart = deleteItemInCart;
        this.updateItemInCart =updateItemInCart;

    }

    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_cart, parent, false);
        CartListAdapter.ViewHolder viewHolder = new CartListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CartListAdapter.ViewHolder holder, final int position) {
        final CartList.List data = listProduct.get(position);
        int check = 0;
        AppUtils.loadImageWithPicasso(data.getImage(), holder.img, context, 0, 0);

        holder.tvCourseName.setText(data.getCourseName());
        holder.tvPrice.setText(data.getCoursePrice() + "");

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemInCart.deleteCart(position);
            }
        });
        final ArrayList<String> list=new ArrayList<>();
        list.add("1");list.add("2");
        list.add("3");list.add("4");
        list.add("5");list.add("6");
        list.add("7");list.add("8");
        list.add("9");list.add("10");
        list.add("11");list.add("12");
        list.add("13");list.add("14");
        list.add("15");list.add("16");
        list.add("17");list.add("18");
        list.add("19");list.add("20");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item,list);

        holder.spinnerUser.setAdapter(adapter);
        //https://stackoverflow.com/questions/13397933/android-spinner-avoid-onitemselected-calls-during-initialization/25070707
      //  holder.spinnerUser.setSelection(Adapter.NO_SELECTION, true);
        holder.spinnerUser.setSelection(0, false);
        if(data.getNoOfUser() ==1){

        }else
        {
            holder.spinnerUser.setSelection(data.getNoOfUser()-1,false);
        }
        holder.spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               // Log.d("set selection :: ", i+"");
                     updateItemInCart.updateCart(position,list.get(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public CartList.List getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCourseName,  tvPrice, tvOldPrice;
        private LinearLayout llMain;
        private ImageView img, imgDelete;
        private Spinner spinnerUser;


        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            img = itemView.findViewById(R.id.img);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            spinnerUser=itemView.findViewById(R.id.spinnerUser);
       }
    }
}




