package com.et.secondworld.forum.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.Poi;
import com.et.secondworld.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class ForumLocRVAdapter extends RecyclerView.Adapter<ForumLocRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<Poi> dataList ;
//    private Map<Object,RObject> rObjectMap = new HashMap<>(); PoiInfo
    public ForumLocRVAdapter(List<Poi> dataList1){
        dataList = dataList1;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<Poi> list) {
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
    public void addData(int position, List<Poi> list) {
        dataList.addAll(position,list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }
/*
    public ArrayList<RObject> getrObjectList(){
        Collection values = rObjectMap.values();
        ArrayList<RObject> rObjectList = new ArrayList<>();
        for (Object object : values) {
            rObjectList.add((RObject) object);
        }
        return rObjectList;
    }*/

    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForumLocRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_forum_loc_rv_item, parent, false));
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
        @BindView(R.id.tv_forum_loc_rv_item_title)
        TextView tvForumLocRVItemTitle;
        @BindView(R.id.tv_forum_loc_rv_item_address)
        TextView tvForumLocRVItemAddress;
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String name = dataList.get(position).getName();
            String addr = dataList.get(position).getAddr();

            tvForumLocRVItemTitle.setText(name);
            tvForumLocRVItemAddress.setText(addr);


        }
    }


}
