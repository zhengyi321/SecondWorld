package com.et.secondworld.first.fragment.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.LoginActivity;
import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetRecommendBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.network.RegisterLoginNetWork;
import com.et.secondworld.utils.UniversalID;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.MessageQueryDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.et.secondworld.widget.imageview.CircleImageView;

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
public class FirstReCommend2RVAdapter extends RecyclerView.Adapter<FirstReCommend2RVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetRecommendBean.ListBean> dataList ;
    private Boolean isNoMoreData = false;
    public void isNoMoreData(Boolean isNoMoreData){
        this.isNoMoreData = isNoMoreData;
    }
    public FirstReCommend2RVAdapter(List<GetRecommendBean.ListBean> dataList1){
        dataList = dataList1;
    }

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
        return new FirstReCommend2RVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_first_recommend_rv_item2, parent, false));
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



        @BindView(R.id.tv_first_recommend_rv_item_browses3)
        TextView tvFirstReCommendRVItemBrowses3;
        @BindView(R.id.tv_first_recommend_rv_item_browses)
        TextView tvFirstReCommendRVItemBrowses;
        @BindView(R.id.tv_first_recommend_rv_item_comments)
        TextView tvFirstReCommendRVItemComments;
        @BindView(R.id.tv_first_recommend_rv_item_comments3)
        TextView tvFirstReCommendRVItemComments3;
        @BindView(R.id.tv_first_recommend_rv_item_praise)
        TextView tvFirstReCommendRVItemPraise;
        @BindView(R.id.tv_first_recommend_rv_item_praise3)
        TextView tvFirstReCommendRVItemPraise3;
        @BindView(R.id.tv_first_recommend_rv_item_repost)
        TextView tvFirstReCommendRVItemRepost;
        @BindView(R.id.tv_first_recommend_rv_item_repost3)
        TextView tvFirstReCommendRVItemRepost3;
        @BindView(R.id.iv_first_recommend_rv_item_praise3)
        ImageView ivFirstRecommendRVItemPraise3;
        @BindView(R.id.iv_first_recommend_rv_item_praise)
        ImageView ivFirstRecommendRVItemPraise;










        @BindView(R.id.tv_first_recommend_rv_item_title)
        TextView tvFirstRecommendRVItemTitle;
        @BindView(R.id.tv_first_recommend_rv_item_title2)
        TextView tvFirstRecommendRVItemTitle2;
        @BindView(R.id.tv_first_recommend_rv_item_title3)
        TextView tvFirstRecommendRVItemTitle3;
        @BindView(R.id.tv_first_recommend_rv_item_content3)
        TextView tvFirstRecommendRVItemContent3;
        @BindView(R.id.rly_first_recommend_rv_item_title_logo1)
        RelativeLayout rlyFirstRecommendRVItemTitleLogo1;
        @BindView(R.id.rly_first_recommend_rv_item_top2)
        RelativeLayout rlyFirstRecommendRVItemTop2;
        @BindView(R.id.rly_first_recommend_rv_item_title_logo3)
        RelativeLayout rlyFirstRecommendRVItemTitleLogo3;
        @BindView(R.id.iv_first_recommend_rv_item_title_logo1)
        ImageView ivFirstRecommendRVItemTitleLogo1;
        @BindView(R.id.iv_first_recommend_rv_item_title_logo3)
        ImageView ivFirstRecommendRVItemTitleLogo3;

        @BindView(R.id.tv_first_recommend_rv_item_good)
        TextView tvFirstRecommendRVItemGood;
        /*@BindView(R.id.tv_first_recommend_rv_item_comments)
        TextView tvFirstRecommendRVItemComments;
        @BindView(R.id.tv_first_recommend_rv_item_comments2)
        TextView tvFirstRecommendRVItemComments2;
        @BindView(R.id.tv_first_recommend_rv_item_comments3)
        TextView tvFirstRecommendRVItemComments3;*/
        @BindView(R.id.iv_first_recommend_rv_item_img)
        ImageView ivFirstRecommendRVItemImg;
        @BindView(R.id.iv_first_recommend_rv_item_img2_one)
        ImageView ivFirstRecommendRVItemImg2One;
        @BindView(R.id.iv_first_recommend_rv_item_img2_two)
        ImageView ivFirstRecommendRVItemImg2Two;
        @BindView(R.id.iv_first_recommend_rv_item_img2_three)
        ImageView ivFirstRecommendRVItemImg2Three;

        @BindView(R.id.civ_first_recommend_rv_item_head1)
        CircleImageView civFirstRecommendRVItemHead1;
        @BindView(R.id.civ_first_recommend_rv_item_head2)
        CircleImageView civFirstRecommendRVItemHead2;
        @BindView(R.id.civ_first_recommend_rv_item_head3)
        CircleImageView civFirstRecommendRVItemHead3;

        @BindView(R.id.tv_first_recommend_rv_item_nick1)
        TextView tvFirstRecommendRVItemNick1;
        @BindView(R.id.tv_first_recommend_rv_item_nick2)
        TextView tvFirstRecommendRVItemNick2;
        @BindView(R.id.tv_first_recommend_rv_item_nick3)
        TextView tvFirstRecommendRVItemNick3;

        @BindView(R.id.lly_first_recommend_rv_item1)
        LinearLayout llyFirstRecommendRVItem1;
        @BindView(R.id.lly_first_recommend_rv_item2)
        LinearLayout llyFirstRecommendRVItem2;
        @BindView(R.id.lly_first_recommend_rv_item3)
        LinearLayout llyFirstRecommendRVItem3;
        @BindView(R.id.lly_splitview)
        LinearLayout llySplitView;
        @BindView(R.id.fly_first_recommend_rv_item)
        FrameLayout flyFirstRecommendRVItem;
        private long clickTime = 0;
       @OnClick(R.id.fly_first_recommend_rv_item)
       public void llyFirstRecommendRVItemOnclick(){
           if(System.currentTimeMillis() - clickTime > 2000) {
               checkLogin();
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
               String model = dataList.get(pos).getModules();
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
               if (model.equals("M3")) {
                   Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
                   intent.putExtra("articleAccount", articleAccount);
                   intent.putExtra("articleid", articleid);

                   intent.putExtra("title", title);
                   v.getContext().startActivity(intent);
               } else if (model.equals("M4")) {
                   Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
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

        private void checkLogin(){
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String tel = xcCacheManager.readCache(xcCacheSaveName.userTel);
            String uuuid = UniversalID.getUniversalID(v.getContext());
            RegisterLoginNetWork registerLoginNetWork = new RegisterLoginNetWork();
            Map<String,Object> map = new HashMap<>();
            map.put("tel",tel);
            map.put("uuuid",uuuid.trim());
            registerLoginNetWork.checkLoginFromNet(map, new Observer<BaseBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseBean baseBean) {
                    if(baseBean.getIssuccess().equals("1")){
                        MessageQueryDialog queryCancelDialog = new MessageQueryDialog(v.getContext()).Build.setCallBackListener(new MessageQueryDialog.OnFinishClickListener() {
                            @Override
                            public void isQuery(boolean isQuery) {
                                xcCacheManager.writeCache(xcCacheSaveName.account,"");
                                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                v.getContext().startActivity(intent);
//                            MainActivity.this.finish();
                            }
                        }).build(v.getContext(),"您的账号("+baseBean.getMsg()+")在其它地方登陆,请确认是否是本人操作,如若不是请及时修改密码!");
                        queryCancelDialog.show();


                    }
                }
            });
        }
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            if(position != 0){
                rlyFirstRecommendRVItemTitleLogo1.setVisibility(View.GONE);
                rlyFirstRecommendRVItemTop2.setVisibility(View.GONE);
                rlyFirstRecommendRVItemTitleLogo3.setVisibility(View.GONE);
            }
           /* if(isNoMoreData){
                if(position == dataList.size() -1){
                    flyFirstRecommendRVItem.setVisibility(View.GONE);
                    llySplitView.setVisibility(View.VISIBLE);
                }else {
                    flyFirstRecommendRVItem.setVisibility(View.VISIBLE);
                    llySplitView.setVisibility(View.GONE);
                }
//                return;

            }else {
                flyFirstRecommendRVItem.setVisibility(View.VISIBLE);
                llySplitView.setVisibility(View.GONE);
            }*/
            String title = dataList.get(position).getTitle();
            String nick = dataList.get(position).getNick();
            String content = dataList.get(position).getContent();
            int comments = dataList.get(position).getComment();
            String head = dataList.get(position).getLogo();
            Object imgOne = dataList.get(position).getImageone();
            Object imgTwo = dataList.get(position).getImagetwo();
            Object imgThree = dataList.get(position).getImagethree();
            String types  = dataList.get(position).getTypes();
            String isSoluation =dataList.get(position).getIssoluation();
            int repost = dataList.get(position).getRepost();
            int good = dataList.get(position).getGood();
            int ispraised = dataList.get(position).getIspraised();
            int readers = dataList.get(position).getReaders();


//            if(imgOne != null && imgTwo!= null && imgThree != null  && !imgOne.toString().isEmpty()&& !imgTwo.toString().isEmpty()&& !imgThree.toString().isEmpty()){
//                llyFirstRecommendRVItem1.setVisibility(View.GONE);
//                llyFirstRecommendRVItem2.setVisibility(View.VISIBLE);
//                llyFirstRecommendRVItem3.setVisibility(View.GONE);
//                tvFirstRecommendRVItemComments2.setText(comments+"");
//                tvFirstRecommendRVItemTitle2.setText(title);
//                tvFirstRecommendRVItemNick2.setText(nick);
//                Glide.with(v.getContext()).load(imgOne.toString())
//                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                        .into(ivFirstRecommendRVItemImg2One);
//                Glide.with(v.getContext()).load(imgTwo.toString())
//                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                        .into(ivFirstRecommendRVItemImg2Two);
//                Glide.with(v.getContext()).load(imgThree.toString())
//                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                        .into(ivFirstRecommendRVItemImg2Three);
//                Glide.with(v.getContext()).load(head)
//                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                        .into(civFirstRecommendRVItemHead2);
//            }else if(((imgOne == null || imgOne.toString().isEmpty()) && (imgTwo == null || imgTwo.toString().isEmpty()) && (imgThree == null || imgThree.toString().isEmpty()))){
            if(((imgOne == null || imgOne.toString().isEmpty()) && (imgTwo == null || imgTwo.toString().isEmpty()) && (imgThree == null || imgThree.toString().isEmpty()))){
                llyFirstRecommendRVItem1.setVisibility(View.GONE);
                llyFirstRecommendRVItem2.setVisibility(View.GONE);
                llyFirstRecommendRVItem3.setVisibility(View.VISIBLE);
//                tvFirstRecommendRVItemComments3.setText(comments+"");
                tvFirstRecommendRVItemTitle3.setText(title);
                tvFirstRecommendRVItemNick3.setText(nick);
                tvFirstRecommendRVItemContent3.setText(content);
                tvFirstReCommendRVItemBrowses3.setText(readers+"");
                tvFirstReCommendRVItemComments3.setText(comments+"");
                tvFirstReCommendRVItemPraise3.setText(good+"");
                tvFirstReCommendRVItemRepost3.setText(repost+"");
                if(ispraised == 1){
                    ivFirstRecommendRVItemPraise3.setBackgroundResource(R.mipmap.praised);
                }else {
                    ivFirstRecommendRVItemPraise3.setBackgroundResource(R.mipmap.praiseicon);
                }
                if(types != null) {
                    if (types.equals("help") || types.equals("data")) {
                        rlyFirstRecommendRVItemTitleLogo3.setVisibility(View.VISIBLE);
                        if (isSoluation != null && isSoluation.equals("1")) {
                            ivFirstRecommendRVItemTitleLogo3.setBackgroundResource(R.mipmap.soluationed);
                        } else {
                            ivFirstRecommendRVItemTitleLogo3.setBackgroundResource(R.mipmap.help);
                        }
                    } else {
                        rlyFirstRecommendRVItemTitleLogo3.setVisibility(View.GONE);
                    }
                }
                Glide.with(v.getContext()).load(head)
                        .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                .error(R.mipmap.imghead))
                        .into(civFirstRecommendRVItemHead3);
            }else {
                llyFirstRecommendRVItem1.setVisibility(View.VISIBLE);
                llyFirstRecommendRVItem2.setVisibility(View.GONE);
                llyFirstRecommendRVItem3.setVisibility(View.GONE);
//                tvFirstRecommendRVItemComments.setText(comments+"");
                tvFirstRecommendRVItemTitle.setText(title);
                tvFirstRecommendRVItemNick1.setText(nick);
                tvFirstReCommendRVItemBrowses.setText(readers+"");
                tvFirstReCommendRVItemComments.setText(comments+"");
                tvFirstReCommendRVItemPraise.setText(good+"");
                tvFirstReCommendRVItemRepost.setText(repost+"");
                if(ispraised == 1){
                    ivFirstRecommendRVItemPraise.setBackgroundResource(R.mipmap.praised);
                }else {
                    ivFirstRecommendRVItemPraise.setBackgroundResource(R.mipmap.praiseicon);
                }
                if(types != null) {
                    if (types.equals("help") || types.equals("data")) {
                        rlyFirstRecommendRVItemTitleLogo1.setVisibility(View.VISIBLE);
                        if (isSoluation != null && isSoluation.equals("1")) {
                            ivFirstRecommendRVItemTitleLogo1.setBackgroundResource(R.mipmap.soluationed);
                        } else {
                            ivFirstRecommendRVItemTitleLogo1.setBackgroundResource(R.mipmap.help);
                        }
                    } else {
                        rlyFirstRecommendRVItemTitleLogo1.setVisibility(View.GONE);
                    }
                }
//                tvForumRVItemContent3.setText(content);
                if(imgOne != null && !imgOne.toString().isEmpty()){
                    Glide.with(v.getContext()).load(imgOne)
                            .apply(new RequestOptions().fallback(R.mipmap.imghead))
                            .into(ivFirstRecommendRVItemImg);
                }else {
                    if(imgTwo != null && !imgTwo.toString().isEmpty()){
                        Glide.with(v.getContext()).load(imgTwo)
                                .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                .into(ivFirstRecommendRVItemImg);
                    }else {
                        if(imgThree != null && !imgThree.toString().isEmpty()){
                            Glide.with(v.getContext()).load(imgThree)
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                    .into(ivFirstRecommendRVItemImg);
                        }
                    }
                }





                Glide.with(v.getContext()).load(head)
                        .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                .error(R.mipmap.imghead))
                        .into(civFirstRecommendRVItemHead1);
            }

        }
    }


}
