package com.et.secondworld.forum;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.LocationClient;
import com.et.secondworld.R;
import com.et.secondworld.bean.AddArticleBean;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetTempArticleBean;
import com.et.secondworld.forum.adapter.ForumPostSelectImageRVAdapter;
import com.et.secondworld.mapview.MapPostExtendActivity;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.et.secondworld.widget.edittext.REditText;
import com.et.secondworld.widget.edittext.RObject;
import com.et.secondworld.widget.imagepicker.GlideLoader;
import com.et.secondworld.widget.imagepicker.ImagePicker;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;

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
 * @Date 2020/6/24
 **/
public class EmergencyHelpPostActivity extends AppCompatActivity implements LocationSource, TencentLocationListener {


    private ForumPostSelectImageRVAdapter rvAdapter;
    private int type = 0;
    private String lat ="";
    private String lon ="";
    private String province = "";
    private boolean isloc = false;
    private String city = "";
    private String addr = "";
    private String atids = "";
    private String atnicks = "";
    private int selectPicCountNum = 4;
    private final int SELECT_IMAGE_REQUEST_IMAGES = 0x002;
    private final int SELECT_AT_REQUEST = 0x0033;
    private TencentLocationManager locationManager;
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
        String title = etForumPostTitle.getText().toString();
        String content = etForumPostContent.getText().toString();
        if(title.length() <1 && content.length()<1 && mSelectImages.size() == 0){
//            Toast.makeText(getBaseContext(),"标题请输入至少一个字",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        QueryCancelDialog queryCancelDialog = new QueryCancelDialog(this).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
            @Override
            public void isQuery(boolean isQuery) {
                if(isQuery){
                    new Thread(runnable).start();
                }else {
                    saveTemp(false);
                }
            }
        }).build(this,"保存草稿吗？");
        queryCancelDialog.show();
        /*
        new AlertDialog.Builder(this).setTitle("保存草稿吗？").setMessage("")

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
//                        dialog.dismiss();
                        new Thread(runnable).start();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveTemp(false);
//                        finish();
                    }
                }).show();*/
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

//            Bundle data = new Bundle();
//            data.putString("value","请求结果");
//            msg.setData(data);
//            handler.sendMessage(msg);
            saveTemp(true);
        }
    };
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
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
//            release();
            intoExtendActivity();

        }
    }
    private boolean flag = true;
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
        isloc = true;
        tencentLoc();
//        loc();
//        Intent intent = new Intent(this,ForumLocActivity.class);
//        startActivity(intent);
//        startActivityForResult(intent, SELECT_AT_REQUEST);
    }

    @BindView(R.id.ib_forum_post_loc_close)
    ImageButton ibForumPostLocClose;
    @OnClick(R.id.ib_forum_post_loc_close)
    public void ibForumPostLocCloseOnclick(){
        llyForumPostLoc.setVisibility(View.INVISIBLE);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();

        addr = xcCacheManager.readCache(xcCacheSaveName.forumVillage);
        lat = xcCacheManager.readCache(xcCacheSaveName.forumVillageLat);
        lon = xcCacheManager.readCache(xcCacheSaveName.forumVillageLon);
        tvForumPostLoc.setText(addr);
    }
    @BindView(R.id.lly_forum_post_loc)
    LinearLayout llyForumPostLoc;
    LocationClient mLocClient;
//    public MyLocationListenner myListener = new MyLocationListenner();

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
        initRecycleView(selectPicCountNum);
        initLoc();
//        etForumPostTitle.setText("紧急寻人");
//        etForumPostTitle.setEnabled(false);
//        tencentLoc();
//        loc();
        initGetTempFromNet();
        initStatusBar();
//        etForumPostContent.setText("@zz");
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
    private void initLoc(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();

        addr = xcCacheManager.readCache(xcCacheSaveName.forumVillage);
        lon = xcCacheManager.readCache(xcCacheSaveName.forumVillageLat);
        lat = xcCacheManager.readCache(xcCacheSaveName.forumVillageLon);
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

    private void initGetTempFromNet(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        Map<String,Object> map = new HashMap<>();
        map.put("account",account);
        map.put("modules","M4");
        ArticleNetWork articleNetWork = new ArticleNetWork();
        articleNetWork.getTempArticleFromNet(map, new Observer<GetTempArticleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetTempArticleBean getTempArticleBean) {
                if(getTempArticleBean.getIssuccess().equals("1")){
                    String title = getTempArticleBean.getTitle();
                    String content = getTempArticleBean.getContent();
                    String atnicks = getTempArticleBean.getAtnick();
                    Log.d("zzz111",content);
                    etForumPostContent.setText(content);
//                    etForumPostTitle.setText(title);
                    String atids = getTempArticleBean.getAtid();
                    city = getTempArticleBean.getCity();
                    addr = getTempArticleBean.getAddr();
                    lat= getTempArticleBean.getLat();
                    lon = getTempArticleBean.getLon();
                    List<GetTempArticleBean.ImglistBean> imglist = getTempArticleBean.getImglist();
                    for(GetTempArticleBean.ImglistBean item:imglist){
                        mSelectImages.add(item.getImgurl());
                    }
                    rvAdapter.replaceAll(mSelectImages);
                    try {
                        String[] nicks = atnicks.split(",");
                        String[] ids = atids.split(",");
                        List<RObject> rObjectList = new ArrayList<>();
                        for (int i=0;i<nicks.length;i++) {
                            if (!nicks[i].isEmpty()){
                                String removeNick = "@"+nicks[i];

                                content = content.replace(removeNick,"");
                                Log.d("temparticle11",content);
                                RObject rObject = new RObject();
                                rObject.setId(ids[i]);
                                rObject.setObjectText(nicks[i]);
                                rObjectList.add(rObject);
//                                etForumPostContent.setObject(rObject);
                            }
                        }


                        if(!addr.isEmpty()){
                            tvForumPostLoc.setText(addr);
                        }
                        for(RObject item:rObjectList){
                            etForumPostContent.setObject(item);
                        }
                    }catch (Exception e){

                    }
                }
            }
        });
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
//                .setImagePaths(mSelectImages)
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

    private void intoExtendActivity(){
        Intent intent = new Intent(this, MapPostExtendActivity.class);
        String content = etForumPostContent.getText().toString();
        String title = etForumPostTitle.getText().toString();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
        if(title.length() <1){
            Toast.makeText(getBaseContext(),"标题请输入至少一个字",Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.length() >=5 || mSelectImages.size()>0){

        }else {
            Toast.makeText(getBaseContext(),"内容请输入至少五个字",Toast.LENGTH_SHORT).show();
            return;
        }
        List<RObject> rObjectList = etForumPostContent.getObjects();
        for(RObject item:rObjectList){
            atids += item.getId()+",";
        }
//        sections="-1";
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("addr",addr);
        intent.putExtra("city",city);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        intent.putStringArrayListExtra("GET_IMGS",mSelectImages);
        intent.putExtra("atids",atids);
        intent.putExtra("village",village);
        intent.putExtra("types","help");
//        intent.putExtra("type","M4");
       /* switch (type){
            case 1:

                intent.putExtra("type","M1");
                break;

            case 2:
                intent.putExtra("type","M2");
                break;

            case 3:
                intent.putExtra("type","M3");
                break;
        }*/
        startActivity(intent);
        finish();
    }


    private void release(){
        String title = etForumPostTitle.getText().toString();
        String content = etForumPostContent.getText().toString();
        String imgs = "";
        BitmapUtils bitmapUtils = new BitmapUtils();
        for(String item : mSelectImages) {
            if(item.indexOf("http") >=0){
                imgs += item+",";
                continue;
            }
            Bitmap bitmapHead = bitmapUtils.compressImageFromFile(item);
            //将图片显示到ImageView中
            imgs += bitmapUtils.bitmapConvertBase64(bitmapHead)+",";
        }
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        String village = xcCacheManager.readCache(xcCacheSaveName.forumVillageAllName);
        String city = xcCacheManager.readCache(xcCacheSaveName.forumCity);
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
        map.put("village",village);
        map.put("platform","android");
        map.put("type","help");
        ArticleNetWork articleNetWork = new ArticleNetWork();
        articleNetWork.addNormalArticleToNet(map, new Observer<AddArticleBean>() {
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


    private void saveTemp(boolean issave){
        String title = etForumPostTitle.getText().toString();
        String content = etForumPostContent.getText().toString();
        String imgs = "";
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        Map<String,Object> map = new HashMap<>();
        if(issave) {

            BitmapUtils bitmapUtils = new BitmapUtils();
            for(String item : mSelectImages) {
                if(item.indexOf("http") >=0){
                    imgs += item+",";
                    continue;
                }
                Bitmap bitmapHead = bitmapUtils.compressImageFromFile(item);
                //将图片显示到ImageView中
                imgs += bitmapUtils.bitmapConvertBase64(bitmapHead)+",";
            }

            List<RObject> rObjectList = etForumPostContent.getObjects();
            for(RObject item:rObjectList){
                atids += item.getId()+",";
                atnicks += item.getObjectText()+",";
            }
            map.put("title", title);
            map.put("content", content);
            map.put("account", account);
            map.put("imgs", imgs);
            map.put("city", province+city);
            map.put("addr", addr);
            map.put("lat", lat);
            map.put("lon", lon);
            map.put("atid", atids);
            map.put("atnick", atnicks);
            map.put("type", "M4");
            map.put("sections", "-1");
        }else {
            map.put("title", "");
            map.put("content", "");
            map.put("account", account);
            map.put("imgs", "");
            map.put("city", "");
            map.put("addr", "");
            map.put("lat", "");
            map.put("lon", "");
            map.put("atid", "");
            map.put("atnick", "");
            map.put("type", "M4");
            map.put("sections", "-1");
        }
        ArticleNetWork articleNetWork = new ArticleNetWork();
        articleNetWork.addTempArticleToNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getIssuccess().equals("1")){
                    if(issave) {
                        Toast.makeText(getBaseContext(), baseBean.getMsg(), Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
            }
        });

    }

//    private void loc(){
//        // 定位初始化
//        mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        option.setIsNeedLocationDescribe(true);
//        option.setIsNeedAddress(true);
////可选，是否需要地址信息，默认为不需要，即参数为false
////如果开发者需要获得当前点的地址信息，此处必须为true
//        option.setAddrType("all");
//        option.setIsNeedLocationPoiList(true);
////可选，是否需要周边POI信息，默认为不需要，即参数为false
////如果开发者需要获得周边POI信息，此处必须为true
//        option.setNeedNewVersionRgc(true);
//        mLocClient.setLocOption(option);
//        mLocClient.start();
//
//    }

    private void tencentLoc(){
//        locationManager = TencentLocationManager.getInstance(this);
//        locationRequest = TencentLocationRequest.create()
//                .setInterval(1000*3) // 定位周期 (毫秒)
//                .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI) ;// 定位要求水准
//                ; // 是否使用缓存
        locationManager= TencentLocationManager.getInstance(this);
        int error = TencentLocationManager.getInstance(this)
                .requestLocationUpdates(
                        TencentLocationRequest
                                .create().setInterval(5000)
                                .setRequestLevel(
                                        TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA), this);

        if (error == 0) {

            Log.e("监听状态:", "监听成功!");

        } else if (error == 1) {

            Log.e("监听状态:", "设备缺少使用腾讯定位SDK需要的基本条件");

        } else if (error == 2) {

            Log.e("监听状态:", "配置的 key 不正确");

        }
    }
    @Override
    public void onLocationChanged(TencentLocation location, int error, String s) {
        if (error == TencentLocation.ERROR_OK ) {
//            Log.e("maplocation", "location: " + location.getCity() + " " + location.getProvider() + " " + location.getBearing()+" "+reason);
            //当前位置信息
//            tvForumPostAfterSelectLoc.setText(arg0.getAddress());
            if(isloc) {
                llyForumPostLoc.setVisibility(View.VISIBLE);
                tvForumPostLoc.setText(location.getAddress());
            }
            lon = location.getLongitude()+"";
            lat = location.getLatitude()+"";
            province = location.getProvince();
            city = location.getCity();
            addr = location.getAddress();
            locationManager.removeUpdates(this);
//            double lat = location.getLatitude();// 纬度
//            double lot = location.getLongitude();// 经度
//            double altitude = location.getAltitude();// 海拔
//            float accuracy = location.getAccuracy();// 精度
//            String nation = location.getNation();// 国家
//            String province = location.getProvince();// 省份
//            String city = location.getCity();// 城市
//            String district = location.getDistrict();// 区
//            String town = location.getTown();// 镇
//            String village = location.getVillage();// 村
//            String street = location.getStreet();// 街道
//            String streetNo = location.getStreetNo();// 门号
//
//
//
//            Log.e("定位信息:", lat + " | " + lot + " | " + altitude + " | "
//                    + accuracy + " | " + nation + " | " + province + " | "
//                    + city + " | " + district + " | " + town + " | " + village
//                    + " | " + street + " | " + streetNo);
//
////            task = lat + " | " + lot + " | " + altitude + " | " + accuracy
////                    + " | " + nation + " | " + province + " | " + city + " | "
////                    + district + " | " + town + " | " + village + " | "
////                    + street + " | " + streetNo + " | ";
//
//
//            String provider=location.getProvider();//定位方式
//            if (TencentLocation.GPS_PROVIDER.equals(provider)) {
//                // location 是GPS定位结果
//                Log.e("定位方式:","GPS");
////                ((MainActivity)main).phoneCall("腾讯定位:"+task+"GPS");
//            } else if (TencentLocation.NETWORK_PROVIDER.equals(provider)) {
//                // location 是网络定位结果
//                Log.e("定位方式:","NetWork");
////                ((MainActivity)main).phoneCall("腾讯定位:"+task+"NetWork"+" | "+location.getAddress());
////                Log.e("地址:", location.getAddress());
//            }
//            locationManager.removeUpdates(this);
//            if(mLocClient != null) {
//                mLocClient.stop();
//            }
//            Location location = new Location(arg0.getProvider());
//            location.setLatitude(arg0.getLatitude());
//            location.setLongitude(arg0.getLongitude());
//            location.setAccuracy(arg0.getAccuracy());
//            // 定位 sdk 只有 gps 返回的值才有可能获取到偏向角
//            location.setBearing(arg0.getBearing());
//            mChangedListener.onLocationChanged(location);

//            mDestinationPoint = new LatLng(39.010737 * 1.0001, 115.469589 * 1.0001);//假设公司坐标
//            //计算两个坐标点的距离（单位m）
//            mDistance = TencentLocationUtils.distanceBetween(location.getLatitude(), location.getLongitude(),40.007499,116.472209);
//
//            Log.d("jl---",mDistance + "" + "\n" + location.getLatitude() + "\n" + location.getLongitude());

        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
    public void onPause() {
        super.onPause();
        if(locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public void onResume() {
        super.onResume();
//        locationManager.requestLocationUpdates(locationRequest, this);
    }

    /**
     * 定位SDK监听函数
     */
//    public class MyLocationListenner extends BDAbstractLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // map view 销毁后不在处理新接收的位置
//            if (location == null) {
//                return;
//            }
//            if(isloc) {
//                tvForumPostLoc.setText(location.getAddrStr());
//            }
//            lon = location.getLongitude()+"";
//            lat = location.getLatitude()+"";
//            province = location.getProvince();
//            city = location.getCity();
//            addr = location.getAddrStr();
//            mLocClient.stop();
//        }
//    }
}
