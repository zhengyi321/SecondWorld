package com.et.secondworld.mapview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.dialog.SelectAreaDialog;
import com.et.secondworld.dialog.SelectCityDialog;
import com.et.secondworld.dialog.SelectTownDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class MapPostExtendRVAdapter extends RecyclerView.Adapter<MapPostExtendRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<String> dataList = new ArrayList<>();
    private int isSelectQuanGuo = 0;
    private Map<String,String> maps;
    private RecyclerView recyclerView;
    private int type = 0;
//    Map<Object,Object> provinceMap = new HashMap<>();
//    Map<Object,Object> cityMap = new HashMap<>();
    Map<Object,Object> townMap = new HashMap<>();
//    private Map<String,String> placeMapCity = new HashMap<>();
//    private Map<String,String> placeMapProvince = new HashMap<>();
    private Map<String,String> placeMapTown = new HashMap<>();
    int selectPos = 0;
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(ArrayList<String> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }
//    public Map<Object,Object> getMap(){
//        if(maps == null){
//            maps = new HashMap<>();
//        }
//        return maps;
//    }
    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, ArrayList<String> list) {
        dataList.addAll(position,list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public int gettype(){
        return type;
    }
    public Map<String,String> getPlaceMap(){
//        if(type == 0){
//            return null;
//        }
//        if(type == 1){
//            return placeMapProvince;
//        }
//        if(type == 2){
//            return placeMapCity;
//        }
//        if(type == 3){
            return placeMapTown;
//        }
//        return null;
    }
    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.recyclerView = (RecyclerView) parent;
        return new MapPostExtendRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_extend_rv_item, parent, false));
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

        @BindView(R.id.tv_extend_rv_item_city)
        TextView tvExtendRVItemCity;
        @BindView(R.id.rly_extend_rv_item_city)
        RelativeLayout rlyExtendRVItemCity;
//        ArrayList<JsonBean> dataList1 = new ArrayList<>();

        @OnClick(R.id.rly_extend_rv_item_city)
        public void rlyExtendRVItemCityOnclick(){
//            if(pos == 0){
////                if(isSelectQuanGuo == 0){
////                    tvExtendRVItemCity.setTextColor(Color.RED);
////                    isSelectQuanGuo = 1;
////                }else {
////
////                    tvExtendRVItemCity.setTextColor(Color.BLACK);
////                    isSelectQuanGuo = 0;
////                }
//                selectPos =0;
//                placeMapCity.clear();
//                placeMapProvince.clear();
//                placeMapTown.clear();
//                cityMap.clear();
//                provinceMap.clear();
//                townMap.clear();
//                type = 0;
//            }
//
//            if(pos == 1) {
////                WindowUtils windowUtils = new WindowUtils((Activity) v.getContext());
//                SelectCityDialog selectCityPopup = new SelectCityDialog(v.getContext()).Build.setCallBackListener(new SelectCityDialog.OnFinishClickListener() {
//                    @Override
//                    public void getMaps(Map<Object, Object> map) {
//                        maps = map;
//                    }
//                }).setDataCallBackListener(new SelectCityDialog.OnDataListClickListener() {
//                    @Override
//                    public void getData(ArrayList<JsonBean> dataList) {
////                        dataList1.addAll(dataList);
//                    }
//                }).build(v.getContext(),provinceMap,placeMapProvince);
//                selectCityPopup.show();
//                selectPos =1;
//                placeMapCity.clear();
//                cityMap.clear();
//                placeMapTown.clear();
//                townMap.clear();
////                Log.d("v1111",placeMapProvince.size()+"");
////                for(Object key : placeMapProvince.keySet()){
////
////                    Log.d("v1111",placeMapProvince.get(key)+"");
////                }
//                type = 1;
////                provinceMap.clear();
////                SelectCityPopup popupWindow = new SelectCityPopup(v.getContext());
////                popupWindow.setOutsideTouchable(true);
////                popupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
//////                popupWindow.setHeight((int)windowUtils.getWindowHeight()-200);
////                popupWindow.setAnimationStyle(R.style.pop_anim);
////                popupWindow.setFocusable(true);
////                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////                popupWindow.showAtLocation(v, Gravity.BOTTOM,0,0);
//            }
//            if(pos == 2){
//
//                SelectAreaDialog selectAreaDialog = new SelectAreaDialog(v.getContext()).Build.setCallBackListener(new SelectAreaDialog.OnFinishClickListener() {
//                    @Override
//                    public void getMaps(Map<Object, Object> map) {
//                        maps = map;
//                    }
//                }).build(v.getContext(),cityMap,placeMapCity);
//                selectAreaDialog.show();
//                placeMapProvince.clear();
//                provinceMap.clear();
//                placeMapTown.clear();
//                townMap.clear();
//
//                selectPos =2;
//                type = 2;
////                notifyDataSetChanged();
//            }
//
//            if(pos == 3){
                SelectTownDialog selectTownDialog = new SelectTownDialog(v.getContext(),5).Build.setCallBackListener(new SelectTownDialog.OnFinishClickListener() {
                    @Override
                    public void getMaps(Map<String, String> map) {
                        maps = map;
                    }
                }).build(v.getContext(),townMap,placeMapTown);
                selectTownDialog.show();
//                placeMapProvince.clear();
//                provinceMap.clear();
//                placeMapCity.clear();
//                cityMap.clear();
//                selectPos =3;
//                type = 3;
//                notifyDataSetChanged();
//            }
           /* for(int i = 0; i < recyclerView.getChildCount(); i++){
                RelativeLayout ll = (RelativeLayout)recyclerView .getChildAt(i);
                TextView textView = (TextView) ll.getChildAt(0);
                textView.setTextColor(Color.BLACK);
            }
            tvExtendRVItemCity.setTextColor(Color.RED);*/
            notifyDataSetChanged();
        }
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            String city = dataList.get(position);
            tvExtendRVItemCity.setText(city);
            pos = position;
//            tvExtendRVItemCity.setTextColor(Color.BLACK);
            if(position == 0){

//                isSelectQuanGuo = 0;
            }
            if(position == selectPos){
                tvExtendRVItemCity.setTextColor(Color.RED);
            }else {
                tvExtendRVItemCity.setTextColor(Color.BLACK);
            }
//            switch (selectPos){
//                case 0:
//                    tvExtendRVItemCity.setTextColor(Color.RED);
//                    break;
//                case 1:
//
//                    tvExtendRVItemCity.setTextColor(Color.RED);
//                    break;
//                case 2:
//
//                    tvExtendRVItemCity.setTextColor(Color.RED);
//                    break;
//                case 3:
//
//                    tvExtendRVItemCity.setTextColor(Color.RED);
//                    break;
//            }
        }
    }


}
