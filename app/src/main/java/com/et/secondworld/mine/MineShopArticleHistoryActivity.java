package com.et.secondworld.mine;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.GetMyShopArticleHistoryListBean;
import com.et.secondworld.mine.adapter.MineShopArticleHistoryRVAdapter;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
 * @Date 2020/6/20
 **/
public class MineShopArticleHistoryActivity extends AppCompatActivity {


    @BindView(R.id.rly_mine_shop_article_history_back)
    RelativeLayout rlyMineShopArticleHistoryBack;
    @OnClick(R.id.rly_mine_shop_article_history_back)
    public void rlyMineShopArticleHistoryBackOnclick(){
        finish();
    }
//    @BindView(R.id.rfl_mine_shop_article_history)
//    SmartRefreshLayout rflMineShopArticleHistory;
    @BindView(R.id.rv_mine_shop_article_history)
    RecyclerView rvMineShopArticleHistory;
    @BindView(R.id.tv_mine_shop_article)
    TextView tvMineShopArticle;
//    @BindView(R.id.pb_mine_shop_article_history_loading)
//    ProgressBar pbMineShopArticleHistoryLoading;
    MineShopArticleHistoryRVAdapter rvAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_shop_article_history);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initRV();
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
    private void initRV(){
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 8;i++){
//            dataList.add("");
//        }
        rvAdapter = new MineShopArticleHistoryRVAdapter();
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
//        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
//        //设置布局管理器， 参数gridLayoutManager对象
//        rvSearch.setLayoutManager(gridLayoutManager);
        rvMineShopArticleHistory.setLayoutManager(new LinearLayoutManager(this));
        rvMineShopArticleHistory.setAdapter(rvAdapter);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
        Map<String,Object>map = new HashMap<>();
        map.put("shopid",shopid);
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.getMyShopArticleHistoryFromNet(map, new Observer<GetMyShopArticleHistoryListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetMyShopArticleHistoryListBean getMyShopArticleHistoryListBean) {
                if(getMyShopArticleHistoryListBean.getList().size() == 0){
                    tvMineShopArticle.setVisibility(View.VISIBLE);
                }else {
                    tvMineShopArticle.setVisibility(View.GONE);
                }
                rvAdapter.replaceAll(getMyShopArticleHistoryListBean.getList());
            }
        });
//        rvAdapter.replaceAll(dataList);
    }
}
