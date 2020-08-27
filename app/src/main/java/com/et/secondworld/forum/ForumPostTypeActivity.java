package com.et.secondworld.forum;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.et.secondworld.ExtendActivity;
import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetTempArticleBean;
import com.et.secondworld.forum.adapter.ForumPostSelectImageRVAdapter;
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

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/10
 **/
public class ForumPostTypeActivity extends AppCompatActivity implements LocationSource, TencentLocationListener {


    private boolean isHandFlag = false;
    private OnLocationChangedListener mChangedListener;
    private TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;
    @BindView(R.id.nsv_forum_post_after_select_hand)
    NestedScrollView nsvForumPostAfterSelectHand;
    @BindView(R.id.lly_forum_post_after_select_loc)
    LinearLayout llyForumPostAfterSelectLoc;
    @OnClick(R.id.rly_forum_post_after_select_loc_close)
    public void ibForumPostAfterSelectLocCloseOnclick(){
        addr = "";
        llyForumPostAfterSelectLoc.setVisibility(View.GONE);
        tvForumPostAfterSelectLoc.setText("");
    }
    @BindView(R.id.lly_forum_post_after_select_auto)
    LinearLayout llyForumPostAfterSelectAuto;
    @BindView(R.id.rly_forum_post_after_select_auto_hand)
    RelativeLayout rlyForumPostAfterSelectAutoHand;
    @BindView(R.id.cb_forum_post_after_select_auto_hand)
    CheckBox cbForumPostAfterSelectAutoHand;
    @BindView(R.id.lly_forum_post_after_select_content1)
    LinearLayout llyForumPostAfterSelectContent1;
    @BindView(R.id.ret_forum_post_after_select_content1)
    REditText retForumPostAfterSelectContent1;
    @BindView(R.id.rly_forum_post_after_select_content1_img)
    RelativeLayout rlyForumPostAfterSelectContent1Img;
    @BindView(R.id.iv_forum_post_after_select_content1_img)
    ImageView ivForumPostAfterSelectContent1Img;
    private long clickTime = 0;
    @OnClick({R.id.iv_forum_post_after_select_content1_img,R.id.rly_forum_post_after_select_content1_img})
    public void ivForumPostAfterSelectContent1ImgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectImgHandActivity(1);
        }
    }
    @OnClick({R.id.iv_forum_post_after_select_content2_img,R.id.rly_forum_post_after_select_content2_img})
    public void ivForumPostAfterSelectContent2ImgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectImgHandActivity(2);
        }
    }
    @OnClick({R.id.iv_forum_post_after_select_content3_img,R.id.rly_forum_post_after_select_content3_img})
    public void ivForumPostAfterSelectContent3ImgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectImgHandActivity(3);
        }
    }

    @OnClick({R.id.iv_forum_post_after_select_content4_img,R.id.rly_forum_post_after_select_content4_img})
    public void ivForumPostAfterSelectContent4ImgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectImgHandActivity(4);
        }
    }

    @OnClick({R.id.iv_forum_post_after_select_content5_img,R.id.rly_forum_post_after_select_content5_img})
    public void ivForumPostAfterSelectContent5ImgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectImgHandActivity(5);
        }
    }

    @OnClick({R.id.iv_forum_post_after_select_content6_img,R.id.rly_forum_post_after_select_content6_img})
    public void ivForumPostAfterSelectContent6ImgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectImgHandActivity(6);
        }
    }

    @BindView(R.id.tv_forum_post_after_select_content1_add)
    TextView tvForumPostAfterSelectContent1Add;
    @BindView(R.id.rly_forum_post_after_select_content1_add)
    RelativeLayout rlyForumPostAfterSelectContent1Add;
    @OnClick(R.id.rly_forum_post_after_select_content1_add)
    public void tvForumPostAfterSelectContent1AddOnclick(){
        handModule2Add();

    }
    @BindView(R.id.rly_forum_post_after_select_module1_img)
    RelativeLayout rlyForumPostAfterSelectModule1Img;
    @BindView(R.id.iv_forum_post_after_select_module1_img)
    ImageView ivForumPostAfterSelectModule1Img;
    @BindView(R.id.rly_forum_post_after_select_module1)
    RelativeLayout rlyForumPostAfterSelectModule1;
    @OnClick(R.id.rly_forum_post_after_select_module1)
    public void rlyForumPostAfterSelectModule1Onclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectImgModule1();
        }
    }
    private void handModule2Add(){
        rlyForumPostAfterSelectContent1Add.setVisibility(View.GONE);
        llyForumPostAfterSelectContent2.setVisibility(View.VISIBLE);
        tvForumPostAfterSelectContent2Delete.setVisibility(View.VISIBLE);
        rlyForumPostAfterSelectContent2Add.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.lly_forum_post_after_select_content1)
    public void llyForumPostAfterSelectContent1Onclick(){
        retForumPostAfterSelectContent1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
    @OnClick(R.id.lly_forum_post_after_select_content2)
    public void llyForumPostAfterSelectContent2Onclick(){
        retForumPostAfterSelectContent2.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
    @OnClick(R.id.lly_forum_post_after_select_content3)
    public void llyForumPostAfterSelectContent3Onclick(){
        retForumPostAfterSelectContent3.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
    @OnClick(R.id.lly_forum_post_after_select_content4)
    public void llyForumPostAfterSelectContent4Onclick(){
        retForumPostAfterSelectContent4.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
    @OnClick(R.id.lly_forum_post_after_select_content5)
    public void llyForumPostAfterSelectContent5Onclick(){
        retForumPostAfterSelectContent5.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
    @OnClick(R.id.lly_forum_post_after_select_content6)
    public void llyForumPostAfterSelectContent6Onclick(){
        retForumPostAfterSelectContent6.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
    @BindView(R.id.lly_forum_post_after_select_content2)
    LinearLayout llyForumPostAfterSelectContent2;
    @BindView(R.id.ret_forum_post_after_select_content2)
    REditText retForumPostAfterSelectContent2;
    @BindView(R.id.rly_forum_post_after_select_content2_img)
    RelativeLayout rlyForumPostAfterSelectContent2Img;
    @BindView(R.id.iv_forum_post_after_select_content2_img)
    ImageView ivForumPostAfterSelectContent2Img;
    @BindView(R.id.rly_forum_post_after_select_content2_add)
    RelativeLayout rlyForumPostAfterSelectContent2Add;
    @OnClick(R.id.rly_forum_post_after_select_content2_add)
    public void rlyForumPostAfterSelectContent2AddOnclick(){
        handModule3Add();
    }
    private void handModule3Add(){
        rlyForumPostAfterSelectContent2Add.setVisibility(View.GONE);
        llyForumPostAfterSelectContent3.setVisibility(View.VISIBLE);
        tvForumPostAfterSelectContent2Delete.setVisibility(View.GONE);


        tvForumPostAfterSelectContent3Delete.setVisibility(View.VISIBLE);
        rlyForumPostAfterSelectContent3Add.setVisibility(View.VISIBLE);
    }

    @BindView(R.id.tv_forum_post_after_select_content2_delete)
    TextView tvForumPostAfterSelectContent2Delete;
    @OnClick(R.id.tv_forum_post_after_select_content2_delete)
    public void tvForumPostAfterSelectContent2DeleteOnclick(){
        handModule2Delete();
    };
    private void handModule2Delete(){
        rlyForumPostAfterSelectContent1Add.setVisibility(View.VISIBLE);
        llyForumPostAfterSelectContent2.setVisibility(View.GONE);
        rlyForumPostAfterSelectContent2Img.setVisibility(View.VISIBLE);
        ivForumPostAfterSelectContent2Img.setVisibility(View.GONE);
        mSelectImagesHand2.clear();
    }
    @BindView(R.id.lly_forum_post_after_select_content3)
    LinearLayout llyForumPostAfterSelectContent3;
    @BindView(R.id.ret_forum_post_after_select_content3)
    REditText retForumPostAfterSelectContent3;
    @BindView(R.id.rly_forum_post_after_select_content3_img)
    RelativeLayout rlyForumPostAfterSelectContent3Img;
    @BindView(R.id.iv_forum_post_after_select_content3_img)
    ImageView ivForumPostAfterSelectContent3Img;
    @BindView(R.id.rly_forum_post_after_select_content3_add)
    RelativeLayout rlyForumPostAfterSelectContent3Add;
    @OnClick(R.id.rly_forum_post_after_select_content3_add)
    public void rlyForumPostAfterSelectContent3AddOnclick(){
        handModule4Add();
    }
    private void handModule4Add(){
        rlyForumPostAfterSelectContent3Add.setVisibility(View.GONE);
        tvForumPostAfterSelectContent3Delete.setVisibility(View.GONE);
        llyForumPostAfterSelectContent4.setVisibility(View.VISIBLE);


        tvForumPostAfterSelectContent4Delete.setVisibility(View.VISIBLE);
        rlyForumPostAfterSelectContent4Add.setVisibility(View.VISIBLE);
    }
    @BindView(R.id.tv_forum_post_after_select_content3_delete)
    TextView tvForumPostAfterSelectContent3Delete;
    @OnClick(R.id.tv_forum_post_after_select_content3_delete)
    public void tvForumPostAfterSelectContent3DeleteOnclick(){
        handModule3Delete();
    };
    private void handModule3Delete(){
        rlyForumPostAfterSelectContent2Add.setVisibility(View.VISIBLE);
        tvForumPostAfterSelectContent2Delete.setVisibility(View.VISIBLE);
        llyForumPostAfterSelectContent3.setVisibility(View.GONE);

        rlyForumPostAfterSelectContent3Img.setVisibility(View.VISIBLE);
        ivForumPostAfterSelectContent3Img.setVisibility(View.GONE);
        mSelectImagesHand3.clear();
    }
    @BindView(R.id.lly_forum_post_after_select_content4)
    LinearLayout llyForumPostAfterSelectContent4;
    @BindView(R.id.ret_forum_post_after_select_content4)
    REditText retForumPostAfterSelectContent4;
    @BindView(R.id.rly_forum_post_after_select_content4_img)
    RelativeLayout rlyForumPostAfterSelectContent4Img;
    @BindView(R.id.iv_forum_post_after_select_content4_img)
    ImageView ivForumPostAfterSelectContent4Img;
    @BindView(R.id.rly_forum_post_after_select_content4_add)
    RelativeLayout rlyForumPostAfterSelectContent4Add;
    @OnClick(R.id.rly_forum_post_after_select_content4_add)
    public void rlyForumPostAfterSelectContent4AddOnclick(){
        handModule5Add();
    }
    private void handModule5Add(){
        rlyForumPostAfterSelectContent4Add.setVisibility(View.GONE);
        tvForumPostAfterSelectContent4Delete.setVisibility(View.GONE);
        llyForumPostAfterSelectContent5.setVisibility(View.VISIBLE);
        tvForumPostAfterSelectContent5Delete.setVisibility(View.VISIBLE);
        rlyForumPostAfterSelectContent5Add.setVisibility(View.VISIBLE);
    }
    @BindView(R.id.tv_forum_post_after_select_content4_delete)
    TextView tvForumPostAfterSelectContent4Delete;
    @OnClick(R.id.tv_forum_post_after_select_content4_delete)
    public void tvForumPostAfterSelectContent4DeleteOnclick(){
        handModule4Delete();

    };
    private void handModule4Delete(){
        rlyForumPostAfterSelectContent3Add.setVisibility(View.VISIBLE);
        tvForumPostAfterSelectContent3Delete.setVisibility(View.VISIBLE);
        llyForumPostAfterSelectContent4.setVisibility(View.GONE);

        rlyForumPostAfterSelectContent4Img.setVisibility(View.VISIBLE);
        ivForumPostAfterSelectContent4Img.setVisibility(View.GONE);
        mSelectImagesHand4.clear();
    }
    @BindView(R.id.lly_forum_post_after_select_content5)
    LinearLayout llyForumPostAfterSelectContent5;
    @BindView(R.id.ret_forum_post_after_select_content5)
    REditText retForumPostAfterSelectContent5;
    @BindView(R.id.rly_forum_post_after_select_content5_img)
    RelativeLayout rlyForumPostAfterSelectContent5Img;
    @BindView(R.id.iv_forum_post_after_select_content5_img)
    ImageView ivForumPostAfterSelectContent5Img;
    @BindView(R.id.rly_forum_post_after_select_content5_add)
    RelativeLayout rlyForumPostAfterSelectContent5Add;
    @OnClick(R.id.rly_forum_post_after_select_content5_add)
    public void rlyForumPostAfterSelectContent5AddOnclick(){
        handModule6Add();
//        rlyForumPostAfterSelectContent6Add.setVisibility(View.VISIBLE);
    };
    private void handModule6Add(){
        rlyForumPostAfterSelectContent5Add.setVisibility(View.GONE);
        tvForumPostAfterSelectContent5Delete.setVisibility(View.GONE);
        llyForumPostAfterSelectContent6.setVisibility(View.VISIBLE);
        tvForumPostAfterSelectContent6Delete.setVisibility(View.VISIBLE);
    }
    @BindView(R.id.tv_forum_post_after_select_content5_delete)
    TextView tvForumPostAfterSelectContent5Delete;
    @OnClick(R.id.tv_forum_post_after_select_content5_delete)
    public void tvForumPostAfterSelectContent5DeleteOnclick(){

        handModule5Delete();
    };
    private void handModule5Delete(){
        rlyForumPostAfterSelectContent4Add.setVisibility(View.VISIBLE);
        tvForumPostAfterSelectContent4Delete.setVisibility(View.VISIBLE);
        llyForumPostAfterSelectContent5.setVisibility(View.GONE);

        rlyForumPostAfterSelectContent5Img.setVisibility(View.VISIBLE);
        ivForumPostAfterSelectContent5Img.setVisibility(View.GONE);
        mSelectImagesHand5.clear();
    }
    @BindView(R.id.lly_forum_post_after_select_content6)
    LinearLayout llyForumPostAfterSelectContent6;
    @BindView(R.id.ret_forum_post_after_select_content6)
    REditText retForumPostAfterSelectContent6;
    @BindView(R.id.rly_forum_post_after_select_content6_img)
    RelativeLayout rlyForumPostAfterSelectContent6Img;
    @BindView(R.id.iv_forum_post_after_select_content6_img)
    ImageView ivForumPostAfterSelectContent6Img;
    @BindView(R.id.tv_forum_post_after_select_content6_delete)
    TextView tvForumPostAfterSelectContent6Delete;
    @OnClick(R.id.tv_forum_post_after_select_content6_delete)
    public void tvForumPostAfterSelectContent6DeleteOnclick(){

        handModule6Delete();
    };
    private void handModule6Delete(){
        rlyForumPostAfterSelectContent5Add.setVisibility(View.VISIBLE);
        tvForumPostAfterSelectContent5Delete.setVisibility(View.VISIBLE);
        llyForumPostAfterSelectContent6.setVisibility(View.GONE);
        rlyForumPostAfterSelectContent6Img.setVisibility(View.VISIBLE);
        ivForumPostAfterSelectContent6Img.setVisibility(View.GONE);
        mSelectImagesHand6.clear();
    }


    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    @BindView(R.id.tv_forum_post_after_select_module)
    TextView tvForumPostAfterSelectModule;
    @BindView(R.id.rv_forum_post_after_select_images)
    RecyclerView rvForumPostAfterSelectImages;
    @BindView(R.id.rly_forum_post_after_select_image)
    RelativeLayout rlyForumPostAfterSelectImage;
    @OnClick(R.id.rly_forum_post_after_select_image)
    public void rlyForumPostAfterSelectImageOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            selectImgActivity();
        }
    }
    private final int SELECT_AT_REQUEST = 0x0033;
    @BindView(R.id.et_forum_post_after_select_title)
    EditText etForumPostAfterSelectTitle;
    @BindView(R.id.ret_forum_post_after_select_content)
    REditText retForumPostAfterSelectContent;
    @OnClick({R.id.rly_forum_post_after_select_content,R.id.lly_forum_post_after_select_auto})
    public void rlyForumPostAfterSelectContentOnclick(){

        retForumPostAfterSelectContent.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @BindView(R.id.rly_forum_post_after_select_back)
    RelativeLayout rlyForumPostAfterSelectBack;
    @OnClick(R.id.rly_forum_post_after_select_back)
    public void rlyForumPostAfterSelectBackOnclick(){
//        String title = etForumPostAfterSelectTitle.getText().toString();
        String content = "";
        String content1 = retForumPostAfterSelectContent1.getText().toString();
        String content2 = retForumPostAfterSelectContent2.getText().toString();
        String content3 = retForumPostAfterSelectContent3.getText().toString();
        String content4 = retForumPostAfterSelectContent4.getText().toString();
        String content5 = retForumPostAfterSelectContent5.getText().toString();
        String content6 = retForumPostAfterSelectContent6.getText().toString();
        String title = etForumPostAfterSelectTitle.getText().toString();
        if(isHandFlag) {
            content = content1 + content2 + content3 + content4 + content5 + content6;

        }else {
            content = retForumPostAfterSelectContent.getText().toString();
        }
        if(title.length() <1 && content.length()<1 && mSelectImages.size() == 0){
//            Toast.makeText(getBaseContext(),"标题请输入至少一个字",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

//        if(content.length() >=5 || mSelectImages.size()>0){
//
//        }else {
////            Toast.makeText(getBaseContext(),"内容请输入至少五个字",Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
        QueryCancelDialog queryCancelDialog = new QueryCancelDialog(this).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
            @Override
            public void isQuery(boolean isQuery) {
                if(isQuery) {
                    new Thread(runnable).start();
                }else {
                    if(cbForumPostAfterSelectAutoHand.isChecked()){
                        saveTempHand(false);
                    }else {
                        saveTempAuto(false);
                    }
                }
            }
        }).build(this,"保存草稿吗？");
        queryCancelDialog.show();
//        new AlertDialog.Builder(this).setTitle("保存草稿吗？").setMessage("")
//
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which){
////                        dialog.dismiss();
//                        new Thread(runnable).start();
//
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(cbForumPostAfterSelectAutoHand.isChecked()){
//                            saveTempHand(false);
//                        }else {
//                            saveTempAuto(false);
//                        }
////                        saveTemp(false);
////                        finish();
//                    }
//                }).show();
    }
    private String atnicks = "";

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
            if(cbForumPostAfterSelectAutoHand.isChecked()){
                saveTempHand(true);
            }else {
                saveTempAuto(true);
            }

        }
    };

    private void saveTempHand(boolean issave){
        String title = etForumPostAfterSelectTitle.getText().toString();
        String imgs = "";
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        Map<String,Object> map = new HashMap<>();
        if(issave) {
            String content = "";
            String content1 = retForumPostAfterSelectContent1.getText().toString();
            String content2 = retForumPostAfterSelectContent2.getText().toString();
            String content3 = retForumPostAfterSelectContent3.getText().toString();
            String content4 = retForumPostAfterSelectContent4.getText().toString();
            String content5 = retForumPostAfterSelectContent5.getText().toString();
            String content6 = retForumPostAfterSelectContent6.getText().toString();
            content = content1+content2+content3+content4+content5+content6;
            sections = content1.length()+","+content2.length()+","+content3.length()+","+content4.length()+","+content5.length()+","+content6.length();

            if(mSelectImagesHand1 != null && mSelectImagesHand1.size() > 0){
                mSelectImagesHandTotal.add(mSelectImagesHand1.get(0));
            }else {
                if(content1.length() != 0) {
                    mSelectImagesHandTotal.add("");
                }
            }
            if(mSelectImagesHand2 != null && mSelectImagesHand2.size() > 0){
                mSelectImagesHandTotal.add(mSelectImagesHand2.get(0));
            }else {
                if(content2.length() != 0) {
                    mSelectImagesHandTotal.add("");
                }
            }
            if(mSelectImagesHand3 != null && mSelectImagesHand3.size() > 0){
                mSelectImagesHandTotal.add(mSelectImagesHand3.get(0));
            }else {
                if(content3.length() != 0) {
                    mSelectImagesHandTotal.add("");
                }
            }
            if(mSelectImagesHand4 != null && mSelectImagesHand4.size() > 0){
                mSelectImagesHandTotal.add(mSelectImagesHand4.get(0));
            }else {
                if(content4.length() != 0) {
                    mSelectImagesHandTotal.add("");
                }
            }
            if(mSelectImagesHand5 != null && mSelectImagesHand5.size() > 0){
                mSelectImagesHandTotal.add(mSelectImagesHand5.get(0));
            }else {
                if(content5.length() != 0) {
                    mSelectImagesHandTotal.add("");
                }
            }
            if(mSelectImagesHand6 != null && mSelectImagesHand6.size() > 0){
                mSelectImagesHandTotal.add(mSelectImagesHand6.get(0));
            }else {
                if(content6.length() != 0) {
                    mSelectImagesHandTotal.add("");
                }
            }
            BitmapUtils bitmapUtils = new BitmapUtils();
            for(String item : mSelectImagesHandTotal) {
                if(item.indexOf("http") >=0){
                    imgs += item+",";
                    continue;
                }
                if(item.isEmpty()){
                    imgs +=  ",";
                }else {
                    Bitmap bitmapHead = bitmapUtils.compressImageFromFile(item);
                    //将图片显示到ImageView中
                    imgs += bitmapUtils.bitmapConvertBase64(bitmapHead) + ",";
                }
            }
            Log.d("temptype11",mSelectImagesHandTotal.size()+"");
            List<RObject> rObjectList = retForumPostAfterSelectContent1.getObjects();
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
            map.put("type", "M3");
            map.put("sections", sections);
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
            map.put("type", "M3");
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


    private void saveTempAuto(boolean issave){
        String title = etForumPostAfterSelectTitle.getText().toString();
        String content = retForumPostAfterSelectContent.getText().toString();
        String imgs = "";
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        Map<String,Object> map = new HashMap<>();
        switch (type){
            case 1:
                map.put("type", "M1");
                break;
            case 2:
                map.put("type", "M2");
                break;
            case 3:
                map.put("type", "M3");
                break;
        }
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

            List<RObject> rObjectList = retForumPostAfterSelectContent.getObjects();
            for(RObject item:rObjectList){
                atids += item.getId()+",";
                atnicks += item.getObjectText()+",";
            }


            map.put("title", title);
            map.put("content", content);
            map.put("account", account);
            map.put("imgs", imgs);
            map.put("city", province+ city);
            map.put("addr", addr);
            map.put("lat", lat);
            map.put("lon", lon);
            map.put("atid", atids);
            map.put("atnick", atnicks);
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
    @BindView(R.id.tv_forum_post_after_select_loc)
    TextView tvForumPostAfterSelectLoc;
    @BindView(R.id.rly_forum_post_after_select_loc)
    RelativeLayout rlyForumPostAfterSelectLoc;
    @OnClick(R.id.rly_forum_post_after_select_loc)
    public void rlyForumPostAfterSelectLocOnclick(){
        isLoc = true;
//        loc();
        tencentLoc();
    }
    @BindView(R.id.rly_forum_post_after_select_at)
    RelativeLayout rlyForumPostAfterSelectAt;
    @OnClick(R.id.rly_forum_post_after_select_at)
    public void rlyForumPostAfterSelectAtOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, ForumAtActivity.class);
            startActivityForResult(intent, SELECT_AT_REQUEST);
        }
    }
    @BindView(R.id.rly_forum_post_after_select_release)
    public RelativeLayout rlyForumPostAfterSelectRelease;
    @OnClick(R.id.rly_forum_post_after_select_release)
    public void rlyForumPostAfterSelectReleaseOnclick(){
        if(isHandFlag){

            handRealse();
        }else {
            autoRealse();
        }

    }

    private void autoRealse(){
        Intent intent = new Intent(this, ExtendActivity.class);
        String content = retForumPostAfterSelectContent.getText().toString();
        String title = etForumPostAfterSelectTitle.getText().toString();
        if(title.length() <1){
            Toast.makeText(getBaseContext(),"标题请输入至少一个字",Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.length() >=5 || mSelectImages.size()>0){

        }else {
            Toast.makeText(getBaseContext(),"内容请输入至少五个字",Toast.LENGTH_SHORT).show();
            return;
        }
        List<RObject> rObjectList = retForumPostAfterSelectContent.getObjects();
        for(RObject item:rObjectList){
            atids += item.getId()+",";
        }
        sections="-1";
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("addr",addr);
        intent.putExtra("city",province+city);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        intent.putStringArrayListExtra("GET_IMGS",mSelectImages);
        intent.putExtra("atids",atids);
        intent.putExtra("sections",sections);
        switch (type){
            case 1:

                intent.putExtra("type","M1");
                break;

            case 2:
                intent.putExtra("type","M2");
                break;

            case 3:
                intent.putExtra("type","M3");
                break;
        }
        startActivity(intent);
        finish();

    }

    private void handRealse(){
        Intent intent = new Intent(this, ExtendActivity.class);
        String content = "";
        String content1 = retForumPostAfterSelectContent1.getText().toString();
        String content2 = retForumPostAfterSelectContent2.getText().toString();
        String content3 = retForumPostAfterSelectContent3.getText().toString();
        String content4 = retForumPostAfterSelectContent4.getText().toString();
        String content5 = retForumPostAfterSelectContent5.getText().toString();
        String content6 = retForumPostAfterSelectContent6.getText().toString();
        String title = etForumPostAfterSelectTitle.getText().toString();
        content = content1+content2+content3+content4+content5+content6;
        sections = content1.length()+","+content2.length()+","+content3.length()+","+content4.length()+","+content5.length()+","+content6.length();
        if(title.length() <1){
            Toast.makeText(getBaseContext(),"标题请输入至少一个字",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mSelectImagesHand1 != null && mSelectImagesHand1.size() > 0){
            mSelectImagesHandTotal.add(mSelectImagesHand1.get(0));
        }else {
            if(content1.length() != 0) {
                mSelectImagesHandTotal.add("");
            }
        }
        if(mSelectImagesHand2 != null && mSelectImagesHand2.size() > 0){
            mSelectImagesHandTotal.add(mSelectImagesHand2.get(0));
        }else {
            if(content2.length() != 0) {
                mSelectImagesHandTotal.add("");
            }
        }
        if(mSelectImagesHand3 != null && mSelectImagesHand3.size() > 0){
            mSelectImagesHandTotal.add(mSelectImagesHand3.get(0));
        }else {
            if(content3.length() != 0) {
                mSelectImagesHandTotal.add("");
            }
        }
        if(mSelectImagesHand4 != null && mSelectImagesHand4.size() > 0){
            mSelectImagesHandTotal.add(mSelectImagesHand4.get(0));
        }else {
            if(content4.length() != 0) {
                mSelectImagesHandTotal.add("");
            }
        }
        if(mSelectImagesHand5 != null && mSelectImagesHand5.size() > 0){
            mSelectImagesHandTotal.add(mSelectImagesHand5.get(0));
        }else {
            if(content5.length() != 0) {
                mSelectImagesHandTotal.add("");
            }
        }
        if(mSelectImagesHand6 != null && mSelectImagesHand6.size() > 0){
            mSelectImagesHandTotal.add(mSelectImagesHand6.get(0));
        }else {
            if(content6.length() != 0) {
                mSelectImagesHandTotal.add("");
            }
        }


        if(content.length() >=5 || mSelectImagesHandTotal.size()>0){

        }else {
            Toast.makeText(getBaseContext(),"内容请输入至少五个字",Toast.LENGTH_SHORT).show();
            return;
        }

        List<RObject> rObjectList = retForumPostAfterSelectContent1.getObjects();
        for(RObject item:rObjectList){
            atids += item.getId()+",";
        }
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("addr",addr);
        intent.putExtra("city",province+city);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        intent.putStringArrayListExtra("GET_IMGS",mSelectImagesHandTotal);
        intent.putExtra("atids",atids);
        intent.putExtra("sections",sections);
        switch (type){
            case 1:

                intent.putExtra("type","M1");
                break;

            case 2:
                intent.putExtra("type","M2");
                break;

            case 3:
                intent.putExtra("type","M3");
                break;
        }
        startActivity(intent);
        finish();
    }
    private String atids = "";
    private String lat ="";
    private String lon ="";
    private boolean isLoc = false;
    private String province = "";
    private String city = "";
    private String addr = "";
    private String sections = "";
    private ForumPostSelectImageRVAdapter rvAdapter;
    private int type = 0;
    private int selectPicCountNum = 0;
    private final int SELECT_IMAGE_REQUEST_IMAGES = 0x002;
    private final int SELECT_IMAGE_REQUEST_IMAGES_HAND1 = 0x003;
    private final int SELECT_IMAGE_REQUEST_IMAGES_HAND2 = 0x004;
    private final int SELECT_IMAGE_REQUEST_IMAGES_HAND3 = 0x005;
    private final int SELECT_IMAGE_REQUEST_IMAGES_HAND4 = 0x006;
    private final int SELECT_IMAGE_REQUEST_IMAGES_HAND5 = 0x007;
    private final int SELECT_IMAGE_REQUEST_IMAGES_HAND6 = 0x008;
    private final int SELECT_IMAGE_REQUEST_IMAGES_MODULE1 = 0x009;
    private ArrayList<String> mSelectImages = new ArrayList<>();
    private ArrayList<String> mSelectImagesHandTotal = new ArrayList<>();
    private ArrayList<String> mSelectImagesHand1 = new ArrayList<>();
    private ArrayList<String> mSelectImagesHand2 = new ArrayList<>();
    private ArrayList<String> mSelectImagesHand3 = new ArrayList<>();
    private ArrayList<String> mSelectImagesHand4 = new ArrayList<>();
    private ArrayList<String> mSelectImagesHand5 = new ArrayList<>();
    private ArrayList<String> mSelectImagesHand6 = new ArrayList<>();
    private ArrayList<String> mSelectImagesModule1 = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_forum_post_after_select);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getType();
//        tencentLoc();
//        loc();
        initAutoHand();
        initGetTempFromNet();
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


    private void initGetTempFromNet(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        Map<String,Object> map = new HashMap<>();
        map.put("account",account);
        switch (type){
            case 1:
                map.put("modules", "M1");
                break;
            case 2:
                map.put("modules", "M2");
                break;
            case 3:
                map.put("modules", "M3");
                break;
        }
//        map.put("modules","M4");
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
                    String atids = getTempArticleBean.getAtid();
                    String sections = getTempArticleBean.getSections();
                    String[] nicks = atnicks.split(",");
                    String[] ids = atids.split(",");
                    List<RObject> rObjectList = new ArrayList<>();
                    city = getTempArticleBean.getCity();
                    addr = getTempArticleBean.getAddr();
                    lat= getTempArticleBean.getLat();
                    lon = getTempArticleBean.getLon();
                    if(sections != null && !sections.isEmpty()){
                        List<GetTempArticleBean.ImglistBean> imglist = getTempArticleBean.getImglist();
                        try {

                            for (int i = 0; i < nicks.length; i++) {
                                if (!nicks[i].isEmpty()) {
                                    String removeNick = "@" + nicks[i];

                                    content = content.replace(removeNick, "");
                                    //                                Log.d("temparticle11",content);
                                    RObject rObject = new RObject();
                                    rObject.setId(ids[i]);
                                    rObject.setObjectText(nicks[i]);
                                    rObjectList.add(rObject);
                                    //                                etForumPostContent.setObject(rObject);
                                }
                            }
                            etForumPostAfterSelectTitle.setText(title);
                            retForumPostAfterSelectContent.setText(content.trim());
                            if(sections.equals("-1")) {

                                for (GetTempArticleBean.ImglistBean item : imglist) {
                                    mSelectImages.add(item.getImgurl());
                                }
                                rvAdapter.replaceAll(mSelectImages);




                                    //                        if(!addr.isEmpty()){
                                    //                            tvForumPostLoc.setText(addr);
                                    //                        }
                                    for (RObject item : rObjectList) {
                                        retForumPostAfterSelectContent.setObject(item);
                                    }


                            }else {

                                cbForumPostAfterSelectAutoHand.setChecked(true);
                                String[] tempSections = sections.split(",");

                                String temp = "";
                                int first = 0;
                                int last = 0;
                                for (int i = 0; i < tempSections.length; i++) {
                                    switch (i){
                                        case 0:
                                            last = Integer.parseInt(tempSections[i]);
                                            temp = content.substring(first,last);
                                            retForumPostAfterSelectContent1.setText(temp);
                                            if (i < imglist.size()) {
                                                mSelectImagesHand1.add(imglist.get(i).getImgurl());
                                                if (!imglist.get(i).getImgurl().isEmpty()){
                                                    rlyForumPostAfterSelectContent1Img.setVisibility(View.GONE);
                                                    ivForumPostAfterSelectContent1Img.setVisibility(View.VISIBLE);

                                                    Glide.with(getBaseContext()).load(imglist.get(i).getImgurl()).into(ivForumPostAfterSelectContent1Img);
                                                }
                                            }
                                            continue;

                                        case 1:
                                            first = last;
                                            last += Integer.parseInt(tempSections[i]);


                                            if (i < imglist.size()) {
                                                if(first != last ||  !imglist.get(i).getImgurl().isEmpty()){
                                                    handModule2Add();
                                                    temp = content.substring(first,last);

                                                    retForumPostAfterSelectContent2.setText(temp);

                                                }
                                                mSelectImagesHand2.add(imglist.get(i).getImgurl());
                                                if (!imglist.get(i).getImgurl().isEmpty()){
                                                    rlyForumPostAfterSelectContent2Img.setVisibility(View.GONE);
                                                    ivForumPostAfterSelectContent2Img.setVisibility(View.VISIBLE);
                                                    Glide.with(getBaseContext()).load(imglist.get(i).getImgurl()).into(ivForumPostAfterSelectContent2Img);
                                                }
                                            }
                                            continue;
                                        case 2:
                                            first = last;
                                            last += Integer.parseInt(tempSections[i]);


                                            if (i < imglist.size()) {
                                                if(first != last ||  !imglist.get(i).getImgurl().isEmpty()){
                                                    handModule3Add();
                                                    temp = content.substring(first,last);

                                                    retForumPostAfterSelectContent3.setText(temp);

                                                }
                                                mSelectImagesHand3.add(imglist.get(i).getImgurl());
                                                if (!imglist.get(i).getImgurl().isEmpty()){
                                                    rlyForumPostAfterSelectContent3Img.setVisibility(View.GONE);
                                                    ivForumPostAfterSelectContent3Img.setVisibility(View.VISIBLE);
                                                    Glide.with(getBaseContext()).load(imglist.get(i).getImgurl()).into(ivForumPostAfterSelectContent3Img);
                                                }
                                            }
                                            continue;
                                        case 3:
                                            first = last;
                                            last += Integer.parseInt(tempSections[i]);


                                            if (i < imglist.size()) {
                                                if(first != last ||  !imglist.get(i).getImgurl().isEmpty()){
                                                    handModule4Add();
                                                    temp = content.substring(first,last);

                                                    retForumPostAfterSelectContent4.setText(temp);

                                                }
                                                mSelectImagesHand4.add(imglist.get(i).getImgurl());
                                                if (!imglist.get(i).getImgurl().isEmpty()){
                                                    rlyForumPostAfterSelectContent4Img.setVisibility(View.GONE);
                                                    ivForumPostAfterSelectContent4Img.setVisibility(View.VISIBLE);
                                                    Glide.with(getBaseContext()).load(imglist.get(i).getImgurl()).into(ivForumPostAfterSelectContent4Img);
                                                }
                                            }
                                            continue;
                                        case 4:
                                            first = last;
                                            last += Integer.parseInt(tempSections[i]);


                                            if (i < imglist.size()) {
                                                if(first != last ||  !imglist.get(i).getImgurl().isEmpty()){
                                                    handModule5Add();
                                                    temp = content.substring(first,last);

                                                    retForumPostAfterSelectContent5.setText(temp);

                                                }
                                                mSelectImagesHand5.add(imglist.get(i).getImgurl());
                                                if (!imglist.get(i).getImgurl().isEmpty()){
                                                    rlyForumPostAfterSelectContent5Img.setVisibility(View.GONE);
                                                    ivForumPostAfterSelectContent5Img.setVisibility(View.VISIBLE);
                                                    Glide.with(getBaseContext()).load(imglist.get(i).getImgurl()).into(ivForumPostAfterSelectContent5Img);
                                                }
                                            }
                                            continue;
                                        case 5:
                                            first = last;
                                            last += Integer.parseInt(tempSections[i]);


                                            if (i < imglist.size()) {
                                                if(first != last ||  !imglist.get(i).getImgurl().isEmpty()){
                                                    handModule6Add();
                                                    temp = content.substring(first,last);

                                                    retForumPostAfterSelectContent6.setText(temp);

                                                }
                                                mSelectImagesHand6.add(imglist.get(i).getImgurl());
                                                if (!imglist.get(i).getImgurl().isEmpty()){
                                                    rlyForumPostAfterSelectContent6Img.setVisibility(View.GONE);
                                                    ivForumPostAfterSelectContent6Img.setVisibility(View.VISIBLE);
                                                    Glide.with(getBaseContext()).load(imglist.get(i).getImgurl()).into(ivForumPostAfterSelectContent6Img);
                                                }
                                            }
                                            continue;
                                    }

                                }


                            }
                        }catch (Exception e){

                        }

                    }

                }
            }
        });
    }

    private void initAutoHand(){
        if(type == 3){
            rlyForumPostAfterSelectAutoHand.setVisibility(View.VISIBLE);
        }else {
            rlyForumPostAfterSelectAutoHand.setVisibility(View.GONE);
        }
        cbForumPostAfterSelectAutoHand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    llyForumPostAfterSelectAuto.setVisibility(View.GONE);
                    nsvForumPostAfterSelectHand.setVisibility(View.VISIBLE);
//                    rlyForumPostAfterSelectContent1Img.setVisibility(View.VISIBLE);
//                    ivForumPostAfterSelectContent1Img.setVisibility(View.GONE);
                    mSelectImages.clear();
                    rvAdapter.replaceAll(mSelectImages);
                    isHandFlag = true;
                }else {
                    llyForumPostAfterSelectAuto.setVisibility(View.VISIBLE);
                    nsvForumPostAfterSelectHand.setVisibility(View.GONE);

                    rlyForumPostAfterSelectContent1Img.setVisibility(View.VISIBLE);
                    ivForumPostAfterSelectContent1Img.setVisibility(View.GONE);
                    mSelectImagesHand1.clear();
                    handModule6Delete();
                    handModule5Delete();
                    handModule4Delete();
                    handModule3Delete();
                    handModule2Delete();
                    isHandFlag = false;
                }
            }
        });
    }

    private void getType(){
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        switch (type){
            case 1:
                tvForumPostAfterSelectModule.setText("正在使用模板1发帖");
                rlyForumPostAfterSelectImage.setVisibility(View.VISIBLE);
                rvForumPostAfterSelectImages.setVisibility(View.VISIBLE);
                rlyForumPostAfterSelectModule1.setVisibility(View.GONE);
//                rlyForumPostAfterSelectModule1Img.setVisibility(View.VISIBLE);
//                rlyForumPostAfterSelectModule1Img.setVisibility(View.VISIBLE)
                selectPicCountNum = 1;
                initRecycleView(selectPicCountNum);
                break;
            case 2:
                tvForumPostAfterSelectModule.setText("正在使用模板2发帖");
                selectPicCountNum = 3;
                rlyForumPostAfterSelectModule1.setVisibility(View.GONE);
                initRecycleView(selectPicCountNum);
                break;
            case 3:
                tvForumPostAfterSelectModule.setText("正在使用模板3发帖");
                selectPicCountNum = 6;
                rlyForumPostAfterSelectModule1.setVisibility(View.GONE);
                initRecycleView(selectPicCountNum);
                break;
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
        rvForumPostAfterSelectImages.setLayoutManager(gridLayoutManager);
        rvForumPostAfterSelectImages.setAdapter(rvAdapter);
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
                case SELECT_IMAGE_REQUEST_IMAGES_HAND1:
                    List<String> imagePaths1 = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths1 == null){
                        mSelectImagesHand1.clear();
                        return;
                    }
                    if(imagePaths1.size() == 0){
                        mSelectImagesHand1.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImagesHand1.clear();
                    mSelectImagesHand1.addAll(imagePaths1);
                    rlyForumPostAfterSelectContent1Img.setVisibility(View.GONE);
                    ivForumPostAfterSelectContent1Img.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mSelectImagesHand1.get(0)).into(ivForumPostAfterSelectContent1Img);
                    break;
                case SELECT_IMAGE_REQUEST_IMAGES_HAND2:
                    List<String> imagePaths2 = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths2 == null){
                        mSelectImagesHand2.clear();
                        return;
                    }
                    if(imagePaths2.size() == 0){
                        mSelectImagesHand2.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImagesHand2.clear();
                    mSelectImagesHand2.addAll(imagePaths2);
                    rlyForumPostAfterSelectContent2Img.setVisibility(View.GONE);
                    ivForumPostAfterSelectContent2Img.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mSelectImagesHand2.get(0)).into(ivForumPostAfterSelectContent2Img);
                    break;
                case SELECT_IMAGE_REQUEST_IMAGES_HAND3:
                    List<String> imagePaths3 = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths3 == null){
                        mSelectImagesHand3.clear();
                        return;
                    }
                    if(imagePaths3.size() == 0){
                        mSelectImagesHand3.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImagesHand3.clear();
                    mSelectImagesHand3.addAll(imagePaths3);
                    rlyForumPostAfterSelectContent3Img.setVisibility(View.GONE);
                    ivForumPostAfterSelectContent3Img.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mSelectImagesHand3.get(0)).into(ivForumPostAfterSelectContent3Img);
                    break;
                case SELECT_IMAGE_REQUEST_IMAGES_HAND4:
                    List<String> imagePaths4 = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths4 == null){
                        mSelectImagesHand4.clear();
                        return;
                    }
                    if(imagePaths4.size() == 0){
                        mSelectImagesHand4.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImagesHand4.clear();
                    mSelectImagesHand4.addAll(imagePaths4);
                    rlyForumPostAfterSelectContent4Img.setVisibility(View.GONE);
                    ivForumPostAfterSelectContent4Img.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mSelectImagesHand4.get(0)).into(ivForumPostAfterSelectContent4Img);
                    break;
                case SELECT_IMAGE_REQUEST_IMAGES_HAND5:
                    List<String> imagePaths5 = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths5 == null){
                        mSelectImagesHand5.clear();
                        return;
                    }
                    if(imagePaths5.size() == 0){
                        mSelectImagesHand5.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImagesHand5.clear();
                    mSelectImagesHand5.addAll(imagePaths5);
                    rlyForumPostAfterSelectContent5Img.setVisibility(View.GONE);
                    ivForumPostAfterSelectContent5Img.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mSelectImagesHand5.get(0)).into(ivForumPostAfterSelectContent5Img);
                    break;
                case SELECT_IMAGE_REQUEST_IMAGES_HAND6:
                    List<String> imagePaths6 = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imagePaths6 == null){
                        mSelectImagesHand6.clear();
                        return;
                    }
                    if(imagePaths6.size() == 0){
                        mSelectImagesHand6.clear();
                        return;
                    }
//                    Toast.makeText(this, "size" + selectImages.size() + " path:" + selectImages.get(0), Toast.LENGTH_LONG).show();
                    mSelectImagesHand6.clear();
                    mSelectImagesHand6.addAll(imagePaths6);
                    rlyForumPostAfterSelectContent6Img.setVisibility(View.GONE);
                    ivForumPostAfterSelectContent6Img.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mSelectImagesHand6.get(0)).into(ivForumPostAfterSelectContent6Img);
                    break;
                    /*Glide.with(this)
                            .load(mSelectImages.get(0).getPath())
                            .into(civMineEditdataHead);*/

                case SELECT_AT_REQUEST:
                    if(data != null){
                        ArrayList<RObject> atLists = data.getParcelableArrayListExtra(ForumAtActivity.EXTRA_RESULT);
                        if(atLists == null){
                            return;
                        }
                        for (RObject item:atLists){
//                            Toast.makeText(this,""+item.getId(),Toast.LENGTH_LONG).show();
                            if(cbForumPostAfterSelectAutoHand.isChecked()){
                                retForumPostAfterSelectContent1.setObject(item);
                            }else {
                                retForumPostAfterSelectContent.setObject(item);
                            }
                        }
                    }
                    break;
                case SELECT_IMAGE_REQUEST_IMAGES_MODULE1:
                    List<String> imgModule1List = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if(imgModule1List == null){
                        mSelectImagesModule1.clear();
                        return;
                    }
                    if(imgModule1List.size() == 0){
                        mSelectImagesModule1.clear();
                        return;
                    }
                    mSelectImagesModule1.clear();
                    mSelectImagesModule1.addAll(imgModule1List);
                    rlyForumPostAfterSelectModule1Img.setVisibility(View.GONE);
                    ivForumPostAfterSelectModule1Img.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mSelectImagesModule1.get(0)).into(ivForumPostAfterSelectModule1Img);
                    break;
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

    private void selectImgModule1(){
        ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
//                .setImagePaths(mSelectImagesModule1)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(this, SELECT_IMAGE_REQUEST_IMAGES_MODULE1);
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

    private void selectImgHandActivity(int type){
        switch (type){
            case 1:
                ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(false)//设置是否展示视频
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
//                        .setImagePaths(mSelectImagesHand1)
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(this, SELECT_IMAGE_REQUEST_IMAGES_HAND1);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
                break;
            case 2:
                ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(false)//设置是否展示视频
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
//                        .setImagePaths(mSelectImagesHand2)
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(this, SELECT_IMAGE_REQUEST_IMAGES_HAND2);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
                break;
            case 3:
                ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(false)//设置是否展示视频
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
//                        .setImagePaths(mSelectImagesHand3)
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(this, SELECT_IMAGE_REQUEST_IMAGES_HAND3);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
                break;
            case 4:
                ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(false)//设置是否展示视频
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
//                        .setImagePaths(mSelectImagesHand4)
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(this, SELECT_IMAGE_REQUEST_IMAGES_HAND4);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
                break;
            case 5:
                ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(false)//设置是否展示视频
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
//                        .setImagePaths(mSelectImagesHand5)
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(this, SELECT_IMAGE_REQUEST_IMAGES_HAND5);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
                break;
            case 6:
                ImagePicker.getInstance()
//                .setTitle("标题")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(false)//设置是否展示视频
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
//                        .setImagePaths(mSelectImagesHand6)
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(this, SELECT_IMAGE_REQUEST_IMAGES_HAND6);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
                break;

        }
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

    private void tencentLoc(){
//        locationManager = TencentLocationManager.getInstance(this);
//        locationRequest = TencentLocationRequest.create()
//                .setInterval(1000*3) // 定位周期 (毫秒)
//                .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI) ;// 定位要求水准
//                ; // 是否使用缓存
        locationManager=TencentLocationManager.getInstance(this);
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
    public void onLocationChanged(TencentLocation location, int error, String reason) {
//        tvForumPostAfterSelectLoc.setText(arg0.getAddress());
        if (error == TencentLocation.ERROR_OK ) {
//            Log.e("maplocation", "location: " + location.getCity() + " " + location.getProvider() + " " + location.getBearing()+" "+reason);
            //当前位置信息
//            tvForumPostAfterSelectLoc.setText(arg0.getAddress());
            if(isLoc) {
//                rlyForumPostAfterSelectLoc.setVisibility(View.VISIBLE);
                llyForumPostAfterSelectLoc.setVisibility(View.VISIBLE);
                tvForumPostAfterSelectLoc.setText(location.getAddress());
            }
            lon = location.getLongitude()+"";
            lat = location.getLatitude()+"";
            province = location.getProvince();
            city = location.getCity();
            addr = location.getAddress();
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
//        mChangedListener = onLocationChangedListener;
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
        if(locationManager != null) {
            locationManager.requestLocationUpdates(locationRequest, this);
        }
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
            if(isLoc) {
                tvForumPostAfterSelectLoc.setText(location.getAddrStr());
            }
            lon = location.getLongitude()+"";
            lat = location.getLatitude()+"";
            province = location.getProvince();
            city = location.getCity();
            addr = location.getAddrStr();
            mLocClient.stop();
        }
    }
}
