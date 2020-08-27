package com.et.secondworld.widget.imageview.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.R;
import com.et.secondworld.widget.imagepicker.manager.ConfigManager;
import com.et.secondworld.widget.imagepicker.view.PinchImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * 大图浏览适配器（并不是比较好的方案，后期会用RecyclerView来实现）
 * Create by: chenWei.li
 * Date: 2018/8/30
 * Time: 上午12:57
 * Email: lichenwei.me@foxmail.com
 */
public class NormalImagePreViewAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mMediaFileList;
//    PinchImageView imageView;
//    LinkedList<PinchImageView> viewCache = new LinkedList<PinchImageView>();
    boolean isSet = false;
    public NormalImagePreViewAdapter(Context context, List<String> mediaFileList) {
        this.mContext = context;
        this.mMediaFileList = mediaFileList;
    }
    public void setImage(){
        isSet = true;
    }
//    public PinchImageView getImageView(){
//
//        return imageView;
//    }
    @Override
    public int getCount() {
        return mMediaFileList == null ? 0 : mMediaFileList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PinchImageView imageView;
//        if (viewCache.size() > 0) {
//            imageView = viewCache.remove();
//            imageView.reset();
//        } else {
            imageView = new PinchImageView(mContext);
//        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Activity)v.getContext()).finish();
                ((Activity)v.getContext()).overridePendingTransition(0, 0);
            }
        });
//        imageView = new PinchImageView(mContext);

        try {
            Glide.with(mContext)
                    .load(mMediaFileList.get(position))
                    .apply(new RequestOptions()
                            .dontAnimate()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE))
                    // 重点在这行
                    .into(imageView);
//            Glide.with(mContext).load(mMediaFileList.get(position)).into(imageView);
//            ConfigManager.getInstance().getImageLoader().loadPreImage(imageView, mMediaFileList.get(position));
//            ConfigManager.getInstance().getImageLoader().loadPreImage(imageView, "http://192.168.0.4/article/articleimg020200428093407725072250.jpg");
//            ImageLoader.getInstance().displayImage("http://192.168.0.4/article/articleimg020200428093407725072250.jpg",imageView, ImageLoaderUtils.options);
//            ConfigManager.getInstance().getImageLoader().loadPreImage(imageView, "http://192.168.0.4/article/articleimg020200428093407725072250.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if(isSet){
//            imageView.setImageResource(R.mipmap.imghead);
//        }
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        PinchImageView imageView = (PinchImageView) object;
        container.removeView(imageView);
//        viewCache.add(imageView);
    }
}
