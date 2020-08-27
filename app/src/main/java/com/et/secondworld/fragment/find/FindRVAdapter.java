package com.et.secondworld.fragment.find;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.LoginActivity;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetFindBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.utils.WindowUtils;
import com.et.secondworld.widget.imageview.CircleImageView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class FindRVAdapter extends RecyclerView.Adapter<FindRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetFindBean.ListBean> dataList ;
    private Boolean isNoMoreData = false;
    public void isNoMoreData(Boolean isNoMoreData){
        this.isNoMoreData = isNoMoreData;
    }
    public FindRVAdapter(List<GetFindBean.ListBean> dataList1 ){
        dataList = dataList1;
    }

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetFindBean.ListBean> list) {
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
    public void addData(int position, List<GetFindBean.ListBean> list) {
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
        return new FindRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_find_rv_item, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        @BindView(R.id.tv_find_rv_item_nums)
        TextView tvFindRVItemNums;
        @BindView(R.id.tv_find_rv_item_title)
        TextView tvFindRVItemTitle;
        @BindView(R.id.tv_find_rv_item_account)
        TextView tvFindRVItemAccount;
        @BindView(R.id.civ_find_rv_item_logo)
        CircleImageView civFindRVItemLogo;
        /*@BindView(R.id.tv_find_rv_item_readers)
        TextView tvFindRVItemReaders;*/
       /* @BindView(R.id.lly_find_rv_item_two)
        LinearLayout llyFindRVItemTwo;*/
        @BindView(R.id.lly_find_rv_item_three)
        LinearLayout llyFindRVItemThree;
        @BindView(R.id.rly_find_rv_item_one)
        RelativeLayout rlyFindRVItemOne;
        @BindView(R.id.rly_find_rv_item_img3)
        RelativeLayout rlyFindRVItemImg3;
        @BindView(R.id.iv_find_rv_item_one_img1)
        ImageView ivFindRVItemOneImg1;
       /* @BindView(R.id.iv_find_rv_item_two_img1)
        ImageView ivFindRVItemTwoImg1;
        @BindView(R.id.iv_find_rv_item_two_img2)
        ImageView ivFindRVItemTwoImg2;*/

        @BindView(R.id.iv_find_rv_item_img1)
        ImageView ivFindRVItemImg1;
        @BindView(R.id.iv_find_rv_item_img2)
        ImageView ivFindRVItemImg2;
        @BindView(R.id.iv_find_rv_item_img3)
        ImageView ivFindRVItemImg3;
        @BindView(R.id.iv_find_rv_item_shop)
        ImageView ivFindRVItemShop;

        @BindView(R.id.tv_find_rv_item_repost)
        TextView tvFindRVItemRepost;
        @BindView(R.id.tv_find_rv_item_praise)
        TextView tvFindRVItemPraise;
        @BindView(R.id.iv_find_rv_item_praise)
        ImageView ivFindRVItemPraise;
        @BindView(R.id.tv_find_rv_item_comments)
        TextView tvFindRVItemComments;
        @BindView(R.id.tv_find_rv_item_brows)
        TextView tvFindRVItemBrows;
        @BindView(R.id.tv_find_rv_item_content)
        TextView tvFindRVItemContent;
        @BindView(R.id.iv_find_rv_item_hot)
        ImageView ivFindRVItemHot;
        @BindView(R.id.iv_find_rv_item_title_logo)
        ImageView ivFindRVItemTitleLogo;
        /*@BindView(R.id.lly_splitview)
        LinearLayout llySplitView;*/
        @BindView(R.id.lly_find_item)
        LinearLayout llyFindItem;
        private long clickTime = 0;
        @OnClick(R.id.lly_find_item)
        public void llyFindItemOnclick(){

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
                String modules = dataList.get(pos).getModules();
                if (articleAccount == null) {
                    articleAccount = "";
                }
                if (articleid == null) {
                    articleid = "";
                }
                if (title == null) {
                    title = "";
                }
                if (modules == null) {
                    modules = "";
                }
                if (modules.equals("M3")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);
                    intent.putExtra("type", 0);

                    intent.putExtra("title", title);
                    v.getContext().startActivity(intent);
                }
                if (modules.equals("M1") || modules.equals("M2")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);
                    intent.putExtra("type", 0);

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


        @RequiresApi(api = Build.VERSION_CODES.N)
        void setData(Object data, int position) {
            pos = position;
           /* if(isNoMoreData){
                if(position == dataList.size() -1){
                    llyFindItem.setVisibility(View.GONE);
                    llySplitView.setVisibility(View.VISIBLE);
                }
//                return;

            }else {
                llyFindItem.setVisibility(View.VISIBLE);
                llySplitView.setVisibility(View.GONE);
            }*/
            String time = dataList.get(position).getShoptime();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date nows=new Date();
            Date date= null;
            Log.d("hours22",time + "");
            try {
                date = dateFormat.parse(time);
                long hous=(nows.getTime()-date.getTime())/(60*60*1000);
                Log.d("hours22",hous + "");
                if(hous <= 168){
                    ivFindRVItemShop.setVisibility(View.VISIBLE);
                }else {
                    ivFindRVItemShop.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int ispraised = dataList.get(position).getIspraised();
            int istop = dataList.get(position).getIstop();
            if(position == 0){
                if(istop == 1){
                    ivFindRVItemTitleLogo.setVisibility(View.VISIBLE);

                }else {
                    ivFindRVItemTitleLogo.setVisibility(View.GONE);
                }
            }
            if(position == 1){
                if(istop == 1){
                    ivFindRVItemTitleLogo.setVisibility(View.VISIBLE);

                }else {
                    ivFindRVItemTitleLogo.setVisibility(View.GONE);
                }
            }
            if(position == 2){
                if(istop == 1){
                    ivFindRVItemTitleLogo.setVisibility(View.VISIBLE);

                }else {
                    ivFindRVItemTitleLogo.setVisibility(View.GONE);
                }
            }

            if(ispraised == 1){
                ivFindRVItemPraise.setBackgroundResource(R.mipmap.praised);
            }else {
                ivFindRVItemPraise.setBackgroundResource(R.mipmap.praiseicon);
            }


            int hidecount = 0;
            int nums = position +1;
            tvFindRVItemNums.setText(""+nums);
            String imgOne =dataList.get(position).getImageone();
            String imgTwo =dataList.get(position).getImagetwo();
            String imgThree =dataList.get(position).getImagethree();

            int repost = dataList.get(position).getRepost();
            int comments = dataList.get(position).getComments();
            int good = dataList.get(position).getGood();
            int readers = dataList.get(position).getReaders();

           tvFindRVItemRepost.setText(repost+"");
           tvFindRVItemComments.setText(comments+"");
           tvFindRVItemPraise.setText(good+"");
           tvFindRVItemBrows.setText(readers+"");
            int isfire = dataList.get(position).getIsfire();
            if(isfire == 1){
                ivFindRVItemHot.setVisibility(View.VISIBLE);
            }else {
                ivFindRVItemHot.setVisibility(View.GONE);
            }
            if(imgOne ==null||imgOne.isEmpty()){
                hidecount++;
            }
            if(imgTwo ==null||imgTwo.isEmpty()){
                hidecount++;
            }
            if(imgThree ==null||imgThree.isEmpty()){
                hidecount++;
            }
            Log.d("hidecount11",hidecount+"");
            WindowUtils windowUtils = new WindowUtils((Activity) v.getContext());
            switch (hidecount){
                case 0:
                    rlyFindRVItemOne.setVisibility(View.GONE);
//                    llyFindRVItemTwo.setVisibility(View.GONE);
                    ivFindRVItemImg3.setVisibility(View.VISIBLE);
                    llyFindRVItemThree.setVisibility(View.VISIBLE);
                    tvFindRVItemContent.setVisibility(View.VISIBLE);
//                    ivFindRVItemImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    ViewGroup.LayoutParams layoutParams = ivFindRVItemImg1.getLayoutParams();
//                    layoutParams.width = (int)windowUtils.getWindowWidth()/3-19;
//                    layoutParams.height = (int)windowUtils.getWindowWidth()/3-19;
////                layoutParams.height = 596;
//                    ivFindRVItemImg1.setLayoutParams(layoutParams);
//                    ivFindRVItemImg2.setLayoutParams(layoutParams);
//                    ivFindRVItemImg3.setLayoutParams(layoutParams);
                    Glide.with(v).load(imgOne).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemImg1);
                    Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemImg2);
                    Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemImg3);
//                    ImageLoader.getInstance().displayImage(imgOne, ivFindRVItemImg1, ImageLoaderUtils.options);
//                    ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg2, ImageLoaderUtils.options);
//                    ImageLoader.getInstance().displayImage(imgThree, ivFindRVItemImg3, ImageLoaderUtils.options);
                    break;
                case 1:

                    rlyFindRVItemOne.setVisibility(View.GONE);
//                    llyFindRVItemTwo.setVisibility(View.GONE);
                    ivFindRVItemImg3.setVisibility(View.INVISIBLE);
                    llyFindRVItemThree.setVisibility(View.VISIBLE);
                    tvFindRVItemContent.setVisibility(View.VISIBLE);
//                    ivFindRVItemImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    ViewGroup.LayoutParams layoutParams2 = ivFindRVItemTwoImg1.getLayoutParams();
//                    layoutParams2.width = (int)windowUtils.getWindowWidth()/2-19;
//                    layoutParams2.height = (int)windowUtils.getWindowWidth()/2-19;
////                layoutParams.height = 596;
//                    ivFindRVItemTwoImg1.setLayoutParams(layoutParams2);
//                    ivFindRVItemTwoImg2.setLayoutParams(layoutParams2);
                    if(imgOne != null && !imgOne.isEmpty()) {
                        Glide.with(v).load(imgOne).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemImg1);
//                        ImageLoader.getInstance().displayImage(imgOne, ivFindRVItemImg1, ImageLoaderUtils.options);
                        if(imgTwo != null && !imgTwo.isEmpty()) {
                            Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemImg2);
//                            ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg2, ImageLoaderUtils.options);
                        }else {
                            if (imgThree != null && !imgThree.isEmpty()) {
                                Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemImg2);
//                            Im
                            }
                        }
                    }else {
                        if (imgTwo != null && !imgTwo.isEmpty()) {
                            Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemImg1);
//                        ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg1, ImageLoaderUtils.options);
                            if (imgThree != null && !imgThree.isEmpty()) {
                                Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemImg2);
//                            ImageLoader.getInstance().displayImage(imgThree, ivFindRVItemImg2, ImageLoaderUtils.options);
                            }
                        }
                    }
                    break;
                case 2:
                    rlyFindRVItemOne.setVisibility(View.VISIBLE);
//                    llyFindRVItemTwo.setVisibility(View.GONE);
                    llyFindRVItemThree.setVisibility(View.GONE);
//                    tvFindRVItemContent.setVisibility(View.GONE);
//                    Bitmap bitmap= null;
//                    ivFindRVItemImg1.setDrawingCacheEnabled(true);
//                    BitmapUtils bitmapUtils = new BitmapUtils();

//                    ViewGroup.LayoutParams layoutParams1 = ivFindRVItemImg1.getLayoutParams();
//                    layoutParams1.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    layoutParams1.height = (int)windowUtils.getWindowHeight()/3;
//                    layoutParams1.height = 400;
//                    ivFindRVItemOneImg1.setLayoutParams(layoutParams1);
//                    ivFindRVItemOneImg1.setScaleType(ImageView.ScaleType.FIT_START);
                    if(imgOne != null && !imgOne.isEmpty()) {
//                        FutureTarget<Bitmap> bitmaps = Glide.with(v)
//                                .asBitmap()
//                                .load(imgOne)
//                                .submit();
//                        try{
//                             bitmap = bitmaps.get();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                        Glide.with(v).load(imgOne).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemOneImg1);
//                        bitmap = bitmapUtils.getBitMBitmap(imgOne);
//                        ImageLoader.getInstance().displayImage(imgOne, ivFindRVItemImg1, ImageLoaderUtils.options);

                    }else {
                        if (imgTwo != null && !imgTwo.isEmpty()) {

//                        ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg1, ImageLoaderUtils.options);
//                        FutureTarget<Bitmap> bitmaps = Glide.with(v)
//                                .asBitmap()
//                                .load(imgTwo)
//                                .submit();
//                        try{
//                            bitmap = bitmaps.get();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                            Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemOneImg1);
                        }else {
                            if (imgThree != null && !imgThree.isEmpty()) {

//                        ImageLoader.getInstance().displayImage(imgThree, ivFindRVItemImg1, ImageLoaderUtils.options);
//                        FutureTarget<Bitmap> bitmaps = Glide.with(v)
//                                .asBitmap()
//                                .load(imgThree)
//                                .submit();
//                        try{
//                            bitmap = bitmaps.get();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                                Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.imghead)).into(ivFindRVItemOneImg1);
                            }
                        }
                    }
//                    ivFindRVItemImg1.setLayoutParams(layoutParams1);
//                    ivFindRVItemImg1.setDrawingCacheEnabled(true);
//                    bitmap = ((BitmapDrawable) ivFindRVItemImg1.getBackground()).getBitmap();
//                    ivFindRVItemImg1.setDrawingCacheEnabled(false);
//                    ViewGroup.LayoutParams layoutParams1 = ivFindRVItemImg1.getLayoutParams();
//                    layoutParams1.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    layoutParams1.height = (int)windowUtils.getWindowHeight()/3;
//                    ivFindRVItemImg1.setLayoutParams(layoutParams1);
//                    if(bitmap != null){
//                    final int imageview_width =ivFindRVItemImg1.getMeasuredWidth();

//                    double x = (double) ivFindRVItemImg1.getDrawable().getIntrinsicWidth()/(double) ivFindRVItemImg1.getDrawable().getIntrinsicHeight();
//                    double x = ((double) bitmap.getWidth())/((double) bitmap.getHeight());
//                    double x = (double) bitmap.getWidth()/(double) bitmap.getHeight();
//                    Log.d("findxxxxx11","width"+bitmap.getWidth() );
//                    Log.d("findxxxxx11","height"+bitmap.getHeight() );
//                    Log.d("findxxxxx11","x"+x );
//                    if( layoutParams1.height > 800 ){
//                        layoutParams1.height = (int)windowUtils.getWindowWidth()/2-19;
//                        layoutParams1.width = (int) (layoutParams1.height*x);
//                    /*if(x > 1){
//                        layoutParams.width = (int) (layoutParams.height*x);
//                    }else{
//                        layoutParams.width = (int) (layoutParams.height*x);
//                    }*/
//                    }else {
//                        layoutParams1.height = (int)windowUtils.getWindowWidth()/2;
//                        layoutParams1.width = (int) (layoutParams1.height*x);
//
//                    }
//                        Log.d("findxxxxx11","width"+layoutParams1.width );
//                        Log.d("findxxxxx11","height"+layoutParams1.height  );
                /*if(2.5<=x&& x<=3){
                    layoutParams.height = (int) (layoutParams.width/2.5);
                }else if(x>3){
                    layoutParams.height = (int) (layoutParams.width/3);
                }*/
//                    ivFindRVItemImg1.setLayoutParams(layoutParams1);
//                    ivFindRVItemImg1.setImageBitmap(bitmap);
//                    }
//                    ivFindRVItemImg1.setDrawingCacheEnabled(false);
                    break;
                case 3:
                    rlyFindRVItemOne.setVisibility(View.GONE);
//                    llyFindRVItemTwo.setVisibility(View.GONE);
                    llyFindRVItemThree.setVisibility(View.GONE);
//                    tvFindRVItemContent.setVisibility(View.VISIBLE);
//                    if(dataList.get(position).getContent() !=null) {
//                        String content = dataList.get(position).getContent();
//                        if(content.length() > 100){
//                            content = content.substring(0, 100);
//                        }
//                        tvFindRVItemContent.setText(content);
//                    }
                    break;
            }
            String content = dataList.get(position).getContent();
//                        if(content.length() > 100){
//                            content = content.substring(0, 100);
//                        }
            if(content == null || content.isEmpty()){
                tvFindRVItemContent.setVisibility(View.GONE);
            }
            tvFindRVItemContent.setText(content);
//            Log.d("widthheight","width:"+layoutParams.width+" height:"+layoutParams.height);
            if(dataList.get(position).getTitle() != null) {
                tvFindRVItemTitle.setText(dataList.get(position).getTitle());
            }
            Glide.with(v.getContext()).load(dataList.get(position).getLogo())
                        .apply(new RequestOptions().circleCrop().fallback(R.mipmap.imghead)
                                .error(R.mipmap.imghead))
                    .into(civFindRVItemLogo);
//            ImageLoader.getInstance().displayImage(dataList.get(position).getLogo(),civFindRVItemLogo,ImageLoaderUtils.options);
            if(dataList.get(position).getAccountid() != null){
                tvFindRVItemAccount.setText(dataList.get(position).getNick());
            }
//            tvFindRVItemReaders.setText(""+dataList.get(position).getReaders());
            /*if(dataList.get(position).getImageone() != null && !dataList.get(position).getImageone().isEmpty()){
                rlyFindRVItemImg1.setVisibility(View.VISIBLE);
                tvFindRVItemContent.setVisibility(View.GONE);
                layoutParams.width = (int)windowUtils.getWindowWidth()/3-19;
                layoutParams.height = (int)windowUtils.getWindowWidth()/3-19;
                ivFindRVItemImg1.setLayoutParams(layoutParams);
                ImageLoader.getInstance().displayImage(dataList.get(position).getImageone(),ivFindRVItemImg1, ImageLoaderUtils.options);
            }else{
                hidecount++;
                rlyFindRVItemImg1.setVisibility(View.GONE);
            }

            if(dataList.get(position).getImagetwo() != null && !dataList.get(position).getImagetwo().isEmpty()){
                rlyFindRVItemImg2.setVisibility(View.VISIBLE);
                tvFindRVItemContent.setVisibility(View.GONE);
                layoutParams.width = (int)windowUtils.getWindowWidth()/3-19;
//                layoutParams.height = 596;
                layoutParams.height = (int)windowUtils.getWindowWidth()/3-19;
                ivFindRVItemImg2.setLayoutParams(layoutParams);
                ImageLoader.getInstance().displayImage(dataList.get(position).getImagetwo(),ivFindRVItemImg2, ImageLoaderUtils.options);
            }else{
                hidecount++;
                rlyFindRVItemImg2.setVisibility(View.GONE);
            }

            if(dataList.get(position).getImagethree() != null && !dataList.get(position).getImagethree().isEmpty()){
                rlyFindRVItemImg3.setVisibility(View.VISIBLE);
                tvFindRVItemContent.setVisibility(View.GONE);

                layoutParams.width = (int)windowUtils.getWindowWidth()/3-19;
                layoutParams.height = (int)windowUtils.getWindowWidth()/3-19;
//                layoutParams.height = 596;
                ivFindRVItemImg3.setLayoutParams(layoutParams);
                ImageLoader.getInstance().displayImage(dataList.get(position).getImagethree(),ivFindRVItemImg3, ImageLoaderUtils.options);
            }else{
                hidecount++;
                rlyFindRVItemImg3.setVisibility(View.GONE);
            }
            if(hidecount  == 2){
                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                float x = layoutParams.width/layoutParams.height;
                if(layoutParams.height > 800 && layoutParams.height < 950){
                    layoutParams.height = (int)windowUtils.getWindowWidth()/2-19;
                    layoutParams.width = (int) (layoutParams.height*x);
                    *//*if(x > 1){
                        layoutParams.width = (int) (layoutParams.height*x);
                    }else{
                        layoutParams.width = (int) (layoutParams.height*x);
                    }*//*
                }else {
                    layoutParams.height = (int)windowUtils.getWindowWidth()/2;
                    layoutParams.width = (int) (layoutParams.height*x);

                }
                *//*if(2.5<=x&& x<=3){
                    layoutParams.height = (int) (layoutParams.width/2.5);
                }else if(x>3){
                    layoutParams.height = (int) (layoutParams.width/3);
                }*//*
                ivFindRVItemImg1.setLayoutParams(layoutParams);
                ivFindRVItemImg2.setLayoutParams(layoutParams);
                ivFindRVItemImg3.setLayoutParams(layoutParams);
            }*/
            /*if(dataList.get(position).getImageone() == null &&dataList.get(position).getImagetwo() == null &&dataList.get(position).getImagethree() == null ){
                tvFindRVItemContent.setVisibility(View.VISIBLE);
                if(dataList.get(position).getContent() !=null) {
                    String content = dataList.get(position).getContent();
                    if(content.length() > 100){
                        content = content.substring(0, 100);
                    }

                    tvFindRVItemContent.setText(content);
                }
                return;
            }
            if(dataList.get(position).getImageone().isEmpty() && dataList.get(position).getImagetwo().isEmpty() && dataList.get(position).getImagethree().isEmpty() ){

            }*/


        }
    }


}
