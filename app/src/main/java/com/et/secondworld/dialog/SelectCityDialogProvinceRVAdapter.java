package com.et.secondworld.dialog;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.GetCityBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.network.StreetNetWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class SelectCityDialogProvinceRVAdapter extends RecyclerView.Adapter<SelectCityDialogProvinceRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<JsonBean> dataList = new ArrayList<>();
    private SelectCityDialogCityRVAdapter rvAdapter ;
    private CheckBox checkBox;
    private  Map<Object,Object> provinceMap ;
    private  Map<String,String> placeMap ;
    int selectPos = 0;

    public SelectCityDialogProvinceRVAdapter(SelectCityDialogCityRVAdapter rvAdapter1, CheckBox checkBox1,Map<String,String> placeMap){
        rvAdapter = rvAdapter1;
        checkBox = checkBox1;
        this.placeMap = placeMap;
    }

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(ArrayList<JsonBean> list,Map<Object,Object> provinceMap1) {
        dataList.clear();
        provinceMap = provinceMap1;
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public ArrayList<JsonBean> getDataList(){
        return dataList;
    }
    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, ArrayList<JsonBean> list) {
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
        return new SelectCityDialogProvinceRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_select_by_province_rv_item_province, parent, false));
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
        public void llyPopupSelectByProvinceRVItemProvinceOnclick() {
//            String checkNums = tvPopupSelectByProvinceRVItemSelectNum.getText().toString();
            String province = dataList.get(pos).getName();
           /* if(checkNums.equals("0")){
                rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum,dataList,pos);
                checkBox.setChecked(false);
            }
            if(checkNums.equals(""+dataList.get(pos).getCityList().size())){
                rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum,dataList,pos);
                checkBox.setChecked(true);
            }else {
                rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum,dataList,pos);
//                checkBox.setChecked(false);
            }*/
//            tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//            rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum,provinceMap,province);
            String pro  = placeMap.get("province");

            if(pro != null && !pro.isEmpty()){

            }else {
                selectPos = pos;
                notifyDataSetChanged();
            }
            /*Map<Object, Object> tempMap = (Map<Object, Object>) provinceMap.get(province);
            int checkNums = 0;
            if (tempMap != null){
                for (Object key : tempMap.keySet()) {
                    checkNums++;
                }
                if (checkNums == dataList.get(selectPos).getCityList().size()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }else{
//                checkBox.setChecked(false);
            }*/
//            Log.d("position",pos+"");
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
            String province = dataList.get(position).getName();
//            String selectNums = dataList.get(position).getSelectNums();
//            tvExtendRVItemCity.setText(city);
            pos = position;
            String pro  = placeMap.get("province");
            if(pro != null && !pro.isEmpty()) {
                if (province.equals(pro)) {
                    selectPos = position;
                }
            }
            if(position == 0 ){
                Log.d("position",position+"");
//                tvPopupSelectByProvinceRVItemSelectNum.setText("243");
//                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.VISIBLE);
            }else {

//                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.GONE);
            }
            Map<Object,Object> map =(Map<Object,Object>) provinceMap.get(province);
//            tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.VISIBLE);

            if(map!= null && map.size() != 0){
//                Log.d("zz1zzz",map.size()+"");
                tvPopupSelectByProvinceRVItemSelectNum.setText(map.size()+"");
                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.VISIBLE);
            }else {
                tvPopupSelectByProvinceRVItemSelectNum.setText("0");
                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.GONE);
            }
            if(position != 0){
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.GONE);
            }else {
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.VISIBLE);


            }
            if(selectPos == position) {
//                String selectProvince = dataList.get(selectPos).getName();
                Map<String,Object> pmap = new HashMap<>();
                String provincesearch = province.substring(0,2);
                pmap.put("province", provincesearch);
//                if(province.equals(city)){
//                    map.put("city", province );
//                }else {
//                    map.put("city", province + city);
//                }
//                map.put("city", city);
                StreetNetWork streetNetWork = new StreetNetWork();
                streetNetWork.getCityFromNet(pmap, new Observer<GetCityBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCityBean getCityBean) {
                        rvAdapter.replaceAll(getCityBean.getList(), tvPopupSelectByProvinceRVItemSelectNum, provinceMap, province);
//                        rvAdapter.replaceAll(getCityBean.getList(),province);
//                            rvAdapter.replaceAll(dataList.get(pos).getArea(),tvPopupSelectByProvinceRVItemSelectNum,selectNumsMap,city,province);
                    }
                });
//                rvAdapter.replaceAll(dataList.get(selectPos).getCityList(), tvPopupSelectByProvinceRVItemSelectNum, provinceMap, province);
            }
            if(selectPos !=position ){
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
            }else {
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
            }
            /*String pro  = placeMap.get("province");
            if(pro != null && !pro.isEmpty()) {
                if (province.equals(pro)) {
                    tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
                    Map<String,Object> pmap = new HashMap<>();
                    String provincesearch = province.substring(0,2);
                    pmap.put("province", provincesearch);
//                if(province.equals(city)){
//                    map.put("city", province );
//                }else {
//                    map.put("city", province + city);
//                }
//                map.put("city", city);
                    StreetNetWork streetNetWork = new StreetNetWork();
                    streetNetWork.getCityFromNet(pmap, new Observer<GetCityBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(GetCityBean getCityBean) {
                            rvAdapter.replaceAll(getCityBean.getList(), tvPopupSelectByProvinceRVItemSelectNum, provinceMap, province);
//                        rvAdapter.replaceAll(getCityBean.getList(),province);
//                            rvAdapter.replaceAll(dataList.get(pos).getArea(),tvPopupSelectByProvinceRVItemSelectNum,selectNumsMap,city,province);
                        }
                    });
                }
            }*/
//            tvPopupSelectByProvinceRVItemSelectNum.setText("0");
            tvPopupSelectByProvinceRVItemProvince.setText(province);

        }
    }


}
