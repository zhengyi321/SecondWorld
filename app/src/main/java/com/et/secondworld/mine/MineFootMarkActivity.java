package com.et.secondworld.mine;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.et.secondworld.R;
import com.et.secondworld.mine.adapter.FootMarkTabFragmentPagerAdapter;
import com.et.secondworld.mine.fragment.FootMarkHaveSeenInvitationFragment;
import com.et.secondworld.mine.fragment.FootMarkRecentVisitorFragment;
import com.et.secondworld.mine.fragment.FootMarkVisitPostOwnerFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class MineFootMarkActivity extends AppCompatActivity {

    @BindView(R.id.tly_footmark)
    TabLayout tlyFootMark;
    @BindView(R.id.vp_footmark)
    ViewPager vpFootMark;
    @BindView(R.id.rly_footmark_back)
    RelativeLayout rlyFoorMarkBack;
    @OnClick(R.id.rly_footmark_back)
    public void rlyFootMarkBackOnclick(){
        finish();
    }
    private FootMarkHaveSeenInvitationFragment footMarkHaveSeenInvitationFragment;
    private FootMarkVisitPostOwnerFragment footMarkVisitPostOwnerFragment;
    private FootMarkRecentVisitorFragment footMarkRecentVisitorFragment;
    private ArrayList<Fragment> list_fragment = new ArrayList<>();
    private ArrayList<String> list_title = new ArrayList<>();
    private FootMarkTabFragmentPagerAdapter footMarkTabFragmentPagerAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_footmark);
        init();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initViewPage();
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
    private void initViewPage(){
        footMarkHaveSeenInvitationFragment = new FootMarkHaveSeenInvitationFragment();
        footMarkVisitPostOwnerFragment = new FootMarkVisitPostOwnerFragment();
        footMarkRecentVisitorFragment = new FootMarkRecentVisitorFragment();
        list_fragment.add(footMarkHaveSeenInvitationFragment);
//        list_fragment.add(footMarkVisitPostOwnerFragment);
        list_fragment.add(footMarkRecentVisitorFragment);

        list_title.add("看过的帖子");
//        list_title.add("访问帖主");
        list_title.add("最近访客");
        footMarkTabFragmentPagerAdapter = new FootMarkTabFragmentPagerAdapter(getSupportFragmentManager(), list_fragment, list_title);
//
        vpFootMark.setAdapter(footMarkTabFragmentPagerAdapter);
        tlyFootMark.setupWithViewPager(vpFootMark);
        tlyFootMark.setTabMode(TabLayout.MODE_FIXED);
    }

}
