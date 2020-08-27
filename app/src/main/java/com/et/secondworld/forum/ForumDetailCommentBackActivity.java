package com.et.secondworld.forum;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.R;
import com.et.secondworld.bean.AddCommentBackBean;
import com.et.secondworld.bean.AddCancelZanBean;
import com.et.secondworld.bean.GetCommentBackBean;
import com.et.secondworld.forum.adapter.ForumDetailCommentBackRVAdapter;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.network.PraiseArticleNetWork;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.emoji.Emoji;
import com.et.secondworld.widget.emoji.FaceFragment;
import com.et.secondworld.widget.imageview.CircleImageView;

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
 * @Date 2020/4/21
 **/
public class ForumDetailCommentBackActivity extends FragmentActivity implements FaceFragment.OnEmojiClickListener{
    private String head = "";
    private String nick = "";
    private String content="";
    private String time = "";
    private int good = 0;
    private int bad = 0;
    private String commentid = "";
    private String commentAccount = "";
    private String articleid = "";
    private int isPraise = 0;
    private List<GetCommentBackBean.BacklistBean> tempList = new ArrayList<>();
    private int isSort = 0;
    @BindView(R.id.et_popup_reply_all_comments_huifu)
    EditText etPopupReplyAllCommentsHuiFu;
    @BindView(R.id.rly_popup_reply_all_comments_close)
    RelativeLayout rlyPopupReplyAllCommentsClose;
    @OnClick(R.id.rly_popup_reply_all_comments_close)
    public void rlyPopupReplyAllCommentsCloseOnclick(){
        finish();
    }
    @BindView(R.id.rv_popup_reply_all_comments_back)
    RecyclerView rvPopupReplyAllCommentsBack;
    @BindView(R.id.tv_popup_reply_all_comments_content)
    TextView tvPopupReplyAllCommentsContent;
    @BindView(R.id.civ_popup_reply_all_comments_head)
    CircleImageView civPopupReplyAllCommentsHead;
    @BindView(R.id.tv_popup_reply_all_comments_all_back_size)
    TextView tvPopupReplyAllCommentsAllBackSize;
    @BindView(R.id.tv_popup_reply_all_comments_all_back_size_two)
    TextView tvPopupReplyAllCommentsAllBackSizeTwo;
    @BindView(R.id.tv_popup_reply_all_comments_nick)
    TextView tvPopupReplyAllCommentsNick;
    @BindView(R.id.tv_popup_reply_all_comments_time)
    TextView tvPopupReplyAllCommentsTime;
    @BindView(R.id.ib_popup_reply_all_comments_good)
    ImageButton ibPopupReplyAllCommentsGood;
    @OnClick(R.id.lly_popup_reply_all_comments_good)
    public void ibPopupReplyAllCommentsGoodOnclick(){
        commentZan();
    }
    @OnClick(R.id.rly_popup_reply_all_comments_sort)
    public void rlyPopupReplyAllCommentsSortOnclick(){
        if(isSort == 0) {
            List<GetCommentBackBean.BacklistBean> sortList = new ArrayList<>();
            for (int i = tempList.size() - 1; i >= 0; i--) {
                sortList.add(tempList.get(i));
            }
            rvAdapter.replaceAll(sortList);
            isSort = 1;
        }else {
            isSort = 0;
            rvAdapter.replaceAll(tempList);
        }
    }
    @BindView(R.id.tv_popup_reply_all_comments_good)
    TextView tvPopupReplyAllCommentsGood;
    @OnClick(R.id.tv_popup_reply_all_comments_good)
    public void tvPopupReplyAllCommentsGoodOnclick(){
        Map<String,Object> map= new HashMap<>();
        map.put("commentid",commentid);
        good++;
        map.put("good",good);
        map.put("bad",bad);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        forumPostNetWork.addArticleCommentZanCaiToNet(map, new Observer<AddCancelZanBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddCancelZanBean addZanCaiBean) {
                if(addZanCaiBean.getIssuccess().equals("1")){
                    tvPopupReplyAllCommentsGood.setText(good+"");
                }
            }
        });
    }
    @BindView(R.id.ib_popup_reply_all_comments_comment)
    ImageButton ibPopupReplyAllCommentsComment;
    @OnClick(R.id.ib_popup_reply_all_comments_comment)
    public void ibPopupReplyAllCommentsCommentOnclick(){
        /*etPopupReplyAllCommentsHuiFu.setFocusable(true);
        etPopupReplyAllCommentsHuiFu.setFocusableInTouchMode(true);
        etPopupReplyAllCommentsHuiFu.requestFocus();
//        etPopupReplyAllCommentsHuiFu.setText("hello");
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        showHideSoftInput();
        map.put("toaccountid",commentAccount);
        map.put("tonick", nick);*/
    }
    @BindView(R.id.lly_popup_reply_all_comments_comments)
    LinearLayout llyPopupReplyAllCommentsComments;
    @OnClick(R.id.lly_popup_reply_all_comments_comments)
    public void llyPopupReplyAllCommentsCommentsOnclick(){
        etPopupReplyAllCommentsHuiFu.setFocusable(true);
        etPopupReplyAllCommentsHuiFu.setFocusableInTouchMode(true);
        etPopupReplyAllCommentsHuiFu.requestFocus();
//        etPopupReplyAllCommentsHuiFu.setText("hello");
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        showHideSoftInput();
        map.put("toaccountid",commentAccount);
        map.put("tonick", nick);
        etPopupReplyAllCommentsHuiFu.setHint("回复 @"+map.get("tonick")+":");
    };
    @BindView(R.id.fg_popup_reply_all_comments)
    FrameLayout fgPopupReplyAllComments;
    @BindView(R.id.rly_popup_reply_all_comments_look)
    RelativeLayout rlyPopupReplyAllCommentsLook;
    @OnClick(R.id.rly_popup_reply_all_comments_look)
    public void rlyPopupReplyAllCommentsLookOnclick(){
        showHideEmoji();
    }
    @BindView(R.id.rly_popup_reply_all_comments_fabu)
    RelativeLayout rlyPopupReplyAllCommentsFaBu;
    private String account = "";
    @OnClick(R.id.rly_popup_reply_all_comments_fabu)
    public void rlyPopupReplyAllCommentsFaBuOnclick(){
//        mPopView.getContext().startActivity(new Intent(mPopView.getContext(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "aa"));
//        Toast.makeText(getBaseContext(),"tonick"+map.get("tonick"),Toast.LENGTH_LONG).show();
        String commentBackContent = etPopupReplyAllCommentsHuiFu.getText().toString();
        if(commentBackContent.equals("")||commentBackContent.isEmpty()){
            return;
        }
        if(thirdid == null){
            thirdid = "";
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("toaccountid",map.get("toaccountid"));
        paramMap.put("tonick",map.get("tonick"));


        paramMap.put("account",account);
        paramMap.put("content",etPopupReplyAllCommentsHuiFu.getText());
        paramMap.put("commentid",commentid);
        paramMap.put("platform","android");
        paramMap.put("thirdid",thirdid);
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
                    etPopupReplyAllCommentsHuiFu.setText("");
                    hideEmoji();
                    showHideSoftInput();
                    initCommentList();
                }
            }
        });

    }
    private int backsize = 0;

    private ForumDetailCommentBackRVAdapter rvAdapter;
    private List<GetCommentBackBean.BacklistBean> dataList = new ArrayList<>();
    private Map<String,Object> map = new HashMap<>();
    private List<GetCommentBackBean.BacklistBean> maxEightList = new ArrayList<>();
    private String thirdid = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.popup_reply_all_comments);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initFaceFragment();
        initTitle();
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

    private void initFaceFragment(){
        FaceFragment faceFragment = FaceFragment.Instance();
//        fgPopupReplyAllComments.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().add(R.id.fg_popup_reply_all_comments,faceFragment).commit();

    }
    private void initTitle(){
        Intent intent= getIntent();
//        backsize = intent.getIntExtra("backsize",0);
        head = intent.getStringExtra("head");
        nick = intent.getStringExtra("nick");
        content = intent.getStringExtra("content");
        time = intent.getStringExtra("time");
        good = intent.getIntExtra("good",0);
        isPraise = intent.getIntExtra("isPraise",0);
//        bad = intent.getIntExtra("bad",0);
        commentid = intent.getStringExtra("commentid");
        commentAccount = intent.getStringExtra("commentAccount");
        articleid = intent.getStringExtra("articleid");
        account = intent.getStringExtra("account");
        thirdid = intent.getStringExtra("thirdid");
        Glide.with(this).load(head).apply(new RequestOptions().fallback(R.mipmap.imghead)
                .error(R.mipmap.imghead)).into(civPopupReplyAllCommentsHead);
//        ImageLoader.getInstance().displayImage(head,civPopupReplyAllCommentsHead, ImageLoaderUtils.options);
        if(nick != null) {
            tvPopupReplyAllCommentsNick.setText(nick);
        }
        if(content != null) {
            tvPopupReplyAllCommentsContent.setText(content);
        }
        if(time != null) {
            tvPopupReplyAllCommentsTime.setText(time);
        }
        tvPopupReplyAllCommentsGood.setText(good+"");
        if(isPraise >0){
            ibPopupReplyAllCommentsGood.setBackgroundResource(R.mipmap.zandown);
        }else {
            ibPopupReplyAllCommentsGood.setBackgroundResource(R.mipmap.zan);
        }
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//
//        String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
//        if(shopid == null){
//            shopid="";
//        }
//        if(commentAccount == null){
//            commentAccount = "";
//        }
//        if(commentAccount.equals(shopid)){
//            account = shopid;
//        }else {
//            account = xcCacheManager.readCache(xcCacheSaveName.account);
//        }
    }
    private void initRV(){
        map.put("toaccountid",commentAccount);
        map.put("tonick", nick);
        rvAdapter = new ForumDetailCommentBackRVAdapter(dataList,etPopupReplyAllCommentsHuiFu,this,map,articleid,commentid,commentAccount);

        rvPopupReplyAllCommentsBack.setLayoutManager(new LinearLayoutManager(this));
        rvPopupReplyAllCommentsBack.setAdapter(rvAdapter);

        initCommentList();
    }

    private void initCommentList(){
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("commentid",commentid);
        forumPostNetWork.getCommentBackFromNet(map, new Observer<GetCommentBackBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetCommentBackBean getCommentBackBean) {
                if(getCommentBackBean.getIssuccess().equals("1")){
                    tvPopupReplyAllCommentsAllBackSize.setText("全部"+getCommentBackBean.getBacklist().size()+"条回复");
                    tvPopupReplyAllCommentsAllBackSizeTwo.setText("回复("+getCommentBackBean.getBacklist().size()+")");
                    tempList.clear();
                    tempList.addAll(getCommentBackBean.getBacklist());
                    List<GetCommentBackBean.BacklistBean> commentList = new ArrayList<>();
                    maxEightList.clear();
                    commentList.addAll(tempList);


                    if(commentList.size() > 0) {
                        for(int j = 0;j< (commentList.size() >=8?8:commentList.size());j++) {
                            int maxPos = 0;
                            GetCommentBackBean.BacklistBean maxItem = commentList.get(0);
                            for (int i = 0; i < commentList.size(); i++) {
                                Log.d("commentsort1","good:"+commentList.get(i).getPraisecount());
                                if (maxItem.getPraisecount() < commentList.get(i).getPraisecount()) {
                                    maxItem = commentList.get(i);
                                    maxPos = i;
                                }
                            }
//                            Log.d("commentsort1","maxPos:"+maxPos+" maxItem:"+maxItem.getPraisecount());
                            commentList.remove(maxPos);
                            maxEightList.add(maxItem);

                        }
//                        Log.d("commentsort1","commentlist:"+commentList.size()+" maxeightlist:"+maxEightList.size());

                        for(int i=0;i<commentList.size();i++){
                            maxEightList.add(commentList.get(i));
                        }

                    }
                    rvAdapter.replaceAll(maxEightList);
//                    rvAdapter.replaceAll(getCommentBackBean.getBacklist());

                }
            }
        });
    }

    private void commentZan(){
        Map<String,Object> map= new HashMap<>();
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        map.put("articleid",articleid);
        map.put("account",account);
        map.put("commentid",commentid);
        map.put("good",good);
        PraiseArticleNetWork praiseArticleNetWork = new PraiseArticleNetWork();
        praiseArticleNetWork.addCancelZanCommentToNet(map, new Observer<AddCancelZanBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddCancelZanBean addCancelZanBean) {
                if(addCancelZanBean.getIssuccess().equals("1")){
                    ibPopupReplyAllCommentsGood.setBackgroundResource(R.mipmap.zandown);
                    good++;
                    tvPopupReplyAllCommentsGood.setText(good+"");
                }
                if(addCancelZanBean.getIssuccess().equals("2")){
                    ibPopupReplyAllCommentsGood.setBackgroundResource(R.mipmap.zan);
                    if(good >0) {
                        good--;
                    }
                    tvPopupReplyAllCommentsGood.setText(good+"");
                }
//                initCommentList();
            }
        });
    }

    @Override
    public void onEmojiDelete() {
//        Toast.makeText(mPopView.getContext(),"this is emoji",Toast.LENGTH_SHORT).show();
        String text = etPopupReplyAllCommentsHuiFu.getText().toString();
        if (text.isEmpty()) {
            return;
        }
        if ("]".equals(text.substring(text.length() - 1, text.length()))) {
            int index = text.lastIndexOf("[");
            if (index == -1) {
                int action = KeyEvent.ACTION_DOWN;
                int code = KeyEvent.KEYCODE_DEL;
                KeyEvent event = new KeyEvent(action, code);
                etPopupReplyAllCommentsHuiFu.onKeyDown(KeyEvent.KEYCODE_DEL, event);
                displayTextView();
                return;
            }
            etPopupReplyAllCommentsHuiFu.getText().delete(index, text.length());
            displayTextView();
            return;
        }
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        etPopupReplyAllCommentsHuiFu.onKeyDown(KeyEvent.KEYCODE_DEL, event);
//        displayTextView();
    }
    private void displayTextView() {
//        try {
//            EmojiUtil.handlerEmojiText(tvPopupReplyAllCommentsContent, etPopupReplyAllCommentsHuiFu.getText().toString(), this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    @Override
    public void onEmojiClick(Emoji emoji) {
//        Toast.makeText(mPopView.getContext(),"this is emoji",Toast.LENGTH_SHORT).show();
        if (emoji != null) {
            int index = etPopupReplyAllCommentsHuiFu.getSelectionStart();
            Editable editable = etPopupReplyAllCommentsHuiFu.getEditableText();
            if (index < 0) {
                editable.append(emoji.getContent());
            } else {
                editable.insert(index, emoji.getContent());
            }
        }
        displayTextView();
    }

    private void showHideEmoji(){
        if(fgPopupReplyAllComments.getVisibility() == View.VISIBLE) {
            fgPopupReplyAllComments.setVisibility(View.GONE);
        }else {
            fgPopupReplyAllComments.setVisibility(View.VISIBLE);
        }
    }
    private void hideEmoji(){
        if(fgPopupReplyAllComments.getVisibility() == View.VISIBLE) {
            fgPopupReplyAllComments.setVisibility(View.GONE);
        }
    }
    private void showHideSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
