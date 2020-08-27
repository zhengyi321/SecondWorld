package com.et.secondworld.forum.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetGuanZhuBean;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.edittext.RObject;
import com.et.secondworld.widget.imageview.CircleImageView;

import java.util.ArrayList;
import java.util.Collection;
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
public class ForumAtRVAdapter extends RecyclerView.Adapter<ForumAtRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetGuanZhuBean.ListBean> dataList ;
    private Map<Object,RObject> rObjectMap = new HashMap<>();
    public ForumAtRVAdapter(List<GetGuanZhuBean.ListBean> dataList1){
        dataList = dataList1;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetGuanZhuBean.ListBean> list) {
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
    public void addData(int position, List<GetGuanZhuBean.ListBean> list) {
        dataList.addAll(position,list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<RObject> getrObjectList(){
        Collection values = rObjectMap.values();
        ArrayList<RObject> rObjectList = new ArrayList<>();
        for (Object object : values) {
            rObjectList.add((RObject) object);
        }
        return rObjectList;
    }

    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForumAtRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_forum_at_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OneViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
        holder.cbForumAtRVItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked == true){
                    RObject rObject = new RObject();
                    rObject.setId(dataList.get(position).getAccountid());
                    rObject.setObjectText(""+dataList.get(position).getNick());
                    rObjectMap.put(position,rObject);

                }else {
                    rObjectMap.remove(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }



    public class OneViewHolder extends RecyclerView.ViewHolder {
        //        private ImageView ivImage;
        public View v;
        int pos = 0;
        int isChecked = 0;
        @BindView(R.id.tv_forum_at_rv_item_nick)
        TextView tvForumAtRVItemNick;
        @BindView(R.id.cb_forum_at_rv_item)
        CheckBox cbForumAtRVItem;
        @BindView(R.id.lly_forum_at_rv_item)
        LinearLayout llyForumAtRVItem;
        @OnClick(R.id.lly_forum_at_rv_item)
        public void llyForumAtRVItemOnclick(){
            if(isChecked == 0){
                cbForumAtRVItem.setChecked(true);
                isChecked = 1;
            }else {

                cbForumAtRVItem.setChecked(false);
                isChecked = 0;
            }
        }
        @BindView(R.id.tv_forum_at_rv_item_personnalnote)
        TextView tvForumAtRVItemPersonnalnote;
        @BindView(R.id.tv_mine_guanzhu_rv_item_guanzhu)
        TextView tvForumAtRVItemGuanZhu;
        @BindView(R.id.civ_forum_at_rv_item_head)
        CircleImageView civForumAtRVItemHead;
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String nick = dataList.get(position).getNick();
            String personnalNote = dataList.get(position).getPersonalnote();
            String head = dataList.get(position).getHead();
            int isFriends = dataList.get(position).getIsfriends();
            int isFans = dataList.get(pos).getIsfans();
            if(nick != null){
                tvForumAtRVItemNick.setText(nick);
//                Log.d("guanzhubeanposition",position+"");
            }else {
                tvForumAtRVItemNick.setText("");
            }
            if(personnalNote != null){
                tvForumAtRVItemPersonnalnote.setText(personnalNote);
//                Log.d("guanzhubean",personnalNote);
            }else {
                tvForumAtRVItemPersonnalnote.setText("");
            }
//            Log.d("guanzhubean",nick);
//            Log.d("guanzhubean",personnalNote);
            if(head != null){
                ImageLoader.getInstance().displayImage(head,civForumAtRVItemHead, ImageLoaderUtils.options);
            }else {
                ImageLoader.getInstance().displayImage("",civForumAtRVItemHead, ImageLoaderUtils.options);
            }



        }
    }


}
