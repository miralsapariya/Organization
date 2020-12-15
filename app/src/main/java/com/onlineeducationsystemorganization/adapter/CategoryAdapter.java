package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.tommykw.tagview.DataTransform;
import com.github.tommykw.tagview.TagView;
import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.TrendingCourseActivity;
import com.onlineeducationsystemorganization.interfaces.OnSubItemClick;
import com.onlineeducationsystemorganization.model.Category;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
        implements OnSubItemClick {

    private ArrayList<Category.AllCategoriesList> listProduct;
    private LayoutInflater mInflater;
    private OnSubItemClick onItemClick;
    private Context context;

    public CategoryAdapter(Context context,
                           ArrayList<Category.AllCategoriesList> listProduct, OnSubItemClick onItemClick) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listProduct = listProduct;
        this.onItemClick = onItemClick;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_category_tag, parent, false);
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.ViewHolder holder, int position) {
        final Category.AllCategoriesList data = listProduct.get(position);

       holder.tvNameCategory.setText(data.getCategoryName());

       /* List<Category.SubCategory> list = new ArrayList<>();
        Item i= new Item(1, "It certification");
        list.add(i);
        Item i1= new Item(2, "Netword and security");
        list.add(i1);
        Item i11= new Item(3, "Hardware");
        list.add(i11);
        Item i111= new Item(4, "Operating system");
        list.add(i111);
        Item i1111= new Item(5, "Other");
        list.add(i1111);*/


        holder.tagCategory.setTags(data.getSubCategories(), new DataTransform<Category.SubCategory>() {
            @NotNull
            @Override
            public String transfer(Category.SubCategory item) {
                return item.getSubCategoryName();
            }
        });

        holder.tagCategory.setClickListener(new TagView.TagClickListener<Category.SubCategory>() {
            @Override
            public void onTagClick(Category.SubCategory item) {

                Log.d("======= >>",item.getId()+"");
                Intent intent =new Intent(context, TrendingCourseActivity.class);
                intent.putExtra("title", item.getSubCategoryName());
                intent.putExtra("from", "");
                intent.putExtra("subcat_id", item.getId()+"");

                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onSubGridClick(int pos) {

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public Category.AllCategoriesList getItem(int id) {
        return listProduct.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameCategory,tvViewAll;
        TagView<Category.SubCategory> tagCategory ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameCategory = itemView.findViewById(R.id.tvNameCategory);
            tvViewAll = itemView.findViewById(R.id.tvViewAll);
            tagCategory =itemView.findViewById(R.id.tagCategory);

        }
    }
}

