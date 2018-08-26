package com.example.james.demotab2.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.james.demotab2.R;

import java.util.ArrayList;
import java.util.List;


public class ListViewAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> showUpList;
    private List<String> searchList;
    private DataFilter filter;
    private int TAB;


    public ListViewAdapter(Context context, int resource, List<String> myList, int TAB) {
        super(context, resource, myList);
        this.searchList = new ArrayList<>();
        this.showUpList = new ArrayList<>();
        this.searchList.addAll(myList);
        this.showUpList.addAll(myList);
        this.TAB = TAB;
    }

    @Override
    public int getCount() {
        return showUpList.size();
    }

    @Override
    public String getItem(int position) {
        return showUpList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(AjendaContext.getContext());
        View myView;
        if (TAB == 0) {
            myView = myInflater.inflate(R.layout.professor_item, parent, false);
            setValues(myView, position);
            return myView;
        } else if (TAB == 1) {
            myView = myInflater.inflate(R.layout.course_item, parent, false);
            setValues(myView, position);
            return myView;
        } else if (TAB == 2) {
            myView = myInflater.inflate(R.layout.section_item, parent, false);
            setValues(myView, position);
            return myView;
        }
        return null;
    }

    private void setValues(View myView, int position) {
        String nameItem = getItem(position);
        TextView textView = (TextView) myView.findViewById(R.id.textValue);
        ImageView imageView = (ImageView) myView.findViewById(R.id.imageValue);

        textView.setText(nameItem);
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new DataFilter();
        }
        return filter;
    }

    private class DataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            constraint = constraint.toString().toLowerCase();
            if (constraint != null && constraint.toString().length() > 0) {

                List<String> founded = new ArrayList<String>();
                for (String item : searchList) {
                    if (item.toString().toLowerCase().contains(constraint)) {
                        founded.add(item);
                    }
                }
                filterResults.values = founded;
                filterResults.count = founded.size();
            } else {
                synchronized (this) {
                    filterResults.values = searchList;
                    filterResults.count = searchList.size();
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            showUpList = (ArrayList<String>) filterResults.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0; i < showUpList.size(); i++) {
                add(showUpList.get(i));
            }
            notifyDataSetInvalidated();
        }
    }
}