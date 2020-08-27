package com.et.secondworld.widget.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.TecentMessageListBean;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class MapViewMessageListRVAdapter extends RecyclerView.Adapter<MapViewMessageListRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<TecentMessageListBean.ListBean> dataList = new ArrayList<>();
    private TencentMap tencentMap;
    public interface OnFinishClickListener{
        public void isQuery(String lat, String lon);
    }
    public void setCallBackListener(OnFinishClickListener onFinishClickListener1){//设置回调
        onFinishClickListener = onFinishClickListener1;

    }
    private OnFinishClickListener onFinishClickListener;
    public MapViewMessageListRVAdapter(TencentMap tencentMap1 ){
        tencentMap = tencentMap1;

    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<TecentMessageListBean.ListBean> list) {
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
    public void addData(int position, ArrayList<TecentMessageListBean.ListBean> list) {
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
        return new MapViewMessageListRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_mapview_message_rv_item, parent, false));
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
        @OnClick(R.id.lly_mapview_message_rv_item)
        public  void llyMapViewMessageRVItemOnclick(){
            String lat = dataList.get(pos).getLat();
            String lon = dataList.get(pos).getLon();
            LatLng latLng = null;
            if(lat != null && lon != null){
                latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
            }
            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            latLng, //中心点坐标，地图目标经纬度
                            16,  //目标缩放级别
                            0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                            0)); //目标旋转角 0~360° (正北方为0)
            tencentMap.animateCamera(cameraSigma);
            if(onFinishClickListener != null){
                onFinishClickListener.isQuery(lat,lon);
            }
        }

        @BindView(R.id.tv_mapview_message_rv_item_content)
        TextView tvMapViewMessageRVItemContent;
        @BindView(R.id.tv_mapview_message_rv_item_time)
        TextView tvMapViewMessageRVItemTime;
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String lat = dataList.get(position).getLat();
            String lon = dataList.get(position).getLon();
            String nick = dataList.get(position).getNick();
            String time = dataList.get(position).getTime();
            tvMapViewMessageRVItemContent.setText("您关注的"+nick+"在("+lat+","+lon+")摆下了摊位");
            tvMapViewMessageRVItemTime.setText(time);
        }
    }


}
