package com.et.secondworld.forum;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.et.secondworld.R;
import com.et.secondworld.forum.adapter.ForumModulSelectTwotabFragmentPagerAdapter;
import com.et.secondworld.forum.fragment.ForumModulSelectTwoOneFragment;
import com.et.secondworld.forum.fragment.ForumModulSelectTwoThreeFragment;
import com.et.secondworld.forum.fragment.ForumModulSelectTwoTwoFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/10
 **/
public class ForumModulSelectTwoActivity extends AppCompatActivity {

    @BindView(R.id.rly_forum_select_modul_two_back)
    RelativeLayout rlyForumSelectModulTwoBack;
    @OnClick(R.id.rly_forum_select_modul_two_back)
    public void rlyForumSelectModulTwoBackOnclick(){
        finish();
    }
    @BindView(R.id.tly_forum_select_modul_two)
    TabLayout tlyForumSelectModulTwo;
    @BindView(R.id.vp_forum_select_modul_two)
    ViewPager vpForumSelectModulTwo;
    private ArrayList<Fragment> list_fragment = new ArrayList<>();
    private ArrayList<String> list_title = new ArrayList<>();
    private ForumModulSelectTwotabFragmentPagerAdapter forumModulSelectTwotabFragmentPagerAdapter;
    private ForumModulSelectTwoOneFragment forumModulSelectTwoOneFragment;
    private ForumModulSelectTwoTwoFragment forumModulSelectTwoTwoFragment;
    private ForumModulSelectTwoThreeFragment forumModulSelectTwoThreeFragment;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_forum_select_modul_two);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
        initViewPage();
    }

    private void initViewPage(){

            forumModulSelectTwoOneFragment = new ForumModulSelectTwoOneFragment();
            forumModulSelectTwoTwoFragment = new ForumModulSelectTwoTwoFragment();
            forumModulSelectTwoThreeFragment = new ForumModulSelectTwoThreeFragment();
            list_fragment.add(forumModulSelectTwoOneFragment);
            list_fragment.add(forumModulSelectTwoTwoFragment);
            list_fragment.add(forumModulSelectTwoThreeFragment);

            list_title.add("模板1");
            list_title.add("模板2");
            list_title.add("模板3");
            forumModulSelectTwotabFragmentPagerAdapter = new ForumModulSelectTwotabFragmentPagerAdapter(getSupportFragmentManager(), list_fragment, list_title);
//
            vpForumSelectModulTwo.setAdapter(forumModulSelectTwotabFragmentPagerAdapter);
            tlyForumSelectModulTwo.setupWithViewPager(vpForumSelectModulTwo);
            tlyForumSelectModulTwo.setTabMode(TabLayout.MODE_FIXED);

    }
}
