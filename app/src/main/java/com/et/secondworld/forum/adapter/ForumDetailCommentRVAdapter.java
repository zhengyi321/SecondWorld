package com.et.secondworld.forum.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.R;
import com.et.secondworld.TecentMapViewActivity;
import com.et.secondworld.VisitOthersActivity;
import com.et.secondworld.VisitOthersShopActivity;
import com.et.secondworld.bean.AddCancelZanBean;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetCommentBean;
import com.et.secondworld.forum.ForumDetailCommentBackActivity;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.network.PraiseArticleNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.DeleteReportDialog;
import com.et.secondworld.widget.imageview.CircleImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class ForumDetailCommentRVAdapter extends RecyclerView.Adapter<ForumDetailCommentRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetCommentBean.CommentlistBean> dataList ;
    private String articleid = "";
    private Map<String,Object> map;
    private EditText editText;
    private Activity activity;
    private String account ="" ;
    public boolean isOpen ;
    private String thirdid="";
    private String articleAccount = "";

//    private TextView tvPublish;
//    private LinearLayout llyPublish;
    public ForumDetailCommentRVAdapter(List<GetCommentBean.CommentlistBean> dataList1){
        dataList = dataList1;
    }
    public ForumDetailCommentRVAdapter(List<GetCommentBean.CommentlistBean> dataList1,String articleid1, EditText editText1,Activity activity1,Map<String,Object> map1,String account1){
        articleid = articleid1;
        dataList = dataList1;
        editText = editText1;
        map = map1;
        activity = activity1;
        account = account1;
//        tvPublish = tvPublish1;
//        llyPublish = llyPublish1;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetCommentBean.CommentlistBean> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void setThirdid(String thirdid){
        this.thirdid= thirdid;
    }
    public void setArticleAccount(String articleAccount){
        this.articleAccount= articleAccount;
    }
    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, List<GetCommentBean.CommentlistBean> list) {
        dataList.addAll(position,list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForumDetailCommentRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_forum_detail_comment_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OneViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }



    public class OneViewHolder extends RecyclerView.ViewHolder {
        //        private ImageView ivImage;
        public View v;

        int good=0;
        int bad = 0;
        int praiseCount = 0;
        int isPraise = 0;
        private int pos = 0;
        String commentid = "";
        String time = "";
        String content = "";
        String nick = "";
        String head = "";
        String commentaccount = "";
        @BindView(R.id.lly_forum_detail_comment_rv_item)
        LinearLayout llyForumDetailCommentRVItem;
       /* @OnClick(R.id.lly_forum_detail_comment_rv_item)
        public void llyForumDetailCommentRVItemOnclick(){

//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                tvPublish.setVisibility(View.VISIBLE);
//                llyPublish.setVisibility(View.GONE);


//            }else {
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);

//                map.clear();
//                editText.setHint("火不火靠你了~");
//                tvPublish.setVisibility(View.GONE);
//                llyPublish.setVisibility(View.VISIBLE);
//            }

        }*/
        @RequiresApi(api = Build.VERSION_CODES.O)
        @OnClick(R.id.lly_forum_detail_comment_rv_item)
        public void llyForumDetailCommentRVItemOnLongclick(){
            if (editText == null){
                return;
            }
//            civForumDetailCommentHead.requestFocus();


//            Log.d("zzzz11",editText.requestFocus()+"");
//            if(editText.getFocusable())
//            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) ((Activity) v.getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
//            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View view = ((Activity) v.getContext()).getWindow().peekDecorView();
            if(imm.isActive(editText)){
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                isOpen = true;
                editText.clearFocus();
                civForumDetailCommentHead.setFocusable(true);
                civForumDetailCommentHead.setFocusableInTouchMode(true);
                civForumDetailCommentHead.requestFocus();
            }else {
                isOpen = false;
            }
            if (null != view) {
                if(isOpen) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    isOpen = false;
                }else {
                    DeleteReportDialog praiseDialog = new DeleteReportDialog(v.getContext()).Build.setCallBackListener(new DeleteReportDialog.OnFinishClickListener() {
                        @Override
                        public void isBack(Boolean isBack) {
                            if (isBack) {
                                if (editText == null) {
                                    return;
                                }
                                isOpen = true;
//            editText.setFocusable(true);
//            editText.setFocusableInTouchMode(true);
                                editText.requestFocus();
                                InputMethodManager imm = (InputMethodManager) ((Activity) v.getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                                Log.d("commentrv1", "" + account);
//            if(imm.isActive(editText)){
                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                llyPublish.setFocusableInTouchMode(true);
//                llyPublish.requestFocus();
                                map.put("toaccountid", dataList.get(pos).getAccount());
                                map.put("tonick", dataList.get(pos).getNick());
                                map.put("commentid", commentid);
                                editText.setHint("回复 @" + map.get("tonick") + ":");
                            }else {
                                dataList.remove(pos);
                                notifyDataSetChanged();
                            }
                        }
                    }).build(v.getContext(), content, commentaccount, nick,commentid,0);
                    praiseDialog.show();
                }
            }

//            editText.setFocusable(true);
//            editText.setFocusableInTouchMode(true);


//            Log.d("commentrv1",""+account);
//            if(imm.isActive(editText)){




            /*XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            if(account == null){
                account = "";
            }
            if(account.equals(commentaccount)) {
                new AlertDialog.Builder(activity).setTitle("确定删除评论吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Log.d("comment111","this is 确定");
                                //点击确定触发的事件
                                ArticleNetWork articleNetWork = new ArticleNetWork();
                                Map<String,Object> parammap = new HashMap<>();
                                parammap.put("commentid",commentid);
                                articleNetWork.deleteCommentToNet(parammap, new Observer<BaseBean>() {
                                    @Override
                                    public void onCompleted() {
//                                        Log.d("comment111","this is onCompleted");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
//                                        Log.d("comment111","this is onCompleted"+e);
                                    }

                                    @Override
                                    public void onNext(BaseBean baseBean) {
//                                        Log.d("comment111","this is onCompleted"+baseBean.getIssuccess());
                                        if(baseBean.getIssuccess().equals("1")) {
                                            Toast.makeText(v.getContext(), baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                                            dataList.remove(pos);
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //点击取消触发的事件
                            }
                        }).show();
            }*/
        }
        private long clickTime = 0;
        @BindView(R.id.civ_forum_detail_comment_head)
        CircleImageView civForumDetailCommentHead;
        @OnClick(R.id.civ_forum_detail_comment_head)
        public void civForumDetailCommentHeadOnclick(){

            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();


            String rule = dataList.get(pos).getRule();
            String accountid = dataList.get(pos).getAccount();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
            if(rule!= null && rule.equals("normal")){
                if(account != null && account.equals(accountid)){
                    return;
                }
                Intent intent = new Intent(v.getContext(), VisitOthersActivity.class);
                intent.putExtra("articleaccount",accountid);
                v.getContext().startActivity(intent);
            }

            if(rule!= null && rule.equals("shop")){
                if(shopid != null && shopid.equals(accountid)){
                    return;
                }
                Intent intent = new Intent(v.getContext(), VisitOthersShopActivity.class);
                intent.putExtra("shopid",accountid);
                v.getContext().startActivity(intent);
            }
            }
        }
        @BindView(R.id.tv_forum_detail_comment_nick)
        TextView tvForumDetailCommentNick;
        @BindView(R.id.tv_forum_detail_comment_time)
        TextView tvForumDetailCommentTime;
        @BindView(R.id.tv_forum_detail_comment_good)
        TextView tvForumDetailCommentGood;
        @BindView(R.id.tv_forum_detail_comment_account)
        TextView tvForumDetailCommentAccount;
        @BindView(R.id.ib_forum_detail_comment_good)
        ImageButton ibForumDetailCommentGood;
        @OnClick(R.id.lly_forum_detail_comment_good)
        public void tvForumDetailCommentGoodOnclick(){
           /* Map<String,Object> map= new HashMap<>();
            map.put("commentid",commentid);
//            good++;
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
//                        tvForumDetailCommentGood.setText(good+"");
                    }
                }
            });*/
            commentZan();
        }

        private void commentZan(){
            Map<String,Object> map= new HashMap<>();
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
//            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            map.put("articleid",articleid);
            map.put("account",account);
            map.put("commentid",commentid);
            map.put("good",praiseCount);
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
                        ibForumDetailCommentGood.setBackgroundResource(R.mipmap.zandown);
                        praiseCount++;
                        isPraise++;
                        tvForumDetailCommentGood.setText(praiseCount+"");
                        dataList.get(pos).setPraisecount(praiseCount);
                        dataList.get(pos).setIspraise(1);
                    }
                    if(addCancelZanBean.getIssuccess().equals("2")){
                        ibForumDetailCommentGood.setBackgroundResource(R.mipmap.zan);
                        if(praiseCount >0) {
                            praiseCount--;
                        }
                        isPraise = 0;
                        tvForumDetailCommentGood.setText(praiseCount+"");
                        dataList.get(pos).setPraisecount(praiseCount);
                        dataList.get(pos).setIspraise(0);
                    }
                }
            });
        }
       /* @BindView(R.id.tv_forum_detail_comment_bad)
        TextView tvForumDetailCommentBad;
        @OnClick(R.id.tv_forum_detail_comment_bad)
        public void tvForumDetailCommentBadOnclick(){
            Map<String,Object> map= new HashMap<>();
            map.put("commentid",commentid);
            bad++;
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
                        tvForumDetailCommentBad.setText(bad+"");
                    }
                }
            });
        }*/
        @BindView(R.id.tv_forum_detail_comment_content)
        TextView tvForumDetailCommentContent;
        @BindView(R.id.tv_forum_detail_comment_backlist)
        TextView tvForumDetailCommentBackList;
        @BindView(R.id.rly_forum_detail_comment_backlist)
        RelativeLayout rlyForumDetailCommentBackList;
        @SuppressLint("WrongConstant")
        @OnClick(R.id.rly_forum_detail_comment_backlist)
        public void tvForumDetailCommentBackListOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                if (account == null) {
                    return;
                }
                Intent intent = new Intent(v.getContext(), ForumDetailCommentBackActivity.class);
                intent.putExtra("head", head);
                intent.putExtra("nick", nick);
                intent.putExtra("content", content);
                intent.putExtra("articleid", articleid);
                intent.putExtra("time", time);
                intent.putExtra("good", praiseCount);
                intent.putExtra("isPraise", isPraise);
//            intent.putExtra("bad",bad);
                intent.putExtra("commentid", commentid);
                intent.putExtra("commentAccount", commentaccount);
                intent.putExtra("account", account);
                intent.putExtra("thirdid", thirdid);
                v.getContext().startActivity(intent);
            }
        }
        /*public void llyForumOneItemOnclick(){
//            Toast.makeText(v.getContext(),"this is one"+pos,Toast.LENGTH_LONG).show();

        }*/
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
             head = dataList.get(position).getHead();
             nick = dataList.get(position).getNick();
             content = dataList.get(position).getContent();
             time = dataList.get(position).getTime();
//             good = dataList.get(position).getGood();
//             bad = dataList.get(position).getBad();
             commentid = dataList.get(position).getCommentid();
            commentaccount = dataList.get(position).getAccount();
            praiseCount = dataList.get(position).getPraisecount();
            isPraise = dataList.get(position).getIspraise();
            int backlist = dataList.get(position).getBacksize();
            Glide.with(v).load(head).apply(new RequestOptions().fallback(R.mipmap.imghead)
                    .error(R.mipmap.imghead)).into(civForumDetailCommentHead);

            if(articleAccount != null && articleAccount.equals(commentaccount)){
                tvForumDetailCommentAccount.setVisibility(View.VISIBLE);
            }else {
                tvForumDetailCommentAccount.setVisibility(View.GONE);
            }
//            ImageLoader.getInstance().displayImage(head,civForumDetailCommentHead, ImageLoaderUtils.options);
            if(nick != null){
                tvForumDetailCommentNick.setText(nick);
            }
//            if(time != null){
////                time = time.split(" ")[0];
//                tvForumDetailCommentTime.setText(time);
//            }
            if(content != null){
                String str1 = content+"                 <font color= \"#BEBEBE\"><small>"+time+"</small></font>";
                tvForumDetailCommentContent.setText(Html.fromHtml(str1));
//                tvForumDetailCommentContent.setText(content+"     "+time);
            }
//            tvForumDetailCommentGood.setText(good+"");
//            tvForumDetailCommentBad.setText(bad+"");
            if(backlist == 0) {
                rlyForumDetailCommentBackList.setVisibility(View.GONE);
            }else {
                rlyForumDetailCommentBackList.setVisibility(View.VISIBLE);
                tvForumDetailCommentBackList.setText("查看" + backlist + "条回复");
            }
            tvForumDetailCommentGood.setText(praiseCount+"");
            if(isPraise >0){
                ibForumDetailCommentGood.setBackgroundResource(R.mipmap.zandown);
            }else {
                ibForumDetailCommentGood.setBackgroundResource(R.mipmap.zan);
            }
        }
    }


}
