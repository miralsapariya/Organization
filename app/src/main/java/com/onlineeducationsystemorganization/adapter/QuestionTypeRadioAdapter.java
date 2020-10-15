package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Exam;

import java.util.ArrayList;

public class QuestionTypeRadioAdapter extends RecyclerView.Adapter<QuestionTypeRadioAdapter.ViewHolder> {

    private ArrayList<Exam.Option> listProduct;
    private LayoutInflater mInflater;
    private Context context;
    public int mSelectedItem = -1;

    public QuestionTypeRadioAdapter(Context context,
                                    ArrayList<Exam.Option> listProduct, int mSelectedItem) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.mSelectedItem =mSelectedItem;
    }

    @Override
    public QuestionTypeRadioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_question_type_radio, parent, false);
        QuestionTypeRadioAdapter.ViewHolder viewHolder = new QuestionTypeRadioAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuestionTypeRadioAdapter.ViewHolder holder, final int position) {
        final Exam.Option data = listProduct.get(position);
        holder.radioOption.setText(data.getOption());
        holder.radioOption.setChecked(position == mSelectedItem);

       /* if(listProduct.get(position).getSelected().equalsIgnoreCase("true")){
            holder.radioOption.setChecked(true);
            listProduct.get(position).setSelected(true);
        }else
        {
            holder.radioOption.setChecked(false);
            listProduct.get(position).setSelected(false);
        }*/


    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Exam.Option getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RadioButton radioOption;
        public ViewHolder(View itemView) {
            super(itemView);
            radioOption = itemView.findViewById(R.id.radioOption);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    for (int i=0;i<listProduct.size();i++) {
                        if(mSelectedItem == i)
                        listProduct.get(i).setSelected(true);
                        else
                            listProduct.get(i).setSelected(false);
                    }
                    notifyDataSetChanged();
                }
            };
            itemView.setOnClickListener(clickListener);
            radioOption.setOnClickListener(clickListener);
        }
    }
}





