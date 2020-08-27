package com.et.secondworld.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.VisitOthersShopActivity;
import com.et.secondworld.bean.VillageListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class SearchVillageShopRVAdapter extends RecyclerView.Adapter<SearchVillageShopRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<VillageListBean.ListBean> dataList = new ArrayList<>();
    private Activity activity;
    public SearchVillageShopRVAdapter(Activity activity1){
        activity = activity1;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<VillageListBean.ListBean> list) {
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
    public void addData(int position, List<VillageListBean.ListBean> list) {
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
        return new SearchVillageShopRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_village_shop_rv_item, parent, false));
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
        int pos = 0;
        @BindView(R.id.tv_search_village_shop_rv_item_locate)
        TextView tvSearchVillageShopRVItemLocate;
        @BindView(R.id.tv_search_village_shop_rv_item_name)
        TextView tvSearchVillageShopRVItemName;
        private long clickTime = 0;
        @OnClick(R.id.lly_search_village_shop_rv_item)
        public  void llySearchVillageShopRVItemOnclick(){
            String lat = dataList.get(pos).getLat();
            String lon = dataList.get(pos).getLon();
            String village = dataList.get(pos).getVillage();
            String allname = dataList.get(pos).getLocate();
            String areacode = dataList.get(pos).getAreacode();
            String shopid = dataList.get(pos).getShopid();
            if(lat != null && !lat.isEmpty()) {
                Intent intent = new Intent();
                //把需要返回的数据存放在intent
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("village", village);
                intent.putExtra("allname", allname);
                intent.putExtra("areacode", areacode);
                //设置返回数据
                activity.setResult(RESULT_OK, intent);
                activity.finish();
            }
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                if (shopid != null && !shopid.isEmpty()) {
                    Intent intent = new Intent(activity, VisitOthersShopActivity.class);
                    intent.putExtra("shopid", shopid);
                    activity.startActivity(intent);
                }
            }
        }
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String name = dataList.get(position).getVillage();
            String locate = dataList.get(position).getLocate();
            tvSearchVillageShopRVItemLocate.setText(locate);
            tvSearchVillageShopRVItemName.setText(name);

        }
    }


}
