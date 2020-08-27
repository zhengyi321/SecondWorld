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
public class SelectAreaDialogProvinceRVAdapter extends RecyclerView.Adapter<SelectAreaDialogProvinceRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<JsonBean> dataList = new ArrayList<>();
    private SelectAreaDialogCityRVAdapter rvAdapter ;
    private RecyclerView recyclerView;

    int selectPos = 0;
//    private CheckBox checkBox;
    public SelectAreaDialogProvinceRVAdapter(SelectAreaDialogCityRVAdapter rvAdapter1){
        rvAdapter = rvAdapter1;
//        checkBox = checkBox1;
    }

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(ArrayList<JsonBean> list) {
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
        this.recyclerView = (RecyclerView) parent;
        return new SelectAreaDialogProvinceRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_select_by_province_rv_item_province, parent, false));
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
//            String checkNums = tvPopupSelectByProvinceRVItemSelectNum.getText().toString();
//            if(checkNums.equals("0")){
//                rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum);
//                checkBox.setChecked(false);
//            }
//            if(checkNums.equals(""+dataList.get(pos).getCityList().size())){
//                rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum);
//                checkBox.setChecked(true);
//            }else {
//                rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum);
////                checkBox.setChecked(false);
//            }

//            Log.d("childcount",""+dataList.size());
            /*for(int i = 0; i < recyclerView.getChildCount(); i++){
                RelativeLayout ll = (RelativeLayout)recyclerView .getChildAt(i);
                TextView textView = (TextView) ll.getChildAt(1);
                textView.setTextColor(Color.BLACK);
            }
            selectPos = pos;
            rvAdapter.replaceAll(dataList.get(pos).getCityList());
            tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);*/
            selectPos = pos;
            notifyDataSetChanged();
//            selectPos = this.getAdapterPosition();
//            notifyDataSetChanged();
//            tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);


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
            pos = position;
//            tvExtendRVItemCity.setText(city);
            if(position != 0){
//                if(selectPos == pos){
//                    tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//                }else {
                    tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
//                }
                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.GONE);
            }else {
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.VISIBLE);


            }

//            Log.d("position",selectPos+"");
            if(selectPos == pos){
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
                Map<String,Object> map = new HashMap<>();
//                if(province.equals(city)){
//                    map.put("city", province );
//                }else {
//                    map.put("city", province + city);
//                }
                String provincesearch = province.substring(0,2);
                map.put("province", provincesearch);
//                if(province.equals(city)){
//                    map.put("city", province );
//                }else {
//                    map.put("city", province + city);
//                }
//                map.put("city", city);
                StreetNetWork streetNetWork = new StreetNetWork();
                streetNetWork.getCityFromNet(map, new Observer<GetCityBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCityBean getCityBean) {
                        rvAdapter.replaceAll(getCityBean.getList(),province);
//                            rvAdapter.replaceAll(dataList.get(pos).getArea(),tvPopupSelectByProvinceRVItemSelectNum,selectNumsMap,city,province);
                    }
                });
//                rvAdapter.replaceAll(dataList.get(pos).getCityList(),province);

            }else {
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
            }
                tvPopupSelectByProvinceRVItemProvince.setText(province);
        }
    }


}
