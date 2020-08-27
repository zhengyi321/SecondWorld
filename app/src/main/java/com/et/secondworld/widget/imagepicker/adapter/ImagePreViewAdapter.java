package com.et.secondworld.widget.imagepicker.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.et.secondworld.R;
import com.et.secondworld.widget.imagepicker.data.MediaFile;
import com.et.secondworld.widget.imagepicker.manager.ConfigManager;
import com.et.secondworld.widget.imagepicker.manager.SelectionManager;
import com.et.secondworld.widget.imagepicker.view.PinchImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 大图浏览适配器（并不是比较好的方案，后期会用RecyclerView来实现）
 * Create by: chenWei.li
 * Date: 2018/8/30
 * Time: 上午12:57
 * Email: lichenwei.me@foxmail.com
 */
public class ImagePreViewAdapter extends PagerAdapter {

    private Context mContext;
    private List<MediaFile> mMediaFileList;

    LinkedList<PinchImageView> viewCache = new LinkedList<PinchImageView>();
    private TextView tvCount;
    private ImageView ivCheck;
    private TextView mTvPreSelect;
    public ImagePreViewAdapter(Context context, List<MediaFile> mediaFileList, TextView tvCount1, ImageView ivCheck1,TextView mTvPreSelect) {
        this.mContext = context;
        this.mMediaFileList = mediaFileList;
        this.tvCount = tvCount1;
        this.ivCheck = ivCheck1;
        this.mTvPreSelect = mTvPreSelect;
    }

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
        if (viewCache.size() > 0) {
            imageView = viewCache.remove();
            imageView.reset();
        } else {
            imageView = new PinchImageView(mContext);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果是单类型选取，判断添加类型是否满足（照片视频不能共存）
                if (ConfigManager.getInstance().isSingleType()) {
                    ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                    if (!selectPathList.isEmpty()) {
                        //判断选中集合中第一项是否为视频
                        if (!SelectionManager.isCanAddSelectionPaths(mMediaFileList.get(position).getPath(), selectPathList.get(0))) {
                            //类型不同
                            Toast.makeText(v.getContext(), v.getContext().getString(R.string.single_type_choose), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                boolean addSuccess = SelectionManager.getInstance().addImageToSelectList(mMediaFileList.get(position).getPath());
                if (addSuccess) {
                    updateSelectButton(mMediaFileList.get(position).getPath());
                    updateCommitButton();
                } else {
                    Toast.makeText(v.getContext(), String.format( v.getContext().getString(R.string.select_image_max), SelectionManager.getInstance().getMaxCount()), Toast.LENGTH_SHORT).show();
                }
            }
        });
        try {
            ConfigManager.getInstance().getImageLoader().loadPreImage(imageView, mMediaFileList.get(position).getPath());
//            ConfigManager.getInstance().getImageLoader().loadPreImage(imageView, "http://192.168.0.4/article/articleimg020200428093407725072250.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        container.addView(imageView);
        return imageView;
    }
    /**
     * 更新选择按钮状态
     */
    private void updateSelectButton(String imagePath) {
        boolean isSelect = SelectionManager.getInstance().isImageSelect(imagePath);
        int selectCount = SelectionManager.getInstance().getSelectPaths().size();
        if (isSelect) {
            ivCheck.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.selected));
            mTvPreSelect.setVisibility(View.VISIBLE);
            mTvPreSelect.setText(selectCount+"");
//            ivCheck.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_image_checked));
        } else {
            mTvPreSelect.setVisibility(View.GONE);
            ivCheck.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.oval));
//            ivCheck.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_image_check));
        }
    }
    /**
     * 更新确认按钮状态
     */
    private void updateCommitButton() {

        int maxCount = SelectionManager.getInstance().getMaxCount();

        //改变确定按钮UI
        int selectCount = SelectionManager.getInstance().getSelectPaths().size();
        if (selectCount == 0) {
            tvCount.setEnabled(false);
            tvCount.setText(mContext.getString(R.string.confirm));
            return;
        }
        if (selectCount < maxCount) {
            tvCount.setEnabled(true);
            tvCount.setText(String.format(mContext.getString(R.string.confirm_msg), selectCount, maxCount));
            return;
        }
        if (selectCount == maxCount) {
            tvCount.setEnabled(true);
            tvCount.setText(String.format(mContext.getString(R.string.confirm_msg), selectCount, maxCount));
            return;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        PinchImageView imageView = (PinchImageView) object;
        container.removeView(imageView);
        viewCache.add(imageView);
    }
}
