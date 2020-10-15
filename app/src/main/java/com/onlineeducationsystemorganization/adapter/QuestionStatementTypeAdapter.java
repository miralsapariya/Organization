package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Exam;

import java.util.ArrayList;

public class QuestionStatementTypeAdapter extends RecyclerView.Adapter<QuestionStatementTypeAdapter.ViewHolder> {

    private ArrayList<Exam.Option> listProduct;
    private LayoutInflater mInflater;
    private Context context;

    public QuestionStatementTypeAdapter(Context context,
                                        ArrayList<Exam.Option> listProduct) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public QuestionStatementTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_question_type_true_flase, parent, false);
        QuestionStatementTypeAdapter.ViewHolder viewHolder = new QuestionStatementTypeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuestionStatementTypeAdapter.ViewHolder holder, final int position) {
        final Exam.Option data = listProduct.get(position);
        holder.tvSatement.setText(data.getOption());

        if(listProduct.get(position).getSelected().equals("true"))
        holder.radioOption.check(R.id.rbTrue);
        else if(listProduct.get(position).getSelected().equals("false"))
            holder.radioOption.check(R.id.rbFalse);

        holder.radioOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(i == R.id.rbTrue)
                {
                    data.setSelected(true);
                    data.setSelected("true");
                }else if(i == R.id.rbFalse)
                {
                    data.setSelected(false);
                    data.setSelected("false");
                }else
                {
                    data.setSelected("");
                }
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
        public RadioGroup radioOption;
        public RadioButton rbTrue,rbFalse;
        public TextView tvSatement;
        public ViewHolder(View itemView) {
            super(itemView);
            radioOption = itemView.findViewById(R.id.radioOption);
            rbTrue =itemView.findViewById(R.id.rbTrue);
            rbFalse =itemView.findViewById(R.id.rbFalse);
            tvSatement =itemView.findViewById(R.id.tvSatement);
        }
    }
}





