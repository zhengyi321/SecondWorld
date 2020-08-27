package com.et.secondworld.mine.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.forum.adapter.ForumPostSelectImageRVAdapter;
import com.et.secondworld.network.ReportNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.SpringScrollView;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
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
 * @Date 2020/5/23
 **/
public class MineSettingReportActivity extends AppCompatActivity {

    @BindView(R.id.et_report_reason)
    EditText etReportReason;
    @BindView(R.id.et_report_content)
    EditText etReportContent;
    @BindView(R.id.et_report_name)
    EditText etReportName;
    @BindView(R.id.et_report_identifycode)
    EditText etReportIdentifyCode;
    @BindView(R.id.et_report_tel)
    EditText etReportIdentifyTel;
    @OnClick(R.id.tv_report_name_select)
    public void tvReportNameSelectOnclick(){
        nameSelect();
    }
    @BindView(R.id.ssv_report)
    SpringScrollView ssvReport;
    @BindView(R.id.rly_report_name_select)
    RelativeLayout rlyReportNameSelect;
    @BindView(R.id.lly_report_name_select)
    LinearLayoutCompat llyReportNameSelect;
    @BindView(R.id.rv_report_select_images)
    RecyclerView rvReportSelectImages;
    @OnClick({R.id.rly_report_back,R.id.rly_report_back2})
    public void rlyReportBackOnclick(){
        finish();
    }
    @BindView(R.id.rly_report_back)
    RelativeLayout rlyReportBack;
    @BindView(R.id.rly_report_back2)
    RelativeLayout rlyReportBack2;

    @BindView(R.id.rly_report_identifyface)
    RelativeLayout rlyReportIdentifyFace;
    @OnClick({R.id.rly_report_identifyface,R.id.iv_report_identifyface})
    public void rlyReportIdentifyFaceOnclick(){
        selectIdentify(0);
    }
    @BindView(R.id.rly_report_identifyback)
    RelativeLayout rlyReportIdentifyBack;
    @OnClick({R.id.rly_report_identifyback,R.id.iv_report_identifyback})
    public void rlyReportIdentifyBackOnclick(){
        selectIdentify(1);
    }

    @BindView(R.id.iv_report_identifyface)
    ImageView ivReportIdentifyFace;
    @BindView(R.id.iv_report_identifyback)
    ImageView ivReportIdentifyBack;

    @BindView(R.id.rly_report_content)
    RelativeLayout rlyReportContent;

    @OnClick(R.id.rly_report_content)
    public void rlyReportContentOnclick(){
        etReportContent.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
 /*   @BindView(R.id.rly_report_identifyface)
    RelativeLayout rlyReportIdentifyFace;
    @BindView(R.id.rly_report_identifyback)
    RelativeLayout rlyReportIdentifyBack;*/
    @BindView(R.id.rly_report_save)
    RelativeLayout rlyReportSave;
    @OnClick(R.id.rly_report_save)
    public void rlyReportSaveOnclick(){
        report();
    }
    @BindView(R.id.tv_report_content_count)
    TextView tvReportContentCount;
    private ForumPostSelectImageRVAdapter rvAdapter;
    private int selectPicCountNum = 5;
    private ArrayList<String> mSelectImages = new ArrayList<>();
    private ArrayList<String> mSelectImageIdentifyFace = new ArrayList<>();
    private ArrayList<String> mSelectImageIdentifyBack = new ArrayList<>();
    private boolean isShow = false;
    private final int SELECT_IMAGE_REQUEST_IMAGES = 0x002;
    private final int SELECT_IMAGE_REQUEST_IMAGES_IDENTIFYFACE = 0x003;
    private final int SELECT_IMAGE_REQUEST_IMAGES_IDENTIFYBACK = 0x004;
    private String reportid = "";
    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_setting_report_content);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
//        llyReportNameSelect.setVisibility(View.GONE);
        getReportid();
        initRecycleView(selectPicCountNum);
        initContentInputListener();

    }
    private void getReportid(){
        Intent intent = getIntent();
        reportid = intent.getStringExtra("reportid");
    }
    private void initContentInputListener(){
        etReportContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String content = etReportContent.getText().toString();
                tvReportContentCount.setText(content.length() + "/500"
                        );
            }

        });

    }
    private void nameSelect(){
        if(isShow){

            rlyReportNameSelect.setVisibility(View.GONE);
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                etReportName.getFocusable();
            }*/
//            llyReportNameSelect.clearAnimation();
//            rlyReportNameSelect.requestLayout();
            isShow = false;
        }else  {
            isShow = true;
            rlyReportNameSelect.setVisibility(View.VISIBLE);
        }

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
        rvReportSelectImages.setLayoutManager(gridLayoutManager);
        rvReportSelectImages.setAdapter(rvAdapter);
        rvAdapter.replaceAll(dataList);
    }
    private void selectIdentify(int type){
       /* Intent intent = new Intent(this, SelectImageActivity.class);
        intent.putExtra("type","pic");
        intent.putExtra("count",selectPicCountNum);
        intent.putParcelableArrayListExtra("selected_images", mSelectImages);

        startActivityForResult(intent, SELECT_IMAGE_REQUEST_IMAGES);*/
        if(type == 0) {
            ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                    .showCamera(true)//设置是否显示拍照按钮
                    .showImage(true)//设置是否展示图片
                    .showVideo(false)//设置是否展示视频
                    .setSingleType(true)//设置图片视频不能同时选择
                    .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                    .setImagePaths(mSelectImageIdentifyFace)
                    .setImageLoader(new GlideLoader())//设置自定义图片加载器
                    .start(this, SELECT_IMAGE_REQUEST_IMAGES_IDENTIFYFACE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
        }else {
            ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                    .showCamera(true)//设置是否显示拍照按钮
                    .showImage(true)//设置是否展示图片
                    .showVideo(false)//设置是否展示视频
                    .setSingleType(true)//设置图片视频不能同时选择
                    .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                    .setImagePaths(mSelectImageIdentifyBack)
                    .setImageLoader(new GlideLoader())//设置自定义图片加载器
                    .start(this, SELECT_IMAGE_REQUEST_IMAGES_IDENTIFYBACK);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
        }
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
                case SELECT_IMAGE_REQUEST_IMAGES_IDENTIFYFACE:
                    List<String> identifyFacePath = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (identifyFacePath == null) {
                        mSelectImageIdentifyFace.clear();
//                        rvAdapter.replaceAll(mSelectImages);
                        return;
                    }
                    if (identifyFacePath.size() == 0) {
                        mSelectImageIdentifyFace.clear();
//                        rvAdapter.replaceAll(mSelectImages);
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImageIdentifyFace.clear();
                    mSelectImageIdentifyFace.addAll(identifyFacePath);
                    rlyReportIdentifyFace.setVisibility(View.GONE);
                    ivReportIdentifyFace.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mSelectImageIdentifyFace.get(0)).into(ivReportIdentifyFace);
//                    rvAdapter.replaceAll(mSelectImages);
                    break;
                case SELECT_IMAGE_REQUEST_IMAGES_IDENTIFYBACK:
                    List<String> identifyBackPath = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (identifyBackPath == null) {
                        mSelectImageIdentifyBack.clear();
//                        rvAdapter.replaceAll(mSelectImages);
                        return;
                    }
                    if (identifyBackPath.size() == 0) {
                        mSelectImageIdentifyBack.clear();
//                        rvAdapter.replaceAll(mSelectImages);
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImageIdentifyBack.clear();
                    mSelectImageIdentifyBack.addAll(identifyBackPath);
                    rlyReportIdentifyBack.setVisibility(View.GONE);
                    ivReportIdentifyBack.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mSelectImageIdentifyBack.get(0)).into(ivReportIdentifyBack);
//                    rvAdapter.replaceAll(mSelectImages);
                    break;
            }
        }
    }

    private void report(){
        String reason = etReportReason.getText().toString();
        String content = etReportContent.getText().toString();
        String name = etReportName.getText().toString();
        String identifycode = etReportIdentifyCode.getText().toString();
        String tel = etReportIdentifyTel.getText().toString();
        if(reason == null || reason.isEmpty()){
            Toast.makeText(this,"请输入举报理由",Toast.LENGTH_LONG).show();
            return;
        }
        String imgs = "";
        String identifycodeFace = "";
        String identifycodeBack = "";
        BitmapUtils bitmapUtils = new BitmapUtils();
        for(String item : mSelectImages) {
            Bitmap bitmapHead = bitmapUtils.compressImageFromFile(item);
            //将图片显示到ImageView中
            imgs += bitmapUtils.bitmapConvertBase64(bitmapHead)+",";
        }
        for(String item : mSelectImageIdentifyFace) {
            Bitmap bitmapHead = bitmapUtils.compressImageFromFile(item);
            //将图片显示到ImageView中
            identifycodeFace = bitmapUtils.bitmapConvertBase64(bitmapHead);
        }
        for(String item : mSelectImageIdentifyBack) {
            Bitmap bitmapHead = bitmapUtils.compressImageFromFile(item);
            //将图片显示到ImageView中
            identifycodeBack = bitmapUtils.bitmapConvertBase64(bitmapHead);
        }
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        Map<String,Object> map = new HashMap<>();
        map.put("identifyface",identifycodeFace);
        map.put("identifyback",identifycodeBack);
        map.put("content",content);
        map.put("accountid",account);
        map.put("imgs",imgs);
        map.put("reportid",reportid);
        map.put("reason",reason);
        map.put("name",name);
        map.put("identifycode",identifycode);
        map.put("tel",tel);
        ReportNetWork reportNetWork = new ReportNetWork();
        reportNetWork.addReportToNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                Toast.makeText(getBaseContext(),baseBean.getMsg(),Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
