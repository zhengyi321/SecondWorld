package com.et.secondworld.first.fragment.adapter;

import android.content.Intent;
import android.util.Log;
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
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetFirstGuanZhuModuleBean;
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
public class FirstGuanZhu2RVAdapter extends RecyclerView.Adapter<FirstGuanZhu2RVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetFirstGuanZhuModuleBean.ListBean> dataList ;
    private Boolean isNoMoreData = false;
    public void isNoMoreData(Boolean isNoMoreData){
        this.isNoMoreData = isNoMoreData;
    }
    public FirstGuanZhu2RVAdapter(List<GetFirstGuanZhuModuleBean.ListBean> dataList1 ){
        dataList = dataList1;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetFirstGuanZhuModuleBean.ListBean> list) {
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
    public void addData(int position, List<GetFirstGuanZhuModuleBean.ListBean> list) {
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
        return new FirstGuanZhu2RVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_first_guanzhu_rv_item2, parent, false));
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

        @BindView(R.id.lly_first_guanzhu_rv_item_module_shop)
        LinearLayout llyFirstGuanZhuRVItemModuleShop;


        @BindView(R.id.tv_first_guanzhu_rv_item_title)
        TextView tvFirstGuanZhuRVItemTitle;
        @BindView(R.id.iv_first_guanzhu_rv_item_imgone_1)
        ImageView ivFirstGuanZhuRVItemImgOne1;
        @BindView(R.id.lly_first_guanzhu_rv_item_imgtwo)
        LinearLayout llyFirstGuanZhuRVItemImgTwo;
        @BindView(R.id.iv_first_guanzhu_rv_item_imgtwo_1)
        ImageView ivFirstGuanZhuRVItemImgTwo1;
        @BindView(R.id.iv_first_guanzhu_rv_item_imgtwo_2)
        ImageView ivFirstGuanZhuRVItemImgTwo2;
        @BindView(R.id.iv_first_guanzhu_rv_item_imgtwo_3)
        ImageView ivFirstGuanZhuRVItemImgTwo3;
        @BindView(R.id.iv_first_guanzhu_rv_item_imgthree_1)
        ImageView ivFirstGuanZhuRVItemImgThree1;
        @BindView(R.id.civ_first_guanzhu_rv_item_head)
        CircleImageView civFirstGuanZhuRVItemHead;
        @BindView(R.id.tv_first_guanzhu_rv_item_nick)
        TextView tvFirstGuanZhuRVItemNick;
        @BindView(R.id.tv_first_guanzhu_rv_item_readers)
        TextView tvFirstGuanZhuRVItemReaders;
        @BindView(R.id.iv_first_guanzhu_rv_item_shop)
        ImageView ivFirstGuanZhuRVItemShop;

        @BindView(R.id.tv_first_guanzhu_rv_item_browse)
        TextView tvFirstGuanZhuRVItemBrowse;

        @BindView(R.id.tv_first_guanzhu_rv_item_repost)
        TextView tvFirstGuanZhuRVItemRepost;

        @BindView(R.id.tv_first_guanzhu_rv_item_comments)
        TextView tvFirstGuanZhuRVItemComments;

        @BindView(R.id.tv_first_guanzhu_rv_item_praise)
        TextView tvFirstGuanZhuRVItemPraise;
        @BindView(R.id.iv_first_guanzhu_rv_item_praise)
        ImageView ivFirstGuanZhuRVItemPraise;

/*
        @BindView(R.id.lly_first_guanzhu_rv_item_module_account)
        LinearLayout llyFirstGuanZhuRVItemModuleAccount;
        @BindView(R.id.lly_first_guanzhu_rv_item_module_account1)
        LinearLayout llyFirstGuanZhuRVItemModuleAccount1;
        @BindView(R.id.lly_first_guanzhu_rv_item_module_account2)
        LinearLayout llyFirstGuanZhuRVItemModuleAccount2;
        @BindView(R.id.lly_first_guanzhu_rv_item_module_account3)
        LinearLayout llyFirstGuanZhuRVItemModuleAccount3;*/
/*
        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_title1)
        TextView tvFirstGuanZhuRVItemModuleAccountTitle1;
        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_title2)
        TextView tvFirstGuanZhuRVItemModuleAccountTitle2;
        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_title3)
        TextView tvFirstGuanZhuRVItemModuleAccountTitle3;

        @BindView(R.id.civ_first_guanzhu_rv_item_module_account_head1)
        CircleImageView civFirstGuanZhuRVItemModuleAccountHead1;
        @BindView(R.id.civ_first_guanzhu_rv_item_module_account_head2)
        CircleImageView civFirstGuanZhuRVItemModuleAccountHead2;
        @BindView(R.id.civ_first_guanzhu_rv_item_module_account_head3)
        CircleImageView civFirstGuanZhuRVItemModuleAccountHead3;*/
//
//        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_nick1)
//        TextView tvFirstGuanZhuRVItemModuleAccountNick1;
//        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_nick2)
//        TextView tvFirstGuanZhuRVItemModuleAccountNick2;
//        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_nick3)
//        TextView tvFirstGuanZhuRVItemModuleAccountNick3;
//
//        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_comments1)
//        TextView tvFirstGuanZhuRVItemModuleAccountComments1;
//        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_comments2)
//        TextView tvFirstGuanZhuRVItemModuleAccountComments2;
//        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_comments3)
//        TextView tvFirstGuanZhuRVItemModuleAccountComments3;
//
//        @BindView(R.id.iv_first_guanzhu_rv_item_module_account_img)
//        ImageView ivFirstGuanZhuRVItemModuleAccountImg;
//        @BindView(R.id.iv_first_guanzhu_rv_item_module_account_img2_one)
//        ImageView ivFirstGuanZhuRVItemModuleAccountImg2One;
//        @BindView(R.id.iv_first_guanzhu_rv_item_module_account_img2_two)
//        ImageView ivFirstGuanZhuRVItemModuleAccountImg2Two;
//        @BindView(R.id.iv_first_guanzhu_rv_item_module_account_img2_three)
//        ImageView ivFirstGuanZhuRVItemModuleAccountImg2Three;
//
//        @BindView(R.id.tv_first_guanzhu_rv_item_module_account_content3)
//        TextView tvFirstGuanZHuRVItemModuleAccountContent3;
        @BindView(R.id.tv_first_guanzhu_rv_item_content)
        TextView tvFirstGuanZHuRVItemContent;
        @BindView(R.id.iv_first_guanzhu_rv_item_title_logo)
        ImageView ivFirstGuanZhuRVItemTitleLogo;
/*
        @BindView(R.id.lly_splitview)
        LinearLayout llySplitView;*/
        /*@BindView(R.id.fly_first_guanzhu_rv_item)
        FrameLayout flyFirstGuanZhuRVItem;*/
        private long clickTime = 0;
        @OnClick(R.id.lly_first_guanzhu_rv_item)
        public void flyFirstGuanZhuItemOnclick(){
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
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

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


        void setData(Object data,int position) {
          /*  if(isNoMoreData){
                if(position == dataList.size() -1) {
                    Log.d("guanzhu2", "nomoredata" + position);
                    flyFirstGuanZhuRVItem.setVisibility(View.GONE);
                    llySplitView.setVisibility(View.VISIBLE);
                }else {
                    flyFirstGuanZhuRVItem.setVisibility(View.VISIBLE);
                    llySplitView.setVisibility(View.GONE);
                }
            }else {
                flyFirstGuanZhuRVItem.setVisibility(View.VISIBLE);
                llySplitView.setVisibility(View.GONE);
            }*/
           /* if(isNoMoreData){
                if(position == dataList.size() -1){
                    flyFirstGuanZhuRVItem.setVisibility(View.GONE);
                    llySplitView.setVisibility(View.VISIBLE);
                }
//                return;

            }else {
                flyFirstGuanZhuRVItem.setVisibility(View.VISIBLE);
                llySplitView.setVisibility(View.GONE);
            }*/
            pos = position;
            String model = dataList.get(pos).getModules();
            String title = dataList.get(position).getTitle();
            String content = dataList.get(position).getContent();
            String head = dataList.get(position).getLogo();
            String nick = dataList.get(position).getNick();
            int comments = dataList.get(position).getComments();
            String types = dataList.get(position).getTypes();
            int isSoluation = dataList.get(position).getIssoluation();
//            String readers = dataList.get(position).getReaders()+"";
            String imgOne = dataList.get(position).getImageone();
            String imgTwo = dataList.get(position).getImagetwo();
            String imgThree = dataList.get(position).getImagethree();
            int readers = dataList.get(position).getReaders();
            int repost = dataList.get(position).getRepost();
            int good = dataList.get(position).getGood();
            int ispraised = dataList.get(position).getIspraised();
            tvFirstGuanZhuRVItemComments.setText(comments+"");
            tvFirstGuanZhuRVItemBrowse.setText(readers+"");
            tvFirstGuanZhuRVItemRepost.setText(repost+"");
            tvFirstGuanZhuRVItemPraise.setText(good+"");
            if(ispraised == 1){
                ivFirstGuanZhuRVItemPraise.setBackgroundResource(R.mipmap.praised);
            }else {
                ivFirstGuanZhuRVItemPraise.setBackgroundResource(R.mipmap.praiseicon);
            }
            Log.d("issoluation11",isSoluation+"");
            if(types != null) {
                if (types.equals("help") || types.equals("data")) {
                    ivFirstGuanZhuRVItemTitleLogo.setVisibility(View.VISIBLE);
                    if (isSoluation == 1) {
                        ivFirstGuanZhuRVItemTitleLogo.setBackgroundResource(R.mipmap.soluationed);
                    } else {
                        ivFirstGuanZhuRVItemTitleLogo.setBackgroundResource(R.mipmap.help);
                    }
                } else {
                    ivFirstGuanZhuRVItemTitleLogo.setVisibility(View.GONE);
                }
            }else {
                ivFirstGuanZhuRVItemTitleLogo.setVisibility(View.GONE);
            }
            int hide = 0;
            if(imgOne == null || imgOne.isEmpty()){
                hide++;
            }

            if(imgTwo == null || imgTwo.isEmpty()){
                hide++;
            }

            if(imgThree == null || imgThree.isEmpty()){
                hide++;
            }
            if(model == null){
                model = "";
            }
            if(model.equals("M4")) {
                ivFirstGuanZhuRVItemShop.setVisibility(View.GONE);
                switch (hide){
                    case 0:{
                        ivFirstGuanZhuRVItemImgOne1.setVisibility(View.GONE);
                        ivFirstGuanZhuRVItemImgThree1.setVisibility(View.GONE);
                        llyFirstGuanZhuRVItemImgTwo.setVisibility(View.VISIBLE);

                        ivFirstGuanZhuRVItemImgTwo3.setVisibility(View.VISIBLE);
                        Glide.with(v.getContext()).load(imgOne.toString())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                .into(ivFirstGuanZhuRVItemImgTwo1);
                        Glide.with(v.getContext()).load(imgTwo.toString())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                .into(ivFirstGuanZhuRVItemImgTwo2);
                        Glide.with(v.getContext()).load(imgThree.toString())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                .into(ivFirstGuanZhuRVItemImgTwo3);
                    }

                    break;
                    case 1:
                        ivFirstGuanZhuRVItemImgOne1.setVisibility(View.GONE);

                        ivFirstGuanZhuRVItemImgThree1.setVisibility(View.GONE);
                        llyFirstGuanZhuRVItemImgTwo.setVisibility(View.VISIBLE);
                        ivFirstGuanZhuRVItemImgTwo3.setVisibility(View.INVISIBLE);
                        if(imgOne != null && !imgOne.isEmpty()){
                            Glide.with(v.getContext()).load(imgOne.toString())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                    .into(ivFirstGuanZhuRVItemImgTwo1);
                            if(imgTwo != null && !imgTwo.isEmpty()){
                                Glide.with(v.getContext()).load(imgTwo.toString())
                                        .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                        .into(ivFirstGuanZhuRVItemImgTwo2);
                            }else {
                                Glide.with(v.getContext()).load(imgThree.toString())
                                        .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                        .into(ivFirstGuanZhuRVItemImgTwo2);
                            }
                        }else {
                            Glide.with(v.getContext()).load(imgTwo.toString())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                    .into(ivFirstGuanZhuRVItemImgTwo1);
                            Glide.with(v.getContext()).load(imgThree.toString())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                    .into(ivFirstGuanZhuRVItemImgTwo2);
                        }

                        break;

                    case 2:
                        ivFirstGuanZhuRVItemImgOne1.setVisibility(View.GONE);
                        ivFirstGuanZhuRVItemImgThree1.setVisibility(View.VISIBLE);
                        llyFirstGuanZhuRVItemImgTwo.setVisibility(View.GONE);
                        if(imgOne != null && !imgOne.isEmpty()){
                            Glide.with(v.getContext()).load(imgOne.toString())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                    .into(ivFirstGuanZhuRVItemImgThree1);
                        }else {
                            if(imgTwo != null && !imgTwo.isEmpty()){
                                Glide.with(v.getContext()).load(imgTwo.toString())
                                        .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                        .into(ivFirstGuanZhuRVItemImgThree1);
                            }else {
                                Glide.with(v.getContext()).load(imgThree.toString())
                                        .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                        .into(ivFirstGuanZhuRVItemImgThree1);
                            }
                        }

                        break;

                    case 3:
                        ivFirstGuanZhuRVItemImgOne1.setVisibility(View.GONE);
                        ivFirstGuanZhuRVItemImgThree1.setVisibility(View.GONE);
                        llyFirstGuanZhuRVItemImgTwo.setVisibility(View.GONE);
                        break;
                }
            }else {
                ivFirstGuanZhuRVItemShop.setVisibility(View.VISIBLE);
                ivFirstGuanZhuRVItemImgOne1.setVisibility(View.VISIBLE);
                ivFirstGuanZhuRVItemImgThree1.setVisibility(View.GONE);
                llyFirstGuanZhuRVItemImgTwo.setVisibility(View.GONE);
                if(imgOne != null && !imgOne.isEmpty()){
                    Glide.with(v.getContext()).load(imgOne.toString())
                            .apply(new RequestOptions().fallback(R.mipmap.imghead))
                            .into(ivFirstGuanZhuRVItemImgOne1);
                }else {
                    if(imgTwo != null && !imgTwo.isEmpty()){
                        Glide.with(v.getContext()).load(imgTwo.toString())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                .into(ivFirstGuanZhuRVItemImgOne1);
                    }else {
                        if(imgTwo != null && !imgTwo.isEmpty()) {
                            Glide.with(v.getContext()).load(imgThree.toString())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead))
                                    .into(ivFirstGuanZhuRVItemImgOne1);
                        }else {
                            ivFirstGuanZhuRVItemImgOne1.setVisibility(View.GONE);
                        }
                    }
                }
            }



            tvFirstGuanZhuRVItemTitle.setText(title);
            tvFirstGuanZhuRVItemNick.setText(nick);
            tvFirstGuanZHuRVItemContent.setText(content);
            Glide.with(v.getContext()).load(head)
                        .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                .error(R.mipmap.imghead).circleCrop())
                    .into(civFirstGuanZhuRVItemHead);
//            if(model == null){
//                model = "";
//            }
//            if(model.equals("M4")){
//                llyFirstGuanZhuRVItemModuleAccount.setVisibility(View.VISIBLE);
//                llyFirstGuanZhuRVItemModuleShop.setVisibility(View.GONE);
                /*if(imgOne != null && imgTwo!= null && imgThree != null  && !imgOne.toString().isEmpty()&& !imgTwo.toString().isEmpty()&& !imgThree.toString().isEmpty()){
                    llyFirstGuanZhuRVItemModuleAccount1.setVisibility(View.GONE);
                    llyFirstGuanZhuRVItemModuleAccount2.setVisibility(View.VISIBLE);
                    llyFirstGuanZhuRVItemModuleAccount3.setVisibility(View.GONE);
                    tvFirstGuanZhuRVItemModuleAccountComments2.setText(comments+"");
                    tvFirstGuanZhuRVItemModuleAccountTitle2.setText(title);
                    tvFirstGuanZhuRVItemModuleAccountNick2.setText(nick);
                    Glide.with(v.getContext()).load(imgOne.toString())
                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                            .into(ivFirstGuanZhuRVItemModuleAccountImg2One);
                    Glide.with(v.getContext()).load(imgTwo.toString())
                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                            .into(ivFirstGuanZhuRVItemModuleAccountImg2Two);
                    Glide.with(v.getContext()).load(imgThree.toString())
                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                            .into(ivFirstGuanZhuRVItemModuleAccountImg2Three);
                    Glide.with(v.getContext()).load(head)
                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                            .into(civFirstGuanZhuRVItemModuleAccountHead2);
                }else */

//                if(((imgOne == null || imgOne.toString().isEmpty()) && (imgTwo == null || imgTwo.toString().isEmpty()) && (imgThree == null || imgThree.toString().isEmpty()))){
//                    llyFirstGuanZhuRVItemModuleAccount1.setVisibility(View.GONE);
//                    llyFirstGuanZhuRVItemModuleAccount2.setVisibility(View.GONE);
//                    llyFirstGuanZhuRVItemModuleAccount3.setVisibility(View.VISIBLE);
//                    tvFirstGuanZhuRVItemModuleAccountComments3.setText(comments+"");
//                    tvFirstGuanZhuRVItemModuleAccountTitle3.setText(title);
//                    tvFirstGuanZhuRVItemModuleAccountNick3.setText(nick);
//                    tvFirstGuanZHuRVItemModuleAccountContent3.setText(content);
//
//                    Glide.with(v.getContext()).load(head)
//                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                            .into(civFirstGuanZhuRVItemModuleAccountHead3);
//                }else {
//                    llyFirstGuanZhuRVItemModuleAccount1.setVisibility(View.VISIBLE);
//                    llyFirstGuanZhuRVItemModuleAccount2.setVisibility(View.GONE);
//                    llyFirstGuanZhuRVItemModuleAccount3.setVisibility(View.GONE);
//                    tvFirstGuanZhuRVItemModuleAccountComments1.setText(comments+"");
//                    tvFirstGuanZhuRVItemModuleAccountTitle1.setText(title);
//                    tvFirstGuanZhuRVItemModuleAccountNick1.setText(nick);
////                tvForumRVItemContent3.setText(content);
//                    if(imgOne != null && !imgOne.isEmpty()){
//                        Glide.with(v.getContext()).load(imgOne)
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                                .into(ivFirstGuanZhuRVItemModuleAccountImg);
//                    }else {
//                        if(imgTwo != null && !imgTwo.isEmpty()){
//                            Glide.with(v.getContext()).load(imgTwo)
//                                    .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                                    .into(ivFirstGuanZhuRVItemModuleAccountImg);
//                        }else {
//                            if(imgThree != null && !imgThree.isEmpty()){
//                                Glide.with(v.getContext()).load(imgThree)
//                                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                                        .into(ivFirstGuanZhuRVItemModuleAccountImg);
//                            }
//                        }
//                    }
                    /*if(imgTwo != null){
                        Glide.with(v.getContext()).load(imgTwo)
                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstGuanZhuRVItemModuleAccountImg);
                    }


                    if(imgThree != null){
                        Glide.with(v.getContext()).load(imgThree)
                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstGuanZhuRVItemModuleAccountImg);
                    }*/
//                    Glide.with(v.getContext()).load(head)
//                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                            .into(civFirstGuanZhuRVItemModuleAccountHead1);
//                }

//            }else {
//                llyFirstGuanZhuRVItemModuleAccount.setVisibility(View.GONE);
//                llyFirstGuanZhuRVItemModuleShop.setVisibility(View.VISIBLE);
//                ivFirstGuanZhuRVItem.setVisibility(View.VISIBLE);
////                tvFirstGuanZHuRVItemContent.setVisibility(View.VISIBLE);
//                tvFirstGuanZhuRVItemTitle.setText(title);
//                if(imgOne != null && !imgOne.isEmpty()){
//                    Glide.with(v.getContext()).load(imgOne)
//                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                            .into(ivFirstGuanZhuRVItem);
//                }else {
//
//                    if(imgTwo != null && !imgTwo.isEmpty()){
//                        Glide.with(v.getContext()).load(imgTwo)
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                                .into(ivFirstGuanZhuRVItem);
//                    }else {
//                        if(imgThree != null && !imgThree.isEmpty()){
//                            Glide.with(v.getContext()).load(imgThree)
//                                    .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                                    .into(ivFirstGuanZhuRVItem);
//                        }else {
//                            ivFirstGuanZhuRVItem.setVisibility(View.GONE);
////                            tvFirstGuanZHuRVItemContent.setVisibility(View.VISIBLE);
//
//
//                        }
//                    }
//                }
//
//
//                Log.d("guanzhu111",content+"");
//
//                tvFirstGuanZHuRVItemContent.setText(content);

//                Glide.with(v.getContext()).load(head)
////                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                        .into(civFirstGuanZhuRVItemHead);
////                tvFirstGuanZhuRVItemReaders.setText(readers);
//                if(position%3 == 1){
//                    tvFirstGuanZhuRVItemReaders.setText("9990");
//                }else if(position%3 ==2){
//                    tvFirstGuanZhuRVItemReaders.setText("11万");
//                }else {
//                    tvFirstGuanZhuRVItemReaders.setText("333");
//                }
//                tvFirstGuanZhuRVItemNick.setText(nick);
//            }

        }
    }


}
