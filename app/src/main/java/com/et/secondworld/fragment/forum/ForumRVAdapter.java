package com.et.secondworld.fragment.forum;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.LoginActivity;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetForumBean;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imageview.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class ForumRVAdapter extends RecyclerView.Adapter<ForumRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetForumBean.ListBean> dataList ;
    private Boolean isNoMoreData = false;
    private LinearLayout llyForumMark;
    private String managetype = "";
    public ForumRVAdapter(List<GetForumBean.ListBean> dataList1,LinearLayout llyForumMark){
        dataList = dataList1;
        this.llyForumMark = llyForumMark;
    }
    public void setManageType(String manageType){
        this.managetype = manageType;
    }
    public void isNoMoreData(Boolean isNoMoreData){
        this.isNoMoreData = isNoMoreData;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetForumBean.ListBean> list) {
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
    public void addData(int position, List<GetForumBean.ListBean> list) {
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
        return new ForumRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_forum_rv_item, parent, false));
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
        int pos = -1;
        @BindView(R.id.tv_forum_rv_item_title)
        TextView tvForumRVItemTitle;
        @BindView(R.id.tv_forum_rv_item_title2)
        TextView tvForumRVItemTitle2;
        @BindView(R.id.tv_forum_rv_item_title3)
        TextView tvForumRVItemTitle3;
        @BindView(R.id.tv_forum_rv_item_content3)
        TextView tvForumRVItemContent3;


        @BindView(R.id.tv_forum_rv_item_good)
        TextView tvForumRVItemGood;
//        @BindView(R.id.tv_forum_rv_item_comments)
//        TextView tvForumRVItemComments;
        @BindView(R.id.tv_forum_rv_item_comments2)
        TextView tvForumRVItemComments2;
//        @BindView(R.id.tv_forum_rv_item_comments3)
//        TextView tvForumRVItemComments3;
        @BindView(R.id.iv_forum_rv_item_img)
        ImageView ivForumRVItemImg;
        @BindView(R.id.iv_forum_rv_item_img2_one)
        ImageView ivForumRVItemImg2One;
        @BindView(R.id.iv_forum_rv_item_img2_two)
        ImageView ivForumRVItemImg2Two;
        @BindView(R.id.iv_forum_rv_item_img2_three)
        ImageView ivForumRVItemImg2Three;

        @BindView(R.id.civ_forum_rv_item_head1)
        CircleImageView civForumRVItemHead1;
        @BindView(R.id.civ_forum_rv_item_head2)
        CircleImageView civForumRVItemHead2;
        @BindView(R.id.civ_forum_rv_item_head3)
        CircleImageView civForumRVItemHead3;

        @BindView(R.id.tv_forum_rv_item_nick1)
        TextView tvForumRVItemNick1;
        @BindView(R.id.tv_forum_rv_item_nick2)
        TextView tvForumRVItemNick2;
        @BindView(R.id.tv_forum_rv_item_nick3)
        TextView tvForumRVItemNick3;
        @BindView(R.id.tv_forum_rv_item_time1)
        TextView tvForumRVItemTime1;
        @BindView(R.id.tv_forum_rv_item_time3)
        TextView tvForumRVItemTime3;

        @BindView(R.id.iv_forum_rv_item_title_logo3)
        ImageView ivForumRVItemTitleLogo3;
        @BindView(R.id.iv_forum_rv_item_title_logo)
        ImageView ivForumRVItemTitleLogo;

        @BindView(R.id.lly_forum_rv_item1)
        LinearLayout llyForumRVItem1;
        @BindView(R.id.lly_forum_rv_item2)
        LinearLayout llyForumRVItem2;
        @BindView(R.id.lly_forum_rv_item3)
        LinearLayout llyForumRVItem3;

        @BindView(R.id.tv_forum_rv_item_browse3)
        TextView tvForumRVItemBrowse3;
        @BindView(R.id.tv_forum_rv_item_browse)
        TextView tvForumRVItemBrowse;

        @BindView(R.id.tv_forum_rv_item_repost)
        TextView tvForumRVItemRepost;

        @BindView(R.id.tv_forum_rv_item_repost3)
        TextView tvForumRVItemRepost3;

        @BindView(R.id.tv_forum_rv_item_praise)
        TextView tvForumRVItemPraise;

        @BindView(R.id.tv_forum_rv_item_praise3)
        TextView tvForumRVItemPraise3;
        @BindView(R.id.iv_forum_rv_item_praise)
        ImageView ivForumRVItemPraise;

        @BindView(R.id.iv_forum_rv_item_praise3)
        ImageView ivForumRVItemPraise3;
        @BindView(R.id.tv_forum_rv_item_comments)
        TextView tvForumRVItemComments;

        @BindView(R.id.tv_forum_rv_item_comments3)
        TextView tvForumRVItemComments3;

//        @BindView(R.id.lly_splitview)
//        LinearLayout llySplitView;
        @BindView(R.id.fly_forum_rv_item)
        FrameLayout flyForumRVItem;
        private long clickTime = 0;
        @OnClick(R.id.fly_forum_rv_item)
        public void llyForumRVItemOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();

                XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String account = xcCacheManager.readCache(xcCacheSaveName.account);
                String role = xcCacheManager.readCache(xcCacheSaveName.role);

                if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    v.getContext().startActivity(intent);

                    return;
                }

                String articleAccount = dataList.get(pos).getAccountid();
                String articleid = dataList.get(pos).getArticleid();
                String title = dataList.get(pos).getTitle();
                String thirdid = dataList.get(pos).getThirdid();
                if (articleAccount == null) {
                    articleAccount = "";
                }
                if (articleid == null) {
                    articleid = "";
                }
                if (title == null) {
                    title = "";
                }

                if(llyForumMark.getVisibility() == View.VISIBLE){
                    llyForumMark.setVisibility(View.GONE);
                    return;
                }
                Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);
                intent.putExtra("managetype", managetype);
                intent.putExtra("thirdid", thirdid);

//            intent.putExtra("title",title);

                v.getContext().startActivity(intent);
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
           /* if(isNoMoreData){
                if(position == dataList.size() -1){
                    flyForumRVItem.setVisibility(View.GONE);
                    llySplitView.setVisibility(View.VISIBLE);
                }
//                return;

            }else {
                flyForumRVItem.setVisibility(View.VISIBLE);
                llySplitView.setVisibility(View.GONE);
            }*/
            String title = dataList.get(position).getTitle();
            String nick = dataList.get(position).getNick();
            String content = dataList.get(position).getContent();
            String backtime = dataList.get(position).getBacktime();
            int comments = dataList.get(position).getComment();
            int repost = dataList.get(position).getRepost();
            int good = dataList.get(position).getGood();
            int readers = dataList.get(position).getReaders();
            int ispraised = dataList.get(position).getIspraised();

            String types = dataList.get(position).getTypes();
            String isSoluation = dataList.get(position).getIssoluation();
            String head = dataList.get(position).getHead();
            Object imgOne = dataList.get(position).getImageone();
            Object imgTwo = dataList.get(position).getImagetwo();
            Object imgThree = dataList.get(position).getImagethree();
            int istop = dataList.get(position).getIstop();
            /*if(imgOne != null && imgTwo!= null && imgThree != null  && !imgOne.toString().isEmpty()&& !imgTwo.toString().isEmpty()&& !imgThree.toString().isEmpty()){
                llyForumRVItem1.setVisibility(View.GONE);
                llyForumRVItem2.setVisibility(View.VISIBLE);
                llyForumRVItem3.setVisibility(View.GONE);
                tvForumRVItemComments.setText(comments+"");
                tvForumRVItemTitle2.setText(title);
                tvForumRVItemNick2.setText(nick);
                Glide.with(v.getContext()).load(imgOne.toString())
                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                        .into(ivForumRVItemImg2One);
                Glide.with(v.getContext()).load(imgTwo.toString())
                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                        .into(ivForumRVItemImg2Two);
                Glide.with(v.getContext()).load(imgThree.toString())
                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                        .into(ivForumRVItemImg2Three);
                Glide.with(v.getContext()).load(head)
                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                        .into(civForumRVItemHead2);
            }else*/
            if(((imgOne == null || imgOne.toString().isEmpty()) && (imgTwo == null || imgTwo.toString().isEmpty()) && (imgThree == null || imgThree.toString().isEmpty()))){
                llyForumRVItem1.setVisibility(View.GONE);
                llyForumRVItem2.setVisibility(View.GONE);
                llyForumRVItem3.setVisibility(View.VISIBLE);
                if(types != null) {
                    if (types.equals("help") || types.equals("data")) {
                        ivForumRVItemTitleLogo3.setVisibility(View.VISIBLE);
                        if (isSoluation != null && isSoluation.equals("1")) {
                            ivForumRVItemTitleLogo3.setBackgroundResource(R.mipmap.soluationed);
                        } else {
                            ivForumRVItemTitleLogo3.setBackgroundResource(R.mipmap.help);
                        }
                    } else {
                        ivForumRVItemTitleLogo3.setVisibility(View.GONE);
                    }
                }else {
                    ivForumRVItemTitleLogo3.setVisibility(View.GONE);
                }
                if(position == 0){
                    if(istop == 1){
                        ivForumRVItemTitleLogo3.setBackgroundResource(R.mipmap.zhiding);
                        ivForumRVItemTitleLogo3.setVisibility(View.VISIBLE);
                    }
                }
                if(position == 1){
                    if(istop == 1){
                        ivForumRVItemTitleLogo3.setBackgroundResource(R.mipmap.zhiding);
                        ivForumRVItemTitleLogo3.setVisibility(View.VISIBLE);
                    }
                }
                if(position == 2){
                    if(istop == 1){
                        ivForumRVItemTitleLogo3.setBackgroundResource(R.mipmap.zhiding);
                        ivForumRVItemTitleLogo3.setVisibility(View.VISIBLE);
                    }
                }
                if(ispraised == 1){
                    ivForumRVItemPraise3.setBackgroundResource(R.mipmap.praised);
                }else {
                    ivForumRVItemPraise3.setBackgroundResource(R.mipmap.praiseicon);
                }
                tvForumRVItemComments3.setText(comments+"");
                tvForumRVItemTitle3.setText(title);
                tvForumRVItemNick3.setText(nick);
                tvForumRVItemTime3.setText("回复于 "+backtime);
                tvForumRVItemContent3.setText(content);
                tvForumRVItemContent3.setVisibility(View.GONE);
                tvForumRVItemPraise3.setText(good+"");
                tvForumRVItemBrowse3.setText(readers+"");
                tvForumRVItemRepost3.setText(repost+"");
                tvForumRVItemComments3.setText(comments+"");
                Glide.with(v.getContext()).load(head)
                        .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                .error(R.mipmap.imghead)

                        )
                        .into(civForumRVItemHead3);
            }else {
                llyForumRVItem1.setVisibility(View.VISIBLE);
                llyForumRVItem2.setVisibility(View.GONE);
                llyForumRVItem3.setVisibility(View.GONE);
                tvForumRVItemComments.setText(comments+"");
                if(ispraised == 1){
                    ivForumRVItemPraise.setBackgroundResource(R.mipmap.praised);
                }else {
                    ivForumRVItemPraise.setBackgroundResource(R.mipmap.praiseicon);
                }
                tvForumRVItemPraise.setText(good+"");
                tvForumRVItemBrowse.setText(readers+"");
                tvForumRVItemRepost.setText(repost+"");
//                tvForumRVItemComments3.setText(comments+"");
                tvForumRVItemTitle.setText(title);
                tvForumRVItemNick1.setText(nick);
                tvForumRVItemTime1.setText("回复于 "+backtime);
//                tvForumRVItemContent3.setText(content);
                if(imgOne != null && !imgOne.toString().isEmpty()){
                    Glide.with(v.getContext()).load(imgOne)
                            .apply(new RequestOptions().fallback(R.mipmap.imghead))
                            .into(ivForumRVItemImg);
                }else {
                    if(imgTwo != null && !imgTwo.toString().isEmpty()){
                        Glide.with(v.getContext()).load(imgTwo)
                                .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                .into(ivForumRVItemImg);
                    }else {
                        if(imgThree != null && !imgThree.toString().isEmpty()){
                            Glide.with(v.getContext()).load(imgThree)
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                    .into(ivForumRVItemImg);
                        }
                    }
                }
                if(types != null) {
                    if (types.equals("help") || types.equals("data")) {
                        ivForumRVItemTitleLogo.setVisibility(View.VISIBLE);
                        if (isSoluation != null && isSoluation.equals("1")) {
                            ivForumRVItemTitleLogo.setBackgroundResource(R.mipmap.soluationed);
                        } else {
                            ivForumRVItemTitleLogo.setBackgroundResource(R.mipmap.help);
                        }
                    } else {
                        ivForumRVItemTitleLogo.setVisibility(View.GONE);
                    }
                }else {
                    ivForumRVItemTitleLogo.setVisibility(View.GONE);
                }
                if(position == 0){
                    if(istop == 1){
                        ivForumRVItemTitleLogo.setBackgroundResource(R.mipmap.zhiding);
                        ivForumRVItemTitleLogo.setVisibility(View.VISIBLE);
                    }
                }
                if(position == 1){
                    if(istop == 1){
                        ivForumRVItemTitleLogo.setBackgroundResource(R.mipmap.zhiding);
                        ivForumRVItemTitleLogo.setVisibility(View.VISIBLE);
                    }
                }
                if(position == 2){
                    if(istop == 1){
                        ivForumRVItemTitleLogo.setBackgroundResource(R.mipmap.zhiding);
                        ivForumRVItemTitleLogo.setVisibility(View.VISIBLE);
                    }
                }

                Glide.with(v.getContext()).load(head)
                        .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                .error(R.mipmap.imghead))
                        .into(civForumRVItemHead1);
            }


            /*
            if(dataList.get(position).getTitle() != null){
                tvForumRVItemTitle.setText(dataList.get(position).getTitle());
            }

            tvForumRVItemGood.setText(""+dataList.get(position).getGood());
            tvForumRVItemComments.setText(""+dataList.get(position).getComment());
            if(dataList.get(position).getImageone() != null){

                Glide.with(v.getContext()).load(dataList.get(position).getImageone())
                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                        .into(ivForumRVItemImg);
//                ImageLoader.getInstance().displayImage(dataList.get(position).getImageone(),ivForumRVItemImg, ImageLoaderUtils.options);
            }*/

        }
    }


}
