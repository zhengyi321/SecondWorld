package com.et.secondworld.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.widget.dialog.SexDialog;
import com.et.secondworld.widget.timeselect.TradeOptionsPickerView;
import com.google.gson.Gson;


import com.yalantis.ucrop.UCrop;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetEditDataBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.bean.SaveEditDataBean;
import com.et.secondworld.network.MineNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.utils.GetJsonDataUtil;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imagepicker.GlideLoader;
import com.et.secondworld.widget.imagepicker.ImagePicker;
import com.et.secondworld.widget.timeselect.ProvCityAreaOptionsPickerView;
import com.et.secondworld.widget.timeselect.SexPickerView;
import com.et.secondworld.widget.timeselect.TimePickerView;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
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

//https://github.com/Android-ScreenShot/AndroidScreenShotService截屏
public class MineEditDataActivity  extends AppCompatActivity {

    @BindView(R.id.iv_mine_editdata_head_logo)
    ImageView ivMineEditDataHeadLogo;
    @BindView(R.id.siv_mine_editdata_head)
    ImageView civMineEditdataHead;
    private long clickTime = 0;
    @OnClick(R.id.siv_mine_editdata_head)
    public void civMineEditDataHeadOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoHead();
        }
    }
    @BindView(R.id.tv_editdata_save)
    TextView tvEditDataSave;
    @OnClick(R.id.tv_editdata_save)
    public void tvEditDataSaveOnclick(){
//        this.finish();
        save();

    }
    @BindView(R.id.tv_mine_editdata_birth)
    TextView tvMineEditDataBirth;
    @OnClick(R.id.tv_mine_editdata_birth)
    public void tvMineEditDataBirthOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectBirth();
        }
    }
    @OnClick(R.id.rly_mine_editdata)
    public void rlyMineEditDataOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
    @BindView(R.id.tv_mine_editdata_sex)
    TextView tvMineEditDataSex;
    @OnClick(R.id.tv_mine_editdata_sex)
    public void tvMineEditDataSexOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectSex();
        }
    }
    @BindView(R.id.tv_mine_editdata_nick)
    TextView tvMineEditDataNick;
    @OnClick(R.id.tv_mine_editdata_nick)
    public void tvMineEditDataNickOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineEditDataNickActivity.class);
            intent.putExtra("nick", tvMineEditDataNick.getText().toString());
            startActivityForResult(intent, NICK);
        }
    }
    @BindView(R.id.tv_mine_editdata_personnalnote)
    TextView tvMineEditDataPersonnalNote;
    private final int PERSONNALNOTE = 0x007;
    private final int NICK = 0x008;
    private final int TRADE = 0x009;
    @OnClick(R.id.tv_mine_editdata_personnalnote)
    public void tvMineEditDataPersonnalNoteOnclick(){

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineEditDataPersonnalNoteActivity.class);
            intent.putExtra("personnalnote", tvMineEditDataPersonnalNote.getText().toString());
            startActivityForResult(intent, PERSONNALNOTE);
        }
    }
    @BindView(R.id.tv_mine_editdata_locate)
    TextView tvMineEditDataLocate;
    @OnClick(R.id.tv_mine_editdata_locate)
    public void tvMineEditDataLocateOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectLocate();
        }
    }
    @BindView(R.id.tv_mine_editdata_trade)
    TextView tvMineEditDataTrade;
    @OnClick(R.id.tv_mine_editdata_trade)
    public void tvMineEditDataTradeOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineEditDataTradeActivity.class);
            intent.putExtra("trade", tvMineEditDataTrade.getText().toString());
            startActivityForResult(intent, TRADE);
        }

    }
    @BindView(R.id.rly_mine_editdata_back)
    RelativeLayout rlyMineEditDataBack;
    @OnClick(R.id.rly_mine_editdata_back)
    public void rlyMineEditDataBackOnclick(){
        this.finish();
    }
    @BindView(R.id.rly_mine_editdata_bg)
    RelativeLayout rlyMineEditDataBg;
    @OnClick(R.id.rly_mine_editdata_bg)
    public void rlyMineEditDataBgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoBg();
        }
    }
    @BindView(R.id.iv_mine_editdata_bg)
    ImageView ivMineEditDataBg;
    @OnClick(R.id.iv_mine_editdata_bg)
    public void ivMineEditDataBgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoBg();
        }
    }
    @BindView(R.id.iv_mine_editdata_loading)
    ImageView ivMineEditDataLoading;

    private ArrayList<String> mSelectHeadImages = new ArrayList<>();
    private ArrayList<String> mSelectBgImages = new ArrayList<>();
    private  final int SELECT_IMAGE_REQUEST = 0x0011;
    private final int SELECT_IMAGE_REQUEST_HEAD = 0x001;
    private final int SELECT_IMAGE_REQUEST_BG = 0x002;
    private String takePhotoType = "";
//    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_editdata);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
//        ivMineEditDataLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.mipmap.pageloading).into(ivMineEditDataLoading);
        initJsonData();
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
    private void initData(){
        getData();
//        rlyMineEditDataBg.setVisibility(View.GONE);
//        ivMineEditDataBg.setVisibility(View.VISIBLE);
//        screenshot();
//        Glide.with(this).load(BitmapUtils.capture(this)).into(ivMineEditDataBg);
//        ivMineEditDataBg.setImageBitmap(BitmapUtils.capture(this));
//        sivMineEditdataHead.setVisibility(View.GONE);
//        ivMineEditDataHeadLogo.setBackgroundResource(R.mipmap.forumdetailone1);
//        Glide.with(this).asBitmap().load("http://n.sinaimg.cn/news/1_img/upload/cf3881ab/67/w1000h667/20200408/b2e7-iryninw4454695.jpg").into(ivMineEditDataHeadLogo);

//        ImageLoader.getInstance().displayImage("http://n.sinaimg.cn/news/1_img/upload/cf3881ab/67/w1000h667/20200408/66a8-iryninw4454635.jpg", ivMineEditDataHeadLogo, ImageLoaderUtils.options);

    }
    private void screenshot()
    {
        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null)
        {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "screenshot.png";

                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                Glide.with(this).load(file).into(ivMineEditDataHeadLogo);
//                ivMineEditDataHeadLogo.setImageResource(R.mipmap.imghead);
            } catch (Exception e) {
            }
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
                    if(imagePaths == null){
                        mSelectHeadImages.clear();
                        return;
                    }
                    if(imagePaths.size() == 0){
                        mSelectHeadImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectHeadImages.clear();
                    mSelectHeadImages.addAll(imagePaths);
                    Glide.with(this)
                            .load(mSelectHeadImages.get(0))
                            .apply(new RequestOptions().circleCrop()
                                    .fallback(R.mipmap.imghead)
                                    .error(R.mipmap.imghead)
                            )
                            .into(civMineEditdataHead);
                    saveHead();
                    break;
                case SELECT_IMAGE_REQUEST_BG:
                     imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths == null){
                        mSelectBgImages.clear();
                        return;
                    }
                    if(imagePaths.size() == 0){
                        mSelectBgImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectBgImages.clear();
                    mSelectBgImages.addAll(imagePaths);

//                    Log.d("urizzzzz",mSelectBgImages.get(0));
//                    Uri uri = Uri.parse(mSelectBgImages.get(0));
                    BitmapUtils bitmapUtils = new BitmapUtils();
                    Bitmap temp = compressImageFromFile(mSelectBgImages.get(0));

//                    Uri uri = Uri.fromFile(new File(mSelectBgImages.get(0)));
                    Uri uri = Uri.fromFile(bitmapUtils.getFile(temp));
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
                case PERSONNALNOTE:
//                    Log.d("personnalnote12","this is PERSONNALNOTE");
                    String result = data.getStringExtra("result");

                    tvMineEditDataPersonnalNote.setText(result);
                    break;
                case NICK:
//                    Log.d("personnalnote12","this is PERSONNALNOTE");
                    String nick = data.getStringExtra("result");

                    tvMineEditDataNick.setText(nick);
                    break;
                case TRADE:
//                    Log.d("personnalnote12","this is PERSONNALNOTE");
                    String trade = data.getStringExtra("result");

                    tvMineEditDataTrade.setText(trade);
                    break;
            }
        }

        /*if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE_REQUEST && data != null) {
                ArrayList<Image> selectImages = data.getParcelableArrayListExtra(SelectImageActivity.EXTRA_RESULT);
                mSelectImages.clear();
                mSelectImages.addAll(selectImages);


            }

        }*/
    }
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Log.d("urizzzzz",UCrop.getOutput(result)+"");

            rlyMineEditDataBg.setVisibility(View.GONE);
            ivMineEditDataBg.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(UCrop.getOutput(result))
                    .apply(new RequestOptions()
                            .dontAnimate()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE))
                     // 重点在这行
                    .into(ivMineEditDataBg);

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

    private void selectBirth(){
        TimePickerView timePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(String date) {
                String birth = date.substring(0, date.indexOf(" "));
//                tvMineEditDataBirth.setText(""+date);
                tvMineEditDataBirth.setText(""+birth);
                saveBirth();
            }
        });
        timePickerView.show();
    }
    private void selectSex(){
        /*SexPickerView sexPickerView = new SexPickerView.Builder(this).setOutSideCancelable(true).build();
        sexPickerView.setOnSexSelectListener(new SexPickerView.OnSexSelectListener() {
            @Override
            public void onTimeSelect(String date) {
                if(date.equals("0")) {
                    tvMineEditDataSex.setText("男");
                }else {
                    tvMineEditDataSex.setText("女");
                }
            }
        });
        sexPickerView.show();*/
        SexDialog sexDialog = new SexDialog(this).Build.setCallBackListener(new SexDialog.OnFinishClickListener() {
            @Override
            public void isQuery(String sex) {
                tvMineEditDataSex.setText(sex);
                saveSex();
            }
        }).build(this);
        sexDialog.show();
    }

    private void selectLocate(){
//        TradeOptionsPickerView optionsPickerView = new TradeOptionsPickerView.Builder(this,new TradeOptionsPickerView.OnOptionsSelectListener(){
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//
//            }
//        }) .setTitleText("城市选择")
//                .setDividerColor(Color.BLACK)
//                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
//                .setContentTextSize(15)
//                .setOutSideCancelable(true)// default is true
//                .build();
//
        ProvCityAreaOptionsPickerView pvOptions = new ProvCityAreaOptionsPickerView.Builder(this, new ProvCityAreaOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
              /*  String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);*/
              String tx = "";
                String province = options1Items.get(options1).getPickerViewText();
                String city = options2Items.get(options1).get(options2);
                String area = options3Items.get(options1).get(options2).get(options3);
                if(province.equals(city)){
                    tx = province+"-"+area;
                }else {
                    tx = province+"-"+city+"-"+area;
                }
             /*       page = 1;
                    getData2FromNet();*/
                tvMineEditDataLocate.setText(tx);
                saveLocate();
                /*   Toast.makeText(activity,tx,Toast.LENGTH_SHORT).show();*/
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(15)
                .setOutSideCancelable(true)// default is true
                .build();

//        pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
//        pvOptions.setDialogOutSideCancelable(true);
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
                        String quanIndex = AreaName.substring(0,1);
                        if(quanIndex != null && quanIndex.equals("全")){
                            continue;
                        }
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }



    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return detail;
    }
    private Bitmap compressImageFromFile(String srcPath) {

        BitmapUtils bitmapUtils = new BitmapUtils();
        Bitmap bitmap = bitmapUtils.getimage(srcPath);
        bitmap = bitmapUtils.comp(bitmap);
        bitmap = bitmapUtils.compressImage(bitmap);


        return bitmap;
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
        if(mSelectHeadImages != null && mSelectHeadImages.size()>0) {
            BitmapUtils bitmapUtils = new BitmapUtils();
            Bitmap bitmapHead = compressImageFromFile(mSelectHeadImages.get(0));
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
                xcCacheManager.writeCache(xcCacheSaveName.userHeadImgUrl, saveEditDataBean.getHead());
//                ivMineEditDataLoading.setVisibility(View.GONE);
            }
        });
    }
    private void saveBg(){
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
        if(mSelectBgImages != null && mSelectBgImages.size()>0) {
            BitmapUtils bitmapUtils = new BitmapUtils();
            Bitmap bitmapBg = compressImageFromFile(mSelectBgImages.get(0));
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
                xcCacheManager.writeCache(xcCacheSaveName.userBgUrl,saveEditDataBean.getBg());
//                ivMineEditDataLoading.setVisibility(View.GONE);
            }
        });
    }
    private void saveBirth(){
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
        map.put("birth",tvMineEditDataBirth.getText().toString());
//        map.put("locate",tvMineEditDataLocate.getText().toString());
//        map.put("trade",etMineEditDataTrade.getText().toString());

        mineNetWork.saveEditDataBirthToNet(map, new Observer<SaveEditDataBean>() {
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
//                xcCacheManager.writeCache(xcCacheSaveName.userBgUrl,saveEditDataBean.getBg());
//                ivMineEditDataLoading.setVisibility(View.GONE);
            }
        });
    }
    private void saveLocate(){
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
        map.put("locate",tvMineEditDataLocate.getText().toString());
//        map.put("trade",etMineEditDataTrade.getText().toString());

        mineNetWork.saveEditDataLocateToNet(map, new Observer<SaveEditDataBean>() {
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
                xcCacheManager.writeCache(xcCacheSaveName.locate,saveEditDataBean.getLocate());
//                ivMineEditDataLoading.setVisibility(View.GONE);
            }
        });
    }

    private void saveSex(){
//        ivMineEditDataLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.mipmap.pageloading).into(ivMineEditDataLoading);
        MineNetWork mineNetWork = new MineNetWork();
        Map<String,String> map = new HashMap<>();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        map.put("account",xcCacheManager.readCache(xcCacheSaveName.account));
//        map.put("nick",etMineEditDataNick.getText().toString());
//        map.put("personnalnote",tvMineEditDataPersonnalNote.getText().toString());
        map.put("sex",tvMineEditDataSex.getText().toString());
//        map.put("birth",tvMineEditDataBirth.getText().toString());
//        map.put("locate",tvMineEditDataLocate.getText().toString());
//        map.put("trade",etMineEditDataTrade.getText().toString());

        mineNetWork.saveEditDataSexToNet(map, new Observer<SaveEditDataBean>() {
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
//                xcCacheManager.writeCache(xcCacheSaveName.locate,saveEditDataBean.getLocate());
//                ivMineEditDataLoading.setVisibility(View.GONE);
            }
        });
    }

    private void save(){
        MineNetWork mineNetWork = new MineNetWork();
        Map<String,String> map = new HashMap<>();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        map.put("account",xcCacheManager.readCache(xcCacheSaveName.account));
        map.put("nick",tvMineEditDataNick.getText().toString());
        map.put("personnalnote",tvMineEditDataPersonnalNote.getText().toString());
        map.put("sex",tvMineEditDataSex.getText().toString());
        map.put("birth",tvMineEditDataBirth.getText().toString());
        map.put("locate",tvMineEditDataLocate.getText().toString());
        map.put("trade",tvMineEditDataTrade.getText().toString());
        if(mSelectHeadImages != null && mSelectHeadImages.size()>0) {
            BitmapUtils bitmapUtils = new BitmapUtils();
            Bitmap bitmapHead = compressImageFromFile(mSelectHeadImages.get(0));
            //将图片显示到ImageView中
            String base64_00Head = bitmapUtils.bitmapConvertBase64(bitmapHead);
            map.put("head",base64_00Head);
            //图片压缩

        }
        if(mSelectBgImages != null && mSelectBgImages.size()>0) {
            BitmapUtils bitmapUtils = new BitmapUtils();
            Bitmap bitmapBg = compressImageFromFile(mSelectBgImages.get(0));
            //将图片显示到ImageView中
            String base64_00Bg = bitmapUtils.bitmapConvertBase64(bitmapBg);
            map.put("bg",base64_00Bg);
            //图片压缩
        }
        mineNetWork.saveEditDataToNet(map, new Observer<SaveEditDataBean>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(getBaseContext(),"onCompleted",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
//                Log.d("mineeditdata111",""+e);
//                Toast.makeText(getBaseContext(),""+e,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(SaveEditDataBean saveEditDataBean) {
                xcCacheManager.writeCache(xcCacheSaveName.userHeadImgUrl,saveEditDataBean.getHead());
                xcCacheManager.writeCache(xcCacheSaveName.userBgUrl,saveEditDataBean.getBg());
                xcCacheManager.writeCache(xcCacheSaveName.userName,saveEditDataBean.getNick());
                xcCacheManager.writeCache(xcCacheSaveName.personnalNote,saveEditDataBean.getPersonnalnote());
                xcCacheManager.writeCache(xcCacheSaveName.locate,saveEditDataBean.getLocate());
                xcCacheManager.writeCache(xcCacheSaveName.trade,saveEditDataBean.getTrade());
                Toast.makeText(getBaseContext(),""+saveEditDataBean.getMsg(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void getData(){
//        ivMineEditDataLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.mipmap.pageloading).into(ivMineEditDataLoading);
        MineNetWork mineNetWork = new MineNetWork();
        Map<String,String> map = new HashMap<>();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        map.put("account",xcCacheManager.readCache(xcCacheSaveName.account));
        mineNetWork.getEditDataFromNet(map, new Observer<GetEditDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetEditDataBean getEditDataBean) {
                String nick = getEditDataBean.getNick();
                String personnalNote = getEditDataBean.getPersonnalnote();
                String sex = getEditDataBean.getSex();
                String birth = getEditDataBean.getBirth();
                String locate = getEditDataBean.getLocate();
                String trade = getEditDataBean.getTrade();
                String head = getEditDataBean.getHead();
//                Toast.makeText(getBaseContext(),"result:"+getEditDataBean.getMsg(),Toast.LENGTH_SHORT).show();
                if(nick != null){
                    tvMineEditDataNick.setText(nick);
                }
                if(personnalNote != null){
                    tvMineEditDataPersonnalNote.setText(personnalNote);
                }if(sex != null){
                    tvMineEditDataSex.setText(sex);
                }if(birth != null){
                    tvMineEditDataBirth.setText(birth);
                }if(locate != null){
                    tvMineEditDataLocate.setText(locate);
                }if(trade != null){
                    tvMineEditDataTrade.setText(trade);
                }
//                Log.d("editData",head);
                if(head != null){
                    Glide.with(getBaseContext())
                            .load(head)
                            .apply(new RequestOptions()
                                    .circleCrop()
                                            .fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead)
                                    )
                            // 重点在这行
                            .into(civMineEditdataHead);
//                    ImageLoader.getInstance().displayImage(head, civMineEditdataHead, ImageLoaderUtils.options);
                }
                ivMineEditDataLoading.setVisibility(View.GONE);

            }
        });
    }
}
