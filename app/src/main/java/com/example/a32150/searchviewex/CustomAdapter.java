package com.example.a32150.searchviewex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 32150 on 2017/11/24.
 */

public class CustomAdapter extends BaseAdapter implements Filterable {

    List<String> item;
    List<String> originalitem;
    private LayoutInflater mLayout;

    public CustomAdapter(Context context, List<String> mList) {
        mLayout = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item = mList;
    }


    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mLayout.inflate(R.layout.customlayout, viewGroup,false);
        TextView title = (TextView)v.findViewById(R.id.textView2);
        TextView content = (TextView)v.findViewById(R.id.textView);
        title.setText(item.get(i).toString());
        content.setText("context: "+item.get(i).toString());

        return v;
    }

    @Override
    public Filter getFilter() {
        Filter filter= new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                charSequence = charSequence.toString();
                FilterResults result = new FilterResults();
                if(originalitem == null){
                    synchronized (this){
                        originalitem = new ArrayList<String>(item);
                        // 若originalitem 沒有資料，會複製一份item的過來.
                    }
                }
                if(charSequence != null && charSequence.toString().length()>0){
                    ArrayList<String> filteredItem = new ArrayList<String>();
                    for(int i=0;i<originalitem.size();i++){
                        String title = originalitem.get(i).toString();
                        if(title.contains(charSequence)){
                            filteredItem.add(title);
                        }
                    }
                    result.count = filteredItem.size();
                    result.values = filteredItem;
                }else{
                    synchronized (this){
                        ArrayList<String> list = new ArrayList<String>(originalitem);
                        result.values = list;
                        result.count = list.size();

                    }
                }
                return result;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                item = (ArrayList<String>)filterResults.values;
                if(filterResults.count>0){
                    notifyDataSetChanged();
                }   else{
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
