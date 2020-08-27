package com.et.secondworld.fragment.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.et.secondworld.LoginActivity;
import com.et.secondworld.TecentMapViewActivity;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.network.RegisterLoginNetWork;
import com.et.secondworld.utils.UniversalID;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.MessageQueryDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.google.android.material.tabs.TabLayout;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
import com.et.secondworld.first.fragment.FirstGuanZhuFragment;
import com.et.secondworld.first.fragment.FirstModuleFourFragment;
import com.et.secondworld.first.fragment.FirstReCommendFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/11
 **/
public class FirstFragment extends BaseFragment {

    View view;
    private long clickTime = 0;
    @BindView(R.id.tly_main_first)
    TabLayout tlyMainFirst;
    @OnClick(R.id.iv_main_first_map)
    public void ivMainFirstMapOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {
            checkLogin();
            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(view.getContext(), TecentMapViewActivity.class);
            startActivity(intent);
        }
//        Intent intent = new Intent(view.getContext(), TecentMapViewActivity.class);
//        startActivity(intent);
    }
    @BindView(R.id.vp_main_first)
    ViewPager vpMainFirst;


    public static FirstFragment newInstance() {


        FirstFragment firstFragment = new FirstFragment();

        // Get arguments passed in, if any
        Bundle args = firstFragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        // Add parameters to the argument bundle
        args.putInt("aa", 0);
        firstFragment.setArguments(args);

        return firstFragment;
    }
    public FirstFragment(){
        super();
    }

    private ArrayList<Fragment> list_fragment = new ArrayList<>();
    private ArrayList<String> list_title = new ArrayList<>();
    private FirstFragmentPageAdapter firstFragmentPageAdapter;
    private  FirstReCommendFragment firstReCommendFragment;
    private  FirstModuleFourFragment firstModuleFourFragment;
    private  FirstGuanZhuFragment firstGuanZhuFragment;
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_first,container,false);
        return view;
    }
    private void checkLogin(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String tel = xcCacheManager.readCache(xcCacheSaveName.userTel);
        String uuuid = UniversalID.getUniversalID(getContext());
        RegisterLoginNetWork registerLoginNetWork = new RegisterLoginNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("tel",tel);
        map.put("uuuid",uuuid.trim());
        registerLoginNetWork.checkLoginFromNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getIssuccess().equals("1")){
                    MessageQueryDialog queryCancelDialog = new MessageQueryDialog(getContext()).Build.setCallBackListener(new MessageQueryDialog.OnFinishClickListener() {
                        @Override
                        public void isQuery(boolean isQuery) {
                            xcCacheManager.writeCache(xcCacheSaveName.account,"");
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
//                            MainActivity.this.finish();
                        }
                    }).build(getContext(),"您的账号在其它地方登陆,请确认是否是本人操作,如若不是请及时修改密码!");
                    queryCancelDialog.show();


                }
            }
        });
    }
    @Override
    public void initView() {
        ButterKnife.bind(this,view);
        initTab();
    }
    private void initTab(){
//        vpFansFollowGoods = view.findViewById(R.id.vp_fans_follow_goods);
        firstReCommendFragment = new FirstReCommendFragment();
        firstGuanZhuFragment = new FirstGuanZhuFragment();
        firstModuleFourFragment = new FirstModuleFourFragment();
//        mineCollectFragment = new MineCollectFragment();
        list_fragment.add(firstGuanZhuFragment);
        list_fragment.add(firstReCommendFragment);
        list_fragment.add(firstModuleFourFragment);
//        list_fragment.add(new FirstPageFragment());
//        list_fragment.add(allOrderFragment);


        list_title.add("关注");
        list_title.add("推荐");
        list_title.add("发现");
//        list_title.add("待收货");
        firstFragmentPageAdapter = new FirstFragmentPageAdapter(getChildFragmentManager(), list_fragment, list_title);
        if (vpMainFirst == null) {
//            Toast.makeText(view.getContext(), "this is null", Toast.LENGTH_SHORT).show();
            return;
        }

        vpMainFirst.setAdapter(firstFragmentPageAdapter);
        vpMainFirst.setOffscreenPageLimit(3);
        tlyMainFirst.setupWithViewPager(vpMainFirst);
        tlyMainFirst.setTabMode(tlyMainFirst.MODE_FIXED);
        /*tlyMainFirst.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                vpMainFirst.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if (null == view) {
                    tab.setCustomView(R.layout.tab_layout_text);
                }
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextAppearance(getActivity(), R.style.TabLayoutTextSize);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                vpMainFirst.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if (null == view) {
                    tab.setCustomView(R.layout.tab_layout_text);
                }
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextAppearance(getActivity(), R.style.TabLayoutTextSize_two);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
    }

    public void onResume(){
        super.onResume();

    }

    public void onDestroyView() {
        super.onDestroyView();
//        vpMainFirst = null;
//        tlyMainFirst = null;
//        view = null;
    }
}
