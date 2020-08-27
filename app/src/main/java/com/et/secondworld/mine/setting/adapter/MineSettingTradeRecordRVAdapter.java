package com.et.secondworld.mine.setting.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.et.secondworld.R;
import com.et.secondworld.bean.TradeOrderListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class MineSettingTradeRecordRVAdapter extends RecyclerView.Adapter<MineSettingTradeRecordRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<TradeOrderListBean.ListBean> dataList = new ArrayList<>();

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<TradeOrderListBean.ListBean> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, List<TradeOrderListBean.ListBean> list) {
        dataList.addAll(position,list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MineSettingTradeRecordRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mine_setting_trade_record_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OneViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }



    public class OneViewHolder extends RecyclerView.ViewHolder {
        //        private ImageView ivImage;
        public View v;
        private int pos = -1;
        @BindView(R.id.tv_mine_setting_trade_record_rv_item_ordertitle)
        TextView tvMineSettingTradeRecordRVItemOrderTitle;
        @BindView(R.id.tv_mine_setting_trade_record_rv_item_time)
        TextView tvMineSettingTradeRecordRVItemTime;
        @BindView(R.id.tv_mine_setting_trade_record_rv_item_title)
        TextView tvMineSettingTradeRecordRVItemTitle;
        @BindView(R.id.tv_mine_setting_trade_record_rv_item_mount)
        TextView tvMineSettingTradeRecordRVItemMount;
        @BindView(R.id.iv_mine_setting_trade_record_rv_item_img)
        ImageView ivMineSettingTradeRecordRVItemImg;

        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,v);

        }



        void setData(Object data,int position) {
            pos = position;
            String ordertitle = dataList.get(position).getOrdertitle();
            String title = dataList.get(position).getTitle();
            String img = dataList.get(position).getImg();
            String time = dataList.get(position).getTime();
            String mount = dataList.get(position).getMount();
            tvMineSettingTradeRecordRVItemOrderTitle.setText("购买 "+ordertitle);
            if(title != null && !title.isEmpty()){
                tvMineSettingTradeRecordRVItemTitle.setVisibility(View.VISIBLE);
                tvMineSettingTradeRecordRVItemTitle.setText(title);
            }else {
                tvMineSettingTradeRecordRVItemTitle.setVisibility(View.GONE);
            }
            if(img != null && !img.isEmpty()){
                ivMineSettingTradeRecordRVItemImg.setVisibility(View.VISIBLE);
                Glide.with(v.getContext()).load(img).into(ivMineSettingTradeRecordRVItemImg);
            }else {
                ivMineSettingTradeRecordRVItemImg.setVisibility(View.GONE);
            }

            tvMineSettingTradeRecordRVItemTime.setText(time);
            tvMineSettingTradeRecordRVItemMount.setText(mount+"元");

        }


    }


}
