package com.et.secondworld.mine.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.R;
import com.et.secondworld.VisitOthersActivity;
import com.et.secondworld.bean.GetVisitorBean;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.imageview.CircleImageView;

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
public class MineFootMarkRecentVisitorRVAdapter extends RecyclerView.Adapter<MineFootMarkRecentVisitorRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetVisitorBean.ListBean> dataList = new ArrayList<>();

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetVisitorBean.ListBean> list) {
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
    public void addData(int position, List<GetVisitorBean.ListBean> list) {
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
        return new MineFootMarkRecentVisitorRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_footmark_recent_visitor_rv_item, parent, false));
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
        @BindView(R.id.lly_footmark_recent_visitor_rv_item)
        LinearLayout llyFootMarkRecentVisitorRVItem;
        private long clickTime = 0;
        @OnClick(R.id.lly_footmark_recent_visitor_rv_item)
        public void llyFootMarkRecentVisitorRVItemOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                Intent intent = new Intent(v.getContext(), VisitOthersActivity.class);
                intent.putExtra("articleaccount", visitorid);
                v.getContext().startActivity(intent);
            }
        }
        @BindView(R.id.civ_footmark_recent_visitor_rv_item_head)
        CircleImageView civFootMarkRecentVisitorRVItemHead;
        @BindView(R.id.tv_footmark_recent_visitor_rv_item_nick)
        TextView tvFootMarkRecentVisitorRVItemNick;
        @BindView(R.id.tv_footmark_recent_visitor_rv_item_time)
        TextView tvFootMarkRecentVisitorRVItemTime;
        @BindView(R.id.tv_footmark_recent_visitor_rv_item_personnalnote)
        TextView tvFootMarkRecentVisitorRVItemPersonnalNote;
        private String visitorid = "";
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String head = dataList.get(position).getHead();
            if(head != null){
                Glide.with(v.getContext()).load(head)
                        .apply(new RequestOptions().fallback(R.mipmap.imghead).error(R.mipmap.imghead).circleCrop())
                        .into(civFootMarkRecentVisitorRVItemHead);
//                ImageLoader.getInstance().displayImage(head,civMineFansRVItemHead, ImageLoaderUtils.options);
            }
//            ImageLoader.getInstance().displayImage(head,civFootMarkRecentVisitorRVItemHead, ImageLoaderUtils.options);
            String nick = dataList.get(position).getNick();
            tvFootMarkRecentVisitorRVItemNick.setText(nick);
            String time = dataList.get(position).getTime();
            tvFootMarkRecentVisitorRVItemTime.setText(time);
            String personnalnote = dataList.get(position).getPersonnalnote();
            tvFootMarkRecentVisitorRVItemPersonnalNote.setText(personnalnote);
            visitorid = dataList.get(position).getVisitorid();

        }
    }


}
