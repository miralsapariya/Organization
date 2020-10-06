package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Home;
import com.onlineeducationsystemorganization.util.AppUtils;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    Context context;


    LayoutInflater mLayoutInflater;
    private ArrayList<Home.BannersList> list;

    public ImageAdapter(Context context, ArrayList<Home.BannersList> list)
    {
        this.context=context;
        this.list = list;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView =  itemView.findViewById(R.id.imageView);
        TextView title =itemView.findViewById(R.id.title);
        TextView tvSubTitle =itemView.findViewById(R.id.tvSubTitle);

        AppUtils.loadImageWithPicasso(list.get(position).getBannerImage() , imageView, context, 0, 0);
        title.setText(list.get(position).getBannerTitle());
        tvSubTitle.setText(list.get(position).getBannerDescription());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
