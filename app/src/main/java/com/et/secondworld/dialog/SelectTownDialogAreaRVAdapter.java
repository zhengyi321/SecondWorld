package com.et.secondworld.dialog;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.GetAreaBean;
import com.et.secondworld.bean.GetTownBean;
import com.et.secondworld.network.StreetNetWork;

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
public class SelectTownDialogAreaRVAdapter extends RecyclerView.Adapter<SelectTownDialogAreaRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<GetAreaBean.ListBean> dataList = new ArrayList<>();
    private SelectCityDialogCityRVAdapter rvAdapter ;
    private Map<Object,Object> map = new HashMap<>();
    private TextView textViewSelect;
    private TextView tvFinish;
    private RecyclerView recyclerView;
    private SelectTownDialogTownRVAdapter selectTownDialogTownRVAdapter;
    private RecyclerView recyclerViewTown;
    private int selectNums = 0;
//    private Map<Object,Object> selectNumsMap ;
    private LinearLayout llyDialogSelectByTown;
    private String city = "";
    private String province = "";
    private ProgressBar pbDialogSelectByTownTown;
    private Map<Object,Object> townMap ;
    private int selectPos = 0;

//    private CheckBox checkBox;
    public SelectTownDialogAreaRVAdapter(SelectTownDialogTownRVAdapter selectTownDialogTownRVAdapter1, TextView tvFinish1, RecyclerView recyclerViewTown1, LinearLayout llyDialogSelectByTown1, ProgressBar pbDialogSelectByTownTown1,Map<Object,Object> townMap1){
        selectTownDialogTownRVAdapter = selectTownDialogTownRVAdapter1;

        recyclerViewTown = recyclerViewTown1;
        llyDialogSelectByTown = llyDialogSelectByTown1;
        tvFinish = tvFinish1;
        pbDialogSelectByTownTown = pbDialogSelectByTownTown1;
        townMap = townMap1;
    }
    public Map<Object,Object> getMap(){
        return map;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetAreaBean.ListBean> list, TextView textViewSelect1, Map<Object,Object> selectNumsMap1, String city1, String province1) {
        dataList.clear();
//        selectNumsMap = selectNumsMap1;
        city = city1;
        province = province1;
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
        this.recyclerView = (RecyclerView) parent;
        return new SelectTownDialogAreaRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_select_by_province_rv_item_province, parent, false));
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
//            Object mapData = map.get(pos+dataList.get(pos));
//            String counts = textViewSelect.getText().toString();
//            selectNums = Integer.parseInt(counts);
//            if(mapData == null ){
////                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//                map.put(pos+dataList.get(pos),dataList.get(pos));
////                selectNums++;
////                textViewSelect.setText(selectNums+"");
////                textViewSelect.setVisibility(View.VISIBLE);
//            }else {
////                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
////                map.remove(pos+dataList.get(pos));
////                selectNums--;
////
////                textViewSelect.setText(selectNums+"");
////                textViewSelect.setVisibility(View.VISIBLE);
//            }
//            if(selectNums == 0){
//                textViewSelect.setVisibility(View.GONE);
//            }
//            tvFinish.setText("完成("+map.size()+"/100)");
//            selectNumsMap.put(name,selectNums);
            //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
//            final TranslateAnimation ctrlAnimation = new TranslateAnimation(
//                    TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
//                    TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
//            ctrlAnimation.setDuration(400l);
//            recyclerViewTown.startAnimation(ctrlAnimation);
            recyclerViewTown.setVisibility(View.VISIBLE);
            llyDialogSelectByTown.setBackgroundResource(R.drawable.up_acute_down_right_shape);
            recyclerViewTown.setBackgroundColor(Color.WHITE);
            selectPos = pos;
            notifyDataSetChanged();
          /*  for(int i = 0; i < recyclerView.getChildCount(); i++){
                RelativeLayout ll = (RelativeLayout)recyclerView .getChildAt(i);
                TextView textView = (TextView) ll.getChildAt(1);
                textView.setTextColor(Color.BLACK);
            }
//            selectNumsMap.put(pos+dataList.get(pos).getName(),dataList.get(pos).getName());
            tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);*/
//            recyclerViewTown.setVisibility(View.INVISIBLE);
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
            if(position == selectPos){
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
                pbDialogSelectByTownTown.setVisibility(View.VISIBLE);
                String searcharea = dataList.get(position).getAllname();
                StreetNetWork streetNetWork = new StreetNetWork();
                Map<String,Object> map = new HashMap<>();
                map.put("area",searcharea);
                streetNetWork.getTownFromNet(map, new Observer<GetTownBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetTownBean getTownBean) {
                        selectTownDialogTownRVAdapter.replaceAll(getTownBean.getList(),tvPopupSelectByProvinceRVItemSelectNum,townMap,area,city,province);
                        pbDialogSelectByTownTown.setVisibility(View.GONE);
                    }
                });
            }else {
                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);
            }

            if(position != 0){
//                if(selectPos == pos){
//                    tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//                }else {
//                }
//                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.GONE);
            }else {
//                StreetNetWork streetNetWork = new StreetNetWork();
//                Map<String,Object> map = new HashMap<>();
//                map.put("area",dataList.get(position));
//                streetNetWork.getTownFromNet(map, new Observer<GetTownBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(GetTownBean getTownBean) {
//                        selectTownDialogTownRVAdapter.replaceAll(getTownBean.getList(),tvPopupSelectByProvinceRVItemSelectNum,selectNumsMap,dataList.get(position));
//                    }
//                });
//                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
//                ivPopupSelectByProvinceRVItemProvince.setVisibility(View.VISIBLE);
//                rvAdapter.replaceAll(dataList.get(pos).getCityList(),tvPopupSelectByProvinceRVItemSelectNum);
            }
            ivPopupSelectByProvinceRVItemProvince.setVisibility(View.GONE);
//            tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.BLACK);

            Map<Object,Object> areaMap = (Map<Object, Object>) townMap.get(area);
            if(areaMap != null && areaMap.size() !=  0){

//                int selectNums = areaMap.size();
//                Log.d("areazzzz",selectNums+"");

                tvPopupSelectByProvinceRVItemSelectNum.setText(areaMap.size()+"");
                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.VISIBLE);

            }else {
                tvPopupSelectByProvinceRVItemSelectNum.setText(0+"");
                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.INVISIBLE);

            }
           /* Object selectNums = selectNumsMap.get(dataList.get(position));
            if(selectNums != null){

                tvPopupSelectByProvinceRVItemSelectNum.setText(selectNums+"");
                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.VISIBLE);
            }else {
                tvPopupSelectByProvinceRVItemSelectNum.setText(0+"");
                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.INVISIBLE);

            }*/

            /*String counts = tvPopupSelectByProvinceRVItemSelectNum.getText().toString();
            int selectNum = Integer.parseInt(counts);
            if(selectNum == 0){
                tvPopupSelectByProvinceRVItemSelectNum.setVisibility(View.INVISIBLE);
            }*/
//            Object mapData = map.get(pos+dataList.get(pos));
//            if(mapData == null ){
////                selectNums++;
////                textViewSelect.setText(selectNums+"");
////                textViewSelect.setVisibility(View.VISIBLE);
//            }else {
////                selectNums++;
////                if(selectNums == 0){
////                    textViewSelect.setVisibility(View.GONE);
////                }
//                tvPopupSelectByProvinceRVItemProvince.setTextColor(Color.RED);
////                textViewSelect.setText(selectNums+"");
////                textViewSelect.setVisibility(View.VISIBLE);
//            }
        }
    }


}
