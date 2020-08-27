package com.et.secondworld.forum.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.et.secondworld.R;
import com.et.secondworld.widget.imagepicker.GlideLoader;
import com.et.secondworld.widget.imagepicker.ImagePicker;
import com.et.secondworld.widget.imageview.NormalImagePreActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class ForumPostSelectImageRVAdapter extends RecyclerView.Adapter<ForumPostSelectImageRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<String> dataList = new ArrayList<>();
    private int count = 0;
    private Activity activity1;
    private ArrayList<String> mSelectImages ;
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(ArrayList<String> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        if(dataList.size() > count ){
            for(int i = dataList.size();i>count;i--) {
                dataList.remove(i-1);
            }
        }
        if(dataList.size() < count){
            dataList.add("");
        }

        notifyDataSetChanged();
    }

    public ForumPostSelectImageRVAdapter(int selectImgCount, Activity activity,ArrayList<String> mSelectImages1){
        count = selectImgCount;
        activity1 = activity;
        mSelectImages = mSelectImages1;
    }

    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, ArrayList<String> list) {
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
        return new ForumPostSelectImageRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_forum_post_after_select_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OneViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }


    @Override
    public int getItemCount() {

        return dataList.size();
    }



    public class OneViewHolder extends RecyclerView.ViewHolder {
        //        private ImageView ivImage;
        public View v;
        private int pos = 0;

        private final int SELECT_IMAGE_REQUEST_IMAGES = 0x002;
        private static final int PERMISSION_REQUEST_CODE = 0;
        @BindView(R.id.rly_forum_post_after_select_rv_item)
        RelativeLayout rlyForumPostAfterSelectRVItem;
        @OnClick(R.id.rly_forum_post_after_select_rv_item)
        public void rlyForumPostAfterSelectRVItemOnclick(){
            int isPermission1 = ContextCompat.checkSelfPermission(activity1, Manifest.permission.READ_EXTERNAL_STORAGE);
            int isPermission2 = ContextCompat.checkSelfPermission(activity1, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (isPermission1 == PackageManager.PERMISSION_GRANTED && isPermission2 == PackageManager.PERMISSION_GRANTED) {
                startActivity();
            } else {
                //申请权限
                ActivityCompat.requestPermissions(activity1, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }

        }
        private void startActivity(){
            ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                    .showCamera(true)//设置是否显示拍照按钮
                    .showImage(true)//设置是否展示图片
                    .showVideo(false)//设置是否展示视频
                    .setSingleType(true)//设置图片视频不能同时选择
                    .setMaxCount(count)//设置最大选择图片数目(默认为1，单选)
                    .setImagePaths(mSelectImages)
                    .setImageLoader(new GlideLoader())//设置自定义图片加载器
                    .start(activity1, SELECT_IMAGE_REQUEST_IMAGES);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
        }
        @BindView(R.id.iv_forum_post_after_select_rv_item)
        ImageView ivForumPostAfterSelectRVItem;
        @OnClick(R.id.iv_forum_post_after_select_rv_item)
        public void ivForumPostAfterSelectRVItemOnclick(){
            startImagePre(pos,ivForumPostAfterSelectRVItem);
        }
        @BindView(R.id.ib_forum_post_after_select_rv_item_remove)
        ImageButton ibForumPostAfterSelectRVItemRemove;
        @OnClick(R.id.ib_forum_post_after_select_rv_item_remove)
        public void ibForumPostAfterSelectRVItemRemoveOnclick(){
            dataList.remove(pos);
            mSelectImages.remove(pos);
            if(count > 1) {
                if (dataList.size() == count - 1 && !dataList.get(count - 2).isEmpty()) {
                    dataList.add("");

                }
            }else {
                dataList.add("");
//                rlyForumPostAfterSelectRVItem.setVisibility(View.VISIBLE);
//                ivForumPostAfterSelectRVItem.setVisibility(View.GONE);
            }
            notifyDataSetChanged();
        }
        public void llyForumOneItemOnclick(){
//            Toast.makeText(v.getContext(),"this is one"+pos,Toast.LENGTH_LONG).show();

        }

        private void startImagePre(int position,ImageView view){

            Intent intent =new Intent(v.getContext(), NormalImagePreActivity.class);
            intent.putStringArrayListExtra("imgurlList",(ArrayList<String>) mSelectImages);
            intent.putExtra("imagePosition",position);
//					ActivityOptionsCompat optionsCompat =
//							ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), v, "image");

            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view,100,100,400,400);
            v.getContext().startActivity(intent,compat.toBundle());
        }
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;


            if(dataList.get(position).isEmpty()){

                ibForumPostAfterSelectRVItemRemove.setVisibility(View.GONE);
                rlyForumPostAfterSelectRVItem.setVisibility(View.VISIBLE);
                ivForumPostAfterSelectRVItem.setVisibility(View.GONE);
            }else {
                Glide.with(v.getContext())
                        .asBitmap()
                        .load(dataList.get(position))
                        .into(ivForumPostAfterSelectRVItem);
                rlyForumPostAfterSelectRVItem.setVisibility(View.GONE);
                ivForumPostAfterSelectRVItem.setVisibility(View.VISIBLE);
                ibForumPostAfterSelectRVItemRemove.setVisibility(View.VISIBLE);
            }


        }
    }


}
