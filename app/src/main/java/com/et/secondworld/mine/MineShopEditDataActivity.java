package com.et.secondworld.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.et.secondworld.widget.behavior.CircleImageView;
import com.yalantis.ucrop.UCrop;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetShopEditDataBean;
import com.et.secondworld.bean.UpdateShopEditDataBean;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imagepicker.GlideLoader;
import com.et.secondworld.widget.imagepicker.ImagePicker;
import com.et.secondworld.widget.timeselect.MorningAfterNoonPickerView;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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
 * @Date 2020/5/8
 **/
public class MineShopEditDataActivity extends AppCompatActivity {


    @BindView(R.id.rly_mine_shop_editdata_back)
    RelativeLayout rlyMineShopEditDataBack;
    @OnClick(R.id.rly_mine_shop_editdata_back)
    public void rlyMineShopEditDataBackOnclick(){
        finish();
    }
    @BindView(R.id.bt_mine_shop_editdata_save)
    Button btMineShopEditDataSave;

    @OnClick(R.id.bt_mine_shop_editdata_save)
    public void btMineShopEditDataSaveOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            saveData();
        }
    }

    @BindView(R.id.siv_mine_shop_editdata_head)
    ImageView sivMineShopEditDataHead;
    @OnClick(R.id.siv_mine_shop_editdata_head)
    public void sivMineShopEditDataHeadOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoHead();
        }
    }
    @BindView(R.id.tv_mine_shop_editdata_shopname)
    TextView tvMineShopEditDataShopName;
    @BindView(R.id.tv_mine_shop_editdata_addr)
    TextView tvMineShopEditDataAddr;
    @BindView(R.id.tv_mine_shop_editdata_trade)
    TextView tvMineShopEditDataTrade;
    @BindView(R.id.tv_mine_shop_editdata_socialcode)
    TextView tvMineShopEditDataSocialCode;
    @BindView(R.id.tv_mine_shop_editdata_tel)
    TextView tvMineShopEditDataTel;
    private final int TEL = 0x008;
    @OnClick(R.id.tv_mine_shop_editdata_tel)
    public void tvMineShopEditDataTelOnClick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineShopEditDataTelActivity.class);
            intent.putExtra("tel", tvMineShopEditDataTel.getText().toString());
            startActivityForResult(intent, TEL);
        }
    }
    @BindView(R.id.tv_mine_shop_editdata_change)
    TextView tvMineShopEditDataChange;
    @OnClick(R.id.tv_mine_shop_editdata_change)
    public void tvMineShopEditDataChangeOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineShopEditDataChangeActivity.class);
            intent.putExtra("shopname", shopname);
            intent.putExtra("shopid", shopid);
            intent.putExtra("addr", addr);
            intent.putExtra("socialcode", socialcode);
            intent.putExtra("trade", trade);
            startActivity(intent);
        }
    }
    @BindView(R.id.tv_mine_shop_editdata_business_hour_morning1)
    TextView tvMineShopEditDataBusinessHourMorning1;
    @OnClick(R.id.tv_mine_shop_editdata_business_hour_morning1)
    public void tvMineShopEditDataBusinessHourMorning1Onclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        MorningAfterNoonPickerView timePickerView = new MorningAfterNoonPickerView(this, MorningAfterNoonPickerView.Type.MORNING_HOUR_MIN);
        timePickerView.setOnTimeSelectListener(new MorningAfterNoonPickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(String date) {
//                String min = date.substring(date.indexOf(" "),date.length());
                tvMineShopEditDataBusinessHourMorning1.setText(""+date);
                saveBusinessHour();
//                tvMineEditDataBirth.setText(""+birth);
            }
        });
        timePickerView.show();
    }
   /* @BindView(R.id.tv_mine_shop_editdata_business_hour_morning2)
    TextView tvMineShopEditDataBusinessHourMorning2;
    @OnClick(R.id.tv_mine_shop_editdata_business_hour_morning2)
    public void tvMineShopEditDataBusinessHourMorning2Onclick(){
        TimePickerView timePickerView = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(String date) {
//                String birth = date.substring(0, date.indexOf(" "));
                String min = date.substring(date.indexOf(" "),date.length());
                tvMineShopEditDataBusinessHourMorning2.setText(""+min);
//                tvMineEditDataBirth.setText(""+birth);
            }
        });
        timePickerView.show();
    }*/
    @BindView(R.id.tv_mine_shop_editdata_business_hour_afternoon1)
    TextView tvMineShopEditDataBusinessHourAfternoon1;
    @OnClick(R.id.tv_mine_shop_editdata_business_hour_afternoon1)
    public void tvMineShopEditDataBusinessHourAfternoon1Onclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        MorningAfterNoonPickerView timePickerView = new MorningAfterNoonPickerView(this, MorningAfterNoonPickerView.Type.AFTERNOON_HOUR_MIN);
        timePickerView.setOnTimeSelectListener(new MorningAfterNoonPickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(String date) {
//                String birth = date.substring(0, date.indexOf(" "));
//                String min = date.substring(date.indexOf(" "),date.length());
                tvMineShopEditDataBusinessHourAfternoon1.setText(""+date);
                saveBusinessHour();
//                tvMineEditDataBirth.setText(""+birth);
            }
        });
        timePickerView.show();
    }
   /* @BindView(R.id.tv_mine_shop_editdata_business_hour_afternoon2)
    TextView tvMineShopEditDataBusinessHourAfternoon2;
    @OnClick(R.id.tv_mine_shop_editdata_business_hour_afternoon2)
    public void tvMineShopEditDataBusinessHourAfternoon2Onclick(){
        TimePickerView timePickerView = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(String date) {
//                String birth = date.substring(0, date.indexOf(" "));
                String min = date.substring(date.indexOf(" "),date.length());
                tvMineShopEditDataBusinessHourAfternoon2.setText(""+min);
//                tvMineEditDataBirth.setText(""+birth);
            }
        });
        timePickerView.show();
    }*/
   @OnClick(R.id.rly_mine_shop_editdata)
   public void rlyMineShopEditDataOnclick(){
       InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
       // 隐藏软键盘
       imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
   }
    private long clickTime = 0;
    @BindView(R.id.rly_mine_shop_editdata_bg)
    RelativeLayout rlyMineShopEditDataBg;
    @OnClick(R.id.rly_mine_shop_editdata_bg)
    public void rlyMineShopEditDataBgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoBg();
        }
    }
    @BindView(R.id.iv_mine_shop_editdata_bg)
    ImageView ivMineShopEditDataBg;
    @OnClick(R.id.iv_mine_shop_editdata_bg)
    public void ivMineShopEditDataBgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoBg();
        }
    }
    @BindView(R.id.iv_mine_shop_editdata_loading)
    ImageView ivMineShopEditDataLoading;
   /* @BindView(R.id.rly_mine_shop_editdata_licence)
    RelativeLayout rlyMineShopEditDataLicence;
    @OnClick(R.id.rly_mine_shop_editdata_licence)
    public void rlyMineShopEditDataLicenceOnclick(){
        takePhotoLicence();
    }
    @BindView(R.id.iv_mine_shop_editdata_licence)
    ImageView ivMineShopEditDataLicence;
    @OnClick(R.id.iv_mine_shop_editdata_licence)
    public void ivMineShopEditDataLicenceOnclick(){
        takePhotoLicence();
    }*/
    String shopname = "";
    String addr = "";
    String trade = "";
    String socialcode = "";
    private final int SELECT_IMAGE_REQUEST_HEAD = 0x001;
    private final int SELECT_IMAGE_REQUEST_BG = 0x002;
    private final int SELECT_IMAGE_REQUEST_LICENCE = 0x003;
    private ArrayList<String> mSelectHeadImages = new ArrayList<>();
    private ArrayList<String> mSelectBgImages = new ArrayList<>();
    private ArrayList<String> mSelectLicenceImages = new ArrayList<>();
    private String shopid = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_shop_editdata);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        ivMineShopEditDataLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivMineShopEditDataLoading);
        initData();
        initStatusBar();
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
    boolean isFirst = true;
    private void initData(){
        ivMineShopEditDataLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivMineShopEditDataLoading);
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        Map<String,Object> map = new HashMap<>();
        map.put("shopid",shopid);
        Log.d("ddd11",shopid);
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.getShopEditDataFromNet(map, new Observer<GetShopEditDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetShopEditDataBean getShopEditDataBean) {
                if(getShopEditDataBean.getIssuccess().equals("1")){
                    String logo = getShopEditDataBean.getLogo();
                    shopname = getShopEditDataBean.getShopname();
                     addr = getShopEditDataBean.getAddr();
                    String businesshour = getShopEditDataBean.getBusinesshour();
                     trade = getShopEditDataBean.getTrade();
                     socialcode = getShopEditDataBean.getSocialcode();
                     String dataverify = getShopEditDataBean.getDataverify();
                     if(dataverify != null && dataverify.equals("0")){
                         tvMineShopEditDataChange.setText("审核中");
                         tvMineShopEditDataChange.setClickable(false);
                     }else if(dataverify != null && dataverify.equals("1")){
                         tvMineShopEditDataChange.setText("审核已通过");
                     }else if(dataverify != null && dataverify.equals("2")){
                         tvMineShopEditDataChange.setText("审核未通过");
                     }

                    String tel = getShopEditDataBean.getTel();
                     if(logo != null && !logo.isEmpty()) {
                         if(isFirst) {
                             Glide.with(getBaseContext())
                                     .load(logo)
                                     .apply(new RequestOptions()
//                                    .fallback(R.mipmap.head)
                                                     .fallback(R.mipmap.imghead)
                                                     .error(R.mipmap.imghead)
                                                     .circleCrop()
                                     )
                                     // 重点在这行
                                     .into(sivMineShopEditDataHead);
                            isFirst = false;
                         }
                     }
                    if(shopname != null){
                        tvMineShopEditDataShopName.setText(shopname);
                    }
                    if(addr != null){
                        tvMineShopEditDataAddr.setText(addr);
                    }
                    tvMineShopEditDataTrade.setText(trade);
                    tvMineShopEditDataSocialCode.setText(socialcode);
                    if(businesshour != null){
                        String[] hour = businesshour.split(" ");
                        if(hour.length > 1) {
//                            String[] morning = hour[0].split("~");
//                            String[] afternoon = hour[1].split("~");
//                            if(morning.length > 1){
//                                tvMineShopEditDataBusinessHourMorning1.setText(morning[0]);
//                                tvMineShopEditDataBusinessHourMorning2.setText(morning[1]);
//                            }
//                            if(afternoon.length > 1){
//                                tvMineShopEditDataBusinessHourAfternoon1.setText(afternoon[0]);
//                                tvMineShopEditDataBusinessHourAfternoon2.setText(afternoon[1]);
//                            }
                            tvMineShopEditDataBusinessHourMorning1.setText(hour[0]);
                            tvMineShopEditDataBusinessHourAfternoon1.setText(hour[1]);
                        }
                    }
                    if(tel != null){
                        tvMineShopEditDataTel.setText(tel);
                    }
                    ivMineShopEditDataLoading.setVisibility(View.GONE);
                }
            }
        });
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
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_HEAD);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode


    }
    private void takePhotoBg(){

        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_BG);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
    }

    private void takePhotoLicence(){

        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_LICENCE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> imagePaths;
        BitmapUtils bitmapUtils = new BitmapUtils();
//        Log.d("personnalnote12", resultCode + "this is" + resultCode + " PERSONNALNOTE" + requestCode);
        if (resultCode == RESULT_OK) {
//            Log.d("personnalnote12", "this is ok");
            switch (requestCode) {
                case SELECT_IMAGE_REQUEST_HEAD:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (imagePaths == null) {
                        mSelectHeadImages.clear();
                        return;
                    }
                    if (imagePaths.size() == 0) {
                        mSelectHeadImages.clear();
                        return;
                    }

                    mSelectHeadImages.clear();
                    mSelectHeadImages.addAll(imagePaths);
                    Log.d("error11",mSelectHeadImages.get(0)+"");
//                    Toast.makeText(this, "size" + mSelectHeadImages.size() + " path:" + mSelectHeadImages.get(0), Toast.LENGTH_LONG).show();
//                    BitmapUtils bitmapUtils = new BitmapUtils();

//                    Bitmap temp = compressImageFromFile(mSelectHeadImages.get(0));
//                    sivMineShopEditDataHead.setImageBitmap(temp);
//                    sivMineShopEditDataHead.setImageResource(R.mipmap.imghead);
                    Glide.with(this)
                            .load(mSelectHeadImages.get(0))
                            .apply(new RequestOptions().circleCrop()
                                    .fallback(R.mipmap.imghead)
                                    .error(R.mipmap.imghead)
                            )
                            .into(sivMineShopEditDataHead);
                    saveLogo();
                    break;
                case SELECT_IMAGE_REQUEST_LICENCE:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (imagePaths == null) {
                        mSelectLicenceImages.clear();
                        return;
                    }
                    if (imagePaths.size() == 0) {
                        mSelectLicenceImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectLicenceImages.clear();
                    mSelectLicenceImages.addAll(imagePaths);
//                    rlyMineShopEditDataLicence.setVisibility(View.GONE);
//                    ivMineShopEditDataLicence.setVisibility(View.VISIBLE);
//                    Glide.with(this)
//                            .load(mSelectLicenceImages.get(0))
//                            .into(ivMineShopEditDataLicence);
                    break;
                case SELECT_IMAGE_REQUEST_BG:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (imagePaths == null) {
                        mSelectBgImages.clear();
                        return;
                    }
                    if (imagePaths.size() == 0) {
                        mSelectBgImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectBgImages.clear();
                    mSelectBgImages.addAll(imagePaths);

                    Log.d("urizzzzz", mSelectBgImages.get(0));
//                    Uri uri = Uri.parse(mSelectBgImages.get(0));
//                    BitmapUtils bitmapUtils = new BitmapUtils();
                    Bitmap temp1 = compressImageFromFile(mSelectBgImages.get(0));

//                    Uri uri = Uri.fromFile(new File(mSelectBgImages.get(0)));
                    Uri uri = Uri.fromFile(bitmapUtils.getFile(temp1));
                    Uri destination = Uri.fromFile(new File(getCacheDir(), "zz"));
                    UCrop.of(uri, destination)
                            .withAspectRatio(16, 12)
                            .start(this);
                    mSelectBgImages.clear();
//                    Crop.of(uri, destination).asSquare().start(this);


                    break;
                case UCrop.REQUEST_CROP:
                    handleCrop(resultCode, data);
                    break;

                case TEL:
//                    Log.d("personnalnote12","this is PERSONNALNOTE");
                    String result = data.getStringExtra("result");

                    tvMineShopEditDataTel.setText(result);
                    break;
            }
        }
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
//            Log.d("urizzzzz",UCrop.getOutput(result)+"");
            rlyMineShopEditDataBg.setVisibility(View.GONE);
            ivMineShopEditDataBg.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(UCrop.getOutput(result))
                    .apply(new RequestOptions()
                            .fallback(R.mipmap.shopbg)
                            .dontAnimate()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE))
                    // 重点在这行
                    .into(ivMineShopEditDataBg);

            try {
                File file = new File(new URI(UCrop.getOutput(result).toString()));
                mSelectBgImages.add(file.toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            saveBg();

//            resultView.setImageURI();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Toast.makeText(this, UCrop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private Bitmap compressImageFromFile(String srcPath) {

        BitmapUtils bitmapUtils = new BitmapUtils();
        Bitmap bitmap = bitmapUtils.getimage(srcPath);
        bitmap = bitmapUtils.comp(bitmap);
        bitmap = bitmapUtils.compressImage(bitmap);


        return bitmap;
    }

    private void saveLogo(){
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
        if(mSelectHeadImages != null && mSelectHeadImages.size()>0) {

            Bitmap bitmapHead = compressImageFromFile(mSelectHeadImages.get(0));
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
//                    xcCacheManager.writeCache(xcCacheSaveName.shopBg,updateShopEditDataBean.getBg());
//                    finish();
                }
            }
        });
    }
    private void saveBg(){
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
        if(mSelectBgImages != null && mSelectBgImages.size()>0) {

            Bitmap bitmapBg = compressImageFromFile(mSelectBgImages.get(0));
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
//                    finish();
                }
            }
        });
    }
    private void saveBusinessHour(){
        Map<String,Object> map = new HashMap<>();
//        String shopname = etMineShopEditDataShopName.getText().toString();
//        String addr = etMineShopEditDataAddr.getText().toString();
        String businesshour = tvMineShopEditDataBusinessHourMorning1.getText().toString().trim()+" "+tvMineShopEditDataBusinessHourAfternoon1.getText().toString().trim();
//        Log.d("mineshopeditdata1",businesshour);
//        String tel = etMineShopEditDataTel.getText().toString();
        map.put("shopid",shopid);
//        map.put("shopname",shopname);
//        map.put("addr",addr);
        map.put("businesshour",businesshour);
//        map.put("tel",tel);
        /*BitmapUtils bitmapUtils = new BitmapUtils();
        if(mSelectBgImages != null && mSelectBgImages.size()>0) {

            Bitmap bitmapBg = compressImageFromFile(mSelectBgImages.get(0));
            //将图片显示到ImageView中
            String base64_00Bg= bitmapUtils.bitmapConvertBase64(bitmapBg);
            map.put("bg",base64_00Bg);
            //图片压缩
        }*/
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.updateShopEditDataBusinessHourToNet(map, new Observer<UpdateShopEditDataBean>() {
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
                    xcCacheManager.writeCache(xcCacheSaveName.businessHour,businesshour);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopName,shopname);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopTel,tel);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopLocate,addr);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopHead,updateShopEditDataBean.getLogo());
//                    xcCacheManager.writeCache(xcCacheSaveName.shopBg,updateShopEditDataBean.getBg());
//                    finish();
                }
            }
        });
    }

    private void saveData(){
        Map<String,Object> map = new HashMap<>();
//        String shopname = etMineShopEditDataShopName.getText().toString();
//        String addr = etMineShopEditDataAddr.getText().toString();
        String businesshour = tvMineShopEditDataBusinessHourMorning1.getText().toString().trim()+" "+tvMineShopEditDataBusinessHourAfternoon1.getText().toString().trim();
       Log.d("mineshopeditdata1",businesshour);
        String tel = tvMineShopEditDataTel.getText().toString();
        map.put("shopid",shopid);
//        map.put("shopname",shopname);
//        map.put("addr",addr);
        map.put("businesshour",businesshour);
        map.put("tel",tel);
        BitmapUtils bitmapUtils = new BitmapUtils();
        if(mSelectHeadImages != null && mSelectHeadImages.size()>0) {

            Bitmap bitmapHead = compressImageFromFile(mSelectHeadImages.get(0));
            //将图片显示到ImageView中
            String base64_00Head = bitmapUtils.bitmapConvertBase64(bitmapHead);
            map.put("logo",base64_00Head);
            //图片压缩

        }
        if(mSelectBgImages != null && mSelectBgImages.size()>0) {

            Bitmap bitmapBg = compressImageFromFile(mSelectBgImages.get(0));
            //将图片显示到ImageView中
            String base64_00Bg= bitmapUtils.bitmapConvertBase64(bitmapBg);
            map.put("bg",base64_00Bg);
            //图片压缩
        }
        if(mSelectLicenceImages != null && mSelectLicenceImages.size()>0) {

            Bitmap bitmapLicense = compressImageFromFile(mSelectLicenceImages.get(0));
            //将图片显示到ImageView中
            String base64_00License= bitmapUtils.bitmapConvertBase64(bitmapLicense);
            map.put("license",base64_00License);
            //图片压缩
        }
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.updateShopEditDataToNet(map, new Observer<UpdateShopEditDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UpdateShopEditDataBean updateShopEditDataBean) {
                if(updateShopEditDataBean.getIssuccess().equals("1")){
                    Toast.makeText(getBaseContext(),updateShopEditDataBean.getMsg(),Toast.LENGTH_SHORT).show();
                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                    XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                    xcCacheManager.writeCache(xcCacheSaveName.businessHour,businesshour);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopName,shopname);
                    xcCacheManager.writeCache(xcCacheSaveName.shopTel,tel);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopLocate,addr);
                    xcCacheManager.writeCache(xcCacheSaveName.shopHead,updateShopEditDataBean.getLogo());
                    xcCacheManager.writeCache(xcCacheSaveName.shopBg,updateShopEditDataBean.getBg());
                    finish();
                }
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        initData();
    }
}
