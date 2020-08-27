package com.et.secondworld.forum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
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

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.et.secondworld.R;
import com.et.secondworld.bean.AddArticleBean;
import com.et.secondworld.bean.GetEditArticleBean;
import com.et.secondworld.forum.adapter.ForumPostSelectImageRVAdapter;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.edittext.REditText;
import com.et.secondworld.widget.edittext.RObject;
import com.et.secondworld.widget.imagepicker.GlideLoader;
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
 * @Date 2020/4/23
 **/
public class ForumEditActivity extends AppCompatActivity {


    private ForumPostSelectImageRVAdapter rvAdapter;
    private int type = 0;
    private String lat ="";
    private String lon ="";
    private String city = "";
    private String addr = "";
    private String atids = "";
    private String articleid = "";
    private int selectPicCountNum = 4;
    private final int SELECT_IMAGE_REQUEST_IMAGES = 0x002;
    private final int SELECT_AT_REQUEST = 0x0033;
    private ArrayList<String> mSelectImages = new ArrayList<>();
    @BindView(R.id.et_forum_post_title)
    EditText etForumPostTitle;
    @OnClick(R.id.rly_forum_post_title)
    public void rlyForumPostTitleOnclick(){
        etForumPostTitle.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @BindView(R.id.et_forum_post_content)
    REditText etForumPostContent;
    @BindView(R.id.rly_forum_post_back)
    RelativeLayout rlyForumPostBack;
    @OnClick(R.id.rly_forum_post_back)
    public void rlyForumPostBackOnclick(){
        finish();
    }
    @BindView(R.id.tv_forum_post_loc)
    TextView tvForumPostLoc;
    @OnClick(R.id.rly_forum_post_content)
    public void rlyForumPostContentOnclick(){
        etForumPostContent.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @BindView(R.id.rly_forum_post_release)
    RelativeLayout rlyForumPostRelease;
    @OnClick(R.id.rly_forum_post_release)
    public  void rlyForumPostReleaseOnclick(){
//        setTopic();

        new Thread(runnable).start();
    }
    /*Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Bundle data = msg.getData();
//            String val = data.getString("value");
//            Log.i("mylog","请求结果-->" + val);

        }
    };*/

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            //
            // TODO: http request.
            //
            Message msg = new Message();
//            Bundle data = new Bundle();
//            data.putString("value","请求结果");
//            msg.setData(data);
//            handler.sendMessage(msg);
            release();
        }
    };
    /**
     * 添加设置话题
     *
     * @author Ruffian
     */
    private void setTopic() {
/*
        RObject topic = new RObject();
        int id = (int) (Math.random() * 100);
        topic.setId("No." + id);
        topic.setObjectText("双" + id + "狂欢");// 必须设置

        switch (id % 3) {
            case 0:
                topic.setObjectRule("*");// 开发者设置,默认#
                break;
            case 1:
                topic.setObjectRule("$");// 开发者设置,默认#
                break;
            case 2:
                topic.setObjectRule("#");// 开发者设置,默认#
                break;
        }

        etForumPostContent.setObject(topic);// 设置话题*/
    }
    @BindView(R.id.rv_forum_post_images)
    RecyclerView rvForumPostImages;
    @BindView(R.id.rly_forum_post_img)
    RelativeLayout rlyForumPostImg;
    private long clickTime = 0;
    @OnClick(R.id.rly_forum_post_img)
    public void rlyForumPostImgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectImgActivity();
        }
    }
    @BindView(R.id.rly_forum_post_at)
    RelativeLayout rlyForumPostAt;
    @OnClick(R.id.rly_forum_post_at)
    public void rlyForumPostAtOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, ForumAtActivity.class);
            startActivityForResult(intent, SELECT_AT_REQUEST);
        }
    }
    @BindView(R.id.rly_forum_post_loc)
    RelativeLayout rlyForumPostLoc;
    @OnClick(R.id.rly_forum_post_loc)
    public void rlyForumPostLocOnclick(){
        loc();
//        Intent intent = new Intent(this,ForumLocActivity.class);
//        startActivity(intent);
//        startActivityForResult(intent, SELECT_AT_REQUEST);
    }

    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    public  final String EXTRA_RESULT = "EXTRA_RESULT";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_forum_post);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        getArticleid();
        initRecycleView(selectPicCountNum);
        initdata();
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
    private void getArticleid(){
        Intent intent = getIntent();
        articleid = intent.getStringExtra("articleid");

    }
    private void initdata(){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("articleid",articleid);
        ArticleNetWork articleNetWork = new ArticleNetWork();
        articleNetWork.getEditArticleFromNet(paramMap, new Observer<GetEditArticleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetEditArticleBean getEditArticleBean) {
                if(getEditArticleBean.getIssuccess().equals("1")){
                    etForumPostTitle.setText(getEditArticleBean.getTitle());
                    etForumPostContent.setText(getEditArticleBean.getContent());

                    mSelectImages.clear();
                    if(getEditArticleBean.getImglist() != null) {
                        for(GetEditArticleBean.ImglistBean item:getEditArticleBean.getImglist()) {
                            mSelectImages.add(item.getImgurl());
                        }
                        rvAdapter.replaceAll(mSelectImages);
                    }
                }
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
        rvForumPostImages.setLayoutManager(gridLayoutManager);
        rvForumPostImages.setAdapter(rvAdapter);
        rvAdapter.replaceAll(dataList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_IMAGE_REQUEST_IMAGES:
                    List<String> imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths == null){
                        mSelectImages.clear();
                        rvAdapter.replaceAll(mSelectImages);
                        return;
                    }
                    if(imagePaths.size() == 0){
                        mSelectImages.clear();
                        rvAdapter.replaceAll(mSelectImages);
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImages.clear();
                    mSelectImages.addAll(imagePaths);
                    rvAdapter.replaceAll(mSelectImages);
                    break;
                case SELECT_AT_REQUEST:
                    if(data != null){
                        ArrayList<RObject> atLists = data.getParcelableArrayListExtra(ForumAtActivity.EXTRA_RESULT);
                        if(atLists == null){
                            return;
                        }
                        for (RObject item:atLists){
//                            Toast.makeText(this,""+item.getId(),Toast.LENGTH_LONG).show();
                            etForumPostContent.setObject(item);
                        }
                    }
                    break;
                    /*Glide.with(this)
                            .load(mSelectImages.get(0).getPath())
                            .into(civMineEditdataHead);*/
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
    private void selectImgActivity(){
       /* Intent intent = new Intent(this, SelectImageActivity.class);
        intent.putExtra("type","pic");
        intent.putExtra("count",selectPicCountNum);
        intent.putParcelableArrayListExtra("selected_images", mSelectImages);

        startActivityForResult(intent, SELECT_IMAGE_REQUEST_IMAGES);*/

        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(selectPicCountNum)//设置最大选择图片数目(默认为1，单选)
                .setImagePaths(mSelectImages)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_IMAGES);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
    }

    private Bitmap compressImageFromFile(String srcPath) {

        BitmapUtils bitmapUtils = new BitmapUtils();
        Bitmap bitmap = bitmapUtils.netPicToBmp(srcPath);

        if(bitmap == null){
            bitmap = bitmapUtils.getimage(srcPath);
        }
        bitmap = bitmapUtils.compressImage(bitmap);
        bitmap = bitmapUtils.comp(bitmap);

        return bitmap;
    }
    private void release(){
        String title = etForumPostTitle.getText().toString();
        String content = etForumPostContent.getText().toString();
        String imgs = "";
        BitmapUtils bitmapUtils = new BitmapUtils();
        for(String item : mSelectImages) {
            Bitmap bitmapHead = compressImageFromFile(item);
            //将图片显示到ImageView中
            imgs += bitmapUtils.bitmapConvertBase64(bitmapHead)+",";
        }
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        List<RObject> rObjectList = etForumPostContent.getObjects();
        for(RObject item:rObjectList){
            atids += item.getId()+",";
        }
        if(title.length() <1){
            Toast.makeText(getBaseContext(),"标题请输入至少一个字",Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.length() >=5 || !imgs.isEmpty()){

        }else {
            Toast.makeText(getBaseContext(),"内容请输入至少五个字",Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("content",content);
        map.put("account",account);
        map.put("imgs",imgs);
        map.put("city",city);
        map.put("addr",addr);
        map.put("lat",lat);
        map.put("lon",lon);
        map.put("atid",atids);
        map.put("articleid",articleid);
        ArticleNetWork articleNetWork = new ArticleNetWork();
        articleNetWork.editNormalArticleToNet(map, new Observer<AddArticleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddArticleBean addArticleBean) {
                if(addArticleBean.getIssuccess().equals("1")){
                    finish();
                }
            }
        });

    }

    private void loc(){
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true
        option.setAddrType("all");
        option.setIsNeedLocationPoiList(true);
//可选，是否需要周边POI信息，默认为不需要，即参数为false
//如果开发者需要获得周边POI信息，此处必须为true
        option.setNeedNewVersionRgc(true);
        mLocClient.setLocOption(option);
        mLocClient.start();

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            tvForumPostLoc.setText(location.getAddrStr());
            lon = location.getLongitude()+"";
            lat = location.getLatitude()+"";
            city = location.getCity();
            addr = location.getAddrStr();
            mLocClient.stop();
        }
    }
}
