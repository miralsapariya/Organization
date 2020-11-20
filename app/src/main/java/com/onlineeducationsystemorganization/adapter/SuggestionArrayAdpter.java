package com.onlineeducationsystemorganization.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.model.Suggestion;

import java.util.ArrayList;

public class SuggestionArrayAdpter extends ArrayAdapter<Suggestion.Datum> {

private final Activity _context;
private final ArrayList<Suggestion.Datum> rows;

public class ViewHolder
{
    TextView tv;
}

    public SuggestionArrayAdpter(Activity context, ArrayList<Suggestion.Datum> rows)
    {
        super(context,R.layout.row_autocomplete_tv, R.id.tv ,rows);

        this._context = context;
        this.rows = rows;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.row_autocomplete_tv,parent,false);

            holder = new ViewHolder();
            holder.tv = convertView.findViewById(R.id.tv);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.d("22222", "========== "+rows.get(position).getText() );
        holder.tv.setText(""+rows.get(position).getText());

        return convertView;
    }
}
