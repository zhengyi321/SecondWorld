package com.et.secondworld.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetPerfectDataBean;
import com.et.secondworld.network.MineNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imagepicker.GlideLoader;
import com.et.secondworld.widget.imagepicker.ImagePicker;

import java.util.ArrayList;
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
 * @Date 2020/7/14
 **/
public class MinePerfectDataActivity extends AppCompatActivity {


    @BindView(R.id.rly_mine_perfect_data_back)
    RelativeLayout rlyMinePerfectDataBack;
    @OnClick(R.id.rly_mine_perfect_data_back)
    public void rlyMinePerfectDataBackOnclick(){
        this.finish();
    }
    @BindView(R.id.tv_mine_perfect_data_save)
    TextView tvMinePerfectDataSave;
    private long clickTime = 0;
    @OnClick(R.id.tv_mine_perfect_data_save)
    public void tvMinePerfectDataSaveOnclick(){
        if(type == 0) {
            save();
        }
        if(type == 1){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                Intent intent = new Intent(this, MinePerfectDataUpdateActivity.class);
                startActivity(intent);
            }
        }
    }
    @BindView(R.id.et_mine_perfect_data_name)
    EditText etMinePerfectDataName;
    @BindView(R.id.et_mine_perfect_data_identify_pass)
    EditText etMinePerfectDataIdentifyPass;
    @BindView(R.id.tv_mine_perfect_data_query)
    TextView tvMinePerfectDataQuery;
    @OnClick(R.id.rly_mine_perfect_data)
    public void rlyMinePerfectDataOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @BindView(R.id.iv_mine_perfect_data_identifyface)
    ImageView ivMinePerFectDataIdentifyFace;
    @BindView(R.id.iv_mine_perfect_data_identifyback)
    ImageView ivMinePerFectDataIdentifyBack;
    @BindView(R.id.rly_mine_perfect_data_identifyback)
    RelativeLayout rlyMinePerFectDataIdentifyBack;
    @BindView(R.id.lly_mine_perfect_data_identify)
    LinearLayout llyMinePerfectDataIdentify;

    @BindView(R.id.rly_mine_perfect_data_identifyface)
    RelativeLayout rlyMinePerFectDataIdentifyFace;
    @OnClick({R.id.rly_mine_perfect_data_identifyface,R.id.iv_mine_perfect_data_identifyface})
    public void rlyMinePerfectDataIdentifyFaceOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoIdentifyFace();
        }
    }
    @OnClick({R.id.rly_mine_perfect_data_identifyback,R.id.iv_mine_perfect_data_identifyback})
    public void rlyMinePerfectDataIdentifyBackOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoIdentifyBack();
        }
    }

    private void save(){
        MineNetWork mineNetWork = new MineNetWork();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        String name = etMinePerfectDataName.getText().toString();
        String identifypass = etMinePerfectDataIdentifyPass.getText().toString();
        BitmapUtils bitmapUtils = new BitmapUtils();
        Map<String,Object> map = new HashMap<>();
        if(mSelectIdentifyFaceImages.size() >0) {
            Bitmap bitmapIdentifyFace = bitmapUtils.compressImageFromFile(mSelectIdentifyFaceImages.get(0));
            String base64_00IdentifyFace = bitmapUtils.bitmapConvertBase64(bitmapIdentifyFace);
            map.put("identifypassface", base64_00IdentifyFace);
        }
        if(mSelectIdentifyBackImages.size() >0) {
            Bitmap bitmapIdentifyBack = bitmapUtils.compressImageFromFile(mSelectIdentifyBackImages.get(0));
            String base64_00IdentifyBack = bitmapUtils.bitmapConvertBase64(bitmapIdentifyBack);
            map.put("identifypassback", base64_00IdentifyBack);
        }
        if(identifypass.length() != 18){
            Toast.makeText(this,"请输入18位身份证号码",Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("account",account);
        map.put("name",name);
        map.put("identifypass",identifypass);
        mineNetWork.perfectDataToNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                Toast.makeText(getBaseContext(),baseBean.getMsg(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private final int SELECT_IMAGE_REQUEST_IDENTIFYFACE = 0x004;
    private final int SELECT_IMAGE_REQUEST_IDENTIFYBACK = 0x005;
    private int type = 0;
    private void takePhotoIdentifyFace(){
//        ivMineEditDataHeadLogo.setVisibility(View.GONE);
        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_IDENTIFYFACE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
    }
    private void takePhotoIdentifyBack(){
//        ivMineEditDataHeadLogo.setVisibility(View.GONE);
        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_IDENTIFYBACK);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
    }

    private ArrayList<String> mSelectIdentifyFaceImages = new ArrayList<>();
    private ArrayList<String> mSelectIdentifyBackImages = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_perfect_data);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
//        initZoomScrollView();
//        Glide.with(this).load(R.mipmap.pageloading).into(ivMineMyShopLoading);
//        getShopDataFromNet();
        initStatusBar();
        initData();
//        initShopMark();
//        initShopArticle();
    }

    private void initData(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String auth = xcCacheManager.readCache(xcCacheSaveName.userAuth);
        String name = xcCacheManager.readCache(xcCacheSaveName.realName);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        String identifypass = xcCacheManager.readCache(xcCacheSaveName.identifyPass);
        if(auth != null && auth.equals("1")){
            tvMinePerfectDataQuery.setVisibility(View.VISIBLE);
            tvMinePerfectDataSave.setText("修改");
            etMinePerfectDataName.setText(name);
            String head = identifypass.substring(0,2);
            etMinePerfectDataIdentifyPass.setText(head+"************");
            llyMinePerfectDataIdentify.setVisibility(View.GONE);
            type = 1;
        }else {
            tvMinePerfectDataQuery.setVisibility(View.GONE);
        }
        Map<String,Object>map = new HashMap<>();
        map.put("account",account);
        MineNetWork mineNetWork = new MineNetWork();
        mineNetWork.getPerfectDataToNet(map, new Observer<GetPerfectDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetPerfectDataBean getPerfectDataBean) {
                if(getPerfectDataBean.getIssuccess().equals("1")){
                    tvMinePerfectDataQuery.setVisibility(View.VISIBLE);
                    String name = getPerfectDataBean.getName();
                    String identifypass = getPerfectDataBean.getIdentifycode();
                    tvMinePerfectDataSave.setText("修改");
                    tvMinePerfectDataQuery.setText("审核中");
                    etMinePerfectDataName.setText(name);
                    String head = identifypass.substring(0,2);
                    etMinePerfectDataIdentifyPass.setText(head+"************");
                    etMinePerfectDataName.setEnabled(false);
                    etMinePerfectDataIdentifyPass.setEnabled(false);
                    llyMinePerfectDataIdentify.setVisibility(View.GONE);
                    type = 3;
                }
            }
        });
    }

    /*沉浸式状态栏*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initStatusBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.blue);
//        switch (type) {
//            case "index":
//                tintManager.setStatusBarTintResource(R.color.color_main_index_topbar_blue_bg);
//                break;
//            case "release":
//                tintManager.setStatusBarTintResource(R.color.color_main_release_topbar_blue_bg);
//                break;
//            case "advice":
//                tintManager.setStatusBarTintResource(R.color.color_main_advice_content_white_bg);
//                break;
//            case "message":
//                tintManager.setStatusBarTintResource(R.color.color_main_message_content_white_bg);
//                break;
//            case "mine":
//                tintManager.setStatusBarTintResource(R.mipmap.top_big_blue_bg);
//                break;
//        }
    }
    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void setTranslucentStatus(boolean on) {
       /* Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.white ));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> imagePaths;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_IMAGE_REQUEST_IDENTIFYFACE:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (imagePaths == null) {
                        mSelectIdentifyFaceImages.clear();
                        return;
                    }
                    if (imagePaths.size() == 0) {
                        mSelectIdentifyFaceImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectIdentifyFaceImages.clear();
                    mSelectIdentifyFaceImages.addAll(imagePaths);
                    rlyMinePerFectDataIdentifyFace.setVisibility(View.GONE);
                    ivMinePerFectDataIdentifyFace.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(mSelectIdentifyFaceImages.get(0))
                            .into(ivMinePerFectDataIdentifyFace);
                    break;
                case SELECT_IMAGE_REQUEST_IDENTIFYBACK:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (imagePaths == null) {
                        mSelectIdentifyBackImages.clear();
                        return;
                    }
                    if (imagePaths.size() == 0) {
                        mSelectIdentifyBackImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectIdentifyBackImages.clear();
                    mSelectIdentifyBackImages.addAll(imagePaths);
                    rlyMinePerFectDataIdentifyBack.setVisibility(View.GONE);
                    ivMinePerFectDataIdentifyBack.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(mSelectIdentifyBackImages.get(0))
                            .into(ivMinePerFectDataIdentifyBack);
                    break;
            }
        }
    }
}
