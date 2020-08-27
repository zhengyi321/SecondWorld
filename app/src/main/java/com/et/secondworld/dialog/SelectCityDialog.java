package com.et.secondworld.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.et.secondworld.R;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.utils.GetJsonDataUtil;
import com.et.secondworld.utils.WindowUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/28
 **/
public class SelectCityDialog extends Dialog {
    private View mPopView;
    Context context;
    private Activity activity;
    private int selectnums = 0;
    public interface OnFinishClickListener{
        public void getMaps(Map<String,String> map,Boolean isProvince);
    }
    public interface OnDataListClickListener{
        public void getData(ArrayList<JsonBean> dataList);
    }
    private OnFinishClickListener onFinishClickListener;
    private OnDataListClickListener onDataListClickListener;
    public SelectCityDialog(Context context1) {
        super(context1);
        context = context1;
//        init(context);
//        setPopupWindow();
    }
    public SelectCityDialog(Context context1,int selectnums) {
        super(context1, selectnums);
        this.context = context1;
        this.selectnums = selectnums;
    }
    public Builder Build = new Builder(context);
    //用Builder模式来构造Dialog
    public  class Builder {
        private Context mContext;
        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        @BindView(R.id.rv_popup_select_by_province_province)
        RecyclerView rvPopupSelectByProvinceProvince;
        @BindView(R.id.rv_popup_select_by_province_city)
        RecyclerView rvPopupSelectByProvinceCity;
        @BindView(R.id.rly_popup_select_by_province_cancel)
        RelativeLayout rlyPopupSelectByProvinceCancel;
        @OnClick(R.id.rly_popup_select_by_province_cancel)
        public void rlyPopupSelectByProvinceCancelOnclick(){
            cityChangeDialog.dismiss();
        }
        @BindView(R.id.cb_popup_select_by_province_all)
        CheckBox cbPopupSelectByProvinceAll;
        @BindView(R.id.tv_popup_select_by_province_finish)
        TextView tvPopupSelectByProvinceFinish;
        @OnClick(R.id.tv_popup_select_by_province_finish)
        public void tvPopupSelectByProvinceFinishOnclick(){
            if(onFinishClickListener != null){
                onFinishClickListener.getMaps(cityRVAdapter.getMap(),cbPopupSelectByProvinceAll.isChecked());
            }
            if(onDataListClickListener != null){
                onDataListClickListener.getData(provinceRVAdapter.getDataList());
            }
            cityChangeDialog.dismiss();
//            Log.d("cityRVAdapter",cityRVAdapter.getMap().size()+"") ;
        }
        private SelectCityDialogProvinceRVAdapter provinceRVAdapter;
        private SelectCityDialogCityRVAdapter cityRVAdapter;
         SelectCityDialog cityChangeDialog;
//         private ArrayList<JsonBean> dataList ;
        private Map<Object,Object> provinceMap ;
        private Map<String,String> placeMap ;
        public Builder setCallBackListener(OnFinishClickListener onFinishClickListener1){//设置回调
            onFinishClickListener = onFinishClickListener1;
            return this;
        }
        public Builder setDataCallBackListener(OnDataListClickListener onDataListClickListener1){//设置回调
            onDataListClickListener = onDataListClickListener1;
            return this;
        }

        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public SelectCityDialog build(Context context,Map<Object,Object> provinceMap1,Map<String,String> placeMap1) {
            activity = (Activity)context;
            placeMap = placeMap1;

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cityChangeDialog = new SelectCityDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            cityChangeDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.popup_select_by_province, null);
            ButterKnife.bind(this,view);
            cityChangeDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = cityChangeDialog.getWindow().getAttributes();
            provinceMap = provinceMap1;
            WindowUtils windowUtils = new WindowUtils(activity);
            layoutParams.width = (int)windowUtils.getWindowWidth();
//            layoutParams.height = (int)windowUtils.getWindowHeight();
            layoutParams.gravity = Gravity.BOTTOM;
            Log.d("width",""+windowUtils.getWindowWidth());
            cityChangeDialog.getWindow().setAttributes(layoutParams);


            cityChangeDialog.setContentView(view);
            initCityRV();
            initProvinceRV();
            initJsonData();

            return cityChangeDialog;
        }
        private void initProvinceRV(){
//            ArrayList<String> dataList = new ArrayList<>();
//            for(int i = 0;i < 8;i++){
//                dataList.add("");
//            }
            provinceRVAdapter = new SelectCityDialogProvinceRVAdapter(cityRVAdapter,cbPopupSelectByProvinceAll,placeMap);
            rvPopupSelectByProvinceProvince.setLayoutManager(new LinearLayoutManager(activity));
            rvPopupSelectByProvinceProvince.setAdapter(provinceRVAdapter);
            rvPopupSelectByProvinceProvince.setItemViewCacheSize(500);//解决rv重复显示问题
//            provinceRVAdapter.replaceAll(dataList);
        }
        @SuppressLint("WrongConstant")
        private void initCityRV(){
            cityRVAdapter = new SelectCityDialogCityRVAdapter(tvPopupSelectByProvinceFinish,context,placeMap,cbPopupSelectByProvinceAll,onFinishClickListener,selectnums);

            //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
            GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
            //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
            gridLayoutManager.setOrientation(GridLayout.VERTICAL );
            //设置布局管理器， 参数gridLayoutManager对象
            rvPopupSelectByProvinceCity.setLayoutManager(gridLayoutManager);
            rvPopupSelectByProvinceCity.setAdapter(cityRVAdapter);
            String pro  = placeMap.get("province");
            if(pro != null && !pro.isEmpty()) {
                cbPopupSelectByProvinceAll.setChecked(true);
                cityRVAdapter.setSelectAll(true);
//                cityRVAdapter.selectAll(1);
            }else {
                cbPopupSelectByProvinceAll.setChecked(false);
                cityRVAdapter.setSelectAll(false);
//                cityRVAdapter.selectAll(0);
            }
            if(selectnums !=  50){
                cbPopupSelectByProvinceAll.setEnabled(false);
            }
            cbPopupSelectByProvinceAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        cityRVAdapter.selectAll(1);
                        placeMap.put("province",cityRVAdapter.getProvince());
                        provinceRVAdapter.notifyDataSetChanged();
                    }else {
                        cityRVAdapter.selectAll(0);
                        placeMap.remove("province");
                        provinceRVAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        private void initJsonData() {//解析数据

            /**
             * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
             * 关键逻辑在于循环体
             *
             * */
            String JsonData = new GetJsonDataUtil().getJson(activity,"province.json");//获取assets目录下的json文件数据

            ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(context);
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String province = xcCacheManager.readCache(xcCacheSaveName.currentProvince);
            ArrayList<JsonBean> tempJsonBeanList = new ArrayList<>();
            for(int i=0;i<jsonBean.size();i++){
                if(jsonBean.get(i).getName().equals(province)){
                    tempJsonBeanList.add(jsonBean.get(i));
                    jsonBean.remove(i);
                }
            }
            tempJsonBeanList.addAll(jsonBean);
            provinceRVAdapter.replaceAll(tempJsonBeanList,provinceMap);
//            if(dataList != null &&dataList.size() != 0) {
//                provinceRVAdapter.replaceAll(dataList);
//            }
            /**
             * 添加省份数据
             *
             * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
             * PickerView会通过getPickerViewText方法获取字符串显示出来。
             */
            Log.d("selectCity11:",jsonBean.size()+"");
            for (int i=0;i<jsonBean.size();i++){//遍历省份
                ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
                ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                    String CityName = jsonBean.get(i).getCityList().get(c).getName();
                    CityList.add(CityName);//添加城市

                    ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (jsonBean.get(i).getCityList().get(c).getArea() == null
                            ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                        City_AreaList.add("");
                    }else {

                        for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                            String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                            City_AreaList.add(AreaName);//添加该城市所有地区数据
                        }
                    }
                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                }

                /**
                 * 添加城市数据
                 */
//            options2Items.add(CityList);

                /**
                 * 添加地区数据
                 */
//            options3Items.add(Province_AreaList);
            }


        }
        public ArrayList<JsonBean> parseData(String result) {//Gson 解析
            ArrayList<JsonBean> detail = new ArrayList<>();
            try {
                JSONArray data = new JSONArray(result);
                Gson gson = new Gson();
                for (int i = 0; i < data.length(); i++) {
                    JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                    detail.add(entity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return detail;
        }
    }



/*

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_select_by_province, null);
        ButterKnife.bind(this,mPopView);

    }
    private void setPopupWindow() {

        this.setContentView(mPopView);// 设置View
    }
*/

}
