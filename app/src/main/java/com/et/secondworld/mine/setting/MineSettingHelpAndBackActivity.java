package com.et.secondworld.mine.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.VillageListBean;
import com.et.secondworld.forum.adapter.ForumPostSelectImageRVAdapter;
import com.et.secondworld.network.ReportNetWork;
import com.et.secondworld.network.SearchNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
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
 * @Date 2020/4/16
 **/
public class MineSettingHelpAndBackActivity   extends AppCompatActivity {


    @BindView(R.id.rv_mine_setting_help_and_back_img)
    RecyclerView rvMineSettingHelpAndBackImg;
    @BindView(R.id.et_mine_setting_help_and_back_input)
    EditText etMineSettingHelpAndBackInput;
    @BindView(R.id.tv_mine_setting_help_and_back_input)
    TextView tvMineSettingHelpAndBackInput;
    @BindView(R.id.rly_mine_setting_help_and_back_input)
    RelativeLayout rlyMineSettingHelpAndBackInput;
    @OnClick(R.id.rly_mine_setting_help_and_back_input)
    public void rlyMineSettingHelpAndBackInputOnclick(){
        etMineSettingHelpAndBackInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) ((Activity) this).getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(etMineSettingHelpAndBackInput, 0);
    }
    @OnClick(R.id.lly_mine_setting_help_and_back)
    public void llyMineSettingHelpAndBackOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
    @BindView(R.id.et_mine_setting_help_and_back_tel)
    EditText etMineSettingHelpAndBackTel;
    @BindView(R.id.rly_mine_setting_help_and_back_submit)
    RelativeLayout rlyMineSettingHelpAndBackSubmit;
    private long clickTime = 0;
    @OnClick(R.id.rly_mine_setting_help_and_back_submit)
    public void rlyMineSettingHelpAndBackSubmitOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            save();

        }
    }


    private void save(){
        String imgs = "";
        BitmapUtils bitmapUtils = new BitmapUtils();
        for(String item : mSelectImages) {

            Bitmap bitmapHead = bitmapUtils.compressImageFromFile(item);
            //将图片显示到ImageView中
            imgs += bitmapUtils.bitmapConvertBase64(bitmapHead)+",";
        }
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        String content = etMineSettingHelpAndBackInput.getText().toString();
        String tel = etMineSettingHelpAndBackTel.getText().toString();
        if(content.length() < 5){
            Toast.makeText(this,"请输入至少5个字",Toast.LENGTH_LONG).show();
            return;
        }
        if(tel.length() <1){
            Toast.makeText(this,"请输入联系号码",Toast.LENGTH_LONG).show();
            return;
        }
        Map<String,Object>map = new HashMap<>();
        map.put("content",content);
        map.put("tel",tel);
        map.put("accountid",accountid);
        map.put("imgs",imgs);
        ReportNetWork reportNetWork = new ReportNetWork();
        reportNetWork.addHelpAndBackToNet(map, new Observer<BaseBean>() {
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

    private ArrayList<String> mSelectImages = new ArrayList<>();
    @OnClick(R.id.rly_mine_setting_help_and_back_back)
    public  void rlyMineSettingHelpAndBackBackOnclick(){
        finish();
    }
    ForumPostSelectImageRVAdapter rvAdapter ;
    private final int SELECT_IMAGE_REQUEST_IMAGES = 0x002;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_setting_help_and_back);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        ButterKnife.bind(this);
        initRecycleView(3);
        initEditListener();
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
    private void initEditListener(){

        etMineSettingHelpAndBackInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(getBaseContext(),s+"",Toast.LENGTH_SHORT).show();
               tvMineSettingHelpAndBackInput.setText(etMineSettingHelpAndBackInput.length()+"/500");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @SuppressLint("WrongConstant")
    private void initRecycleView(int selectPicCountNum){
        rvAdapter = new ForumPostSelectImageRVAdapter(selectPicCountNum,this,mSelectImages);
        ArrayList<String> dataList = new ArrayList<>();
       /* for(int i = 0;i < 5;i++){
            dataList.add(new Image());
        }*/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
        //设置布局管理器， 参数gridLayoutManager对象
        rvMineSettingHelpAndBackImg.setLayoutManager(gridLayoutManager);
        rvMineSettingHelpAndBackImg.setAdapter(rvAdapter);
        rvAdapter.replaceAll(dataList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_IMAGE_REQUEST_IMAGES:
                    List<String> imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (imagePaths == null) {
                        mSelectImages.clear();
                        rvAdapter.replaceAll(mSelectImages);
                        return;
                    }
                    if (imagePaths.size() == 0) {
                        mSelectImages.clear();
                        rvAdapter.replaceAll(mSelectImages);
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImages.clear();
                    mSelectImages.addAll(imagePaths);
                    rvAdapter.replaceAll(mSelectImages);
                    break;
            }
        }
    }

}