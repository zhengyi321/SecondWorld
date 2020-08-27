package com.et.secondworld.dialog;

import android.content.Context;
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
import com.et.secondworld.bean.GetCityBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.network.StreetNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class SelectTownDialogCityRVAdapter extends RecyclerView.Adapter<SelectTownDialogCityRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<GetCityBean.ListBean> dataList = new ArrayList<>();
    private SelectTownDialogAreaRVAdapter rvAdapter ;
    private String city;
    private RecyclerView recyclerView;
    private Context context ;
    private int selectPos = 0;
    private int flag = 0;
    private Map<Object,Object> selectNumsMap = new HashMap<>();
    private RecyclerView rvDialogSelectByTownTown;
    private String province = "";
//    private CheckBox checkBox;
    public SelectTownDialogCityRVAdapter(SelectTownDialogAreaRVAdapter rvAdapter1, Context context1,RecyclerView rvDialogSelectByTownTown1){
        rvAdapter = rvAdapter1;
        context = context1;
        rvDialogSelectByTownTown = rvDialogSelectByTownTown1;
//        checkBox = checkBox1;
    }

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetCityBean.ListBean> list, String province1) {

        XCCacheManager xcCacheManager = XCCacheManager.getInstance(context);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        city = xcCacheManager.readCache(xcCacheSaveName.currentCity);
        List<GetCityBean.ListBean> cityBeanList = new ArrayList<>();
        cityBeanList.addAll(list);
        dataList.clear();
        province = province1;
        if (cityBeanList != null && cityBeanList.size() > 0) {
            for(int i=0;i<cityBeanList.size();i++){
                    if(city.indexOf(cityBeanList.get(i).getArea())>=0){
                    if(i == 0){
                        continue;
                    }
                    dataList.add(cityBeanList.get(i));
                    cityBeanList.remove(i);
                }
            }
            dataList.addAll(cityBeanList);
        }
        selectPos = 0;
        flag = 0;
        notifyDataSetChanged();
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
        this.recyclerView = (RecyclerView) parent;
        return new SelectTownDialogCityRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_select_by_province_rv_item_province, parent, false));
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

//            for(int i = 0; i < recyclerView.getChildCount(); i++){
//                RelativeLayout ll = (RelativeLayout)recyclerView .getChildAt(i);
//                TextView textView = (TextView) ll.getChildAt(1);
//                textView.setTextColor(Color.BLACK);
//            }
            selectPos = pos;
//            selectNumsMap.put(pos+dataList.get(pos).getName(),dataList.get(pos).getName());
//            tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//            notifyDataSetChanged();

//            TranslateAnimation mCloseAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
//                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
//            mCloseAction.setDuration(500);
//
//            rvDialogSelectByTownTown.startAnimation(mCloseAction);
            notifyDataSetChanged();
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
            String city = dataList.get(position).getArea();
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            String cityLoc = xcCacheManager.readCache(xcCacheSaveName.currentCity);
            pos = position;
//            tvExtendRVItemCity.setText(city);
            if(position != 0){
//                if(selectPos == pos){
//                    tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//                }else {
//                }
                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.GONE);
            }else {
//                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.VISIBLE);
                if(flag == 0) {
//                    String temp = dataList.get(pos).getArea().get(0).substring(0,1);
//                    if(temp.equals("全")){
//                        dataList.get(pos).getArea().remove(0);
//                    }

//                    if(province.equals(city)){
//                        map.put("city", province );
//                    }else {
//                        map.put("city", province + city);
//                    }
                    String searcity = dataList.get(pos).getAllname();
//                int shipos = city.indexOf("市");
//                if(shipos > 0) {
//                    searcity = city.substring(0, shipos);
//                }
                    Map<String,Object>map = new HashMap<>();
//                if(province.equals(city)){
//                    map.put("city", province );
//                }else {
//                    map.put("city", province + city);
//                }
                    map.put("city", searcity);

                    StreetNetWork streetNetWork = new StreetNetWork();
                    streetNetWork.getAreaFromNet(map, new Observer<GetAreaBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(GetAreaBean getAreaBean) {
                            rvAdapter.replaceAll(getAreaBean.getList(),tvPopupSelectByProvinceRVItemSelectNum,selectNumsMap,city,province);
//                            rvAdapter.replaceAll(dataList.get(pos).getArea(),tvPopupSelectByProvinceRVItemSelectNum,selectNumsMap,city,province);
                        }
                    });

                    flag = 1;
                }
//                rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum);
            }
            if(selectPos == pos){
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//                String temp = dataList.get(pos).getArea().get(0).substring(0,1);
//                if(temp.equals("全")){
//                    dataList.get(pos).getArea().remove(0);
//                }
                String searcity = dataList.get(pos).getAllname();
//                int shipos = city.indexOf("市");
//                if(shipos > 0) {
//                    searcity = city.substring(0, shipos);
//                }
                Map<String,Object>map = new HashMap<>();
//                if(province.equals(city)){
//                    map.put("city", province );
//                }else {
//                    map.put("city", province + city);
//                }
                map.put("city", searcity);
                StreetNetWork streetNetWork = new StreetNetWork();
                streetNetWork.getAreaFromNet(map, new Observer<GetAreaBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetAreaBean getAreaBean) {
                        rvAdapter.replaceAll(getAreaBean.getList(),tvPopupSelectByProvinceRVItemSelectNum,selectNumsMap,city,province);
//                            rvAdapter.replaceAll(dataList.get(pos).getArea(),tvPopupSelectByProvinceRVItemSelectNum,selectNumsMap,city,province);
                    }
                });
//                rvAdapter.replaceAll(dataList.get(pos).getArea(),tvPopupSelectByProvinceRVItemSelectNum,selectNumsMap,city,province);
                rvDialogSelectByTownTown.setVisibility(View.INVISIBLE);
            }else {

                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
            }
            if(city != null && cityLoc != null) {
                if (cityLoc.indexOf(city)>=0) {
//                ivPopupSelectByProvinceRVItemCity.setVisibility(View.VISIBLE);
                    tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);

                } else {
                    ivPopupSelectByProvinceRVItemProvince.setVisibility(View.GONE);
                }
            }else {

                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.GONE);
            }
            Object selectNums = selectNumsMap.get(dataList.get(position).getArea());
            if(selectNums != null){
                if(!selectNums.equals("0")){
                    tvPopupSelectByProvinceRVItemSelectNum.setText(selectNums+"");
                    tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.VISIBLE);
                }else {
                    tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.INVISIBLE);
                }
            }else {
                tvPopupSelectByProvinceRVItemSelectNum.setText(0+"");
                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.INVISIBLE);

            }
//            String counts =
            tvPopupSelectByProvinceRVItemProvince.setText(city);

        }
    }


}
