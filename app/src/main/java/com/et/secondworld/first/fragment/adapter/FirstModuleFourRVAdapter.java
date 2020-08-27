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
import com.et.secondworld.bean.GetFourBean;
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
public class FirstModuleFourRVAdapter extends RecyclerView.Adapter<FirstModuleFourRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetFourBean.ListBean>  dataList ;

    public FirstModuleFourRVAdapter(List<GetFourBean.ListBean> dataList1 ){
        dataList = dataList1;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetFourBean.ListBean> list) {
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
    public void addData(int position, List<GetFourBean.ListBean> list) {
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
        return new FirstModuleFourRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_first_module_four_rv_item, parent, false));
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
        @BindView(R.id.tv_first_module_four_rv_item_nums)
        TextView tvFirstModuleRVItemNums;
        @BindView(R.id.tv_first_module_four_rv_item_title)
        TextView tvFirstModuleRVItemTitle;
        @BindView(R.id.tv_first_module_four_rv_item_account)
        TextView tvFirstModuleRVItemAccount;
        @BindView(R.id.civ_first_module_four_rv_item_head)
        CircleImageView civFirstModuleFourRVItemHead;
        @BindView(R.id.tv_first_module_four_rv_item_readers)
        TextView tvFirstModuleRVItemReaders;
        @BindView(R.id.iv_first_module_four_rv_item_one_img1)
        ImageView ivFirstModuleRVItemOneImg1;
        @BindView(R.id.rly_first_module_four_rv_item_one)
        RelativeLayout rlyFirstModuleRVItemOne;
        @BindView(R.id.lly_first_module_four_rv_item_two)
        LinearLayout llyFirstModuleRVItemTwo;
        @BindView(R.id.lly_first_module_four_rv_item_three)
        LinearLayout llyFirstModuleRVItemThree;
        @BindView(R.id.iv_first_module_four_rv_item_two_img1)
        ImageView ivFirstModuleRVItemTwoImg1;
        @BindView(R.id.iv_first_module_four_rv_item_two_img2)
        ImageView ivFirstModuleRVItemTwoImg2;
        @BindView(R.id.iv_first_module_four_rv_item_three_img1)
        ImageView ivFirstModuleRVItemThreeImg1;
        @BindView(R.id.iv_first_module_four_rv_item_three_img2)
        ImageView ivFirstModuleRVItemThreeImg2;
        @BindView(R.id.iv_first_module_four_rv_item_three_img3)
        ImageView ivFirstModuleRVItemThreeImg3;
        @BindView(R.id.tv_first_module_four_rv_item_content)
        TextView tvFirstModuleRVItemContent;
        @BindView(R.id.lly_first_module_four_rv_item)
        LinearLayout llyFirstModuleRVItem;
        @OnClick(R.id.lly_first_module_four_rv_item)
        public void llyFirstModuleRVItemOnclick(){
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
        public void llyForumOneItemOnclick(){
//            Toast.makeText(v.getContext(),"this is one"+pos,Toast.LENGTH_LONG).show();

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
            tvFirstModuleRVItemNums.setText(""+nums);
//            ViewGroup.LayoutParams layoutParams = ivFirstModuleRVItemImg1.getLayoutParams();
//            Log.d("widthheight","width:"+layoutParams.width+" height:"+layoutParams.height);
            if(dataList.get(position).getTitle() != null) {
                tvFirstModuleRVItemTitle.setText(dataList.get(position).getTitle());
            }
            if(dataList.get(position).getNick() != null){
                tvFirstModuleRVItemAccount.setText(dataList.get(position).getNick());
            }
            Object imgOne =dataList.get(position).getImageone();
            Object imgTwo =dataList.get(position).getImagetwo();
            Object imgThree =dataList.get(position).getImagethree();
            String logo = dataList.get(position).getLogo();
            Glide.with(v).load(logo).apply(new RequestOptions().fallback(R.mipmap.head)).into(civFirstModuleFourRVItemHead);
            if(imgOne ==null||imgOne.toString().isEmpty()){
                hidecount++;
            }
            if(imgTwo ==null||imgTwo.toString().isEmpty()){
                hidecount++;
            }
            if(imgThree ==null||imgThree.toString().isEmpty()){
                hidecount++;
            }
            WindowUtils windowUtils = new WindowUtils((Activity) v.getContext());
            tvFirstModuleRVItemReaders.setText(""+dataList.get(position).getReaders());
            switch (hidecount){
                case 0:
                    rlyFirstModuleRVItemOne.setVisibility(View.GONE);
                    llyFirstModuleRVItemTwo.setVisibility(View.GONE);
                    llyFirstModuleRVItemThree.setVisibility(View.VISIBLE);
                    tvFirstModuleRVItemContent.setVisibility(View.GONE);
//                    ivFindRVItemImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ViewGroup.LayoutParams layoutParams = ivFirstModuleRVItemThreeImg1.getLayoutParams();
                    layoutParams.width = (int)windowUtils.getWindowWidth()/3-19;
                    layoutParams.height = (int)windowUtils.getWindowWidth()/3-19;
//                layoutParams.height = 596;
                    ivFirstModuleRVItemThreeImg1.setLayoutParams(layoutParams);
                    ivFirstModuleRVItemThreeImg2.setLayoutParams(layoutParams);
                    ivFirstModuleRVItemThreeImg3.setLayoutParams(layoutParams);
                    Glide.with(v).load(imgOne).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemThreeImg1);
                    Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemThreeImg2);
                    Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemThreeImg3);
//                    ImageLoader.getInstance().displayImage(imgOne, ivFindRVItemImg1, ImageLoaderUtils.options);
//                    ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg2, ImageLoaderUtils.options);
//                    ImageLoader.getInstance().displayImage(imgThree, ivFindRVItemImg3, ImageLoaderUtils.options);
                    break;
                case 1:

                    rlyFirstModuleRVItemOne.setVisibility(View.GONE);
                    llyFirstModuleRVItemTwo.setVisibility(View.VISIBLE);
                    llyFirstModuleRVItemThree.setVisibility(View.GONE);
                    tvFirstModuleRVItemContent.setVisibility(View.GONE);
//                    ivFindRVItemImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ViewGroup.LayoutParams layoutParams2 = ivFirstModuleRVItemTwoImg1.getLayoutParams();
                    layoutParams2.width = (int)windowUtils.getWindowWidth()/2-19;
                    layoutParams2.height = (int)windowUtils.getWindowWidth()/2-19;
//                layoutParams.height = 596;
                    ivFirstModuleRVItemTwoImg1.setLayoutParams(layoutParams2);
                    ivFirstModuleRVItemTwoImg2.setLayoutParams(layoutParams2);
                    if(imgOne != null && !imgOne.toString().isEmpty()) {
                        Glide.with(v).load(imgOne).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemTwoImg1);
//                        ImageLoader.getInstance().displayImage(imgOne, ivFindRVItemImg1, ImageLoaderUtils.options);
                        if(imgTwo != null && !imgTwo.toString().isEmpty()) {
                            Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemTwoImg2);
//                            ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg2, ImageLoaderUtils.options);
                        }
                        if(imgThree != null && !imgThree.toString().isEmpty()) {
                            Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemTwoImg2);
//                            ImageLoader.getInstance().displayImage(imgThree, ivFindRVItemImg2, ImageLoaderUtils.options);
                        }
                    }
                    if(imgTwo != null && !imgTwo.toString().isEmpty()) {
                        Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemTwoImg1);
//                        ImageLoader.getInstance().displayImage(imgTwo, ivFindRVItemImg1, ImageLoaderUtils.options);
                        if(imgThree != null && !imgThree.toString().isEmpty()) {
                            Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemTwoImg2);
//                            ImageLoader.getInstance().displayImage(imgThree, ivFindRVItemImg2, ImageLoaderUtils.options);
                        }
                    }
                    break;
                case 2:
                    rlyFirstModuleRVItemOne.setVisibility(View.VISIBLE);
                    llyFirstModuleRVItemTwo.setVisibility(View.GONE);
                    llyFirstModuleRVItemThree.setVisibility(View.GONE);
                    tvFirstModuleRVItemContent.setVisibility(View.GONE);
//                    Bitmap bitmap= null;
//                    ivFindRVItemImg1.setDrawingCacheEnabled(true);
//                    BitmapUtils bitmapUtils = new BitmapUtils();

//                    ViewGroup.LayoutParams layoutParams1 = ivFindRVItemImg1.getLayoutParams();
//                    layoutParams1.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    layoutParams1.height = (int)windowUtils.getWindowHeight()/3;
//                    layoutParams1.height = 400;
//                    ivFindRVItemOneImg1.setLayoutParams(layoutParams1);
//                    ivFindRVItemOneImg1.setScaleType(ImageView.ScaleType.FIT_START);
                    if(imgOne != null && !imgOne.toString().isEmpty()) {
//                        FutureTarget<Bitmap> bitmaps = Glide.with(v)
//                                .asBitmap()
//                                .load(imgOne)
//                                .submit();
//                        try{
//                             bitmap = bitmaps.get();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                        Glide.with(v).load(imgOne).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemOneImg1);
//                        bitmap = bitmapUtils.getBitMBitmap(imgOne);
//                        ImageLoader.getInstance().displayImage(imgOne, ivFindRVItemImg1, ImageLoaderUtils.options);

                    }
                    if(imgTwo != null && !imgTwo.toString().isEmpty()) {

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
                        Glide.with(v).load(imgTwo).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemOneImg1);
                    }
                    if(imgThree != null && !imgThree.toString().isEmpty()) {

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
                        Glide.with(v).load(imgThree).apply(new RequestOptions().fallback(R.mipmap.findad)).into(ivFirstModuleRVItemOneImg1);
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
                    rlyFirstModuleRVItemOne.setVisibility(View.GONE);
                    llyFirstModuleRVItemTwo.setVisibility(View.GONE);
                    llyFirstModuleRVItemThree.setVisibility(View.GONE);
                    tvFirstModuleRVItemContent.setVisibility(View.VISIBLE);
                    if(dataList.get(position).getContent() !=null) {
                        String content = dataList.get(position).getContent();
                        if(content.length() > 100){
                            content = content.substring(0, 100);
                        }
                        tvFirstModuleRVItemContent.setText(content);
                    }
                    break;
            }


        }
    }


}
