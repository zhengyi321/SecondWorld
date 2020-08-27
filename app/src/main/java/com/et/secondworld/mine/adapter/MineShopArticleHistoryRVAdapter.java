package com.et.secondworld.mine.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetMyShopArticleHistoryListBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumDetailTwoActivity;

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
public class MineShopArticleHistoryRVAdapter extends RecyclerView.Adapter<MineShopArticleHistoryRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<GetMyShopArticleHistoryListBean.ListBean> dataList = new ArrayList<>();

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetMyShopArticleHistoryListBean.ListBean> list) {
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
    public void addData(int position, List<GetMyShopArticleHistoryListBean.ListBean> list) {
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
        return new MineShopArticleHistoryRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mine_my_shop_article_history_rv_item, parent, false));
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
        @BindView(R.id.tv_mine_my_shop_article_history_rv_item_title)
        TextView tvMineMyShopArticleHistoryRVItemTitle;
        @BindView(R.id.iv_mine_my_shop_article_history_rv_item_img)
        ImageView ivMineMyShopArticleHistoryRVItemImg;
        @BindView(R.id.tv_mine_my_shop_article_history_rv_item_loc)
        TextView tvMineMyShopArticleHistoryRVItemLoc;
        @BindView(R.id.tv_mine_my_shop_article_history_rv_item_nick)
        TextView tvMineMyShopArticleHistoryRVItemNick;
        @BindView(R.id.civ_mine_my_shop_article_history_rv_item_head)
        ImageView ivMineMyShopArticleHistoryRVItemHead;
        @BindView(R.id.tv_mine_my_shop_article_history_rv_item_repost)
        TextView tvMineMyShopArticleHistoryRVItemRepost;
        @BindView(R.id.tv_mine_my_shop_article_history_rv_item_comment)
        TextView tvMineMyShopArticleHistoryRVItemComment;
        @BindView(R.id.tv_mine_my_shop_article_history_rv_item_praise)
        TextView tvMineMyShopArticleHistoryRVItemPraise;
        @BindView(R.id.tv_mine_my_shop_article_history_rv_item_browses)
        TextView tvMineMyShopArticleHistoryRVItemBrowses;

        private long clickTime = 0;
        @OnClick(R.id.lly_mine_my_shop_article_history_rv_item)
        public void llyMineMyShopArticleHistoryRVItemOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                String articleid = dataList.get(pos).getArticleid();
                String title = dataList.get(pos).getTitle();
                String model = dataList.get(pos).getModules();
                String articleAccount = dataList.get(pos).getAccountid();
                if (articleAccount == null) {
                    articleAccount = "";
                }
                if (articleid == null) {
                    articleid = "";
                }
                if (title == null) {
                    title = "";
                }

                if (model == null) {
                    model = "";
                }
                if (model.equals("M4")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);

                    intent.putExtra("title", title);

                    v.getContext().startActivity(intent);
                } else if (model.equals("M3")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);

                    intent.putExtra("title", title);
                    v.getContext().startActivity(intent);
                } else if (model.equals("M1") || model.equals("M2")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);

                    intent.putExtra("title", title);
                    v.getContext().startActivity(intent);
                }
            }
        }
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String title = dataList.get(position).getTitle();
            String nick = dataList.get(position).getNick();
            String head = dataList.get(position).getLogo();
            String loc = dataList.get(position).getLoc();
            Object imgOne = dataList.get(position).getImageone();
            Object imgTwo = dataList.get(position).getImagetwo();
            Object imgThree = dataList.get(position).getImagethree();
            int repost = dataList.get(position).getRepost();
            int comments = dataList.get(position).getComment();
            int good = dataList.get(position).getGood();
            int browses = dataList.get(position).getReaders();
            tvMineMyShopArticleHistoryRVItemRepost.setText(repost+"");
            tvMineMyShopArticleHistoryRVItemComment.setText(comments+"");
            tvMineMyShopArticleHistoryRVItemPraise.setText(good+"");
            tvMineMyShopArticleHistoryRVItemBrowses.setText(browses+"");
            tvMineMyShopArticleHistoryRVItemNick.setText(nick);
            tvMineMyShopArticleHistoryRVItemTitle.setText(title);
            tvMineMyShopArticleHistoryRVItemLoc.setText(loc);
            if(imgOne != null && !imgOne.toString().isEmpty()){
                Glide.with(v.getContext()).load(imgOne.toString())
                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                        .into(ivMineMyShopArticleHistoryRVItemImg);
            }else {
                if(imgTwo != null && !imgTwo.toString().isEmpty()){
                    Glide.with(v.getContext()).load(imgTwo.toString())
                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                            .into(ivMineMyShopArticleHistoryRVItemImg);
                }else {
                    if(imgTwo != null && !imgTwo.toString().isEmpty()) {
                        Glide.with(v.getContext()).load(imgThree.toString())
                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivMineMyShopArticleHistoryRVItemImg);
                    }else {
                        ivMineMyShopArticleHistoryRVItemImg.setVisibility(View.GONE);
                    }
                }
            }
            Glide.with(v.getContext()).load(head)
                    .apply(new RequestOptions().fallback(R.mipmap.forumad3).circleCrop())
                    .into(ivMineMyShopArticleHistoryRVItemHead);


        }
    }


}
