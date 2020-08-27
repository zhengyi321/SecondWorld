package com.et.secondworld.first.fragment.adapter;

import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetRecommendBean;
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
public class RecommendMoreEliteRVAdapter extends RecyclerView.Adapter<RecommendMoreEliteRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<GetRecommendBean.ListBean> dataList = new ArrayList<>();

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetRecommendBean.ListBean> list) {
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
    public void addData(int position, List<GetRecommendBean.ListBean> list) {
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
        return new RecommendMoreEliteRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_first_recommend_more_elite_rv_item, parent, false));
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
        @BindView(R.id.civ_first_recommend_more_elite_rv_item_head)
        ImageView civFirstRecommendMoreEliteRVItemHead;
        @BindView(R.id.tv_first_recommend_more_elite_rv_item_nick)
        TextView tvFirstRecommendMoreEliteRVItemNick;
        @BindView(R.id.tv_first_recommend_more_elite_rv_item_loc)
        TextView tvFirstRecommendMoreEliteRVItemLoc;
        @BindView(R.id.iv_first_recommend_more_elite_rv_item_img)
        ImageView ivFirstRecommendMoreEliteRVItemImg;
        @BindView(R.id.tv_first_recommend_more_elite_rv_item_title)
        TextView tvFirstRecommendMoreEliteRVItemTitle;
        @BindView(R.id.tv_first_recommend_more_elite_rv_item_repost)
        TextView tvFirstRecommendMoreEliteRVItemRepost;
        @BindView(R.id.tv_first_recommend_more_elite_rv_item_comments)
        TextView tvFirstRecommendMoreEliteRVItemComments;
        @BindView(R.id.tv_first_recommend_more_elite_rv_item_praise)
        TextView tvFirstRecommendMoreEliteRVItemPraise;
        @BindView(R.id.tv_first_recommend_more_elite_rv_item_browses)
        TextView tvFirstRecommendMoreEliteRVItemBrowses;
        private long clickTime = 0;

        @OnClick(R.id.lly_first_recommend_more_elite_rv_item)
        public void llyFirstRecommendMoreEliteRVItemOnclick(){
            String articleAccount = dataList.get(pos).getAccountid();
            String articleid = dataList.get(pos).getArticleid();
            String title = dataList.get(pos).getTitle();
            if(articleAccount == null){
                articleAccount = "";
            }
            if(articleid == null){
                articleid = "";
            }
            if(title == null){
                title = "";
            }
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

//            intent.putExtra("title",title);

                v.getContext().startActivity(intent);
            }
        }

        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            v = view;

        }



        void setData(Object data,int position) {
            pos = position;
            String logo = dataList.get(position).getLogo();
            String nick = dataList.get(position).getNick();
            String title = dataList.get(position).getTitle();
            String loc = dataList.get(position).getVillage();
            Object imgOne = dataList.get(position).getImageone();
            Object imgTwo = dataList.get(position).getImagetwo();
            Object imgThree = dataList.get(position).getImagethree();
            int repost = dataList.get(position).getRepost();
            int comments = dataList.get(position).getComment();
            int praise = dataList.get(position).getGood();
            int browses = dataList.get(position).getReaders();
            tvFirstRecommendMoreEliteRVItemRepost.setText(repost+"");
            tvFirstRecommendMoreEliteRVItemComments.setText(comments+"");
            tvFirstRecommendMoreEliteRVItemPraise.setText(praise+"");
            tvFirstRecommendMoreEliteRVItemBrowses.setText(browses+"");


            if(imgOne != null && !imgOne.toString().isEmpty()){
                ivFirstRecommendMoreEliteRVItemImg.setVisibility(View.VISIBLE);
                Glide.with(v.getContext()).load(imgOne)
                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                        .into(ivFirstRecommendMoreEliteRVItemImg);
            }else {
                if(imgTwo != null && !imgTwo.toString().isEmpty()){
                    ivFirstRecommendMoreEliteRVItemImg.setVisibility(View.VISIBLE);
                    Glide.with(v.getContext()).load(imgTwo)
                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                            .into(ivFirstRecommendMoreEliteRVItemImg);
                }else {
                    if(imgThree != null && !imgThree.toString().isEmpty()){
                        ivFirstRecommendMoreEliteRVItemImg.setVisibility(View.VISIBLE);
                        Glide.with(v.getContext()).load(imgThree)
                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstRecommendMoreEliteRVItemImg);
                    }else {
                        ivFirstRecommendMoreEliteRVItemImg.setVisibility(View.GONE);
                    }
                }
            }

            Glide.with(v.getContext()).load(logo)
                    .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                    .into(civFirstRecommendMoreEliteRVItemHead);
            tvFirstRecommendMoreEliteRVItemNick.setText(nick);
            tvFirstRecommendMoreEliteRVItemTitle.setText(title);
            tvFirstRecommendMoreEliteRVItemLoc.setText(loc);

        }
    }


}
