package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Exam;

import java.util.ArrayList;

public class QuestionTypeMatrixOneAdapter extends RecyclerView.Adapter<QuestionTypeMatrixOneAdapter.ViewHolder> {

    private ArrayList<Exam.Option>  listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public QuestionTypeMatrixOneAdapter(Context context,
                                        ArrayList<Exam.Option>  listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public QuestionTypeMatrixOneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_quetion_type_matrix_question_sorting, parent, false);
        QuestionTypeMatrixOneAdapter.ViewHolder viewHolder = new QuestionTypeMatrixOneAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuestionTypeMatrixOneAdapter.ViewHolder holder, final int position) {
        final Exam.Option  data = listProduct.get(position);

        holder.tvSort.setText(data.getOption());
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Exam.Option getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSort;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSort = itemView.findViewById(R.id.tvSort);
        }
    }
}





