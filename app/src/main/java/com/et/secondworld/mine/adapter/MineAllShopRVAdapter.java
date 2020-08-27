package com.et.secondworld.mine.adapter;

import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.R;
import com.et.secondworld.VisitOthersShopActivity;
import com.et.secondworld.bean.AddCancelZanBean;
import com.et.secondworld.bean.GetStreetShopListBean;
import com.et.secondworld.network.PraiseArticleNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imageview.CircleImageView;
import com.et.secondworld.widget.imageview.RoundImageView;

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
public class MineAllShopRVAdapter extends RecyclerView.Adapter<MineAllShopRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetStreetShopListBean.ListBean> dataList = new ArrayList<>();

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetStreetShopListBean.ListBean> list) {
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
    public void addData(int position, List<GetStreetShopListBean.ListBean> list) {
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
        return new MineAllShopRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mine_all_shop_rv_item, parent, false));
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
        private int pos = -1;
        @BindView(R.id.rly_mine_all_shop_rv_item)
        RelativeLayout rlyMineAllShopRVItem;
        private long clickTime = 0;
        @OnClick(R.id.rly_mine_all_shop_rv_item)
        public void rlyMineAllShopRVItemOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                Intent intent = new Intent(v.getContext(), VisitOthersShopActivity.class);
                intent.putExtra("shopid", shopid);
                v.getContext().startActivity(intent);
            }
        }
        @BindView(R.id.lly_mine_all_shop_rv_item)
        LinearLayout llyMineAllShopRVItem;
        @BindView(R.id.riv_mine_all_shop_rv_item_image)
        RoundImageView rivMineAllShopRVItemImage;
        @BindView(R.id.iv_mine_all_shop_rv_item_praise)
        ImageView ivMineAllShopRVItemPraise;
        @BindView(R.id.rly_mine_all_shop_rv_item_praise)
        RelativeLayout rlyMineAllShopRVItemPraise;
        @OnClick(R.id.rly_mine_all_shop_rv_item_praise)
        public void rlyMineAllShopRVItemPraiseOnclick(){
            praiseArticle();
        }
        @BindView(R.id.tv_mine_all_shop_rv_item_nick)
        TextView tvMineAllShopRVItemNick;
        @BindView(R.id.civ_mine_all_shop_rv_item_head)
        ImageView civMineAllShopRVItemHead;
        @BindView(R.id.tv_mine_all_shop_rv_item_title)
        TextView tvMineAllShopRVItemTitle;
        private String articleid="";
        private String shopid="";
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,v);

        }
        private void praiseArticle(){
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            if(account == null || account.isEmpty()){
                return;
            }
            PraiseArticleNetWork praiseArticleNetWork = new PraiseArticleNetWork();
            Map<String,Object> map = new HashMap<>();
            map.put("articleid",articleid);
            map.put("account",account);
            praiseArticleNetWork.addCancelZanArticleToNet(map, new Observer<AddCancelZanBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(AddCancelZanBean addCancelZanBean) {
                    if(addCancelZanBean.getIssuccess().equals("1")){
                        ivMineAllShopRVItemPraise.setBackgroundResource(R.mipmap.redgood);

                    }
                    if(addCancelZanBean.getIssuccess().equals("2")){
                        ivMineAllShopRVItemPraise.setBackgroundResource(R.mipmap.thumbs);
//                        zanNums--;
//                        tvForumDetailTwoZan.setText(zanNums+"");
                    }
                }
            });
        }


        void setData(Object data,int position) {
            pos = position;
            String name = dataList.get(position).getName();
            String logo = dataList.get(position).getLogo();
            String img = dataList.get(position).getImg();
            String title = dataList.get(position).getTitle();
            articleid = dataList.get(position).getArticleid();
            shopid = dataList.get(position).getShopid();
            int praise = dataList.get(position).getIspraised();
            tvMineAllShopRVItemNick.setText(name);
            tvMineAllShopRVItemTitle.setText(title);
            Glide.with(v.getContext()).load(logo).apply(new RequestOptions().circleCrop().fallback(R.mipmap.imghead)
                    .error(R.mipmap.imghead)).into(civMineAllShopRVItemHead);
            Glide.with(v.getContext()).load(img).into(rivMineAllShopRVItemImage);
            if(praise > 0){
                ivMineAllShopRVItemPraise.setBackgroundResource(R.mipmap.redgood);
            }else {
                ivMineAllShopRVItemPraise.setBackgroundResource(R.mipmap.thumbs);
            }
            if(position % 2 == 0) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 224,
//                        v.getContext().getResources().getDisplayMetrics()));
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)rlyFirstRecommendMore.getLayoutParams();

                layoutParams.setMargins( (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                        v.getContext().getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                        v.getContext().getResources().getDisplayMetrics()),0,0);
                rlyMineAllShopRVItem.setLayoutParams(layoutParams);
            }
            if(position % 2 == 1) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                        v.getContext().getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                        v.getContext().getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                        v.getContext().getResources().getDisplayMetrics()),0);
//                int width = layoutParams.width;
//                layoutParams.height = (int)(1.28*width);
                rlyMineAllShopRVItem.setLayoutParams(layoutParams);
            }





            /*if(position % 4 == 1 || position % 4 == 2){
//                adjustItem();
            }*/
        }

        private void adjustItem(){
            ViewGroup.LayoutParams layoutParams =   llyMineAllShopRVItem.getLayoutParams();
            ViewGroup.LayoutParams layoutParams1 = rivMineAllShopRVItemImage.getLayoutParams();
            layoutParams1.height = 480;
            layoutParams.height = 920;
//            Toast.makeText(v.getContext(),"height:"+layoutParams1.height,Toast.LENGTH_LONG).show();
            llyMineAllShopRVItem.setLayoutParams(layoutParams);
            rivMineAllShopRVItemImage.setLayoutParams(layoutParams1);
        }
    }


}
