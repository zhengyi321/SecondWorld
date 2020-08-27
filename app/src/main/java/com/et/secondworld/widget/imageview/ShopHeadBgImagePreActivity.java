package com.et.secondworld.widget.imageview;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.et.secondworld.R;
import com.et.secondworld.bean.SaveEditDataBean;
import com.et.secondworld.bean.UpdateShopEditDataBean;
import com.et.secondworld.network.MineNetWork;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imagepicker.GlideLoader;
import com.et.secondworld.widget.imagepicker.ImagePicker;
import com.et.secondworld.widget.imagepicker.activity.BaseActivity;
import com.et.secondworld.widget.imagepicker.manager.ConfigManager;
import com.et.secondworld.widget.imagepicker.manager.SelectionManager;
import com.et.secondworld.widget.imagepicker.provider.ImagePickerProvider;
import com.et.secondworld.widget.imagepicker.view.HackyViewPager;
import com.et.secondworld.widget.imageview.adapter.NormalImagePreViewAdapter;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;

/**
 * 大图预览界面
 * Create by: chenWei.li
 * Date: 2018/10/3
 * Time: 下午11:32
 * Email: lichenwei.me@foxmail.com
 */
public class ShopHeadBgImagePreActivity extends BaseActivity {

    public static final String IMAGE_POSITION = "imagePosition";
    private ArrayList<String> mMediaFileList;
    private int mPosition = 0;

    private TextView mTvTitle;
    private TextView mTvCommit;
    private TextView mTvSelect;
    private ImageView mIvPlay;
    private HackyViewPager mViewPager;
    private LinearLayout mLlPreSelect;
    private ImageView mIvPreCheck;
    private RelativeLayout rlyNormalPre;
    private NormalImagePreViewAdapter mImagePreViewAdapter;

    private final int SELECT_IMAGE_REQUEST_HEAD = 0x001;
    private final int SELECT_IMAGE_REQUEST_BG = 0x002;

    private ArrayList<String> mSelectHeadImages = new ArrayList<>();
    private ArrayList<String> mSelectBgImages = new ArrayList<>();
    private String type = "";
    @Override
    protected int bindLayout() {
        return R.layout.activity_head_bg_pre_image;
    }

    @Override
    protected void initView() {
        mTvTitle = findViewById(R.id.tv_actionBar_title);
        mTvCommit = findViewById(R.id.tv_actionBar_commit);
        mTvSelect = findViewById(R.id.tv_main_select);
        mIvPlay = findViewById(R.id.iv_main_play);
        mViewPager = findViewById(R.id.vp_main_preImage);
        mLlPreSelect = findViewById(R.id.ll_pre_select);
        mIvPreCheck = findViewById(R.id.iv_item_check);
        rlyNormalPre = findViewById(R.id.rly_normal_pre_image);
    }

    @Override
    protected void initListener() {

        findViewById(R.id.iv_actionBar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvTitle.setText(String.format("%d/%d", position + 1, mMediaFileList.size()));
//                setIvPlayShow(mMediaFileList.get(position));
//                updateSelectButton(mMediaFileList.get(position).getPath());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mLlPreSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果是单类型选取，判断添加类型是否满足（照片视频不能共存）
                if (ConfigManager.getInstance().isSingleType()) {
                    ArrayList<String> selectPathList = SelectionManager.getInstance().getSelectPaths();
                    if (!selectPathList.isEmpty()) {
                        //判断选中集合中第一项是否为视频
                        if (!SelectionManager.isCanAddSelectionPaths(mMediaFileList.get(mViewPager.getCurrentItem()), selectPathList.get(0))) {
                            //类型不同
                            Toast.makeText(ShopHeadBgImagePreActivity.this, getString(R.string.single_type_choose), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                boolean addSuccess = SelectionManager.getInstance().addImageToSelectList(mMediaFileList.get(mViewPager.getCurrentItem()));
                if (addSuccess) {
                    updateSelectButton(mMediaFileList.get(mViewPager.getCurrentItem()));
                    updateCommitButton();
                } else {
                    Toast.makeText(ShopHeadBgImagePreActivity.this, String.format(getString(R.string.select_image_max), SelectionManager.getInstance().getMaxCount()), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });

        mIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现播放视频的跳转逻辑(调用原生视频播放器)
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = FileProvider.getUriForFile(ShopHeadBgImagePreActivity.this, ImagePickerProvider.getFileProviderName(ShopHeadBgImagePreActivity.this), new File(mMediaFileList.get(mViewPager.getCurrentItem())));
                intent.setDataAndType(uri, "video/*");
                //给所有符合跳转条件的应用授权
                List<ResolveInfo> resInfoList = getPackageManager()
                        .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivity(intent);
            }
        });
        mTvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaFileList.clear();
                if(type!= null && type.equals("head")){
                    takePhotoHead();
                }
                if(type!= null && type.equals("bg")){
                    takePhotoBg();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> imagePaths;
//        Log.d("personnalnote12",resultCode+"this is"+resultCode+" PERSONNALNOTE"+requestCode);
        if (resultCode == RESULT_OK) {
//            Log.d("personnalnote12","this is ok");
            switch (requestCode) {
                case SELECT_IMAGE_REQUEST_HEAD:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (imagePaths == null) {
                        mMediaFileList.clear();
                        return;
                    }
                    if (imagePaths.size() == 0) {
                        mMediaFileList.clear();
                        return;
                    }
                    mMediaFileList.clear();
                    mMediaFileList.addAll(imagePaths);
                    mImagePreViewAdapter = new NormalImagePreViewAdapter(this, mMediaFileList);
                    mViewPager.setAdapter(mImagePreViewAdapter);
//                    Toast.makeText(this, "size" + mMediaFileList.size() + " path:" + mMediaFileList.get(0), Toast.LENGTH_LONG).show();
//                    mMediaFileList.clear();
//                    mMediaFileList.addAll(imagePaths);
                    mImagePreViewAdapter.notifyDataSetChanged();

//                    Log.d("medialist111",mMediaFileList.size()+"");
//                    mImagePreViewAdapter.setImage();
//                    mImagePreViewAdapter.notifyDataSetChanged();
//                    if(mImagePreViewAdapter.getImageView() !=null) {
//                        mImagePreViewAdapter.getImageView().setImageResource(R.mipmap.imghead);
//                    }
//                    mViewPager.setCurrentItem(0);
//                    Glide.with(this)
//                            .load(mSelectHeadImages.get(0))
//                            .apply(new RequestOptions().circleCrop()
//                                    .fallback(R.mipmap.imghead)
//                                    .error(R.mipmap.imghead)
//                            )
//                            .into(civMineEditdataHead);
                    saveLogo();
                    break;
                case SELECT_IMAGE_REQUEST_BG:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (imagePaths == null) {
                        mMediaFileList.clear();
                        return;
                    }
                    if (imagePaths.size() == 0) {
                        mMediaFileList.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mMediaFileList.clear();
                    mMediaFileList.addAll(imagePaths);

//                    Log.d("urizzzzz",mSelectBgImages.get(0));
//                    Uri uri = Uri.parse(mSelectBgImages.get(0));
                    BitmapUtils bitmapUtils = new BitmapUtils();
                    Bitmap temp = bitmapUtils.compressImageFromFile(mMediaFileList.get(0));

//                    Uri uri = Uri.fromFile(new File(mSelectBgImages.get(0)));
                    Uri uri = Uri.fromFile(bitmapUtils.getFile(temp));
                    Uri destination = Uri.fromFile(new File(getCacheDir(), "zz"));
                    UCrop.of(uri, destination)
                            .withAspectRatio(16, 12)
                            .start(this);
//                    mMediaFileList.clear();
//                    Crop.of(uri, destination).asSquare().start(this);


                    break;
                case UCrop.REQUEST_CROP:
                    handleCrop(resultCode, data);
                    break;
            }
        }
    }
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Log.d("urizzzzz",UCrop.getOutput(result)+"");

//            rlyMineEditDataBg.setVisibility(View.GONE);
//            ivMineEditDataBg.setVisibility(View.VISIBLE);
//            Glide.with(this)
//                    .load(UCrop.getOutput(result))
//                    .apply(new RequestOptions()
//                            .dontAnimate()
//                            .skipMemoryCache(true)
////                            .diskCacheStrategy(DiskCacheStrategy.NONE))
//                    // 重点在这行
//                    .into(ivMineEditDataBg);
            mMediaFileList.clear();
            try {
                File file = new File(new URI(UCrop.getOutput(result).toString()));
                mMediaFileList.add(file.toString());
                mImagePreViewAdapter = new NormalImagePreViewAdapter(this, mMediaFileList);
                mViewPager.setAdapter(mImagePreViewAdapter);
//                    Toast.makeText(this, "size" + mMediaFileList.size() + " path:" + mMediaFileList.get(0), Toast.LENGTH_LONG).show();
//                    mMediaFileList.clear();
//                    mMediaFileList.addAll(imagePaths);
                mImagePreViewAdapter.notifyDataSetChanged();
//                mImagePreViewAdapter.notifyDataSetChanged();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            saveBg();
//            resultView.setImageURI();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Toast.makeText(this, UCrop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void takePhotoHead(){
//        ivMineEditDataHeadLogo.setVisibility(View.GONE);
        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                .setImagePaths(mMediaFileList)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_HEAD);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode


    }
    private void takePhotoBg(){
//        ivMineEditDataHeadLogo.setVisibility(View.GONE);
        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                .setImagePaths(mMediaFileList)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_BG);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode


    }
    private void saveHead(){
//        ivMineEditDataLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.mipmap.pageloading).into(ivMineEditDataLoading);
        MineNetWork mineNetWork = new MineNetWork();
        Map<String,String> map = new HashMap<>();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        map.put("account",xcCacheManager.readCache(xcCacheSaveName.account));
//        map.put("nick",etMineEditDataNick.getText().toString());
//        map.put("personnalnote",tvMineEditDataPersonnalNote.getText().toString());
//        map.put("sex",tvMineEditDataSex.getText().toString());
//        map.put("birth",tvMineEditDataBirth.getText().toString());
//        map.put("locate",tvMineEditDataLocate.getText().toString());
//        map.put("trade",etMineEditDataTrade.getText().toString());
        if(mMediaFileList != null && mMediaFileList.size()>0) {
            BitmapUtils bitmapUtils = new BitmapUtils();
            Bitmap bitmapHead = bitmapUtils.compressImageFromFile(mMediaFileList.get(0));
            //将图片显示到ImageView中
            String base64_00Head = bitmapUtils.bitmapConvertBase64(bitmapHead);
            map.put("head",base64_00Head);
            //图片压缩

        }
        mineNetWork.saveEditDataHeadToNet(map, new Observer<SaveEditDataBean>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(getBaseContext(),"onCompleted",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
//                Log.d("mineeditdata111", "" + e);
//                Toast.makeText(getBaseContext(),""+e,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNext(SaveEditDataBean saveEditDataBean) {
                xcCacheManager.writeCache(xcCacheSaveName.shopHead, saveEditDataBean.getHead());
                finish();
//                ivMineEditDataLoading.setVisibility(View.GONE);
            }
        });
    }
    private void saveLogo(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
        Map<String,Object> map = new HashMap<>();
//        String shopname = etMineShopEditDataShopName.getText().toString();
//        String addr = etMineShopEditDataAddr.getText().toString();
//        String businesshour = tvMineShopEditDataBusinessHourMorning1.getText().toString().trim()+" "+tvMineShopEditDataBusinessHourAfternoon1.getText().toString().trim();
//        Log.d("mineshopeditdata1",businesshour);
//        String tel = etMineShopEditDataTel.getText().toString();
        map.put("shopid",shopid);
//        map.put("shopname",shopname);
//        map.put("addr",addr);
//        map.put("businesshour",businesshour);
//        map.put("tel",tel);
        BitmapUtils bitmapUtils = new BitmapUtils();
        if(mMediaFileList != null && mMediaFileList.size()>0) {

            Bitmap bitmapHead = bitmapUtils.compressImageFromFile(mMediaFileList.get(0));
            //将图片显示到ImageView中
            String base64_00Head = bitmapUtils.bitmapConvertBase64(bitmapHead);
            map.put("logo",base64_00Head);
            //图片压缩

        }
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.updateShopEditDataLogoToNet(map, new Observer<UpdateShopEditDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UpdateShopEditDataBean updateShopEditDataBean) {
                if(updateShopEditDataBean.getIssuccess().equals("1")){
//                    Toast.makeText(getBaseContext(),updateShopEditDataBean.getMsg(),Toast.LENGTH_SHORT).show();
                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                    XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
//                    xcCacheManager.writeCache(xcCacheSaveName.businessHour,businesshour);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopName,shopname);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopTel,tel);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopLocate,addr);
                    xcCacheManager.writeCache(xcCacheSaveName.shopHead,updateShopEditDataBean.getLogo());
                    finish();
//                    xcCacheManager.writeCache(xcCacheSaveName.shopBg,updateShopEditDataBean.getBg());
//                    finish();
                }
            }
        });
    }

    private void saveBg(){

        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        Map<String,Object> map = new HashMap<>();
        String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
//        String shopname = etMineShopEditDataShopName.getText().toString();
//        String addr = etMineShopEditDataAddr.getText().toString();
//        String businesshour = tvMineShopEditDataBusinessHourMorning1.getText().toString().trim()+" "+tvMineShopEditDataBusinessHourAfternoon1.getText().toString().trim();
//        Log.d("mineshopeditdata1",businesshour);
//        String tel = etMineShopEditDataTel.getText().toString();
        map.put("shopid",shopid);
//        map.put("shopname",shopname);
//        map.put("addr",addr);
//        map.put("businesshour",businesshour);
//        map.put("tel",tel);
        BitmapUtils bitmapUtils = new BitmapUtils();
        if(mMediaFileList != null && mMediaFileList.size()>0) {

            Bitmap bitmapBg = bitmapUtils.compressImageFromFile(mMediaFileList.get(0));
            //将图片显示到ImageView中
            String base64_00Bg= bitmapUtils.bitmapConvertBase64(bitmapBg);
            map.put("bg",base64_00Bg);
            //图片压缩
        }
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.updateShopEditDataBgToNet(map, new Observer<UpdateShopEditDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UpdateShopEditDataBean updateShopEditDataBean) {
                if(updateShopEditDataBean.getIssuccess().equals("1")){
//                    Toast.makeText(getBaseContext(),updateShopEditDataBean.getMsg(),Toast.LENGTH_SHORT).show();
                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                    XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
//                    xcCacheManager.writeCache(xcCacheSaveName.businessHour,businesshour);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopName,shopname);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopTel,tel);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopLocate,addr);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopHead,updateShopEditDataBean.getLogo());
                    xcCacheManager.writeCache(xcCacheSaveName.shopBg,updateShopEditDataBean.getBg());
                    finish();
                }
            }
        });
    }
    /*private void saveBg(){
//        ivMineEditDataLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.mipmap.pageloading).into(ivMineEditDataLoading);
        MineNetWork mineNetWork = new MineNetWork();
        Map<String,String> map = new HashMap<>();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        map.put("account",xcCacheManager.readCache(xcCacheSaveName.account));
//        map.put("nick",etMineEditDataNick.getText().toString());
//        map.put("personnalnote",tvMineEditDataPersonnalNote.getText().toString());
//        map.put("sex",tvMineEditDataSex.getText().toString());
//        map.put("birth",tvMineEditDataBirth.getText().toString());
//        map.put("locate",tvMineEditDataLocate.getText().toString());
//        map.put("trade",etMineEditDataTrade.getText().toString());
        if(mMediaFileList != null && mMediaFileList.size()>0) {
            BitmapUtils bitmapUtils = new BitmapUtils();
            Bitmap bitmapBg = bitmapUtils.compressImageFromFile(mMediaFileList.get(0));
            //将图片显示到ImageView中
            String base64_00Bg = bitmapUtils.bitmapConvertBase64(bitmapBg);
            map.put("bg",base64_00Bg);
            //图片压缩
        }
        mineNetWork.saveEditDataBgToNet(map, new Observer<SaveEditDataBean>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(getBaseContext(),"onCompleted",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
//                Log.d("mineeditdata111", "" + e);
//                Toast.makeText(getBaseContext(),""+e,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(SaveEditDataBean saveEditDataBean) {
                xcCacheManager.writeCache(xcCacheSaveName.shopBg,saveEditDataBean.getBg());
                finish();
//                ivMineEditDataLoading.setVisibility(View.GONE);
            }
        });
    }*/
    @Override
    protected void getData() {
        mMediaFileList = getIntent().getStringArrayListExtra("imgurlList");
        if(mMediaFileList == null){
            return;
        }
        type = getIntent().getStringExtra("type");
        ConfigManager.getInstance().setShowImage(true);
        ConfigManager.getInstance().setImageLoader(new GlideLoader());
//        Log.d("normalimagepre1",mMediaFileList.get(0));
//        mMediaFileList = DataUtil.getInstance().getMediaData();
        mPosition = getIntent().getIntExtra(IMAGE_POSITION, 0);
        mTvTitle.setText(String.format("%d/%d", mPosition + 1, mMediaFileList.size()));
        mTvTitle.setVisibility(View.GONE);
        mImagePreViewAdapter = new NormalImagePreViewAdapter(this, mMediaFileList);
        mViewPager.setAdapter(mImagePreViewAdapter);
        if(mPosition >= mMediaFileList.size()){
            return;
        }
        mViewPager.setCurrentItem(mPosition);
//        mImagePreViewAdapter.setImage();
//        mImagePreViewAdapter.notifyDataSetChanged();
        /*rlyNormalPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        //更新当前页面状态
//        setIvPlayShow(mMediaFileList.get(mPosition));
//        updateSelectButton(mMediaFileList.get(mPosition).getPath());
//        updateCommitButton();
    }

    /**
     * 更新确认按钮状态
     */
    private void updateCommitButton() {

        int maxCount = SelectionManager.getInstance().getMaxCount();

        //改变确定按钮UI
        int selectCount = SelectionManager.getInstance().getSelectPaths().size();
        if (selectCount == 0) {
            mTvCommit.setEnabled(false);
            mTvCommit.setText(getString(R.string.confirm));
            return;
        }
        if (selectCount < maxCount) {
            mTvCommit.setEnabled(true);
            mTvCommit.setText(String.format(getString(R.string.confirm_msg), selectCount, maxCount));
            return;
        }
        if (selectCount == maxCount) {
            mTvCommit.setEnabled(true);
            mTvCommit.setText(String.format(getString(R.string.confirm_msg), selectCount, maxCount));
            return;
        }
    }

    /**
     * 更新选择按钮状态
     */
    private void updateSelectButton(String imagePath) {
        boolean isSelect = SelectionManager.getInstance().isImageSelect(imagePath);
        if (isSelect) {
            mIvPreCheck.setImageDrawable(getResources().getDrawable(R.mipmap.icon_image_checked));
        } else {
            mIvPreCheck.setImageDrawable(getResources().getDrawable(R.mipmap.icon_image_check));
        }
    }

    /**
     * 设置是否显示视频播放按钮
     *
     * @param mediaFile
     */
   /* private void setIvPlayShow(MediaFile mediaFile) {
        mIvPlay.setVisibility(View.VISIBLE);
        if (mediaFile.getDuration() > 0) {
            mIvPlay.setVisibility(View.VISIBLE);
        } else {
            mIvPlay.setVisibility(View.GONE);
        }
    }*/

}
