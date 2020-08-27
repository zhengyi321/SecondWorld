package com.et.secondworld.dialog;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.GetAreaBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class SelectAreaDialogAreaRVAdapter extends RecyclerView.Adapter<SelectAreaDialogAreaRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<GetAreaBean.ListBean> dataList = new ArrayList<>();
    private SelectCityDialogCityRVAdapter rvAdapter ;
    private Map<String,String> map = new HashMap<>();
    private TextView textViewSelect;
    private TextView tvFinish;
    private int selectNums = 0;
    private Map<Object,Object> cityMap ;
    private Map<String,String> placeMap ;
    private String city = "";
    private String province = "";
    private int selectCounts = 0;
    SelectAreaDialog.OnFinishClickListener onFinishClickListener;
//    private CheckBox checkBox;
    public SelectAreaDialogAreaRVAdapter(TextView tvFinish1, Map<String,String> placeMap1, SelectAreaDialog.OnFinishClickListener onFinishClickListener,int selectCounts){
        placeMap = placeMap1;
        tvFinish = tvFinish1;
        this.selectCounts = selectCounts;
        this.onFinishClickListener = onFinishClickListener;
    }
    public Map<String,String> getMap(){
        return map;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetAreaBean.ListBean> list, TextView textViewSelect1, Map<Object,Object> cityMap1, String city1, String province1) {
        dataList.clear();
        province = province1;
        cityMap = cityMap1;
        city = city1;
        for(Object key : cityMap.keySet()){
            Map<String,String> tempMap = (Map<String, String>)cityMap.get(key);
            for(String keys:tempMap.keySet()){
                map.put(keys,tempMap.get(keys));
            }
        }

        textViewSelect = textViewSelect1;
        String counts = textViewSelect.getText().toString();
        selectNums = Integer.parseInt(counts);
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
    public void addData(int position, ArrayList<GetAreaBean.ListBean> list) {
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
        return new SelectAreaDialogAreaRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_select_by_province_rv_item_province, parent, false));
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
        @BindView(R.id.tv_popup_select_by_province_rv_item_select_num)
        TextView tvPopupSelectByProvinceRVItemSelectNum;
        @BindView(R.id.rly_popup_select_by_province_rv_item_province)
        RelativeLayout llyPopupSelectByProvinceRVItemProvince;
        @OnClick(R.id.rly_popup_select_by_province_rv_item_province)
        public void llyPopupSelectByProvinceRVItemProvinceOnclick(){
            Object mapData = map.get(pos+city);
            String counts = textViewSelect.getText().toString();

            selectNums = Integer.parseInt(counts);
            if(mapData == null ){
                if(map.size() >= selectCounts){
                    return;
                }
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
                map.put(pos+city,dataList.get(pos).getAllname());
                placeMap.put(pos+city,dataList.get(pos).getAllname());
                selectNums++;
                textViewSelect.setText(selectNums+"");
                textViewSelect.setVisibility(View.VISIBLE);
            }else {
                selectNums--;

                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
                map.remove(pos+city);
                placeMap.remove(pos+city);


                textViewSelect.setText(selectNums+"");
//                textViewSelect.setVisibility(View.VISIBLE);
            }
            if(selectNums == 0){
                textViewSelect.setVisibility(View.GONE);
            }
            if(onFinishClickListener != null){
                onFinishClickListener.getMaps(map);
            }
            tvFinish.setText("完成("+map.size()+"/"+selectCounts+")");
            Map<Object,Object> areaMap = new HashMap<>();
            for(int i=0;i<dataList.size();i++){
                Object item = map.get(i+city);
                if(item != null){
                    areaMap.put(i+city,item);
                }
            }
            cityMap.put(city,areaMap);
//            selectNumsMap.put(name,selectNums);

        }
        @BindView(R.id.tv_popup_select_by_province_rv_item_province)
        TextView tvPopupSelectByProvinceRVItemProvince;
        @BindView(R.id.iv_popup_select_by_province_rv_item_province)
        ImageView ivPopupSelectByProvinceRVItemProvince;
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            String area = dataList.get(position).getArea();
//            tvExtendRVItemCity.setText(city);
            pos = position;
            tvPopupSelectByProvinceRVItemProvince.setText(area);
            if(position != 0){
//                if(selectPos == pos){
//                    tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//                }else {
//                }
//                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.GONE);
            }else {
//                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.VISIBLE);
//                rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum);
            }
            ivPopupSelectByProvinceRVItemProvince.setVisibility(View.GONE);
            tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
            Object mapData = map.get(pos+city);
            if(mapData == null ){
//                selectNums++;
//                textViewSelect.setText(selectNums+"");
//                textViewSelect.setVisibility(View.VISIBLE);
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
            }else {
//                selectNums++;
//                if(selectNums == 0){
//                    textViewSelect.setVisibility(View.GONE);
//                }
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//                textViewSelect.setText(selectNums+"");
//                textViewSelect.setVisibility(View.VISIBLE);
            }
            tvFinish.setText("完成("+map.size()+"/"+selectCounts+")");
        }
    }


}
