package com.et.secondworld.first.fragment.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
import com.et.secondworld.bean.GetRecommendBean;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.utils.WindowUtils;

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
public class FirstReCommendMoreRVAdapter extends RecyclerView.Adapter<FirstReCommendMoreRVAdapter.OneViewHolder> {
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
        return new FirstReCommendMoreRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_first_recommend_more_rv_item, parent, false));
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
        private int pos = 0;
        @BindView(R.id.iv_first_recommend_more_img)
        ImageView ivFirstRecommendMoreImg;
        @BindView(R.id.iv_first_recommend_more_elite)
        ImageView ivFirstRecommendMoreElite;
        @BindView(R.id.tv_first_recommend_more_title)
        TextView tvFirstRecommendMoreTitle;
        @BindView(R.id.civ_first_recommend_more_head)
        ImageView civFirstRecommendMoreHead;
        @BindView(R.id.tv_first_recommend_more_account)
        TextView tvFirstRecommendMoreAccount;
        @BindView(R.id.tv_first_recommend_more_loc)
        TextView tvFirstRecommendMoreLoc;
        @BindView(R.id.rly_first_recommend_more)
        RelativeLayout rlyFirstRecommendMore;
        @BindView(R.id.iv_first_recommend_more_title_logo)
        ImageView ivFirstRecommendMoreTitleLogo;
        private long clickTime = 0;
        @OnClick(R.id.rly_first_recommend_more)
        public void rlyFirstRecommendMoreOnclick(){


            if(System.currentTimeMillis() - clickTime > 2000) {

                    clickTime = System.currentTimeMillis();
                String articleAccount = dataList.get(pos).getAccountid();
                String articleid = dataList.get(pos).getArticleid();
                String title = dataList.get(pos).getTitle();
                if (articleAccount == null) {
                    articleAccount = "";
                }
                if (articleid == null) {
                    articleid = "";
                }
                if (title == null) {
                    title = "";
                }
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
            v=view;
            ButterKnife.bind(this,v);

        }



        void setData(Object data,int position) {
            pos = position;
            String logo = dataList.get(position).getLogo();
            String nick = dataList.get(position).getNick();
            String title = dataList.get(position).getTitle();
            String loc = dataList.get(position).getLoc();
            Object imgOne = dataList.get(position).getImageone();
            Object imgTwo = dataList.get(position).getImagetwo();
            Object imgThree = dataList.get(position).getImagethree();
            String types  = dataList.get(position).getTypes();
            String isSoluation =dataList.get(position).getIssoluation();
            if(types != null) {
                if (types.equals("help") || types.equals("data")) {
                    ivFirstRecommendMoreTitleLogo.setVisibility(View.VISIBLE);
                    if (isSoluation != null && isSoluation.equals("1")) {
                        ivFirstRecommendMoreTitleLogo.setBackgroundResource(R.mipmap.soluationed);
                    } else {
                        ivFirstRecommendMoreTitleLogo.setBackgroundResource(R.mipmap.help);
                    }
                } else {
                    ivFirstRecommendMoreTitleLogo.setVisibility(View.GONE);
                }
            }else {
                ivFirstRecommendMoreTitleLogo.setVisibility(View.GONE);
            }
            if(position % 2 == 0) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 224,
//                        v.getContext().getResources().getDisplayMetrics()));
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)rlyFirstRecommendMore.getLayoutParams();

                layoutParams.setMargins( (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                        v.getContext().getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                        v.getContext().getResources().getDisplayMetrics()),0,0);
                rlyFirstRecommendMore.setLayoutParams(layoutParams);
            }
            if(position % 2 == 1) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                        v.getContext().getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                        v.getContext().getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                        v.getContext().getResources().getDisplayMetrics()),0);
//                int width = layoutParams.width;
//                layoutParams.height = (int)(1.28*width);
                rlyFirstRecommendMore.setLayoutParams(layoutParams);
            }
            ivFirstRecommendMoreElite.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams1 = ivFirstRecommendMoreImg.getLayoutParams();
//            int width = layoutParams1.width;
            WindowUtils windowUtils = new WindowUtils((Activity) v.getContext());
//            Log.d("width11",width+"");
            layoutParams1.height = (int)(1.03*windowUtils.getWindowWidth()/2);
            ivFirstRecommendMoreImg.setLayoutParams(layoutParams1);
            ivFirstRecommendMoreImg.setVisibility(View.VISIBLE);
            if(imgOne != null && !imgOne.toString().isEmpty()){
                Glide.with(v.getContext()).load(imgOne)
                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                        .into(ivFirstRecommendMoreImg);
            }else {
                if(imgTwo != null && !imgTwo.toString().isEmpty()){
                    Glide.with(v.getContext()).load(imgTwo)
                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                            .into(ivFirstRecommendMoreImg);
                }else {
                    if(imgThree != null && !imgThree.toString().isEmpty()){
                        Glide.with(v.getContext()).load(imgThree)
                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstRecommendMoreImg);
                    }else {
                        ivFirstRecommendMoreImg.setVisibility(View.INVISIBLE);
                    }
                }
            }

            Glide.with(v.getContext()).load(logo)
                    .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                    .into(civFirstRecommendMoreHead);
            tvFirstRecommendMoreAccount.setText(nick);
            tvFirstRecommendMoreTitle.setText(title);
            tvFirstRecommendMoreLoc.setText(loc);
        }
    }


}
