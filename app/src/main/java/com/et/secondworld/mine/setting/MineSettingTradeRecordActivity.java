package com.et.secondworld.mine.setting;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.GetFansBean;
import com.et.secondworld.bean.TradeOrderListBean;
import com.et.secondworld.mine.setting.adapter.MineSettingTradeRecordRVAdapter;
import com.et.secondworld.network.GuanZhuFansNetWork;
import com.et.secondworld.network.TradeOrderNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
 * @Date 2020/4/16
 **/
public class MineSettingTradeRecordActivity extends AppCompatActivity {

    @BindView(R.id.rv_mine_setting_trade_record)
    RecyclerView rvMineSettingTradeRecord;
    @BindView(R.id.rfl_mine_setting_trade_record)
    SmartRefreshLayout rflMineSettingTradeRecord;
    @BindView(R.id.tv_mine_setting_trade_record)
    TextView tvMineSettingTradeRecord;
    private int page = 1;
    private int limit = 10;
    @OnClick(R.id.rly_mine_setting_trade_record_back)
    public  void rlyMineSettingTradeRecordBackOnclick(){
        finish();
    }
    private MineSettingTradeRecordRVAdapter rvAdapter;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_setting_trade_record);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
        initRecycleView();
        initData();
    }




    private void initRecycleView(){
        rvAdapter = new MineSettingTradeRecordRVAdapter();
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 4;i++){
//            dataList.add("");
//        }
        rvMineSettingTradeRecord.setLayoutManager(new LinearLayoutManager(this));
        rvMineSettingTradeRecord.setAdapter(rvAdapter);

        rflMineSettingTradeRecord.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        findAdapter.replaceAll(dataList);

//                        page = 1;
                        initData();
                        refreshLayout.finishRefresh();
                    }
                },0);
            }
        });

        rflMineSettingTradeRecord.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.addData(adapter.getItemCount(),picList);
//                        findAdapter.addData(findAdapter.getItemCount(),dataList);

                        Map<String,Object> map = new HashMap<>();

                        TradeOrderNetWork tradeOrderNetWork = new TradeOrderNetWork();

                        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                        String account = xcCacheManager.readCache(xcCacheSaveName.account);
                        page++;
                        map.put("accountid",account);
                        map.put("page",""+page);
                        map.put("limit",""+limit);
                        tradeOrderNetWork.getTradeOrderListBeanFromNet(map, new Observer<TradeOrderListBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(TradeOrderListBean tradeOrderListBean) {


                                if(tradeOrderListBean.getIssuccess().equals("1")){
                                  /*  XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                                    String account = xcCacheManager.readCache(xcCacheSaveName.account);
                                    List<GetFansBean.ListBean> datalist1 = getFansBean.getList();
                                    for(int i=0;i<datalist1.size();){

                                        if(datalist1.get(i).getFollowid().equals(account)){
                                            datalist1.remove(i);
                                            continue;
                                        }
                                        i++;

                                    }*/
                                    rvAdapter.addData(rvAdapter.getItemCount(),tradeOrderListBean.getList());
                                    if(tradeOrderListBean.getList().size() != limit){
                                        refreshLayout.finishLoadMoreWithNoMoreData();
                                    }else {
                                        refreshLayout.finishLoadMore();
                                    }

                                }
                            }
                        });

                    }
                },0);
            }
        });

//        rvAdapter.replaceAll(dataList);
    }

    private void initData(){
        Map<String,Object> map = new HashMap<>();

        TradeOrderNetWork tradeOrderNetWork = new TradeOrderNetWork();

        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        page = 1;
        map.put("accountid",account);
        map.put("page",""+page);
        map.put("limit",""+limit);
        tradeOrderNetWork.getTradeOrderListBeanFromNet(map, new Observer<TradeOrderListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TradeOrderListBean tradeOrderListBean) {
                if(tradeOrderListBean.getList().size() == 0){
                    tvMineSettingTradeRecord.setVisibility(View.VISIBLE);
                }else {
                    tvMineSettingTradeRecord.setVisibility(View.GONE);
                }
                rvAdapter.replaceAll(tradeOrderListBean.getList());
            }
        });

    }




}
