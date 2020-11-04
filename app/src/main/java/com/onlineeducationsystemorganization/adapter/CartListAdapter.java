package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        AppUtils.loadImageWithPicasso(data.getImage(), holder.img, context, 0, 0);

        holder.tvCourseName.setText(data.getCourseName());
        holder.tvPrice.setText(data.getCoursePrice() + "");

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemInCart.deleteCart(position);
            }
        });

        holder.etUser.setText(data.getNoOfUser()+"");
        holder.etUser.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(!TextUtils.isEmpty(holder.etUser.getText().toString()))
                    updateItemInCart.updateCart(position,holder.etUser.getText().toString());

                    return true;
                }
                return false;
            }
        });
       /* final ArrayList<String> list=new ArrayList<>();
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
                android.R.layout.simple_spinner_item,list);*/

      //  holder.spinnerUser.setAdapter(adapter);
      //  holder.spinnerUser.setSelection(Adapter.NO_SELECTION, true);
     //   holder.spinnerUser.setSelection(0, false);
       /* if(data.getNoOfUser() ==1){

        }else
        {
            holder.spinnerUser.setSelection(data.getNoOfUser()-1,false);
        }*/
       /* holder.spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                     updateItemInCart.updateCart(position,list.get(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

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
        private EditText etUser;


        public ViewHolder(View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.llMain);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            img = itemView.findViewById(R.id.img);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            etUser=itemView.findViewById(R.id.etUser);
       }
    }
}




