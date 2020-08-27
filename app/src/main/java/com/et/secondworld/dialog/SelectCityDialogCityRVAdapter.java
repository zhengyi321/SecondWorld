package com.et.secondworld.dialog;

import android.content.Context;
import android.graphics.Color;
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
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

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
public class SelectCityDialogCityRVAdapter extends RecyclerView.Adapter<SelectCityDialogCityRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<GetCityBean.ListBean> dataList = new ArrayList<>();
//    private List<JsonBean> datalistProvince = new ArrayList<>();
    private TextView textViewSelectNums;
    private TextView tvFinish;
    private Map<String,String> map = new HashMap<>();
    private Map<Object,Object> provinceMap ;
    private Map<String,String> placeMap ;
    private String province = "";
    int selectNums = 0;
    private boolean isselectall = false;
    private Context context;
    private CheckBox checkBox;
    SelectCityDialog.OnFinishClickListener onFinishClickListener;
    private int selectcount = 0;
//    Map<String,Object> areaMap = new HashMap<>();
//    private int datalistProvincePos;
    String city;
    public SelectCityDialogCityRVAdapter(TextView tvFinish1, Context context1, Map<String,String> placeMap1, CheckBox checkBox1, SelectCityDialog.OnFinishClickListener onFinishClickListener,int selectcount){
        tvFinish = tvFinish1;
        context = context1;
        placeMap = placeMap1;
        this.checkBox = checkBox1;
        this.selectcount = selectcount;
        this.onFinishClickListener = onFinishClickListener;
    }

    public ArrayList<GetCityBean.ListBean> getDataList(){
        return dataList;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetCityBean.ListBean> list, TextView textViewSelectNums1, Map<Object,Object> provinceMap1, String province1) {
        dataList.clear();
        provinceMap = provinceMap1;
        province = province1;
//        map = (Map<Object, Object>) provinceMap.get(province);

        for(Object key : provinceMap.keySet()){
            Map<String,String> tempMap = (Map<String, String>)provinceMap.get(key);
            for(String keys:tempMap.keySet()){
                map.put(keys,tempMap.get(keys));
            }
        }

//        datalistProvince.addAll(datalistProvince1);
//        datalistProvince = datalistProvince1;
//        datalistProvincePos = pos;
//        for(JsonBean.CityBean item : list){
//            item.setIsSelected(0);
//        }
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(context);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
         city = xcCacheManager.readCache(xcCacheSaveName.currentCity);
        List<GetCityBean.ListBean> cityBeanList = new ArrayList<>();
        cityBeanList.addAll(list);
        String counts = textViewSelectNums1.getText().toString();
        selectNums = Integer.parseInt(counts);

//        String counts = datalistProvince.get(datalistProvincePos).getSelectNums();
//        if(counts != null) {
//            selectNums = Integer.parseInt(counts);
//        }else {
//            String counts1 = textViewSelectNums1.getText().toString();
//            selectNums = Integer.parseInt(counts1);
//        }
        textViewSelectNums = textViewSelectNums1;
//        textViewSelectNums.setVisibility(View.VISIBLE);
        if (cityBeanList != null && cityBeanList.size() > 0) {
            for(int i=0;i<cityBeanList.size();i++){
                if(cityBeanList.get(i).getArea().equals(city)){
                    if(i == 0){
                        continue;
                    }
                    dataList.add(cityBeanList.get(i));
                    cityBeanList.remove(i);
                }
            }
            dataList.addAll(cityBeanList);
        }
        notifyDataSetChanged();
    }
    public String getProvince(){
        return province;
    }

    public void setSelectAll(boolean isselectall){
        this.isselectall = isselectall;
    }
    public void selectAll(int selected){

        int selectNums = 0;
        placeMap.clear();;
        provinceMap.clear();
        map.clear();
        if(selected == 0){
            Map<Object,Object> areaMap1= (Map<Object,Object>) provinceMap.get(province);
            if(areaMap1 != null){
                for(int i = 0;i<areaMap1.size();i++){
                    placeMap.remove(i+province);
                }
                placeMap.remove(province);
            }
            provinceMap.remove(province);
            isselectall = false;

//            for()
//            for (int i =0;i<dataList.size();i++){
//                if(dataList.get(i).getIsSelected() == 0){
//                    continue;
//                }
//                map.remove(i+dataList.get(i).getName());
//                selectNums--;
//                dataList.get(i).setIsSelected(0);
//            }
        }else {
//            for (int i =0;i<dataList.size();i++){
//                if(dataList.get(i).getIsSelected() == 1){
//                    continue;
//                }
//                map.put(i+dataList.get(i).getName(),dataList.get(i).getName());
//                selectNums++;
//                dataList.get(i).setIsSelected(1);
//            }
            isselectall = true;
            Map<Object,Object> areaMap = new HashMap<>();
            for(int i=0;i<dataList.size();i++){
                String city = dataList.get(i).getArea();
                String allname = dataList.get(i).getAllname();
                if(allname.indexOf("市")>=0){
                    allname = allname.substring(0,allname.indexOf("市"));
                }
                placeMap.put(i+province,allname);
                areaMap.put(i+province,city);
                selectNums++;
            }
            placeMap.put(province,province);
            provinceMap.put(province,areaMap);

        }
//        map.clear();
        for(Object key : provinceMap.keySet()){
            Map<String,String> tempMap = (Map<String, String>)provinceMap.get(key);
            for(String keys:tempMap.keySet()){
                map.put(keys,tempMap.get(keys));
            }
        }

        if(selected == 0){
            if(onFinishClickListener != null){
                onFinishClickListener.getMaps(map,false);
            }
        }else {
            if(onFinishClickListener != null){
                onFinishClickListener.getMaps(map,true);
            }
        }
        tvFinish.setText("完成("+map.size()+"/"+selectcount+")");
        if(selectNums > 0) {
            textViewSelectNums.setVisibility(View.VISIBLE);
            textViewSelectNums.setText(selectNums + "");
        }else {
            textViewSelectNums.setVisibility(View.GONE);
            textViewSelectNums.setText(selectNums + "");
        }
        notifyDataSetChanged();
    }

    public  Map<String,String> getMap(){
        return map;
    }
    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, ArrayList<GetCityBean.ListBean> list) {
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
        return new SelectCityDialogCityRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_select_by_province_rv_item_city, parent, false));
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

        @BindView(R.id.rly_popup_select_by_province_rv_item_city)
        RelativeLayout rlyPopupSelectByProvinceRVItemCity;
        @OnClick(R.id.rly_popup_select_by_province_rv_item_city)
        public void rlyPopupSelectByProvinceRVItemCityOnclick(){
//            Log.d("selected111",dataList.get(pos).getIsSelected()+"");
            if(isselectall){
                return;
            }
            String counts = textViewSelectNums.getText().toString();
            String allname = dataList.get(pos).getAllname();
            if(allname.indexOf("市")>=0){
                allname = allname.substring(0,allname.indexOf("市"));
            }
//            Map<Object,Object> tempMap = (Map<Object,Object>) provinceMap.get(province);
//            if(tempMap == null)
            selectNums = Integer.parseInt(counts);
            Object city = map.get(pos+province);

            if(city == null){
                if(map.size() >= selectcount){
                    return;
                }
//                rlyPopupSelectByProvinceRVItemCity.setBackgroundColor(Color.RED);
                tvPopupSelectByProvinceRVItemCity.setTextColor(Color.RED);
//                tvPopupSelectByProvinceRVItemCity.setTextColor(Color.parseColor("#ff82a8"));
                map.put(pos+province,allname);
                placeMap.put(pos+province,allname);
//                areaMap.put(pos+dataList.get(pos).getName(),dataList.get(pos).getName());


//                dataList.get(pos).setIsSelected(1);
//                datalistProvince.get(datalistProvincePos).getCityList().get(pos).setIsSelected(3);
                selectNums++;
                if(selectNums > 0) {
                    textViewSelectNums.setVisibility(View.VISIBLE);
                    textViewSelectNums.setText(selectNums + "");
//                    textViewSelectNums.setText( "1111111");
                }
                if(pos == 0){
                    ivPopupSelectByProvinceRVItemCity.setVisibility(View.GONE);
                }
//                datalistProvince.get(datalistProvincePos).setSelectNums(selectNums+"");
            }else {
                tvPopupSelectByProvinceRVItemCity.setTextColor(Color.BLACK);
                rlyPopupSelectByProvinceRVItemCity.setBackgroundResource(R.drawable.gray_dashwidth_white_shape);
                selectNums--;
                map.remove(pos+province);
                placeMap.remove(pos+province);
//                areaMap.remove(pos+dataList.get(pos).getName());
                if(selectNums == 0) {
                    textViewSelectNums.setVisibility(View.GONE);
                }

                textViewSelectNums.setText(selectNums+"");
//                dataList.get(pos).setIsSelected(0);
//                datalistProvince.get(datalistProvincePos).getCityList().get(pos).setIsSelected(0);
//                datalistProvince.get(datalistProvincePos).setSelectNums(selectNums+"");
                if(pos == 0){
                    if(city != null ) {
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                        String cityLoc = xcCacheManager.readCache(xcCacheSaveName.currentCity);
                        if(cityLoc != null) {
                            if (cityLoc.equals(dataList.get(pos).getArea())) {
                                ivPopupSelectByProvinceRVItemCity.setVisibility(View.VISIBLE);
                            }
                            if (city.equals(cityLoc)) {
                                ivPopupSelectByProvinceRVItemCity.setVisibility(View.INVISIBLE);
                            }
                        }

                    }
                }
            }
            if(onFinishClickListener != null){
                onFinishClickListener.getMaps(map,false);
            }
            tvFinish.setText("完成("+map.size()+"/"+selectcount+")");
            Map<Object,Object> areaMap = new HashMap<>();
            for(int i=0;i<dataList.size();i++){
                Object item = map.get(i+province);
                if(item != null){
                    areaMap.put(i+province,item);
                }
            }
            provinceMap.put(province,areaMap);
            /*int areaNums = 0;
            for(Object key : provinceMap.keySet()){
                Map<Object,Object> tempMap = (Map<Object, Object>)provinceMap.get(key);
                if(tempMap != null){
                    areaNums += tempMap.size();
                }
            }
            tvFinish.setText("完成("+areaNums+"/200)");*/
//            datalistProvince.get(datalistProvincePos).getCityList().get(pos).setIsSelected(dataList.get(pos).getIsSelected());
//            notifyDataSetChanged();
        }

        @BindView(R.id.tv_popup_select_by_province_rv_item_city)
        TextView tvPopupSelectByProvinceRVItemCity;
        @BindView(R.id.iv_popup_select_by_province_rv_item_city)
        ImageView ivPopupSelectByProvinceRVItemCity;
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {

            String city = dataList.get(position).getArea();
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            String cityLoc = xcCacheManager.readCache(xcCacheSaveName.currentCity);
//            tvExtendRVItemCity.setText(city);
            pos = position;

            /*if(position != 0){
//                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
                ivPopupSelectByProvinceRVItemCity.setVisibility(View.GONE);
            }else {
//                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.parseColor("#ff82a8"));
                ivPopupSelectByProvinceRVItemCity.setVisibility(View.VISIBLE);
            }*/
            tvPopupSelectByProvinceRVItemCity.setText(city);
            Object tempCity= map.get(position+province);
            if(tempCity != null) {
//                Object tempArea = tempMap.get(position + province);
//            if(dataList.get(pos).getIsSelected() == 1){
//                if (tempArea != null && tempCity.equals(city)) {
//                rlyPopupSelectByProvinceRVItemCity.setBackgroundColor(Color.RED);
                    tvPopupSelectByProvinceRVItemCity.setTextColor(Color.RED);

               /* if(province != null && !province.isEmpty()){
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }*/
                    if (pos == 0) {
                        ivPopupSelectByProvinceRVItemCity.setVisibility(View.VISIBLE);
                    }
//                tvPopupSelectByProvinceRVItemCity.setTextColor(Color.parseColor("#ff82a8"));
//                dataList.get(pos).setIsSelected(1);
//                }
            } else {

                if (pos == 0) {
                    ivPopupSelectByProvinceRVItemCity.setVisibility(View.VISIBLE);
                }
                tvPopupSelectByProvinceRVItemCity.setTextColor(Color.BLACK);
                rlyPopupSelectByProvinceRVItemCity.setBackgroundResource(R.drawable.gray_dashwidth_white_shape);
//                dataList.get(pos).setIsSelected(0);

            }

            tvFinish.setText("完成("+map.size()+"/"+selectcount+")");
//            textViewSelectNums.setText(selectNums);
            if(city != null && cityLoc != null) {
                if (cityLoc.equals(city)) {
//                ivPopupSelectByProvinceRVItemCity.setVisibility(View.VISIBLE);
                } else {
                    ivPopupSelectByProvinceRVItemCity.setVisibility(View.GONE);
                }
            }else {
                ivPopupSelectByProvinceRVItemCity.setVisibility(View.GONE);
            }
        }
    }


}
