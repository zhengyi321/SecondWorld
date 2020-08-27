package com.et.secondworld.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.bean.UpdateShopEditDataBean;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.utils.GetJsonDataUtil;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.et.secondworld.widget.imagepicker.GlideLoader;
import com.et.secondworld.widget.imagepicker.ImagePicker;
import com.et.secondworld.widget.timeselect.TradeOptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.et.secondworld.application.MyApplication.instance;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/13
 **/
public class MineShopEditDataChangeActivity extends AppCompatActivity {


    private String shopname ="";
    private String shopid ="";
    private String addr ="";
    private String socialcode ="";
    private String trade ="";
    @OnClick(R.id.rly_mine_shop_editdata_change)
    public void rlyMineShopEditDataChangeOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

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
        saveData();
    }
    @BindView(R.id.et_mine_shop_editdata_shopname)
    EditText etMineShopEditDataShopName;
    @BindView(R.id.et_mine_shop_editdata_addr)
    EditText etMineShopEditDataAddr;
    @BindView(R.id.tv_mine_shop_editdata_trade)
    TextView tvMineShopEditDataTrade;
    private long clickTime = 0;
    @OnClick(R.id.tv_mine_shop_editdata_trade)
    public void tvMineShopEditDataTradeOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            // 隐藏软键盘
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            tradeSelect();
        }
    }

    private String cancel = "0";
    private boolean flag = true;
    @BindView(R.id.tv_mine_shop_editdata_cancel)
    TextView tvMineShopEditDataCancel;
    @OnClick(R.id.rly_mine_shop_editdata_cancel)
    public void rlyMineShopEditDataCancelOnclick(){
        if(flag) {
            QueryCancelDialog queryCancelDialog = new QueryCancelDialog(this).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
                @Override
                public void isQuery(boolean isQuery) {
                    if (isQuery) {
                        cancel = "1";
                        tvMineShopEditDataCancel.setText("注销店铺申请中请点击上传");

                    }
                }
            }).build(this, "是否申请注销?");
            queryCancelDialog.show();
            flag = false;
        }
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
                tx = trade+"-"+city;
                tvMineShopEditDataTrade.setText(tx);
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
    @BindView(R.id.et_mine_shop_editdata_socialcode)
    EditText etMineShopEditDataSocialCode;

    @BindView(R.id.rly_mine_shop_editdata_licence)
    RelativeLayout rlyMineShopEditDataLicence;
    @OnClick(R.id.rly_mine_shop_editdata_licence)
    public void rlyMineShopEditDataLicenceOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoLicence();
        }
    }
    @BindView(R.id.iv_mine_shop_editdata_licence)
    ImageView ivMineShopEditDataLicence;
    @OnClick(R.id.iv_mine_shop_editdata_licence)
    public void ivMineShopEditDataLicenceOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoLicence();
        }
    }

    @BindView(R.id.rly_mine_shop_editdata_identifyface)
    RelativeLayout rlyMineShopEditDataIdentifyFace;
    @BindView(R.id.iv_mine_shop_editdata_identifyface)
    ImageView ivMineShopEditDataIdentifyFace;
    @OnClick({R.id.rly_mine_shop_editdata_identifyface,R.id.iv_mine_shop_editdata_identifyface})
    public void rlyMineShopEditDataIdentifyFaceOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoIdentifyFace();
        }
    }
    @OnClick({R.id.rly_mine_shop_editdata_identifyback,R.id.iv_mine_shop_editdata_identifyback})
    public void rlyMineShopEditDataIdentifyBackOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            takePhotoIdentifyBack();
        }
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

    private final int SELECT_IMAGE_REQUEST_IDENTIFYFACE = 0x004;
    private final int SELECT_IMAGE_REQUEST_IDENTIFYBACK = 0x005;
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
    @BindView(R.id.rly_mine_shop_editdata_identifyback)
    RelativeLayout rlyMineShopEditDataIdentifyBack;
    @BindView(R.id.iv_mine_shop_editdata_identifyback)
    ImageView ivMineShopEditDataIdentifyBack;
    @BindView(R.id.et_mine_shop_editdata_realname)
    EditText etMineShopEditDataRealName;
    @BindView(R.id.et_mine_shop_editdata_identifycode)
    EditText etMineShopEditDataIdentifyCode;

    /*@BindView(R.id.sp_mine_shop_editdata_trade1)
    Spinner spMineShopEditDataTrade1;
    @BindView(R.id.sp_mine_shop_editdata_trade2)
    Spinner spMineShopEditDataTrade2;*/
    private final int SELECT_IMAGE_REQUEST_LICENCE = 0x003;
    private ArrayList<String> mSelectLicenceImages = new ArrayList<>();

    private ArrayList<String> mSelectIdentifyFaceImages = new ArrayList<>();
    private ArrayList<String> mSelectIdentifyBackImages = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_shop_editdata_change);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initData();
        initJsonData();
        initStatusBar();
//        initSpinner();
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
        Intent intent = getIntent();
        shopname = intent.getStringExtra("shopname");
        shopid = intent.getStringExtra("shopid");
        addr = intent.getStringExtra("addr");
        socialcode = intent.getStringExtra("socialcode");
        trade = intent.getStringExtra("trade");
        etMineShopEditDataAddr.setText(addr);
        etMineShopEditDataShopName.setText(shopname);
        etMineShopEditDataSocialCode.setText(socialcode);
        tvMineShopEditDataTrade.setText(trade);
    }

/*
    private void initSpinner(){
//        ArrayList<String> spinnerList = new ArrayList<>();
//
//        spinnerList.add("全部关注");
//        spinnerList.add("互相关注");
        final String[] spinnerItems = {"农、林、牧、渔业","采矿业","制造业","电力、热力、燃气及水生产和供应业","建筑业","批发和零售业","交通运输、仓储和邮政业","住宿和餐饮业","信息传输、软件和信息技术服务业","金融业","房地产业","租赁和商务服务业","科学研究和技术服务业","水利、环境和公共设施管理业","居民服务、修理和其他服务业","教育","卫生和社会工作","文化、体育和娱乐业","公共管理、社会保障和社会组织","国际组织"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                R.layout.simple_spinner_item, spinnerItems);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spMineShopEditDataTrade1.setAdapter(spinnerAdapter);
        spMineShopEditDataTrade1.setSelection(0, true);
//        final GuanZhuSpinnerAdapter adapter = new GuanZhuSpinnerAdapter(instance,spinnerList);
//        spGuanZhu.setAdapter(adapter);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMineShopEditDataTrade1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                initSpinner2(pos);
//                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
//                if(pos == 0){
//                    type = 0;
//                    initData(0);
//                }
//                if(pos == 1){
//                    type = 1;
//                    initData(1);
//                }
//                if(pos == 2){
//                    type = 2;
//
//                    initData(2);
//                }
//                rvAdapter.setType(type);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initSpinner2(int type){
        spMineShopEditDataTrade2.setVisibility(View.VISIBLE);
        switch (type){
            case 0:
                final String[] spinnerItems = {"农业","林业","畜牧业","渔业","农、林、牧、渔专业及辅助性活动"};
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                        R.layout.simple_spinner_item, spinnerItems);
                //下拉的样式res
                spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                //绑定 Adapter到控件
                spMineShopEditDataTrade2.setAdapter(spinnerAdapter);
                break;
        }

    }*/

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
//        Log.d("personnalnote12", resultCode + "this is" + resultCode + " PERSONNALNOTE" + requestCode);
        if (resultCode == RESULT_OK) {
//            Log.d("personnalnote12", "this is ok");
            switch (requestCode) {

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
                    rlyMineShopEditDataLicence.setVisibility(View.GONE);
                    ivMineShopEditDataLicence.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(mSelectLicenceImages.get(0))
                            .into(ivMineShopEditDataLicence);
                    break;

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
                    rlyMineShopEditDataIdentifyFace.setVisibility(View.GONE);
                    ivMineShopEditDataIdentifyFace.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(mSelectIdentifyFaceImages.get(0))
                            .into(ivMineShopEditDataIdentifyFace);
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
                    rlyMineShopEditDataIdentifyBack.setVisibility(View.GONE);
                    ivMineShopEditDataIdentifyBack.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(mSelectIdentifyBackImages.get(0))
                            .into(ivMineShopEditDataIdentifyBack);
                    break;
            }
        }
    }

    private Bitmap compressImageFromFile(String srcPath) {

        BitmapUtils bitmapUtils = new BitmapUtils();
        Bitmap bitmap = bitmapUtils.getimage(srcPath);
        bitmap = bitmapUtils.comp(bitmap);
        bitmap = bitmapUtils.compressImage(bitmap);


        return bitmap;
    }
    private void saveData(){
        Map<String,Object> map = new HashMap<>();
//        String shopname = etMineShopEditDataShopName.getText().toString();
//        String addr = etMineShopEditDataAddr.getText().toString();
//        String businesshour = tvMineShopEditDataBusinessHourMorning1.getText().toString().trim()+" "+tvMineShopEditDataBusinessHourAfternoon1.getText().toString().trim();
//        Log.d("mineshopeditdata1",businesshour);
//        String tel = etMineShopEditDataTel.getText().toString();
        shopname = etMineShopEditDataShopName.getText().toString();
        addr = etMineShopEditDataAddr.getText().toString();
        trade = tvMineShopEditDataTrade.getText().toString();
        socialcode = etMineShopEditDataSocialCode.getText().toString();
        String name = etMineShopEditDataRealName.getText().toString();
        String identifycode = etMineShopEditDataIdentifyCode.getText().toString();
        map.put("shopid",shopid);
//        map.put("shopname",shopname);
//        map.put("addr",addr);
        map.put("shopname",shopname);
        map.put("addr",addr);
        map.put("socialcode",socialcode);
        map.put("trade",trade);
        map.put("name",name);
        map.put("identifycode",identifycode);
        map.put("cancel",cancel);
        BitmapUtils bitmapUtils = new BitmapUtils();

        if(mSelectLicenceImages != null && mSelectLicenceImages.size()>0) {

            Bitmap bitmapLicense = compressImageFromFile(mSelectLicenceImages.get(0));
            //将图片显示到ImageView中
            String base64_00License= bitmapUtils.bitmapConvertBase64(bitmapLicense);
            map.put("license",base64_00License);
            //图片压缩
        }else {
            Toast.makeText(this,"请上传营业执照",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mSelectIdentifyFaceImages != null && mSelectIdentifyFaceImages.size()>0) {

            Bitmap bitmapLicense = compressImageFromFile(mSelectIdentifyFaceImages.get(0));
            //将图片显示到ImageView中
            String base64_00License= bitmapUtils.bitmapConvertBase64(bitmapLicense);
            map.put("identifyface",base64_00License);
            //图片压缩
        }
        if(mSelectIdentifyBackImages != null && mSelectIdentifyBackImages.size()>0) {

            Bitmap bitmapLicense = compressImageFromFile(mSelectIdentifyBackImages.get(0));
            //将图片显示到ImageView中
            String base64_00License= bitmapUtils.bitmapConvertBase64(bitmapLicense);
            map.put("identifyback",base64_00License);
            //图片压缩
        }
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.changeShopEditDataToNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getIssuccess().equals("1")){
                    Toast.makeText(getBaseContext(),baseBean.getMsg(),Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
    }
}
