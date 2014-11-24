package com.phy0312.shopassistant.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.phy0312.shopassistant.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/11<br/>
 */
public class PlazaAdapter extends BaseAdapter {

    private List<String> list = new ArrayList<String>();

    private LayoutInflater mInflater;


    public PlazaAdapter(List<String> list, LayoutInflater mInflater) {
        this.list = list;
        this.mInflater = mInflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.plaza_spinner_item);
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.plaza_spinner_drop_item);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resLayout) {
        View view;
        TextView text;

        if (convertView == null) {
            view = mInflater.inflate(resLayout, parent, false);
        } else {
            view = convertView;
        }

        try {
            if(view instanceof TextView) {
                text = (TextView) view;
            }else{
                text = (TextView)view.findViewById(android.R.id.text1);
            }
        } catch (ClassCastException e) {
            Log.e("MiBaoAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "MiBaoAdapter requires the resource ID to be a TextView", e);
        }

        String item = (String) getItem(position);
        text.setText(item);


        return view;
    }
}
