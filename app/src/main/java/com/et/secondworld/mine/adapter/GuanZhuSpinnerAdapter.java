package com.et.secondworld.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.et.secondworld.R;

import java.util.ArrayList;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class GuanZhuSpinnerAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater li;
    private ArrayList<String> dataList;

    public GuanZhuSpinnerAdapter(Context ctx,ArrayList<String> dataList) {
        this.ctx = ctx;
        this.li = LayoutInflater.from(ctx);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public String getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.activity_guanzhu_spinner_item, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();// get convertView's holder

        holder.tvSpinner.setText(getItem(position));

        return convertView;
    }

    class ViewHolder {
        TextView tvSpinner;



        public ViewHolder(View convertView){
            tvSpinner = (TextView) convertView.findViewById(R.id.tv_spinner);
            convertView.setTag(this);//set a viewholder
        }
    }


}
