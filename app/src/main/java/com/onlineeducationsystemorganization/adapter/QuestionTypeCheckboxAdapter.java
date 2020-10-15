package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Exam;

import java.util.ArrayList;

public class QuestionTypeCheckboxAdapter extends RecyclerView.Adapter<QuestionTypeCheckboxAdapter.ViewHolder> {

    private ArrayList<Exam.Option> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public QuestionTypeCheckboxAdapter(Context context,
                                       ArrayList<Exam.Option> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public QuestionTypeCheckboxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_question_type_checkbox, parent, false);
        QuestionTypeCheckboxAdapter.ViewHolder viewHolder = new QuestionTypeCheckboxAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuestionTypeCheckboxAdapter.ViewHolder holder, final int position) {
        final Exam.Option data = listProduct.get(position);
        holder.checkbox.setText(data.getOption());

        if(data.isSelected())
        {
            holder.checkbox.setChecked(true);
        }else
        {
            holder.checkbox.setChecked(false);
        }


        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkbox.isChecked()) {
                    // Do something when checkbox is checked
                    data.setSelected(true);
                } else {
                    // Do something when checkbox is unchecked
                    data.setSelected(false);
                }
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Exam.Option getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);

            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}





