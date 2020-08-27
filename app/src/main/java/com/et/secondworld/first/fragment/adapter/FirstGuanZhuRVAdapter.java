package com.et.secondworld.first.fragment.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.et.secondworld.bean.GetFirstGuanZhuModuleBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.utils.WindowUtils;
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
public class FirstGuanZhuRVAdapter extends RecyclerView.Adapter<FirstGuanZhuRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetFirstGuanZhuModuleBean.ListBean> dataList ;

    public FirstGuanZhuRVAdapter(List<GetFirstGuanZhuModuleBean.ListBean> dataList1 ){
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
        return new FirstGuanZhuRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_first_guanzhu_rv_item, parent, false));
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

        @BindView(R.id.tv_first_guanzhu_rv_item_account_title)
        TextView tvFirstGuanZhuRVItemAccountTitle;
        @BindView(R.id.tv_first_guanzhu_rv_item_account_good)
        TextView tvFirstGuanZhuRVItemAccountGood;
        @BindView(R.id.tv_first_guanzhu_rv_item_account_comments)
        TextView tvFirstGuanZhuRVItemAccountComments;
        @BindView(R.id.iv_first_guanzhu_rv_item_account_img)
        ImageView ivFirstGuanZhuRVItemAccountImg;

        @BindView(R.id.lly_first_guanzhu_rv_item_module_account)
        LinearLayout llyFirstGuanZhuItemModuleAccount;
        @BindView(R.id.lly_first_guanzhu_rv_item_module_shop)
        LinearLayout llyFirstGuanZhuItemModuleShop;
        @BindView(R.id.tv_first_guanzhu_rv_item_nums)
        TextView tvFirstGuanZhuRVItemNums;
        @BindView(R.id.civ_first_guanzhu_rv_item_head)
        CircleImageView civFirstGuanZhuRVItemHead;
        @BindView(R.id.tv_first_guanzhu_rv_item_content)
        TextView tvFirstGuanZhuRVItemContent;
        @BindView(R.id.tv_first_guanzhu_rv_item_title)
        TextView tvFirstGuanZhuRVITemTitle;
        @BindView(R.id.tv_first_guanzhu_rv_item_account)
        TextView tvFirstGuanZhuRVItemAccount;
        @BindView(R.id.tv_first_guanzhu_rv_item_readers)
        TextView tvFirstGuanZhuRVItemReaders;
        @BindView(R.id.iv_first_guanzhu_rv_item_one_img1)
        ImageView ivFirstGuanZhuRVItemOneImg1;
        @BindView(R.id.iv_first_guanzhu_rv_item_two_img1)
        ImageView ivFirstGuanZhuRVItemTwoImg1;
        @BindView(R.id.iv_first_guanzhu_rv_item_two_img2)
        ImageView ivFirstGuanZhuRVItemTwoImg2;
        @BindView(R.id.iv_first_guanzhu_rv_item_three_img1)
        ImageView ivFirstGuanZhuRVItemThreeImg1;
        @BindView(R.id.iv_first_guanzhu_rv_item_three_img2)
        ImageView ivFirstGuanZhuRVItemThreeImg2;
        @BindView(R.id.iv_first_guanzhu_rv_item_three_img3)
        ImageView ivFirstGuanZhuRVItemThreeImg3;
        @BindView(R.id.rly_first_guanzhu_rv_item_one)
        RelativeLayout rlyFirstGuanZhuRVItemOne;
        @BindView(R.id.lly_first_guanzhu_rv_item_two)
        LinearLayout llyFirstGuanZhuRVItemTwo;
        @BindView(R.id.lly_first_guanzhu_rv_item_three)
        LinearLayout llyFirstGuanZhuRVItemThree;
        @OnClick(R.id.fly_first_guanzhu_rv_item)
        public void flyFirstGuanZhuItemOnclick(){
            String articleAccount = dataList.get(pos).getAccountid();
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
            if(model.equals("M3")) {
                Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);
                v.getContext().startActivity(intent);
            }else if(model.equals("M4")){
                Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
                intent.putExtra("articleAccount",articleAccount);
                intent.putExtra("articleid",articleid);

                intent.putExtra("title",title);

                v.getContext().startActivity(intent);
            }else if(model.equals("M1")||model.equals("M2")){
                Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
                intent.putExtra("articleAccount",articleAccount);
                intent.putExtra("articleid",articleid);

                intent.putExtra("title",title);

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
            int hidecount = 0;
            int nums = position +1;
            tvFirstGuanZhuRVItemNums.setText(""+nums);
//            ViewGroup.LayoutParams layoutParams = ivFirstGuanZhuRVItemImg1.getLayoutParams();
//            Log.d("widthheight","width:"+layoutParams.width+" height:"+layoutParams.height);
            if(dataList.get(position).getTitle() != null) {
                tvFirstGuanZhuRVITemTitle.setText(dataList.get(position).getTitle());
            }
            String imgOne =dataList.get(position).getImageone();
            String imgTwo =dataList.get(position).getImagetwo();
            String imgThree =dataList.get(position).getImagethree();
            String logo = dataList.get(position).getLogo();
            String module = dataList.get(position).getModules();
            if(module.equals("M4")){
                llyFirstGuanZhuItemModuleAccount.setVisibility(View.VISIBLE);
                llyFirstGuanZhuItemModuleShop.setVisibility(View.GONE);
                if(dataList.get(position).getTitle() != null){
                    tvFirstGuanZhuRVItemAccountTitle.setText(dataList.get(position).getTitle());
                }

                tvFirstGuanZhuRVItemAccountGood.setText(""+dataList.get(position).getGood());
                tvFirstGuanZhuRVItemAccountComments.setText(""+dataList.get(position).getComments());
                if(dataList.get(position).getImageone() != null){

                    Glide.with(v.getContext()).load(dataList.get(position).getImageone())
                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                            .into(ivFirstGuanZhuRVItemAccountImg);
//                ImageLoader.getInstance().displayImage(dataList.get(position).getImageone(),ivForumRVItemImg, ImageLoaderUtils.options);
                }
                return;
            }else {

                llyFirstGuanZhuItemModuleAccount.setVisibility(View.GONE);
                llyFirstGuanZhuItemModuleShop.setVisibility(View.VISIBLE);
                if (logo != null) {
                    Glide.with(v).load(logo).apply(new RequestOptions().fallback(R.mipmap.head)).into(civFirstGuanZhuRVItemHead);
                }

                if (dataList.get(position).getNick() != null) {
                    tvFirstGuanZhuRVItemAccount.setText(dataList.get(position).getNick());
                }
                if (imgOne == null || imgOne.isEmpty()) {
                    hidecount++;
                }
                if (imgTwo == null || imgTwo.isEmpty()) {
                    hidecount++;
                }
                if (imgThree == null || imgThree.isEmpty()) {
                    hidecount++;
                }
                WindowUtils windowUtils = new WindowUtils((Activity) v.getContext());
                tvFirstGuanZhuRVItemReaders.setText("" + dataList.get(position).getReaders());
                switch (hidecount) {
                    case 0:
                        rlyFirstGuanZhuRVItemOne.setVisibility(View.GONE);
                        llyFirstGuanZhuRVItemTwo.setVisibility(View.GONE);
                        llyFirstGuanZhuRVItemThree.setVisibility(View.VISIBLE);
                        tvFirstGuanZhuRVItemContent.setVisibility(View.GONE);
//                    ivFindRVItemImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ViewGroup.LayoutParams layoutParams = ivFirstGuanZhuRVItemThreeImg1.getLayoutParams();
                        layoutParams.width = (int) windowUtils.getWindowWidth() / 3 - 19;
                        layoutParams.height = (int) windowUtils.getWindowWidth() / 3 - 19;
//                layoutParams.height = 596;
                        ivFirstGuanZhuRVItemThreeImg1.setLayoutParams(layoutParams);
                        ivFirstGuanZhuRVItemThreeImg2.setLayoutParams(layoutParams);
                        ivFirstGuanZhuRVItemThreeImg3.setLayoutParams(layoutParams);
                        Glide.with(v).load(imgOne).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemThreeImg1);
                        Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemThreeImg2);
                        Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemThreeImg3);
//                    ImageLoader.getInstance().displayImage(imgOne, ivFindRVItemImg1, ImageLoaderUtils.options);
//                    ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg2, ImageLoaderUtils.options);
//                    ImageLoader.getInstance().displayImage(imgThree, ivFindRVItemImg3, ImageLoaderUtils.options);
                        break;
                    case 1:

                        rlyFirstGuanZhuRVItemOne.setVisibility(View.GONE);
                        llyFirstGuanZhuRVItemTwo.setVisibility(View.VISIBLE);
                        llyFirstGuanZhuRVItemThree.setVisibility(View.GONE);
                        tvFirstGuanZhuRVItemContent.setVisibility(View.GONE);
//                    ivFindRVItemImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ViewGroup.LayoutParams layoutParams2 = ivFirstGuanZhuRVItemTwoImg1.getLayoutParams();
                        layoutParams2.width = (int) windowUtils.getWindowWidth() / 2 - 19;
                        layoutParams2.height = (int) windowUtils.getWindowWidth() / 2 - 19;
//                layoutParams.height = 596;
                        ivFirstGuanZhuRVItemTwoImg1.setLayoutParams(layoutParams2);
                        ivFirstGuanZhuRVItemTwoImg2.setLayoutParams(layoutParams2);
                        if (imgOne != null && !imgOne.isEmpty()) {
                            Glide.with(v).load(imgOne).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemTwoImg1);
//                        ImageLoader.getInstance().displayImage(imgOne, ivFindRVItemImg1, ImageLoaderUtils.options);
                            if (imgTwo != null && !imgTwo.isEmpty()) {
                                Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemTwoImg2);
//                            ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg2, ImageLoaderUtils.options);
                            }
                            if (imgThree != null && !imgThree.isEmpty()) {
                                Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemTwoImg2);
//                            ImageLoader.getInstance().displayImage(imgThree, ivFindRVItemImg2, ImageLoaderUtils.options);
                            }
                        }
                        if (imgTwo != null && !imgTwo.isEmpty()) {
                            Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemTwoImg1);
//                        ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg1, ImageLoaderUtils.options);
                            if (imgThree != null && !imgThree.isEmpty()) {
                                Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemTwoImg2);
//                            ImageLoader.getInstance().displayImage(imgThree, ivFindRVItemImg2, ImageLoaderUtils.options);
                            }
                        }
                        break;
                    case 2:
                        rlyFirstGuanZhuRVItemOne.setVisibility(View.VISIBLE);
                        llyFirstGuanZhuRVItemTwo.setVisibility(View.GONE);
                        llyFirstGuanZhuRVItemThree.setVisibility(View.GONE);
                        tvFirstGuanZhuRVItemContent.setVisibility(View.GONE);
//                    Bitmap bitmap= null;
//                    ivFindRVItemImg1.setDrawingCacheEnabled(true);
//                    BitmapUtils bitmapUtils = new BitmapUtils();

//                    ViewGroup.LayoutParams layoutParams1 = ivFindRVItemImg1.getLayoutParams();
//                    layoutParams1.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    layoutParams1.height = (int)windowUtils.getWindowHeight()/3;
//                    layoutParams1.height = 400;
//                    ivFindRVItemOneImg1.setLayoutParams(layoutParams1);
//                    ivFindRVItemOneImg1.setScaleType(ImageView.ScaleType.FIT_START);
                        if (imgOne != null && !imgOne.isEmpty()) {
//                        FutureTarget<Bitmap> bitmaps = Glide.with(v)
//                                .asBitmap()
//                                .load(imgOne)
//                                .submit();
//                        try{
//                             bitmap = bitmaps.get();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                            Glide.with(v).load(imgOne).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemOneImg1);
//                        bitmap = bitmapUtils.getBitMBitmap(imgOne);
//                        ImageLoader.getInstance().displayImage(imgOne, ivFindRVItemImg1, ImageLoaderUtils.options);

                        }
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
                            Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemOneImg1);
                        }
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
                            Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstGuanZhuRVItemOneImg1);
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
                        rlyFirstGuanZhuRVItemOne.setVisibility(View.GONE);
                        llyFirstGuanZhuRVItemTwo.setVisibility(View.GONE);
                        llyFirstGuanZhuRVItemThree.setVisibility(View.GONE);
                        tvFirstGuanZhuRVItemContent.setVisibility(View.VISIBLE);
                        if (dataList.get(position).getContent() != null) {
                            String content = dataList.get(position).getContent();
                            if (content.length() > 100) {
                                content = content.substring(0, 100);
                            }
                            tvFirstGuanZhuRVItemContent.setText(content);
                        }
                        break;
                }
            }
        }
    }


}
