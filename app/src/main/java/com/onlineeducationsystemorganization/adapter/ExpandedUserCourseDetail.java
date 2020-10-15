package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.interfaces.OnChildItemClick;
import com.onlineeducationsystemorganization.model.SectionCourse;

import java.util.ArrayList;

public class ExpandedUserCourseDetail extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<SectionCourse.Courseslist> _listDataHeader; // header titles
    // child data in format of header title, child title
    private OnChildItemClick onItemClick;

    public ExpandedUserCourseDetail(Context context, ArrayList<SectionCourse.Courseslist> listDataHeader,
                                    OnChildItemClick onItemClick) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.onItemClick = onItemClick;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataHeader.get(groupPosition).getSectionSlideDetails().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final SectionCourse.SectionSlideDetail childText = (SectionCourse.SectionSlideDetail) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_child_user_course_detail, null);
        }

        TextView tvId =  convertView
                .findViewById(R.id.tvId);
       final TextView tvName =convertView.findViewById(R.id.tvName);

        ImageView imgIsRead =convertView.findViewById(R.id.imgIsRead);

        //is_read = "1" means read complete
        //is_read ="0" means current slide
        //is_read = "" means next slide ke je read nthi kreli but disable mode ma aavse.
        if(childText.getIs_read().equals("1"))
        {
            imgIsRead.setVisibility(View.VISIBLE);
        }else
        {
            imgIsRead.setVisibility(View.GONE);
        }

        tvId.setText(childText.getSlideId()+"");
        tvName.setText(childText.getSlideName());
        if(childText.getIs_read().equals("")){
            tvName.setTextColor(_context.getResources().getColor(R.color.font1));
        }else {
            tvName.setTextColor(_context.getResources().getColor(R.color.black));
        }

        LinearLayout llSection = convertView.findViewById(R.id.llSection);
        llSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(childText.getIs_read().equals("")){
                    tvName.setTextColor(_context.getResources().getColor(R.color.font1));
                }else {
                    tvName.setTextColor(_context.getResources().getColor(R.color.black));
                    onItemClick.onChildClick(groupPosition, childPosition);
                }
            }
        });

        // radio.setText(childText.getName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getSectionSlideDetails().size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        SectionCourse.Courseslist headerTitle = (SectionCourse.Courseslist) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_parent_user_course_detail, null);
        }

        TextView tvName = (TextView) convertView
                .findViewById(R.id.tvName);
        ImageView imgIndicator = convertView.findViewById(R.id.imgIndicator);

        if(isExpanded)
        {
            imgIndicator.setImageDrawable(_context.getResources().getDrawable(R.mipmap.minus));
        }else
        {
            imgIndicator.setImageDrawable(_context.getResources().getDrawable(R.mipmap.plus));

        }
        tvName.setText(headerTitle.getSectionName());

        // ExpandableListView mExpandableListView = (ExpandableListView) parent;
        // mExpandableListView.expandGroup(groupPosition);

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}