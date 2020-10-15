package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Exam;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class QuestionTypeMatrixImgOneAdapter extends RecyclerView.Adapter<QuestionTypeMatrixImgOneAdapter.ViewHolder> {

    private ArrayList<Exam.Option> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public QuestionTypeMatrixImgOneAdapter(Context context,
                                           ArrayList<Exam.Option> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public QuestionTypeMatrixImgOneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_quetion_type_matrix_question_img_sorting, parent, false);
        QuestionTypeMatrixImgOneAdapter.ViewHolder viewHolder = new QuestionTypeMatrixImgOneAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuestionTypeMatrixImgOneAdapter.ViewHolder holder, final int position) {
        final Exam.Option data = listProduct.get(position);

        AppUtils.loadImageWithPicasso(data.getPath() , holder.img, context, 0, 0);


    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Exam.Option getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
        }
    }
}





