package com.et.secondworld.message;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.et.secondworld.R;
import com.et.secondworld.bean.AddCommentBackBean;
import com.et.secondworld.bean.AddCommentBean;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/4
 **/
public class MessageCommentAndAtBackActivity extends AppCompatActivity {

    private String articleid = "";
    private String commentid = "";
    private String toaccountid = "";
    private String type = "";
    private String tonick = "";
    @BindView(R.id.rly_comment_and_at_back_cancel)
    RelativeLayout rlyCommentAndAtBackCancel;
    @OnClick(R.id.rly_comment_and_at_back_cancel)
    public void rlyCommentAndAtBackCancelOnclick(){
        finish();
    }
    @BindView(R.id.rly_comment_and_at_back_realse)
    RelativeLayout rlyCommentAndAtBackRealse;
    @OnClick(R.id.rly_comment_and_at_back_realse)
    public void rlyCommentAndAtBackRealseOnclick(){
        if(type != null){
            if(type.equals("articlecomment")){
                articleComment();
            }
            if(type.equals("backcomment")){
                commentBackComment();
            }
            finish();
        }
    }
    @OnClick(R.id.lly_comment_and_at_back)
    public void llyCommentAndAtBackOnclick(){
        initEditForce();
    }
    @BindView(R.id.et_comment_and_at_back)
    EditText etCommentAndAtBack;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_comment_and_at_back);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initData();
        etCommentAndAtBack.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                etCommentAndAtBack.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                Toast.makeText(MainActivity.this, "show", Toast.LENGTH_SHORT).show();
            }
        }, 100);
        initStatusBar();
//        initEditForce();
       /* Timer timer = new Timer();
        timer.schedule(new TimerTask()
                       {

                           public void run()
                           {
                               initEditForce();
                           }

                       },
                998);*/
//        initEditForce();
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
    private void initEditForce(){
//        etCommentAndAtBack.requestFocus();
        etCommentAndAtBack.requestFocus();
        showHideSoftInput();
//        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        imm.showSoftInput(etCommentAndAtBack, InputMethodManager.SHOW_IMPLICIT);


    }
    private void showHideSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void initData(){
        Intent intent = getIntent();
        articleid = intent.getStringExtra("articleid");
        commentid = intent.getStringExtra("commentid");
        toaccountid = intent.getStringExtra("toaccountid");
        type = intent.getStringExtra("type");
        tonick = intent.getStringExtra("tonick");
    }

    private void articleComment(){
        String content = etCommentAndAtBack.getText().toString();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(content.isEmpty()){
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("account",account);
        map.put("content",content);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        forumPostNetWork.addArticleCommentFromNet(map, new Observer<AddCommentBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddCommentBean addCommentBean) {
                if(addCommentBean.getIssuccess().equals("1")) {
                    Toast.makeText(getBaseContext(), addCommentBean.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void commentBackComment(){
        String commentBackContent = etCommentAndAtBack.getText().toString();
        if(commentBackContent.equals("")||commentBackContent.isEmpty()){
            return;
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("toaccountid",toaccountid);
        paramMap.put("tonick",tonick);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        paramMap.put("account",xcCacheManager.readCache(xcCacheSaveName.account));
        paramMap.put("content",commentBackContent);
        paramMap.put("commentid",commentid);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        forumPostNetWork.addCommentBackToNet(paramMap, new Observer<AddCommentBackBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddCommentBackBean addCommentBackBean) {
                if(addCommentBackBean.getIssuccess().equals("1")){
                    Toast.makeText(getBaseContext(),addCommentBackBean.getMsg(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
