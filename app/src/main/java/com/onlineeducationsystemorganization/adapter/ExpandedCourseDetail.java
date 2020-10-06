package com.onlineeducationsystemorganization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.CourseDetail;

import java.util.ArrayList;

public class ExpandedCourseDetail extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<CourseDetail.SectionDetail> _listDataHeader; // header titles
    // child data in format of header title, child title

    public ExpandedCourseDetail(Context context, ArrayList<CourseDetail.SectionDetail> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return _listDataHeader.get(groupPosition).getSectionSlideDetails().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final CourseDetail.SectionSlideDetail childText = (CourseDetail.SectionSlideDetail) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_child_course_detail, null);
        }

        TextView tvId =  convertView
                .findViewById(R.id.tvId);
        TextView tvName =convertView.findViewById(R.id.tvName);

        tvId.setText(childPosition+1 +"");
        tvName.setText(childText.getSlideName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _listDataHeader.get(groupPosition).getSectionSlideDetails()
                .size();
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
        CourseDetail.SectionDetail headerTitle = (CourseDetail.SectionDetail) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_parent_course_detail, null);
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