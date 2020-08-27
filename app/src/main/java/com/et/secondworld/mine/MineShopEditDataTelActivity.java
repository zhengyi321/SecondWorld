package com.et.secondworld.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.et.secondworld.R;
import com.et.secondworld.bean.SaveEditDataBean;
import com.et.secondworld.bean.UpdateShopEditDataBean;
import com.et.secondworld.network.MineNetWork;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/7
 **/
public class MineShopEditDataTelActivity extends AppCompatActivity {
    @BindView(R.id.rly_mine_editdata_personnalnote_back)
    RelativeLayout rlyMineEditDataPersonnalNoteBack;
    @OnClick(R.id.rly_mine_editdata_personnalnote_back)
    public void rlyMineEditDataPersonnalNoteBackOnclick(){
        finish();
    }
    private final int PERSONNALNOTE = 0x007;
    @BindView(R.id.rly_mine_editdata_personnalnote_save)
    RelativeLayout rlyMineEditDataPersonnalNoteSave;
    @OnClick(R.id.rly_mine_editdata_personnalnote_save)
    public void rlyMineEditDataPersonnalNoteSaveOnclick(){
        saveData();
    }
    @BindView(R.id.et_mine_editdata_personnalnote)
    EditText etMineEditDataPersonnalNote;
    @BindView(R.id.rly_mine_editdata_personnalnote)
    RelativeLayout rlyMineEditDataPersonnalNote;
    @OnClick(R.id.rly_mine_editdata_personnalnote)
    public void rlyMineEditDataPersonnalNoteOnclick(){
        etMineEditDataPersonnalNote.setFocusable(true);
        etMineEditDataPersonnalNote.setFocusableInTouchMode(true);
        etMineEditDataPersonnalNote.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @BindView(R.id.tv_mine_editdata_personnalnote_nick)
    TextView tvMineEditDataPersonnalNoteNick;

    @BindView(R.id.tv_mine_editdata_personnalnote_title)
    TextView tvMineEditDataPersonnalNoteTitle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_editdata_personnalnote);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        getIntentData();
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    private void getIntentData(){
        Intent intent = getIntent();
        String personnalNote = intent.getStringExtra("tel");
        tvMineEditDataPersonnalNoteTitle.setText("修改联系电话");
        tvMineEditDataPersonnalNoteNick.setText("商铺电话");
        etMineEditDataPersonnalNote.setHint("填写商铺电话");
        etMineEditDataPersonnalNote.setText(personnalNote);
    }

    private void saveData(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
        String personnalnote = etMineEditDataPersonnalNote.getText().toString();
//        MineNetWork mineNetWork = new MineNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("shopid",shopid);
        map.put("tel",personnalnote);
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.updateShopEditDataTelToNet(map, new Observer<UpdateShopEditDataBean>() {
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
                    xcCacheManager.writeCache(xcCacheSaveName.shopTel,personnalnote);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopLocate,addr);
//                    xcCacheManager.writeCache(xcCacheSaveName.shopHead,updateShopEditDataBean.getLogo());
//                    xcCacheManager.writeCache(xcCacheSaveName.shopBg,updateShopEditDataBean.getBg());
                    Intent i = new Intent();
//                Log.d("personnalnote1",personnalNote);
                    i.putExtra("result", personnalnote);
                    setResult(RESULT_OK, i);
                    finish();
//                    finish();
                }
            }
        });
       /* mineNetWork.saveEditDataNickToNet(map, new Observer<SaveEditDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SaveEditDataBean updatePersonnalNoteBean) {
                String personnalNote = updatePersonnalNoteBean.getNick();
                xcCacheManager.writeCache(xcCacheSaveName.userName,personnalNote);

            }
        });*/
    }
}
