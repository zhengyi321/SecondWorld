package com.et.secondworld.mine.adapter;

import android.content.Intent;
import android.util.Log;
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
import com.et.secondworld.bean.GetBrowsHistoryBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumDetailTwoActivity;
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
public class MineFootMarkHaveSeenInvitionRVAdapter extends RecyclerView.Adapter<MineFootMarkHaveSeenInvitionRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetBrowsHistoryBean.ListBean> dataList = new ArrayList<>();

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetBrowsHistoryBean.ListBean> list) {
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
    public void addData(int position, List<GetBrowsHistoryBean.ListBean> list) {
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
        return new MineFootMarkHaveSeenInvitionRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_footmark_have_seen_invition_rv_item, parent, false));
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
        @BindView(R.id.tv_footmark_have_seen_invition_rv_item_time)
        TextView tvFootMarkHaveSeenInvitionRVItemTime;
        @BindView(R.id.lly_footmark_have_seen_invition_rv_item)
        LinearLayout llyFootMarkHaveSeenInvitionRVItem;
        private long clickTime = 0;
        @OnClick(R.id.lly_footmark_have_seen_invition_rv_item)
        public void llyFootMarkHaveSeenInvitionRVItemOnclick(){
//            Log.d("FootMarkHaveSeen",articleAccount);
//            Log.d("FootMarkHaveSeen",articleid);
//            Log.d("FootMarkHaveSeen",title);
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                if (modules.equals("M1") || modules.equals("M2")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);

                    intent.putExtra("title", title);
                    v.getContext().startActivity(intent);
                } else if (modules.equals("M3")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);

                    intent.putExtra("title", title);
                    v.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);

                    intent.putExtra("title", title);
                    v.getContext().startActivity(intent);
                }
            }
        }
        @BindView(R.id.tv_footmark_have_seen_invition_rv_item_nick)
        TextView tvFootMarkHaveSeenInvitionRVItemNick;
        @BindView(R.id.tv_footmark_have_seen_invition_rv_item_title)
        TextView tvFootMarkHaveSeenInvitionRVItemTitle;
        /*@BindView(R.id.tv_footmark_have_seen_invition_rv_item_good)
        TextView tvFootMarkHaveSeenInvitionRVItemGood;
        @BindView(R.id.tv_footmark_have_seen_invition_rv_item_comments)
        TextView tvFootMarkHaveSeenInvitionRVItemComments;*/
        @BindView(R.id.civ_footmark_have_seen_invition_rv_item_head)
        CircleImageView civFootMarkHaveSeenInvitionRVItemHead;

        @BindView(R.id.tv_footmark_have_seen_invition_rv_item_repost)
        TextView tvFootMarkHaveSeenInvitionRVItemRepost;
        @BindView(R.id.tv_footmark_have_seen_invition_rv_item_comments)
        TextView tvFootMarkHaveSeenInvitionRVItemComments;
        @BindView(R.id.tv_footmark_have_seen_invition_rv_item_praise)
        TextView tvFootMarkHaveSeenInvitionRVItemPraise;
        @BindView(R.id.tv_footmark_have_seen_invition_rv_item_browses)
        TextView tvFootMarkHaveSeenInvitionRVItemBrowses;
        private String articleid = "";
        private String articleAccount = "";
        private String title = "";
        private String modules = "";
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            v = view;

        }



        void setData(Object data,int position) {
            pos = position;
            String time = dataList.get(position).getTime();
            if(time == null){
                time = "";
            }
//            time = TimeUtil.getMessageDate(time);
            tvFootMarkHaveSeenInvitionRVItemTime.setText(time);
            String nick = dataList.get(position).getNick();
            String head = dataList.get(position).getHead();
            int repost = dataList.get(position).getRepost();
//            int comments = dataList.get(position).getComments();
//            int good = dataList.get(position).getGood();
            int browses = dataList.get(position).getReaders();
            title = dataList.get(position).getTitle();
            articleAccount = dataList.get(position).getAccountid();
            modules = dataList.get(position).getModules();
            articleid = dataList.get(position).getArticleid();
            int good = dataList.get(position).getGood();
            int comments = dataList.get(position).getComments();
            tvFootMarkHaveSeenInvitionRVItemRepost.setText(repost+"");
            tvFootMarkHaveSeenInvitionRVItemBrowses.setText(browses+"");
            tvFootMarkHaveSeenInvitionRVItemComments.setText(comments+"");
            tvFootMarkHaveSeenInvitionRVItemPraise.setText(good+"");
            if(nick == null){
                nick = "";
            }
            if(title == null){
                title = "";
            }
            if(modules == null){
                modules = "";
            }
            if(articleid == null){
                articleid = "";
            }
            Log.d("footmark11",head+","+nick);
            tvFootMarkHaveSeenInvitionRVItemNick.setText(nick);
            if(head != null){
                Glide.with(v.getContext()).load(head)
                        .apply(new RequestOptions().fallback(R.mipmap.imghead).error(R.mipmap.imghead).circleCrop())
                        .into(civFootMarkHaveSeenInvitionRVItemHead);
//                ImageLoader.getInstance().displayImage(head,civMineFansRVItemHead, ImageLoaderUtils.options);
            }
//            ImageLoader.getInstance().displayImage(head,civFootMarkHaveSeenInvitionRVItemHead, ImageLoaderUtils.options);
            tvFootMarkHaveSeenInvitionRVItemTitle.setText(title);
//            tvFootMarkHaveSeenInvitionRVItemGood.setText(good+"");
//            tvFootMarkHaveSeenInvitionRVItemComments.setText(comments+"");
        }
    }


}
