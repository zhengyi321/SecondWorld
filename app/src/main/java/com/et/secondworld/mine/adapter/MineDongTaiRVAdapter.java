package com.et.secondworld.mine.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetDongTaiBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class MineDongTaiRVAdapter extends RecyclerView.Adapter<MineDongTaiRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetDongTaiBean.ListBean> dataList ;
    private Boolean isNoMoreData = false;
    public MineDongTaiRVAdapter(List<GetDongTaiBean.ListBean> dataList1){
        dataList = dataList1;
    }


    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetDongTaiBean.ListBean> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void isNoMoreData(Boolean isNoMoreData){
        this.isNoMoreData = isNoMoreData;
    }
    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, List<GetDongTaiBean.ListBean> list) {
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
        return new MineDongTaiRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mine_dongtai_rv_item, parent, false));
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

        @BindView(R.id.iv_mine_dongtai_rv_item_head)
        ImageView ivMineDongTaiRVItemHead;
        @BindView(R.id.tv_mine_dongtai_rv_item_nick)
        TextView tvMineDongTaiRVItemNick;
        @BindView(R.id.iv_mine_dongtai_rv_item)
        ImageView ivMineDongTaiRVItem;
        @BindView(R.id.tv_mine_dongtai_rv_item_repost)
        TextView tvMineDongTaiRVItemRepost;
        @BindView(R.id.tv_mine_dongtai_rv_item_comments)
        TextView tvMineDongTaiRVItemComments;
        @BindView(R.id.tv_mine_dongtai_rv_item_praise)
        TextView tvMineDongTaiRVItemPraise;
        @BindView(R.id.iv_mine_dongtai_rv_item_praise)
        ImageView ivMineDongTaiRVItemPraise;
        @BindView(R.id.tv_mine_dongtai_rv_item_browses)
        TextView tvMineDongTaiRVItemBrowses;
        @BindView(R.id.tv_mine_dongtai_rv_item_title)
        TextView tvMineDongTaiRVItemTitle;
        @BindView(R.id.tv_mine_dongtai_rv_item_content)
        TextView tvMineDongTaiRVItemContent;
        @BindView(R.id.iv_mine_dongtai_rv_item_title_logo)
        ImageView ivMineDongTaiRVItemTitleLogo;

        @OnClick(R.id.rly_mine_dongtai_rv_item)
        public void rlyMineDongTaiRVItemOnclick(){
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String articleAccount = xcCacheManager.readCache(xcCacheSaveName.account);
            String articleid = dataList.get(pos).getArticleid();
            String title = dataList.get(pos).getTitle();
            String model = dataList.get(pos).getModules();
            if(articleAccount == null){
                articleAccount = "";
            }
            if(articleid == null){
                articleid = "";
            }
            if(title == null){
                title = "";
            }

            if(model == null){
                model = "";
            }
            if(model.equals("M4")){
                Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
                intent.putExtra("articleAccount",articleAccount);
                intent.putExtra("articleid",articleid);

                intent.putExtra("title",title);

                v.getContext().startActivity(intent);
            }
            else if(model.equals("M3")) {
                Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);
                v.getContext().startActivity(intent);
            }else if(model.equals("M1")||model.equals("M2")){
                Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);
                v.getContext().startActivity(intent);
            }
        }
        /*@BindView(R.id.lly_mine_dongtai_rv_item)
        LinearLayout llyMineDongTaiRVItem;
        @OnClick(R.id.lly_mine_dongtai_rv_item)
        public void llyMineDongTaiRVItemOnclick(){
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String articleAccount = xcCacheManager.readCache(xcCacheSaveName.account);
            String articleid = dataList.get(pos).getArticleid();
            String title = dataList.get(pos).getTitle();
            String model = dataList.get(pos).getModules();
            if(articleAccount == null){
                articleAccount = "";
            }
            if(articleid == null){
                articleid = "";
            }
            if(title == null){
                title = "";
            }

            if(model == null){
                model = "";
            }
            if(model.equals("M4")){
                Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
                intent.putExtra("articleAccount",articleAccount);
                intent.putExtra("articleid",articleid);

                intent.putExtra("title",title);

                v.getContext().startActivity(intent);
            }
            else if(model.equals("M3")) {
                Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);
                v.getContext().startActivity(intent);
            }else if(model.equals("M1")||model.equals("M2")){
                Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);
                v.getContext().startActivity(intent);
            }
        }*/
       /* @BindView(R.id.tv_mine_dongtai_rv_item_title)
        TextView tvMineDongTaiRVItemTitle;
        @BindView(R.id.tv_mine_dongtai_rv_item_content)
        TextView tvMineDongTaiRVItemContent;*/
//        @BindView(R.id.tv_mine_dongtai_rv_item_repost)
//        TextView tvMineDongTaiRVItemRepost;
//        @BindView(R.id.tv_mine_dongtai_rv_item_comment)
//        TextView tvMineDongTaiRVItemComment;
//        @BindView(R.id.tv_mine_dongtai_rv_item_good)
//        TextView tvMineDongTaiRVItemGood;
//        @BindView(R.id.tv_mine_dongtai_rv_item_readers)
//        TextView tvMineDongTaiRVItemReaders;
      /*  @BindView(R.id.lly_splitview)
        LinearLayout llySplitView;*/
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
           /* if(isNoMoreData == true){
                if(position == dataList.size() -1){
                    llyMineDongTaiRVItem.setVisibility(View.GONE);
                    llySplitView.setVisibility(View.VISIBLE);
                }
//                return;

            }else {
                llyMineDongTaiRVItem.setVisibility(View.VISIBLE);
                llySplitView.setVisibility(View.GONE);
            }*/
            pos = position;
            String title = dataList.get(position).getTitle();
            String content = dataList.get(position).getContent();
            String nick = dataList.get(position).getNick();
            String head = dataList.get(position).getHead();
            String imgTwo = dataList.get(position).getImgtwo();
            String imgOne = dataList.get(position).getImgone();
            String imgThree = dataList.get(position).getImgthree();
            int repost = dataList.get(position).getRepost();
            int comment = dataList.get(position).getComment();
            int readers = dataList.get(position).getReaders();
            int good = dataList.get(position).getGood();
            int ispraised = dataList.get(position).getIspraised();
            tvMineDongTaiRVItemBrowses.setText(readers+"");
            tvMineDongTaiRVItemComments.setText(comment+"");
            tvMineDongTaiRVItemRepost.setText(repost+"");
            tvMineDongTaiRVItemPraise.setText(good+"");
            String types = dataList.get(position).getTypes();
            int isSoluation = dataList.get(position).getIssoluation();
            if(types != null) {
                if (types.equals("help") || types.equals("data")) {
                    ivMineDongTaiRVItemTitleLogo.setVisibility(View.VISIBLE);
                    if (isSoluation == 1) {
                        ivMineDongTaiRVItemTitleLogo.setBackgroundResource(R.mipmap.soluationed);
                    } else {
                        ivMineDongTaiRVItemTitleLogo.setBackgroundResource(R.mipmap.help);
                    }
                } else {
                    ivMineDongTaiRVItemTitleLogo.setVisibility(View.GONE);
                }
            }
            if(ispraised == 1){
                ivMineDongTaiRVItemPraise.setBackgroundResource(R.mipmap.praised);
            }else {
                ivMineDongTaiRVItemPraise.setBackgroundResource(R.mipmap.praiseicon);
            }
            ivMineDongTaiRVItem.setVisibility(View.VISIBLE);
            if(imgOne != null && !imgOne.isEmpty()){
                Glide.with(v.getContext()).load(imgOne.toString())
                        .apply(new RequestOptions().fallback(R.mipmap.imghead))
                        .into(ivMineDongTaiRVItem);
            }else {
                if(imgTwo != null && !imgTwo.isEmpty()){
                    Glide.with(v.getContext()).load(imgTwo.toString())
                            .apply(new RequestOptions().fallback(R.mipmap.imghead))
                            .into(ivMineDongTaiRVItem);
                }else {
                    if(imgTwo != null && !imgTwo.isEmpty()) {
                        Glide.with(v.getContext()).load(imgThree.toString())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                .into(ivMineDongTaiRVItem);
                    }else {
                        ivMineDongTaiRVItem.setVisibility(View.GONE);
                    }
                }
            }
            Glide.with(v.getContext()).load(head)
                    .apply(new RequestOptions().fallback(R.mipmap.imghead).error(R.mipmap.imghead).circleCrop())
                    .into(ivMineDongTaiRVItemHead);
            tvMineDongTaiRVItemTitle.setText(title);
            tvMineDongTaiRVItemContent.setText(content);
            tvMineDongTaiRVItemNick.setText(nick);
//            if(title != null){
//                tvMineDongTaiRVItemTitle.setText(title);
//            }
//            if(content != null){
//                if(content.length() > 70){
//                    content = content.substring(0,70);
//                }
//                tvMineDongTaiRVItemContent.setText(content);
//            }
//            tvMineDongTaiRVItemRepost.setText(""+repost);
//            tvMineDongTaiRVItemComment.setText(""+comment);
//            tvMineDongTaiRVItemReaders.setText(""+readers);
//            tvMineDongTaiRVItemGood.setText(""+good);

        }
    }


}
