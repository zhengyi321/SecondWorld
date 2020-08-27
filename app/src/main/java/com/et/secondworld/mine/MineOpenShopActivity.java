package com.et.secondworld.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.dialog.PayAwayDialog;
import com.et.secondworld.utils.GetJsonDataUtil;
import com.et.secondworld.widget.timeselect.TradeOptionsPickerView;
import com.google.gson.Gson;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.et.secondworld.R;
import com.et.secondworld.bean.OpenShopBean;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imagepicker.GlideLoader;
import com.et.secondworld.widget.imagepicker.ImagePicker;

import org.json.JSONArray;

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
 * @Date 2020/4/24
 **/
public class MineOpenShopActivity extends AppCompatActivity {

    @BindView(R.id.rly_mine_open_shop_back)
    RelativeLayout rlyMineOpenShopBack;
    @OnClick(R.id.rly_mine_open_shop_back)
    public void rlyMineOpenShopBackOnclick(){
        finish();
    }
    @BindView(R.id.rly_mine_open_shop_save)
    RelativeLayout rlyMineOpenShopSave;
    private boolean isFlag = true;
    @OnClick(R.id.rly_mine_open_shop_save)
    public void rlyMineOpenShopSaveOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            save();
        }
    }
    @BindView(R.id.et_mine_open_shop_shopname)
    EditText etMineOpenShopShopName;
    @BindView(R.id.et_mine_open_shop_realname)
    EditText etMineOpenShopRealName;
    @BindView(R.id.et_mine_open_shop_locate)
    EditText etMineOpenShopLocate;
    @BindView(R.id.tv_mine_open_shop_trade)
    TextView tvMineOpenShopTrade;
    @OnClick(R.id.tv_mine_open_shop_trade)
    public  void tvMineOpenShopTradeOnclick(){
        tradeSelect();
    }

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private void tradeSelect(){
        TradeOptionsPickerView optionsPickerView = new TradeOptionsPickerView.Builder(this,new TradeOptionsPickerView.OnOptionsSelectListener(){
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = "";
                String trade = options1Items.get(options1).getPickerViewText();
                String city = options2Items.get(options1).get(options2);
                tx = trade+"--"+city;
                tvMineOpenShopTrade.setText(tx);
            }
        }) .setTitleText("行业选择选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(15)
                .setOutSideCancelable(true)// default is true
                .build();
        optionsPickerView.setPicker(options1Items, options2Items,options3Items);//三级选择器
//        pvOptions.setDialogOutSideCancelable(true);
        optionsPickerView.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"trade.json");//获取assets目录下的json文件数据

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
    @BindView(R.id.et_mine_open_shop_identifycode)
    EditText etMineOpenShopIdentifyCode;
    @BindView(R.id.et_mine_open_shop_tel)
    EditText etMineOpenShopTel;
    @BindView(R.id.et_mine_open_shop_invitecode)
    EditText etMineOpenShopInviteCode;
    @BindView(R.id.et_mine_open_shop_social_code)
    EditText etMineOpenShopSocialCode;
    @BindView(R.id.rly_mine_open_shop_logo)
    RelativeLayout rlyMineOpenShopLogo;
    @BindView(R.id.iv_mine_open_shop_logo)
    ImageView ivMineOpenShopLogo;
    private long clickTime = 0;
    @OnClick(R.id.iv_mine_open_shop_logo)
    public void ivMineOpenShopLogoOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoLogo();
        }
    }

    @OnClick(R.id.rly_mine_open_shop_logo)
    public void rlyMineOpenShopLogoOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoLogo();
        }
    }
    @BindView(R.id.iv_mine_open_shop_license)
    ImageView ivMineOpenShopLicense;
    @OnClick(R.id.iv_mine_open_shop_license)
    public void ivMineOpenShopLicenseOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoLicense();
        }
    }
    @BindView(R.id.rly_mine_open_shop_license)
    RelativeLayout rlyMineOpenShopLicense;
    @OnClick(R.id.rly_mine_open_shop_license)
    public void rlyMineOpenShopLicenseOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoLicense();
        }
    }
    @BindView(R.id.iv_mine_open_shop_identifyface)
    ImageView ivMineOpenShopIdentifyFace;
    @OnClick(R.id.iv_mine_open_shop_identifyface)
    public void ivMineOpenShopIdentifyFaceOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoIdentifyFace();
        }
    }
    @BindView(R.id.rly_mine_open_shop_identifyface)
    RelativeLayout rlyMineOpenShopIdentifyFace;
    @OnClick(R.id.rly_mine_open_shop_identifyface)
    public void rlyMineOpenShopIdentifyFaceOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoIdentifyFace();
        }
    }
    @BindView(R.id.iv_mine_open_shop_identifyback)
    ImageView ivMineOpenShopIdentifyBack;
    @OnClick(R.id.iv_mine_open_shop_identifyback)
    public void ivMineOpenShopIdentifyBackOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoIdentifyBack();
        }
    }
    @BindView(R.id.rly_mine_open_shop_identifyback)
    RelativeLayout rlyMineOpenShopIdentifyBack;
    @OnClick(R.id.rly_mine_open_shop_identifyback)
    public void rlyMineOpenShopIdentifyBackOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoIdentifyBack();
        }
    }
    @BindView(R.id.iv_mine_open_shop_shopbg)
    ImageView ivMineOpenShopShopBg;
    @OnClick(R.id.iv_mine_open_shop_shopbg)
    public void ivMineOpenShopShopBgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoBg();
        }
    }
    @BindView(R.id.rly_mine_open_shop_shopbg)
    RelativeLayout rlyMineOpenShopShopBg;
    @OnClick(R.id.rly_mine_open_shop_shopbg)
    public void rlyMineOpenShopShopBgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoBg();
        }
    }
    private ArrayList<String> mSelectLogoImages = new ArrayList<>();
    private ArrayList<String> mSelectBgImages = new ArrayList<>();
    private ArrayList<String> mSelectLicenseImages = new ArrayList<>();
    private ArrayList<String> mSelectIdentifyFaceImages = new ArrayList<>();
    private ArrayList<String> mSelectIdentifyBackImages = new ArrayList<>();

    private final int SELECT_IMAGE_REQUEST_LOGO = 0x001;
    private final int SELECT_IMAGE_REQUEST_BG = 0x002;
    private final int SELECT_IMAGE_REQUEST_LICENSE = 0x003;
    private final int SELECT_IMAGE_REQUEST_IDENTIFYFACE = 0x004;
    private final int SELECT_IMAGE_REQUEST_IDENTIFYBACK = 0x005;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_open_shop);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this );
        initJsonData();
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

    private void takePhotoLogo(){
//        ivMineEditDataHeadLogo.setVisibility(View.GONE);
        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_LOGO);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
    }
    private void takePhotoLicense(){
//        ivMineEditDataHeadLogo.setVisibility(View.GONE);
        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_LICENSE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
    }
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
    private void takePhotoBg(){
//        ivMineEditDataHeadLogo.setVisibility(View.GONE);
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_IMAGE_REQUEST_LOGO:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths == null){
                        mSelectLogoImages.clear();
                        return;
                    }
                    if(imagePaths.size() == 0){
                        mSelectLogoImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectLogoImages.clear();
                    mSelectLogoImages.addAll(imagePaths);

                    rlyMineOpenShopLogo.setVisibility(View.GONE);
                    ivMineOpenShopLogo.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(mSelectLogoImages.get(0))
                            .into(ivMineOpenShopLogo);
                    break;
                case SELECT_IMAGE_REQUEST_LICENSE:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths == null){
                        mSelectLicenseImages.clear();
                        return;
                    }
                    if(imagePaths.size() == 0){
                        mSelectLicenseImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectLicenseImages.clear();
                    mSelectLicenseImages.addAll(imagePaths);
                    rlyMineOpenShopLicense.setVisibility(View.GONE);
                    ivMineOpenShopLicense.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(mSelectLicenseImages.get(0))
                            .into(ivMineOpenShopLicense);
                    break;
                case SELECT_IMAGE_REQUEST_IDENTIFYFACE:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths == null){
                        mSelectIdentifyFaceImages.clear();
                        return;
                    }
                    if(imagePaths.size() == 0){
                        mSelectIdentifyFaceImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectIdentifyFaceImages.clear();
                    mSelectIdentifyFaceImages.addAll(imagePaths);
                    rlyMineOpenShopIdentifyFace.setVisibility(View.GONE);
                    ivMineOpenShopIdentifyFace.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(mSelectIdentifyFaceImages.get(0))
                            .into(ivMineOpenShopIdentifyFace);
                    break;
                case SELECT_IMAGE_REQUEST_IDENTIFYBACK:
                    imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths == null){
                        mSelectIdentifyBackImages.clear();
                        return;
                    }
                    if(imagePaths.size() == 0){
                        mSelectIdentifyBackImages.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectIdentifyBackImages.clear();
                    mSelectIdentifyBackImages.addAll(imagePaths);
                    rlyMineOpenShopIdentifyBack.setVisibility(View.GONE);
                    ivMineOpenShopIdentifyBack.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(mSelectIdentifyBackImages.get(0))
                            .into(ivMineOpenShopIdentifyBack);
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
                    Glide.with(this)
                            .load(mSelectBgImages.get(0))
                            .into(ivMineOpenShopShopBg);
                    rlyMineOpenShopShopBg.setVisibility(View.GONE);
                    ivMineOpenShopShopBg.setVisibility(View.VISIBLE);
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



    private void save(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        Map<String,Object> map = new HashMap<>();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        String shopname = etMineOpenShopShopName.getText().toString();
        String realName = etMineOpenShopRealName.getText().toString();
        String locate = etMineOpenShopLocate.getText().toString();
        String trade = tvMineOpenShopTrade.getText().toString();
        String identifycode = etMineOpenShopIdentifyCode.getText().toString();
        String tel = etMineOpenShopTel.getText().toString();
        String invitecode = etMineOpenShopInviteCode.getText().toString();
        String socialcode = etMineOpenShopSocialCode.getText().toString();
        if(!account.isEmpty()){
            map.put("account",account);
        }

        if(shopname.isEmpty()){
            Toast.makeText(this,"请输入店铺名称",Toast.LENGTH_SHORT).show();
            return;
        }else {
            map.put("shopname",shopname);
        }
        if(realName.isEmpty()){
            Toast.makeText(this,"请输入您的真实姓名",Toast.LENGTH_SHORT).show();
            return;
        }else {
            map.put("realName",realName);
        }
        if(identifycode.isEmpty() || identifycode.length()<18){
            Toast.makeText(this,"请正确输入您的身份证号码",Toast.LENGTH_SHORT).show();
            return;
        }else {
            map.put("identifycode",identifycode);
        }
        if(socialcode.isEmpty() || socialcode.length()<18){
            Toast.makeText(this,"请正确输入您的社会统一信用代码",Toast.LENGTH_SHORT).show();
            return;
        }else {
            map.put("socialcode",socialcode);
        }
        if(tel.isEmpty()){
            Toast.makeText(this,"请输入您的联系电话",Toast.LENGTH_SHORT).show();
            return;
        }else {
            map.put("tel",tel);
        }
        if(locate.isEmpty()){
            Toast.makeText(this,"请输入地址",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mSelectLicenseImages.size() ==0) {
            Toast.makeText(this,"请上传您的营业执照",Toast.LENGTH_SHORT).show();
            return;
        }
//        isFlag = false;
            map.put("locate",locate);
            map.put("trade",trade);
            map.put("invitecode",invitecode);
        BitmapUtils bitmapUtils = new BitmapUtils();
        if(mSelectLogoImages.size() >0) {
            Bitmap bitmapLogo = compressImageFromFile(mSelectLogoImages.get(0));
            String base64_00Logo = bitmapUtils.bitmapConvertBase64(bitmapLogo);
            map.put("logo",base64_00Logo);
        }
        if(mSelectLicenseImages.size() >0) {
            Bitmap bitmapLicenses = compressImageFromFile(mSelectLicenseImages.get(0));
            String base64_00Licenses = bitmapUtils.bitmapConvertBase64(bitmapLicenses);
            map.put("licences", base64_00Licenses);
        }
        if(mSelectIdentifyFaceImages.size() >0) {
            Bitmap bitmapIdentifyFace = compressImageFromFile(mSelectIdentifyFaceImages.get(0));
            String base64_00IdentifyFace = bitmapUtils.bitmapConvertBase64(bitmapIdentifyFace);
            map.put("identifyface", base64_00IdentifyFace);
        }
        if(mSelectIdentifyBackImages.size() >0) {
            Bitmap bitmapIdentifyBack = compressImageFromFile(mSelectIdentifyBackImages.get(0));
            String base64_00IdentifyBack = bitmapUtils.bitmapConvertBase64(bitmapIdentifyBack);
            map.put("identifyback", base64_00IdentifyBack);
        }
        if(mSelectBgImages.size() >0) {
            Bitmap bitmapBg = compressImageFromFile(mSelectBgImages.get(0));
            //将图片显示到ImageView中
            String base64_00Bg = bitmapUtils.bitmapConvertBase64(bitmapBg);
            map.put("shopbg", base64_00Bg);
        }
        PayAwayDialog selectTownDialog = new PayAwayDialog(this).Build.setOnPaySuccessfulListener(new PayAwayDialog.OnPaySuccessfulListener() {
            @Override
            public void isSuccessful(boolean isSuccessful) {
                if (isSuccessful) {
                    String payorder = xcCacheManager.readCache(xcCacheSaveName.tradeorder);
                    map.put("payorder",payorder);
                    ShopNetWork shopNetWork = new ShopNetWork();
                    shopNetWork.openShopToNet(map, new Observer<OpenShopBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(OpenShopBean openShopBean) {
                            Toast.makeText(getBaseContext(),openShopBean.getMsg(),Toast.LENGTH_SHORT).show();
                            if(openShopBean.getIssuccess().equals("1")) {
//                    try {
                                xcCacheManager.writeCache(xcCacheSaveName.shopId,openShopBean.getShopid());
                                regHuanXin(openShopBean.getShopid());
//                        EMClient.getInstance().createAccount(registerBean.getId(), "11");//同步方法
//                        EMClient.getInstance().createAccount(openShopBean.getShopid(), "11");//同步方法
//                    } catch (HyphenateException e) {
//                        e.printStackTrace();
//                    }
                                finish();
                            }
                        }
                    });
                } else {

/*
                    String payorder = xcCacheManager.readCache(xcCacheSaveName.tradeorder);
                    map.put("payorder",payorder);
                    ShopNetWork shopNetWork = new ShopNetWork();
                    shopNetWork.openShopToNet(map, new Observer<OpenShopBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(OpenShopBean openShopBean) {
                            Toast.makeText(getBaseContext(),openShopBean.getMsg(),Toast.LENGTH_SHORT).show();
                            if(openShopBean.getIssuccess().equals("1")) {
//                    try {
                                xcCacheManager.writeCache(xcCacheSaveName.shopId,openShopBean.getShopid());
                                regHuanXin(openShopBean.getShopid());
//                        EMClient.getInstance().createAccount(registerBean.getId(), "11");//同步方法
//                        EMClient.getInstance().createAccount(openShopBean.getShopid(), "11");//同步方法
//                    } catch (HyphenateException e) {
//                        e.printStackTrace();
//                    }
                                finish();
                            }
                        }
                    });*/
                }
            }
            }).build(this, this, "300", "推广", "店铺推广", true);
//        }).build(this, this, "0.01", "推广", "店铺推广", true);
//        Log.d("costzz11",cost+"");

        selectTownDialog.show();

        //将图片显示到ImageView中


    }
    private void regHuanXin(String registerid){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    if(registerid == null || !registerid.isEmpty()){
                        return;
                    }
                    EMClient.getInstance().createAccount(registerid,"11");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // save current user
//								Toast.makeText(getApplicationContext(),"恭喜注册成功！！！", Toast.LENGTH_SHORT).show();
                            Log.d("注册环信", "Regist: onSuccess");
                            /*           activity.finish();*/
                        }
                    });
                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode=e.getErrorCode();
                            if(errorCode== EMError.NETWORK_ERROR){
//									Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_ALREADY_EXIST){
//									Toast.makeText(getApplicationContext(),"用户已存在！", Toast.LENGTH_SHORT).show();
                                Log.d("注册环信", "用户已存在！");
                            }else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
//									Toast.makeText(getApplicationContext(),"注册失败，无权限！", Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
//								    Toast.makeText(getApplicationContext(),"用户名不合法",Toast.LENGTH_SHORT).show();
                            }else{
//									Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        }).start();
    }

    private Bitmap compressImageFromFile(String srcPath) {

        BitmapUtils bitmapUtils = new BitmapUtils();
        Bitmap bitmap = bitmapUtils.getimage(srcPath);
        bitmap = bitmapUtils.compressImage(bitmap);
        bitmap = bitmapUtils.comp(bitmap);

        return bitmap;
    }

}
