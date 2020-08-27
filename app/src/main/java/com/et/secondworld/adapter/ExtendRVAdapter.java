package com.et.secondworld.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.dialog.SelectAreaDialog;
import com.et.secondworld.dialog.SelectCityDialog;
import com.et.secondworld.dialog.SelectTownDialog;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

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
public class ExtendRVAdapter extends RecyclerView.Adapter<ExtendRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<String> dataList = new ArrayList<>();
    private int isSelectQuanGuo = 0;
    private Map<String,String> maps;
    private RecyclerView recyclerView;
    private int type = -1;
    Map<Object,Object> provinceMap = new HashMap<>();
    Map<Object,Object> cityMap = new HashMap<>();
    Map<Object,Object> townMap = new HashMap<>();
    private Map<String,String> placeMapCity = new HashMap<>();
    private Map<String,String> placeMapProvince = new HashMap<>();
    private Map<String,String> placeMapTown = new HashMap<>();
    int selectPos = -1;
    private double cost = 0.01;
    TextView tvFactPay;
    RelativeLayout rlyExtendPay;
    TextView tvContactGov;
    boolean isForum = false;
    public boolean isFinish = true;
    private String articletype = "-1";
    //    ArrayList<String> list 中String改成int 就是本地图片
    public ExtendRVAdapter(Activity activity,TextView tvFactPay, RelativeLayout rlyExtendPay, TextView tvContactGov, boolean isForum, String articletype){
        this.tvFactPay = tvFactPay;
        this.rlyExtendPay = rlyExtendPay;
        this.tvContactGov = tvContactGov;
        this.isForum =isForum;
        this.articletype = articletype;
        if(isForum) {
            cost = 125000;
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
            String role = xcCacheManager.readCache(xcCacheSaveName.role);
            if(role != null && role.indexOf("107")>=0){
                cost = 0;
            }
        }else {
//            cost = 100000;
            if(articletype.equals("M2")) {
                cost =  10;
            }else if(articletype.equals("M3")){
                cost = 20;
            }else {
                cost = 0;
            }
        }
        tvFactPay.setText("实付：￥" + cost);
    }

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
    public void setType(){
        type = 0;
        selectPos = 0;
        notifyDataSetChanged();
    }
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
    public String getCost(){
        return cost+"";
    }

    public int gettype(){
        return type;
    }
    public Map<String,String> getPlaceMap(){
        if(type == 0){
            return null;
        }
        if(type == 1 || type == 2){
            return placeMapProvince;
        }
        if(type == 3){
            return placeMapCity;
        }
        if(type == 4){
            return placeMapTown;
        }
        return null;
    }
    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.recyclerView = (RecyclerView) parent;
        return new ExtendRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_extend_rv_item, parent, false));
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
            if(pos == 0){
//                if(isSelectQuanGuo == 0){
//                    tvExtendRVItemCity.setTextColor(Color.RED);
//                    isSelectQuanGuo = 1;
//                }else {
//
//                    tvExtendRVItemCity.setTextColor(Color.BLACK);
//                    isSelectQuanGuo = 0;
//                }
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                String role = xcCacheManager.readCache(xcCacheSaveName.role);
                if(selectPos != 0) {
                    cost = 0;
                }
                if(isForum) {
                    cost = 125000;
                    if(role != null && role.indexOf("107")>=0){
                        cost = 0;
                    }
                }else {
                    if(articletype.equals("M2")) {
                        cost =  100010;
                    }else if(articletype.equals("M3")){
                        cost = 100020;
                    }else {
                        cost = 100000;
                    }


                    if(role != null && role.indexOf("100")>=0){
                        if(articletype.equals("M2")) {
                            cost =  10;
                        }else if(articletype.equals("M3")){
                            cost = 20;
                        }else {
                            cost = 0;
                        }
                    }
//                    cost = 100000;
                }

                selectPos =0;
                placeMapCity.clear();
                placeMapProvince.clear();
                placeMapTown.clear();
                cityMap.clear();
                provinceMap.clear();
                townMap.clear();
                type = 0;
            }

            if(pos == 1) {
                if(selectPos != 1) {
                    if(isForum) {
                        cost = 0;
                    }else {
                        if(articletype.equals("M2")) {
                            cost =  10;
                        }else if(articletype.equals("M3")){
                            cost = 20;
                        }else {
                            cost = 0;
                        }
                    }
                }
                selectPos =1;
                type = 2;
                notifyDataSetChanged();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String isFirst = xcCacheManager.readCache(xcCacheSaveName.shopFirst);
                String role = xcCacheManager.readCache(xcCacheSaveName.role);
                int selectNums = 50;
                if(role != null && role.indexOf("100")>=0||role.indexOf("101")>=0){
                    if(!isForum) {
                        if(isFinish) {
                            selectNums = 1;
                        }else {
                            selectNums = 50;
                        }

                    }
                }
                if(role != null && role.indexOf("105") >=0 || role.indexOf("109")>=0){
                    if(!isForum) {

                    }else {
                        return;
                    }
                }
                if(role != null && role.indexOf("106")>=0){
                    selectNums = 50;
                }
                if(role != null && role.indexOf("107")>=0|| role.indexOf("108")>=0){
                    if(isForum) {
                        selectNums = 1;

                    }
                }

                /*if(isFirst== null || isFirst.indexOf("province")<0){
                    if(!isForum) {
                        selectNums = 1;
//                        return;
                    }
                }*/
//                WindowUtils windowUtils = new WindowUtils((Activity) v.getContext());
                SelectCityDialog selectCityPopup = new SelectCityDialog(v.getContext(),selectNums).Build.setCallBackListener(new SelectCityDialog.OnFinishClickListener() {


                    @Override
                    public void getMaps(Map<String, String> map,Boolean isProvince) {
                        maps = map;
                        String place = "";
                        if(maps != null) {
                            for (Object key : maps.keySet()) {
                                place += maps.get(key) + ",";
//                                Log.d("pay1111",place);
                            }
                        }
//                        if(maps.size() == 0){
                            cost = 0;
//                        }
                        if(isForum) {
                            int count = 0;
                            if(place.indexOf("北京")>= 0){
                                cost += 15000;
                                count ++;
                            }
                            if(place.indexOf("天津")>= 0){
                                cost += 15000;
                                count ++;
                            }
                            if(place.indexOf("重庆直辖县")>= 0){
                                cost += 7500;
                                count ++;
                            }
                            if(place.indexOf("重庆")>= 0){
                                cost += 7500;
                                count ++;
                            }
                            if(place.indexOf("上海")>= 0){
                                cost += 15000;
                                count ++;
                            }
                             cost += 4500 * (map.size()-count);
                            tvFactPay.setText("实付：￥" + cost);
                            type = 2;
                            if(isProvince){
                                if(place.indexOf("北京")>= 0){
                                    cost = 15000;
//                                    count ++;
                                }else if(place.indexOf("天津")>= 0){
                                    cost = 15000;
//                                    count ++;
                                } else if(place.indexOf("上海")>= 0){
                                    cost = 15000;

                                }else {
                                    cost = 20000;
                                }

                                tvFactPay.setText("实付：￥"+cost );
                                type = 1;
                            }

                            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                            String role = xcCacheManager.readCache(xcCacheSaveName.role);
                            if(role != null && role.indexOf("107")>=0||role.indexOf("108")>=0){
                                cost = 0;
                                tvFactPay.setText("实付：￥"+cost );
                            }
                        }else {
                             cost = 0;
                            int count = 0;
                            if(place.indexOf("北京")>= 0){
                                cost += 10000;
                                count ++;
                            }
                            if(place.indexOf("天津")>= 0){
                                cost += 10000;
                                count ++;
                            }
                            if(place.indexOf("重庆直辖县")>= 0){
                                cost += 5000;
                                count ++;
                            }
                            if(place.indexOf("重庆")>= 0){
                                cost += 5000;
                                count ++;
                            }
                            if(place.indexOf("上海")>= 0){
                                cost += 10000;
                                count ++;
                            }
//                            cost += 4500 * (map.size()-count);
                            if(articletype.equals("M2")) {
                                cost += 3000 *(map.size()-count) + 10;
                            }else if(articletype.equals("M3")){
                                cost += 3000 * (map.size()-count) + 20;
                            }else {
                                cost += 3000 * (map.size()-count);
                            }
                            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                            String role = xcCacheManager.readCache(xcCacheSaveName.role);
                            if(role != null && role.indexOf("100")>=0||role.indexOf("101")>=0||role.indexOf("106")>=0){
                                if(articletype.equals("M2")) {
                                    cost =  10;
                                }else if(articletype.equals("M3")){
                                    cost = 20;
                                }else {
                                    cost = 0;
                                }
                            }
                            tvFactPay.setText("实付：￥" + cost);
                            type = 2;
                            if(isProvince){
//                                cost = 15000;
                                if(place.indexOf("北京")>= 0){
                                    cost = 10000;
//                                    count ++;
                                }else if(place.indexOf("天津")>= 0){
                                    cost = 10000;
//                                    count ++;
                                } else if(place.indexOf("上海")>= 0){
                                    cost = 10000;

                                }else {
                                    cost = 15000;
                                }
                                if(articletype.equals("M2")) {
                                    cost +=  10;
                                }else if(articletype.equals("M3")){
                                    cost +=  20;
                                }else {
//                                    cost += 3000 * (map.size()-count);
                                }
//                                XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//                                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//                                String role = xcCacheManager.readCache(xcCacheSaveName.role);
//
//                                if(role != null && role.equals("02")) {
////            Toast.makeText(getContext(),"禁言中",Toast.LENGTH_SHORT).show();
////            return;
////                                    tvExtendBenefit.setText("贵宾卡");
//                                    cost = 0;
//
//                                }
                                tvFactPay.setText("实付：￥"+cost );
                                if(isFinish) {
                                    if (role != null && role.equals("106")) {
                                        if (articletype.equals("M2")) {
                                            cost = 10;
                                        } else if (articletype.equals("M3")) {
                                            cost = 20;
                                        } else {
                                            cost = 0;
                                        }
                                    }
                                    tvFactPay.setText("实付：￥" + cost);
                                }
                            }

                                /*XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                                String isFirst = xcCacheManager.readCache(xcCacheSaveName.shopFirst);
                                if(isFirst == null || isFirst.indexOf("province")<0){
                                    if(articletype.equals("M2")) {
                                        cost =  10;
                                    }else if(articletype.equals("M3")){
                                        cost =  20;
                                    }else {
                                        cost = 0;
                                    }
                                    tvFactPay.setText("实付：￥" + cost);
                                }*/
//                                tvFactPay.setText("实付：￥15000" );
                                type = 1;
                            }

                    }
                }).setDataCallBackListener(new SelectCityDialog.OnDataListClickListener() {
                    @Override
                    public void getData(ArrayList<JsonBean> dataList) {
//                        dataList1.addAll(dataList);
                    }
                }).build(v.getContext(),provinceMap,placeMapProvince);
                selectCityPopup.show();

                placeMapCity.clear();
                cityMap.clear();
                placeMapTown.clear();
                townMap.clear();
//                Log.d("v1111",placeMapProvince.size()+"");
//                for(Object key : placeMapProvince.keySet()){
//
//                    Log.d("v1111",placeMapProvince.get(key)+"");
//                }

//                provinceMap.clear();
//                SelectCityPopup popupWindow = new SelectCityPopup(v.getContext());
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
////                popupWindow.setHeight((int)windowUtils.getWindowHeight()-200);
//                popupWindow.setAnimationStyle(R.style.pop_anim);
//                popupWindow.setFocusable(true);
//                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                popupWindow.showAtLocation(v, Gravity.BOTTOM,0,0);
            }
            if(pos == 2){
                if(selectPos != 2) {
                    if(isForum) {
                        cost = 0;
                    }else {
                        if(articletype.equals("M2")) {
                            cost =  10;
                        }else if(articletype.equals("M3")){
                            cost = 20;
                        }else {
                            cost = 0;
                        }
                    }
                }
                int selectnum = 50;
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String role = xcCacheManager.readCache(xcCacheSaveName.role);

                if(role != null && role.indexOf("02")>=0||role.indexOf("100")>=0||role.indexOf("101")>=0||role.indexOf("102")>=0||role.indexOf("106")>=0) {
//            Toast.makeText(getContext(),"禁言中",Toast.LENGTH_SHORT).show();
//            return;
//                                    tvExtendBenefit.setText("贵宾卡");
//                    cost = 0;
                    if(!isForum) {
//                        selectnum = 1;
                        if(isFinish) {
                            selectnum = 1;
                        }else {
                            selectnum = 50;
                        }
                    }

                }
                if(role != null && role.indexOf("107") >=0 || role.indexOf("108")>=0|| role.indexOf("109")>=0){
                    if(isForum){
                        selectnum = 1;
                    }
                }

                String isFirst = xcCacheManager.readCache(xcCacheSaveName.shopFirst);
//                int selectNums = 50;
                if(isFirst== null || isFirst.indexOf("area")<0){
                    if(!isForum) {
                        selectnum = 1;
                    }
                }
                SelectAreaDialog selectAreaDialog = new SelectAreaDialog(v.getContext(),selectnum).Build.setCallBackListener(new SelectAreaDialog.OnFinishClickListener() {
                    @Override
                    public void getMaps(Map<String, String> map) {
                        maps = map;
                        String place = "";
                        int count = 0;
                        if(maps != null) {
                            for (Object key : maps.keySet()) {
                                place += maps.get(key) + ",";
//                                Log.d("pay1111",place);
                            }
                        }
                        cost = 0;
                        if(isForum) {
                            if(place.indexOf("广东省东莞市")>=0){
                                cost += 4500;
                                count++;
                            }
                            if(place.indexOf("海南省儋州市")>=0){
                                cost += 4500;
                                count++;
                            }

                             cost += 750 * (map.size()-count);
                            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                            String role = xcCacheManager.readCache(xcCacheSaveName.role);
                            if(role != null && role.indexOf("107")>=0||role.indexOf("108")>=0||role.indexOf("109")>=0) {
                                cost = 0;
                            }
                            tvFactPay.setText("实付：￥" + cost);
                        }else {
//                             cost = 0;
                            if(place.indexOf("广东省东莞市")>=0){
                                cost += 3000;
                                count++;
                            }
                            if(place.indexOf("海南省儋州市")>=0){
                                cost += 3000;
                                count++;
                            }
                            if(articletype.equals("M2")) {
                                cost += 500 * (map.size()-count)+ 10;
                            }else if(articletype.equals("M3")){
                                cost += 500 * (map.size()-count) + 20;
                            }else {
                                cost += 500 * (map.size()-count);
                            }
                            tvFactPay.setText("实付：￥" + cost);
                            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                            String role = xcCacheManager.readCache(xcCacheSaveName.role);
                            String account = xcCacheManager.readCache(xcCacheSaveName.shopId);
                            if(role != null && role.indexOf("02")>=0||role.indexOf("100")>=0||role.indexOf("101")>=0||role.indexOf("102")>=0||role.indexOf("106")>=0) {
//            Toast.makeText(getContext(),"禁言中",Toast.LENGTH_SHORT).show();
//            return;
//                                    tvExtendBenefit.setText("贵宾卡");
                                Map<String,Object> paramMap = new HashMap<>();
                                paramMap.put("accountid",account);
                                ArticleNetWork articleNetWork = new ArticleNetWork();
                                articleNetWork.isHasFreeTimeFromNet(paramMap, new Observer<BaseBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(BaseBean baseBean) {
                                        Log.d("zzz111",baseBean.getIssuccess());
                                        Log.d("zzz111",baseBean.getMsg());
                                        if(baseBean.getIssuccess().equals("1")){
                                            if(articletype.equals("M2")) {
                                                cost =  10;
                                            }else if(articletype.equals("M3")){
                                                cost =  20;
                                            }else {
                                                cost = 0;
                                            }
                                            tvFactPay.setText("实付：￥" + cost);
                                        }
                                    }
                                });


                            }
                           /* String isFirst = xcCacheManager.readCache(xcCacheSaveName.shopFirst);
                            if(isFirst== null || isFirst.indexOf("area")<0){
                                if(articletype.equals("M2")) {
                                    cost =  10;
                                }else if(articletype.equals("M3")){
                                    cost =  20;
                                }else {
                                    cost = 0;
                                }
                                tvFactPay.setText("实付：￥" + cost);
                            }*/


//                            int cost = 750 * map.size();

                        }
                    }
                }).build(v.getContext(),cityMap,placeMapCity);
                selectAreaDialog.show();
                placeMapProvince.clear();
                provinceMap.clear();
                placeMapTown.clear();
                townMap.clear();

                selectPos =2;
                type = 3;
//                notifyDataSetChanged();
            }

            if(pos == 3){
                if(selectPos != 3) {
                    if(isForum) {
                        cost = 0;
                    }else {
                        if(articletype.equals("M2")) {
                            cost =  10;
                        }else if(articletype.equals("M3")){
                            cost = 20;
                        }else {
                            cost = 0;
                        }
                    }
                }
                int selectnum = 50;
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String role = xcCacheManager.readCache(xcCacheSaveName.role);

                if(role != null && role.equals("02")||role.equals("100")||role.equals("101")||role.equals("102")||role.equals("106")) {
//            Toast.makeText(getContext(),"禁言中",Toast.LENGTH_SHORT).show();
//            return;
//                                    tvExtendBenefit.setText("贵宾卡");
//                    cost = 0;
                    if(!isForum) {
//                        selectnum = 5;
                        if(isFinish) {
                            selectnum = 5;
                        }else {
                            selectnum = 50;
                        }
                    }

                }

                if(role != null && role.indexOf("107")>=0||role.indexOf("108")>=0||role.indexOf("109")>=0) {
                    if(isForum){
                        selectnum = 5;
                    }
                }

                String isFirst = xcCacheManager.readCache(xcCacheSaveName.shopFirst);
//                int selectNums = 50;
                if(isFirst== null || isFirst.indexOf("town")<0){
                    if(!isForum) {
                        selectnum = 5;
                    }
                }
                SelectTownDialog selectTownDialog = new SelectTownDialog(v.getContext(),selectnum).Build.setCallBackListener(new SelectTownDialog.OnFinishClickListener() {
                    @Override
                    public void getMaps(Map<String, String> map) {
                        maps = map;
                        if(isForum) {
                             cost = 100 * map.size();
                            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                            String role = xcCacheManager.readCache(xcCacheSaveName.role);
                            if(role != null && role.indexOf("107")>=0||role.indexOf("108")>=0||role.indexOf("109")>=0) {
                                cost = 0;
                            }
                            tvFactPay.setText("实付：￥" + cost);
                        }else {
                             cost = 0;
                            if(articletype.equals("M2")) {
                                cost = 50 * map.size() + 10;
                            }else if(articletype.equals("M3")){
                                cost = 50 * map.size() + 20;
                            }else {
                                cost = 50 * map.size();
                            }
                            tvFactPay.setText("实付：￥" + cost);
                            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                            String role = xcCacheManager.readCache(xcCacheSaveName.role);
                            String account = xcCacheManager.readCache(xcCacheSaveName.shopId);
                            if(role != null && role.indexOf("02")>=0||role.indexOf("100")>=0||role.indexOf("101")>=0||role.indexOf("102")>=0||role.indexOf("106")>=0) {
//            Toast.makeText(getContext(),"禁言中",Toast.LENGTH_SHORT).show();
//            return;
//                                    tvExtendBenefit.setText("贵宾卡");
                                Map<String,Object> paramMap = new HashMap<>();
                                paramMap.put("accountid",account);
//                                Log.d("zzz11",account);
                                ArticleNetWork articleNetWork = new ArticleNetWork();
                                articleNetWork.isHasFreeTimeFromNet(paramMap, new Observer<BaseBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(BaseBean baseBean) {
                                        if(baseBean.getIssuccess().equals("1")){
                                            if(articletype.equals("M2")) {
                                                cost =  10;
                                            }else if(articletype.equals("M3")){
                                                cost =  20;
                                            }else {
                                                cost = 0;
                                            }
                                            tvFactPay.setText("实付：￥" + cost);
                                        }
                                    }
                                });


                            }

                          /*  String isFirst = xcCacheManager.readCache(xcCacheSaveName.shopFirst);
                            if(isFirst== null || isFirst.indexOf("town")<0){
                                if(articletype.equals("M2")) {
                                    cost =  10;
                                }else if(articletype.equals("M3")){
                                    cost =  20;
                                }else {
                                    cost = 0;
                                }
                                tvFactPay.setText("实付：￥" + cost);
                            }*/
//                            int cost = 100 * map.size();

                        }
                    }
                }).build(v.getContext(),townMap,placeMapTown);
                selectTownDialog.show();
                placeMapProvince.clear();
                provinceMap.clear();
                placeMapCity.clear();
                cityMap.clear();
                selectPos =3;
                type = 4;
//                notifyDataSetChanged();
            }
            tvFactPay.setText("实付：￥" + cost);
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
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            String role = xcCacheManager.readCache(xcCacheSaveName.role);
//            tvExtendRVItemCity.setTextColor(Color.BLACK);
            if(position == 0){

//                isSelectQuanGuo = 0;
            }
            if(selectPos == 0){
                if(role != null && role.indexOf("02")>=0||role.indexOf("101")>=0||role.indexOf("102")>=0||role.indexOf("106")>=0){
                    if(!isForum){
                        tvContactGov.setVisibility(View.VISIBLE);
                        tvFactPay.setVisibility(View.GONE);
                    }else {
                        rlyExtendPay.setVisibility(View.VISIBLE);
                        tvContactGov.setVisibility(View.GONE);
                        tvFactPay.setVisibility(View.VISIBLE);
                    }
                }else {
                    rlyExtendPay.setVisibility(View.VISIBLE);
                    tvContactGov.setVisibility(View.GONE);
                    tvFactPay.setVisibility(View.VISIBLE);
                }

                if(role != null && role.indexOf("104")>=0||role.indexOf("105")>=0||role.indexOf("108")>=0||role.indexOf("109")>=0) {
                    if(isForum){
                        tvContactGov.setVisibility(View.VISIBLE);
                        tvFactPay.setVisibility(View.GONE);
                    }else {
                        rlyExtendPay.setVisibility(View.VISIBLE);
                        tvContactGov.setVisibility(View.GONE);
                        tvFactPay.setVisibility(View.VISIBLE);
                    }

                }
            }else if(selectPos == 1){
                if(role != null && role.indexOf("02")>=0||role.indexOf("102")>=0){
                    if(!isForum){
                        tvContactGov.setVisibility(View.VISIBLE);
                        tvFactPay.setVisibility(View.GONE);
                    }else {
                        rlyExtendPay.setVisibility(View.VISIBLE);
                        tvContactGov.setVisibility(View.GONE);
                        tvFactPay.setVisibility(View.VISIBLE);
                    }
                }else {
                    rlyExtendPay.setVisibility(View.VISIBLE);
                    tvContactGov.setVisibility(View.GONE);
                    tvFactPay.setVisibility(View.VISIBLE);
                }
                if(role != null && role.indexOf("105")>=0||role.indexOf("109")>=0){
                    if(isForum){
                        tvContactGov.setVisibility(View.VISIBLE);
                        tvFactPay.setVisibility(View.GONE);
                    }else {
                        rlyExtendPay.setVisibility(View.VISIBLE);
                        tvContactGov.setVisibility(View.GONE);
                        tvFactPay.setVisibility(View.VISIBLE);
                    }
//                    tvContactGov.setVisibility(View.VISIBLE);
//                    tvFactPay.setVisibility(View.GONE);
                }

            } else if(selectPos == 2){
                rlyExtendPay.setVisibility(View.VISIBLE);
                tvContactGov.setVisibility(View.GONE);
                tvFactPay.setVisibility(View.VISIBLE);
            } else if(selectPos == 3){
                rlyExtendPay.setVisibility(View.VISIBLE);
                tvContactGov.setVisibility(View.GONE);
                tvFactPay.setVisibility(View.VISIBLE);
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
