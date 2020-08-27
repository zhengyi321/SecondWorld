package com.et.secondworld.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class SelectAreaDialog extends Dialog {
    private View mPopView;
    Context context;
    private Activity activity;
    private int selectNums = 0;
    public interface OnFinishClickListener{
        public void getMaps(Map<String,String> map);
    }
    private OnFinishClickListener onFinishClickListener;
    public SelectAreaDialog(Context context1) {
        super(context1);
        context = context1;
//        init(context);
//        setPopupWindow();
    }
    public SelectAreaDialog(Context context1, int selectNums1) {
        super(context1, selectNums1);
//        super(context1,selectNums1);
        selectNums = selectNums1;
        this.context = context1;
    }
    public Builder Build = new Builder(context);
    //用Builder模式来构造Dialog
    public  class Builder {
        private Context mContext;
        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        @BindView(R.id.rv_dialog_select_by_area_province)
        RecyclerView rvDialogSelectByAreaProvince;
        @BindView(R.id.rv_dialog_select_by_area_city)
        RecyclerView rvDialogSelectByAreaCity;
        @BindView(R.id.rv_dialog_select_by_area_area)
        RecyclerView rvDialogSelectByAreaArea;
        @BindView(R.id.rly_dialog_select_by_area_cancel)
        RelativeLayout rlyDialogSelectByAreaCancel;
        @OnClick(R.id.rly_dialog_select_by_area_cancel)
        public void rlyDialogSelectByAreaCancelOnclick(){
            cityChangeDialog.dismiss();
        }

        @BindView(R.id.tv_dialog_select_by_area_finish)
        TextView tvDialogSelectByAreaFinish;
        @OnClick(R.id.tv_dialog_select_by_area_finish)
        public void tvDialogSelectByAreaFinishOnclick(){
            if(onFinishClickListener != null){
                onFinishClickListener.getMaps(areaRVAdapter.getMap());
            }
            cityChangeDialog.dismiss();
//            Log.d("areaRVAdapter",areaRVAdapter.getMap().size()+"") ;
//            Toast.makeText(getContext(),areaRVAdapter.getMap().size()+"",Toast.LENGTH_SHORT).show();
        }
        private SelectAreaDialogProvinceRVAdapter provinceRVAdapter;
        private SelectAreaDialogCityRVAdapter cityRVAdapter;
        private SelectAreaDialogAreaRVAdapter areaRVAdapter;
         SelectAreaDialog cityChangeDialog;

        public Builder setCallBackListener(OnFinishClickListener onFinishClickListener1){//设置回调
            onFinishClickListener = onFinishClickListener1;
            return this;
        }
        Map<Object,Object> cityMap;
        Map<String,String> placeMap;
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public SelectAreaDialog build(Context context,Map<Object,Object> cityMap1,Map<String,String> placeMap1) {
            activity =  (Activity)context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cityChangeDialog = new SelectAreaDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            cityChangeDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.dialog_select_by_area, null);
            ButterKnife.bind(this,view);
            cityMap = cityMap1;
//            cityMap = new HashMap<>();
            placeMap = placeMap1;
            cityChangeDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = cityChangeDialog.getWindow().getAttributes();

            WindowUtils windowUtils = new WindowUtils(activity);
            layoutParams.width = (int)windowUtils.getWindowWidth();
//            layoutParams.height = (int)windowUtils.getWindowHeight();
            layoutParams.gravity = Gravity.BOTTOM;
            Log.d("width",""+windowUtils.getWindowWidth());
            cityChangeDialog.getWindow().setAttributes(layoutParams);


            cityChangeDialog.setContentView(view);
            initAreaRV();
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
            provinceRVAdapter = new SelectAreaDialogProvinceRVAdapter(cityRVAdapter);
            rvDialogSelectByAreaProvince.setLayoutManager(new LinearLayoutManager(activity));
            rvDialogSelectByAreaProvince.setAdapter(provinceRVAdapter);
            rvDialogSelectByAreaProvince.setItemViewCacheSize(500);//解决rv重复显示问题
//            provinceRVAdapter.replaceAll(dataList);
        }
        private void initCityRV(){
            cityRVAdapter = new SelectAreaDialogCityRVAdapter(areaRVAdapter,context,cityMap);
            rvDialogSelectByAreaCity.setLayoutManager(new LinearLayoutManager(activity));
            rvDialogSelectByAreaCity.setAdapter(cityRVAdapter);
            rvDialogSelectByAreaCity.setItemViewCacheSize(500);//解决rv重复显示问题

        }

        private void initAreaRV(){
            areaRVAdapter = new SelectAreaDialogAreaRVAdapter(tvDialogSelectByAreaFinish,placeMap,onFinishClickListener,selectNums);
            rvDialogSelectByAreaArea.setLayoutManager(new LinearLayoutManager(activity));
            rvDialogSelectByAreaArea.setAdapter(areaRVAdapter);
            rvDialogSelectByAreaArea.setItemViewCacheSize(500);//解决rv重复显示问题

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
            provinceRVAdapter.replaceAll(tempJsonBeanList);
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
